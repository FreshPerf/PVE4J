package fr.freshperf.pve4j.throwable;

/**
 * Exception thrown when a Proxmox API request fails.
 * Contains HTTP status code, response body, and URL for debugging.
 */
public class ProxmoxAPIError extends Exception {

    private final int statusCode;
    private final String responseBody;
    private final String url;

    /**
     * Creates an error from another throwable.
     *
     * @param e the cause
     */
    public ProxmoxAPIError(Throwable e) {
        super(e);
        this.statusCode = -1;
        this.responseBody = null;
        this.url = null;
    }

    /**
     * Creates an error with a message.
     *
     * @param message the error message
     */
    public ProxmoxAPIError(String message) {
        super(message);
        this.statusCode = -1;
        this.responseBody = null;
        this.url = null;
    }

    /**
     * Creates an error with a message and cause.
     *
     * @param message the error message
     * @param e       the cause
     */
    public ProxmoxAPIError(String message, Throwable e) {
        super(message, e);
        this.statusCode = -1;
        this.responseBody = null;
        this.url = null;
    }

    /**
     * Creates an error with full HTTP response details.
     *
     * @param message      the error message
     * @param statusCode   the HTTP status code
     * @param responseBody the response body
     * @param url          the request URL
     */
    public ProxmoxAPIError(String message, int statusCode, String responseBody, String url) {
        super(message);
        this.statusCode = statusCode;
        this.responseBody = responseBody;
        this.url = url;
    }

    /**
     * Creates an error with full HTTP response details and a cause.
     *
     * @param message      the error message
     * @param statusCode   the HTTP status code
     * @param responseBody the response body
     * @param url          the request URL
     * @param cause        the underlying cause
     */
    public ProxmoxAPIError(String message, int statusCode, String responseBody, String url, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
        this.responseBody = responseBody;
        this.url = url;
    }

    /**
     * Returns the HTTP status code (-1 if not available).
     *
     * @return the status code
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Returns the response body (may be null).
     *
     * @return the response body
     */
    public String getResponseBody() {
        return responseBody;
    }

    /**
     * Returns the request URL (may be null).
     *
     * @return the URL
     */
    public String getUrl() {
        return url;
    }

    @Override
    public String getMessage() {
        if (statusCode > 0) {
            return String.format("%s | Status: %d | URL: %s | Response: %s",
                super.getMessage(), statusCode, url,
                responseBody != null && responseBody.length() > 200
                    ? responseBody.substring(0, 200) + "..."
                    : responseBody);
        }
        return super.getMessage();
    }
}
