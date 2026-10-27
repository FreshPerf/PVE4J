package fr.freshperf.pve4j.request;

import fr.freshperf.pve4j.Proxmox;
import fr.freshperf.pve4j.entities.PveTask;
import fr.freshperf.pve4j.entities.PveTaskStatus;
import fr.freshperf.pve4j.throwable.ProxmoxAPIError;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.*;

/**
 * Asynchronous task manager for PVE4J.
 * Uses CompletableFuture for a modern API with configurable timeouts.
 *
 * <p>This manager handles long-running Proxmox tasks (like VM cloning, backups, etc.)
 * by polling their status in virtual threads until completion, with optional timeouts
 * and retry mechanisms.</p>
 *
 * @since 0.1.0
 */
public class ProxmoxAsyncTaskManager {

    /**
     * Maximum number of consecutive status-check failures tolerated before polling
     * gives up. Status checks are idempotent GETs, so retrying them is always safe.
     */
    private static final int MAX_CONSECUTIVE_POLL_FAILURES = 3;

    /**
     * Number of halvings of the check interval used for the first checks. Many
     * Proxmox tasks (config updates, stops, small resizes) finish well under a
     * second, so the first checks happen at 1/8, 1/4 and 1/2 of the interval,
     * then at every multiple of the interval. Check times are measured from the
     * start of the wait, so the extra early checks never delay the later ones;
     * this costs at most three extra status requests per task.
     */
    private static final int RAMP_UP_STEPS = 3;

    private final ProxmoxThreadManager threadManager;
    private final ScheduledExecutorService scheduler;

    /**
     * Creates a new asynchronous task manager.
     *
     * @param threadManager the thread manager to use
     */
    public ProxmoxAsyncTaskManager(ProxmoxThreadManager threadManager) {
        this.threadManager = threadManager;
        this.scheduler = Executors.newScheduledThreadPool(1, r -> {
            Thread thread = new Thread(r);
            thread.setName("pve4j-timeout-scheduler");
            thread.setDaemon(true);
            return thread;
        });
    }

    /**
     * Waits for task completion asynchronously.
     *
     * <p>The returned future completes with the terminal status whether the task
     * succeeded or failed — check {@link PveTaskStatus#isSuccessful()}. It only
     * completes exceptionally on polling errors (network, interruption, timeout).</p>
     *
     * @param proxmox the Proxmox instance to check status
     * @param task the task to monitor
     * @param checkInterval the interval between checks (the first checks happen at
     *                      1/8, 1/4 and 1/2 of it to notice short tasks quickly)
     * @param timeout the maximum timeout (null for no timeout)
     * @return a CompletableFuture containing the final status
     */
    public CompletableFuture<PveTaskStatus> waitForTaskAsync(
            Proxmox proxmox,
            PveTask task,
            Duration checkInterval,
            Duration timeout) {

        if (task == null || !hasValidUpid(task)) {
            return CompletableFuture.completedFuture(null);
        }

        CompletableFuture<PveTaskStatus> future = CompletableFuture.supplyAsync(
            () -> pollTaskStatus(proxmox, task, checkInterval, timeout),
            threadManager.getVirtualThreadExecutor()
        );

        if (timeout != null) {
            // The polling loop enforces the deadline itself and stops; this only
            // guarantees the caller's future completes punctually at the timeout.
            return addTimeout(future, timeout);
        }

        return future;
    }

    /**
     * Waits for task completion with a callback.
     *
     * <p>{@link TaskCompletionCallback#onComplete(PveTaskStatus)} is invoked for every
     * terminal status, successful or not. {@link TaskCompletionCallback#onError(Throwable)}
     * is invoked when polling itself fails (network error, timeout, interruption).</p>
     *
     * @param proxmox the Proxmox instance to check status
     * @param task the task to monitor
     * @param checkInterval the interval between checks (the first checks happen at
     *                      1/8, 1/4 and 1/2 of it to notice short tasks quickly)
     * @param timeout the maximum timeout (null for no timeout)
     * @param callback the callback to execute on completion
     * @return a CompletableFuture for task control
     */
    public CompletableFuture<Void> waitForTaskWithCallback(
            Proxmox proxmox,
            PveTask task,
            Duration checkInterval,
            Duration timeout,
            TaskCompletionCallback callback) {

        return waitForTaskAsync(proxmox, task, checkInterval, timeout)
            .handle((status, throwable) -> {
                if (callback == null) {
                    return null;
                }
                if (throwable != null) {
                    callback.onError(unwrap(throwable));
                } else if (status != null) {
                    callback.onComplete(status);
                }
                return null;
            });
    }

