package fr.freshperf.pve4j.request;

import fr.freshperf.pve4j.Proxmox;
import fr.freshperf.pve4j.entities.PveTask;
import fr.freshperf.pve4j.entities.PveTaskStatus;
import fr.freshperf.pve4j.throwable.ProxmoxAPIError;

import java.time.Duration;
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
     * @param checkInterval the interval between checks
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
            () -> pollTaskStatus(proxmox, task, checkInterval),
            threadManager.getVirtualThreadExecutor()
        );

        if (timeout != null) {
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
     * @param checkInterval the interval between checks
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
     * Polls task status until the task reaches a terminal state.
     * Returns the terminal status whether the task succeeded or failed;
     * only throws on polling errors (network, interruption).
     */
    private PveTaskStatus pollTaskStatus(Proxmox proxmox, PveTask task, Duration checkInterval) {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                PveTaskStatus status = proxmox.getTaskStatus(task).execute();

                if (status.isCompleted()) {
                    return status;
                }

                Thread.sleep(checkInterval.toMillis());
            }
            throw new InterruptedException("Task polling was interrupted");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new CompletionException("Task polling interrupted", e);
        } catch (ProxmoxAPIError e) {
            throw new CompletionException("Task status polling failed", e);
        }
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

