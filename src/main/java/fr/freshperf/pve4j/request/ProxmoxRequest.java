package fr.freshperf.pve4j.request;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import fr.freshperf.pve4j.Proxmox;
import fr.freshperf.pve4j.entities.PveTask;
import fr.freshperf.pve4j.entities.PveTaskStatus;
import fr.freshperf.pve4j.throwable.ProxmoxAPIError;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

/**
 * Represents a Proxmox API request with fluent configuration options.
 *
 * <p>This class provides a fluent interface for configuring request behavior including
 * retry logic, task monitoring, timeouts, and asynchronous callbacks. It leverages
 * virtual threads (Java 21+) for efficient handling of concurrent operations.</p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * proxmox.getNodes()
 *     .get("node1")
 *     .getQemu()
 *     .get("100")
 *     .start()
 *     .retry(3)
 *     .retryDelay(Duration.ofSeconds(2))
 *     .taskTimeout(Duration.ofMinutes(5))
 *     .waitForCompletion(proxmox)
 *     .execute();
 * }</pre>
 *
 * @param <T> the return type of the request
 */
public class ProxmoxRequest<T> {
    
    private static ProxmoxThreadManager defaultThreadManager;
    private static ProxmoxAsyncTaskManager defaultAsyncTaskManager;

    static {
        // Initialize default thread managers
        defaultThreadManager = new ProxmoxThreadManager();
        defaultAsyncTaskManager = new ProxmoxAsyncTaskManager(defaultThreadManager);
    }

    private final ProxmoxRequestExecutor<T> requestExecutor;
    private int retryCount = 0;
    private Duration retryDelay = Duration.ofSeconds(1);
    private Duration taskCheckDelay = Duration.ofSeconds(1);
    private Duration taskTimeout = null; // No timeout by default
    private TaskCompletionCallback taskCompletionCallback;
    private Proxmox proxmoxForCallback;
    private Proxmox proxmoxForWait;
    
    /**
     * Creates a new ProxmoxRequest with the given executor.
     *
     * @param requestExecutor the executor that performs the actual API call
     */
    public ProxmoxRequest(ProxmoxRequestExecutor<T> requestExecutor) {
        this.requestExecutor = requestExecutor;
    }
    
    /**
     * Configures the global thread manager for all requests.
     * Use this if you want fine-grained control over thread lifecycle.
     *
     * @param threadManager the custom thread manager
     * @param asyncTaskManager the custom asynchronous task manager
     */
    public static void setThreadManagers(ProxmoxThreadManager threadManager, ProxmoxAsyncTaskManager asyncTaskManager) {
        if (defaultThreadManager != null) {
            defaultThreadManager.shutdown();
        }
        if (defaultAsyncTaskManager != null) {
            defaultAsyncTaskManager.shutdown();
        }
        defaultThreadManager = threadManager;
        defaultAsyncTaskManager = asyncTaskManager;
    }

    /**
     * Shuts down all thread managers gracefully.
     * Should be called before application shutdown.
     */
    public static void shutdownGlobalThreadManagers() {
        if (defaultAsyncTaskManager != null) {
            defaultAsyncTaskManager.shutdown();
        }
        if (defaultThreadManager != null) {
            defaultThreadManager.shutdown();
        }
    }

    /**
     * Configures the number of retry attempts for failed requests.
     *
     * @param attempts the number of retry attempts (must be >= 0)
     * @return this instance for method chaining
     */
    public ProxmoxRequest<T> retry(int attempts) {
        this.retryCount = Math.max(0, attempts);
        return this;
    }
    
    /**
     * Configures the delay between retry attempts.
     *
     * @param milliseconds the delay in milliseconds
     * @return this instance for method chaining
     */
    public ProxmoxRequest<T> retryDelay(long milliseconds) {
        this.retryDelay = Duration.ofMillis(Math.max(0, milliseconds));
        return this;
    }
    
    /**
     * Configures the delay between retry attempts.
     *
     * @param duration the delay duration
     * @return this instance for method chaining
     */
    public ProxmoxRequest<T> retryDelay(Duration duration) {
        this.retryDelay = duration != null ? duration : Duration.ofMillis(0);
        return this;
    }

    /**
     * Configures the delay between task status checks.
     *
     * @param milliseconds the delay in milliseconds
     * @return this instance for method chaining
     */
    public ProxmoxRequest<T> taskCheckDelay(long milliseconds) {
        this.taskCheckDelay = Duration.ofMillis(Math.max(0, milliseconds));
        return this;
    }