    /**
     * Polls task status until the task reaches a terminal state or the timeout expires.
     * Returns the terminal status whether the task succeeded or failed;
     * only throws on polling errors (network, interruption, timeout).
     *
     * <p>The deadline is checked inside the loop itself so that polling actually
     * stops on timeout — cancelling the enclosing future would not interrupt the
     * thread running this loop.</p>
     *
     * <p>A transient status-check failure does not abort a potentially long wait:
     * the check is retried at the next interval, and polling only gives up after
     * {@link #MAX_CONSECUTIVE_POLL_FAILURES} consecutive failures.</p>
     */
    private PveTaskStatus pollTaskStatus(Proxmox proxmox, PveTask task, Duration checkInterval, Duration timeout) {
        Instant deadline = timeout != null ? Instant.now().plus(timeout) : null;
        long startNanos = System.nanoTime();
        int consecutiveFailures = 0;
        try {
            while (!Thread.currentThread().isInterrupted()) {
                if (deadline != null && !Instant.now().isBefore(deadline)) {
                    throw new CompletionException(
                        new TimeoutException("Operation timed out after " + timeout)
                    );
                }

                try {
                    PveTaskStatus status = proxmox.getTaskStatus(task).execute();
                    consecutiveFailures = 0;

                    if (status.isCompleted()) {
                        return status;
                    }
                } catch (ProxmoxAPIError e) {
                    consecutiveFailures++;
                    if (consecutiveFailures >= MAX_CONSECUTIVE_POLL_FAILURES) {
                        throw new CompletionException("Task status polling failed", e);
                    }
                }

                // After a failed check, wait the full interval so that the failure
                // tolerance window is not shortened by the ramp-up.
                long sleepMillis = consecutiveFailures > 0
                    ? checkInterval.toMillis()
                    : pollDelayMillis(checkInterval.toMillis(),
                        TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos));
                if (deadline != null) {
                    long remainingMillis = Duration.between(Instant.now(), deadline).toMillis();
                    sleepMillis = Math.min(sleepMillis, Math.max(1, remainingMillis));
                }
                Thread.sleep(sleepMillis);
            }
            throw new InterruptedException("Task polling was interrupted");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new CompletionException("Task polling interrupted", e);
        }
    }

    /**
     * Returns the delay until the next check, given the time elapsed since the
     * wait started. Checks are due at interval/8, interval/4, interval/2, then at
     * every multiple of the interval; check times that have already passed (for
     * instance because a status request was slow) are skipped.
     */
    static long pollDelayMillis(long intervalMillis, long elapsedMillis) {
        if (intervalMillis <= 0) {
            return intervalMillis;
        }
        if (intervalMillis >= (1L << RAMP_UP_STEPS)) {
            for (int shift = RAMP_UP_STEPS; shift > 0; shift--) {
                long checkTime = intervalMillis >> shift;
                if (elapsedMillis < checkTime) {
                    return checkTime - elapsedMillis;
                }
            }
        }
        return intervalMillis - (elapsedMillis % intervalMillis);
    }

    /**
     * Unwraps a CompletionException to its cause for callback delivery.
     */
    private static Throwable unwrap(Throwable throwable) {
        if (throwable instanceof CompletionException && throwable.getCause() != null) {
            return throwable.getCause();
        }
        return throwable;
    }


    /**
     * Adds a timeout to a CompletableFuture.
     */
    private <T> CompletableFuture<T> addTimeout(CompletableFuture<T> future, Duration timeout) {
        CompletableFuture<T> timeoutFuture = new CompletableFuture<>();

        ScheduledFuture<?> scheduledTimeout = scheduler.schedule(() -> {
            timeoutFuture.completeExceptionally(
                new TimeoutException("Operation timed out after " + timeout)
            );
        }, timeout.toMillis(), TimeUnit.MILLISECONDS);

        future.whenComplete((result, throwable) -> {
            scheduledTimeout.cancel(false);
            if (throwable != null) {
                timeoutFuture.completeExceptionally(throwable);
            } else {
                timeoutFuture.complete(result);
            }
        });

        return timeoutFuture;
    }

    /**
     * Checks if a task has a valid UPID.
     */
    private boolean hasValidUpid(PveTask task) {
        if (task == null) {
            return false;
        }
        String upid = task.getUpid();
        return upid != null
            && upid.startsWith("UPID:")
            && task.getNode() != null
            && !task.getNode().isEmpty();
    }

    /**
     * Shuts down the scheduler.
     */
    public void shutdown() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}

