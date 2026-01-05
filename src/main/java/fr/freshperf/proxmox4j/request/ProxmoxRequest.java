package fr.freshperf.proxmox4j.request;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import fr.freshperf.proxmox4j.Proxmox;
import fr.freshperf.proxmox4j.entities.PveTask;
import fr.freshperf.proxmox4j.entities.PveTaskStatus;
import fr.freshperf.proxmox4j.throwable.ProxmoxAPIError;

import java.time.Duration;

public class ProxmoxRequest<T> {
    
    private final ProxmoxRequestExecutor<T> requestExecutor;
    private int retryCount = 0;
    private long retryDelayMs = 1000;
    private long taskCheckDelayMs = 1000;
    private TaskCompletionCallback taskCompletionCallback;
    private Proxmox proxmoxForCallback;
    private Proxmox proxmoxForWait;
    
    public ProxmoxRequest(ProxmoxRequestExecutor<T> requestExecutor) {
        this.requestExecutor = requestExecutor;
    }
    
    public ProxmoxRequest<T> retry(int attempts) {
        this.retryCount = Math.max(0, attempts);
        return this;
    }
    
    public ProxmoxRequest<T> retryDelay(long milliseconds) {
        this.retryDelayMs = Math.max(0, milliseconds);
        return this;
    }
    
    public ProxmoxRequest<T> retryDelay(Duration duration) {
        this.retryDelayMs = Math.max(0, duration.toMillis());
        return this;
    }

    public ProxmoxRequest<T> taskCheckDelay(long milliseconds) {
        this.taskCheckDelayMs = Math.max(0, milliseconds);
        return this;
    }

    public ProxmoxRequest<T> taskCheckDelay(Duration duration) {
        this.taskCheckDelayMs = Math.max(0, duration.toMillis());
        return this;
    }

    public ProxmoxRequest<T> onCompletion(TaskCompletionCallback callback, Proxmox proxmox) {
        this.taskCompletionCallback = callback;
        this.proxmoxForCallback = proxmox;
        return this;
    }

    public ProxmoxRequest<T> waitForCompletion(Proxmox proxmox) {
        this.proxmoxForWait = proxmox;
        return this;
    }

    public static PveTask waitForCompletion(Proxmox proxmox, PveTask task) throws ProxmoxAPIError, InterruptedException {
        return waitForCompletion(proxmox, task, 1000);
    }

    public static PveTask waitForCompletion(Proxmox proxmox, PveTask task, long checkDelayMs) throws ProxmoxAPIError, InterruptedException {
        if (task == null || !hasValidUpid(task)) {
            return task;
        }
        
        while (true) {
            PveTaskStatus status = proxmox.getTaskStatus(task).execute();
            
            if (status.isCompleted()) {
                if (!status.isSuccessful()) {
                    String errorMsg = status.getExitstatus() != null 
                        ? "Task failed with exit status: " + status.getExitstatus()
                        : "Task completed with errors";
                    throw new ProxmoxAPIError(errorMsg);
                }
                return task;
            }
            
            Thread.sleep(checkDelayMs);
        }
    }

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
                        waitForTaskCompletion(proxmoxForWait, task, taskCheckDelayMs, null);
                        return result;
                    }
                    
                    if (taskCompletionCallback != null && proxmoxForCallback != null) {
                        TaskCompletionCallback callback = this.taskCompletionCallback;
                        long delay = this.taskCheckDelayMs;
                        Proxmox proxmox = this.proxmoxForCallback;
                        
                        new Thread(() -> {
                            try {
                                waitForTaskCompletion(proxmox, task, delay, callback);
                            } catch (ProxmoxAPIError | InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }).start();
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
                    Thread.sleep(retryDelayMs);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw ie;
                }
            }
        }
        
        throw lastException != null ? lastException 
            : new ProxmoxAPIError("Request failed after " + maxAttempts + " attempts");
    }
    
    private static void waitForTaskCompletion(Proxmox proxmox, PveTask task, long checkDelayMs, TaskCompletionCallback callback) throws ProxmoxAPIError, InterruptedException {
        while (true) {
            PveTaskStatus status = proxmox.getTaskStatus(task).execute();
            
            if (status.isCompleted()) {
                if (callback != null) {
                    callback.onComplete(status);
                }
                if (!status.isSuccessful()) {
                    String errorMsg = status.getExitstatus() != null 
                        ? "Task failed with exit status: " + status.getExitstatus()
                        : "Task completed with errors";
                    throw new ProxmoxAPIError(errorMsg);
                }
                return;
            }
            
            Thread.sleep(checkDelayMs);
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

