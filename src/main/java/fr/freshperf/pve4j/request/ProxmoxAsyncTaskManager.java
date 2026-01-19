package fr.freshperf.pve4j.request;

import fr.freshperf.pve4j.Proxmox;
import fr.freshperf.pve4j.entities.PveTask;
import fr.freshperf.pve4j.entities.PveTaskStatus;
import fr.freshperf.pve4j.throwable.ProxmoxAPIError;

import java.time.Duration;
import java.util.concurrent.*;

/**
 * Asynchronous task manager for Proxmox4J.
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
            thread.setName("proxmox4j-timeout-scheduler");
            thread.setDaemon(true);
            return thread;
        });
    }

    /**
     * Waits for task completion asynchronously.
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
            .thenAccept(status -> {
                if (callback != null && status != null) {
                    callback.onComplete(status);
                }
            })
            .exceptionally(throwable -> {
                System.err.println("Error in task completion callback: " + throwable.getMessage());
                return null;
            });
    }

    /**
     * Polls task status until completion.
     */
    private PveTaskStatus pollTaskStatus(Proxmox proxmox, PveTask task, Duration checkInterval) {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                PveTaskStatus status = proxmox.getTaskStatus(task).execute();

                if (status.isCompleted()) {
                    if (!status.isSuccessful()) {
                        String errorMsg = status.getExitstatus() != null
                            ? "Task failed with exit status: " + status.getExitstatus()
                            : "Task completed with errors";
                        throw new ProxmoxAPIError(errorMsg);
                    }
                    return status;
                }

                Thread.sleep(checkInterval.toMillis());
            }
            throw new InterruptedException("Task polling was interrupted");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new CompletionException("Task polling interrupted", e);
        } catch (ProxmoxAPIError e) {
            throw new CompletionException("Task failed", e);
        }
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