    /**
     * Configures the delay between task status checks.
     *
     * @param duration the delay duration
     * @return this instance for method chaining
     */
    public ProxmoxRequest<T> taskCheckDelay(Duration duration) {
        this.taskCheckDelay = duration != null ? duration : Duration.ofMillis(0);
        return this;
    }

    /**
     * Configures a timeout for task completion waiting.
     *
     * @param timeout the maximum timeout duration
     * @return this instance for method chaining
     */
    public ProxmoxRequest<T> taskTimeout(Duration timeout) {
        this.taskTimeout = timeout;
        return this;
    }

    /**
     * Configures a callback to be executed when the task completes (asynchronously).
     * The callback runs in a separate virtual thread and does not block the main execution.
     *
     * @param callback the callback to execute on completion
     * @param proxmox the Proxmox instance to use for status checks
     * @return this instance for method chaining
     */
    public ProxmoxRequest<T> onCompletion(TaskCompletionCallback callback, Proxmox proxmox) {
        this.taskCompletionCallback = callback;
        this.proxmoxForCallback = proxmox;
        return this;
    }

    /**
     * Configures the request to wait (synchronously) for task completion before returning.
     * This blocks the calling thread until the task finishes or times out.
     *
     * @param proxmox the Proxmox instance to use for status checks
     * @return this instance for method chaining
     */
    public ProxmoxRequest<T> waitForCompletion(Proxmox proxmox) {
        this.proxmoxForWait = proxmox;
        return this;
    }

    /**
     * Waits for task completion synchronously.
     *
     * @param proxmox the Proxmox instance
     * @param task the task to wait for
     * @return the completed task
     * @throws ProxmoxAPIError if the task fails
     * @throws InterruptedException if the thread is interrupted
     */
    public static PveTask waitForCompletion(Proxmox proxmox, PveTask task) throws ProxmoxAPIError, InterruptedException {
        return waitForCompletion(proxmox, task, Duration.ofSeconds(1), null);
    }

    /**
     * Waits for task completion synchronously with a custom check delay.
     *
     * @param proxmox the Proxmox instance
     * @param task the task to wait for
     * @param checkDelayMs the delay between status checks in milliseconds
     * @return the completed task
     * @throws ProxmoxAPIError if the task fails
     * @throws InterruptedException if the thread is interrupted
     */
    public static PveTask waitForCompletion(Proxmox proxmox, PveTask task, long checkDelayMs) throws ProxmoxAPIError, InterruptedException {
        return waitForCompletion(proxmox, task, Duration.ofMillis(checkDelayMs), null);
    }

