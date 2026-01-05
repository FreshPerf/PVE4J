package fr.freshperf.proxmox4j.request;

import fr.freshperf.proxmox4j.throwable.ProxmoxAPIError;

import java.time.Duration;

public class ProxmoxRequest<T> {
    
    private final ProxmoxRequestExecutor<T> requestExecutor;
    private int retryCount = 0;
    private long retryDelayMs = 1000;
    
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
    
    public T execute() throws ProxmoxAPIError, InterruptedException {
        int attempts = 0;
        int maxAttempts = retryCount + 1;
        ProxmoxAPIError lastException = null;
        
        while (attempts < maxAttempts) {
            try {
                return requestExecutor.execute();
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
    
    private boolean shouldRetry(ProxmoxAPIError e) {
        int statusCode = e.getStatusCode();
        return statusCode == 429 || statusCode == 503 || statusCode >= 500;
    }
}