    /**
     * Waits for task completion synchronously with a timeout.
     *
     * @param proxmox the Proxmox instance
     * @param task the task to wait for
     * @param checkDelay the delay between status checks
     * @param timeout the maximum timeout (null for no timeout)
     * @return the completed task
     * @throws ProxmoxAPIError if the task fails or times out
     * @throws InterruptedException if the thread is interrupted
     */
    public static PveTask waitForCompletion(Proxmox proxmox, PveTask task, Duration checkDelay, Duration timeout)
            throws ProxmoxAPIError, InterruptedException {
        if (task == null || !hasValidUpid(task)) {
            return task;
        }
        
        try {
            CompletableFuture<PveTaskStatus> future = defaultAsyncTaskManager.waitForTaskAsync(
                proxmox, task, checkDelay, timeout
            );

            future.join();
            return task;
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause instanceof ProxmoxAPIError) {
                throw (ProxmoxAPIError) cause;
            } else if (cause instanceof InterruptedException) {
                throw (InterruptedException) cause;
            } else if (cause instanceof TimeoutException) {
                throw new ProxmoxAPIError("Task timeout exceeded: " + timeout);
            } else {
                throw new ProxmoxAPIError("Failed to wait for task completion: " + e.getMessage());
            }
        }
    }

    /**
     * Executes the request with configured retry, timeout, and task monitoring settings.
     *
     * <p>If a Proxmox task is returned and {@link #waitForCompletion(Proxmox)} was called,
     * this method blocks until the task completes or times out.</p>
     *
     * <p>If {@link #onCompletion(TaskCompletionCallback, Proxmox)} was configured, the callback
     * executes asynchronously in a virtual thread without blocking this call.</p>
     *
     * @return the result of the request execution
     * @throws ProxmoxAPIError if the request fails or times out
     * @throws InterruptedException if the thread is interrupted
     */
    public T execute() throws ProxmoxAPIError, InterruptedException {
        int attempts = 0;
        int maxAttempts = retryCount + 1;
        ProxmoxAPIError lastException = null;
        
        while (attempts < maxAttempts) {
            try {
                T result = requestExecutor.execute();
                
                PveTask task = extractTaskFromResponse(result);
                
                if (task != null && hasValidUpid(task)) {
                    if (proxmoxForWait != null) {
                        waitForTaskCompletion(proxmoxForWait, task, taskCheckDelay, taskTimeout);
                        return result;
                    }
                    
                    if (taskCompletionCallback != null && proxmoxForCallback != null) {
                        defaultAsyncTaskManager.waitForTaskWithCallback(
                            proxmoxForCallback,
                            task,
                            taskCheckDelay,
                            taskTimeout,
                            taskCompletionCallback
                        );
                    }
                }
                
                return result;
            } catch (ProxmoxAPIError e) {
                lastException = e;
                attempts++;
                
                if (attempts >= maxAttempts) {
                    break;
                }
                
                if (!shouldRetry(e)) {
                    throw e;
                }
                
                try {
                    Thread.sleep(retryDelay.toMillis());
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw ie;
                }
            }
        }
        
        throw lastException != null ? lastException 
            : new ProxmoxAPIError("Request failed after " + maxAttempts + " attempts");
    }
    
    /**
     * Waits for task completion internally (blocking).
     */
    private static void waitForTaskCompletion(Proxmox proxmox, PveTask task, Duration checkDelay, Duration timeout)
            throws ProxmoxAPIError, InterruptedException {
        try {
            CompletableFuture<PveTaskStatus> future = defaultAsyncTaskManager.waitForTaskAsync(
                proxmox, task, checkDelay, timeout
            );
            future.join();
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause instanceof ProxmoxAPIError) {
                throw (ProxmoxAPIError) cause;
            } else if (cause instanceof InterruptedException) {
                throw (InterruptedException) cause;
            } else if (cause instanceof TimeoutException) {
                throw new ProxmoxAPIError("Task timeout exceeded: " + timeout);
            } else {
                throw new ProxmoxAPIError("Failed to wait for task completion: " + e.getMessage());
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    private static PveTask extractTaskFromResponse(Object result) {
        if (result == null) {
            return null;
        }
        
        if (result instanceof PveTask) {
            PveTask task = (PveTask) result;
            if (task.getUpid() != null && task.getNode() == null) {
                task.setUpid(task.getUpid());
            }
            return task;
        }
        
        if (result instanceof String) {
            String str = (String) result;
            if (str.startsWith("UPID:")) {
                return new PveTask(str);
            }
        }
        
        if (result instanceof JsonObject) {
            JsonObject jsonObject = (JsonObject) result;
            
            if (jsonObject.has("data") && jsonObject.get("data").isJsonPrimitive()) {
                JsonPrimitive dataPrimitive = jsonObject.get("data").getAsJsonPrimitive();
                if (dataPrimitive.isString()) {
                    String upid = dataPrimitive.getAsString();
                    if (upid.startsWith("UPID:")) {
                        return new PveTask(upid);
                    }
                }
            }
            
            if (jsonObject.has("upid") && jsonObject.get("upid").isJsonPrimitive()) {
                JsonPrimitive upidPrimitive = jsonObject.get("upid").getAsJsonPrimitive();
                if (upidPrimitive.isString()) {
                    String upid = upidPrimitive.getAsString();
                    if (upid.startsWith("UPID:")) {
                        return new PveTask(upid);
                    }
                }
            }
        }
        
        String resultString = result.toString();
        if (resultString.contains("UPID:")) {
            int upidStart = resultString.indexOf("UPID:");
            int upidEnd = resultString.indexOf('"', upidStart);
            if (upidEnd == -1) {
                upidEnd = Math.min(resultString.length(), upidStart + 200);
            }
            String upid = resultString.substring(upidStart, upidEnd);
            if (upid.startsWith("UPID:")) {
                return new PveTask(upid);
            }
        }
        
        return null;
    }
    
    private static boolean hasValidUpid(PveTask task) {
        if (task == null) {
            return false;
        }
        String upid = task.getUpid();
        return upid != null 
            && upid.startsWith("UPID:") 
            && task.getNode() != null 
            && !task.getNode().isEmpty();
    }
    
    private boolean shouldRetry(ProxmoxAPIError e) {
        int statusCode = e.getStatusCode();
        return statusCode == 429 || statusCode == 503 || statusCode >= 500;
    }
}

