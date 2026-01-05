package fr.freshperf.proxmox4j.throwable;

public class ProxmoxAPIError extends Exception {

    private final int statusCode;
    private final String responseBody;
    private final String url;

    public ProxmoxAPIError(Throwable e) {
        super(e);
        this.statusCode = -1;
        this.responseBody = null;
        this.url = null;
    }

    public ProxmoxAPIError(String message) {
        super(message);
        this.statusCode = -1;
        this.responseBody = null;
        this.url = null;
    }

    public ProxmoxAPIError(String message, Throwable e) {
        super(message, e);
        this.statusCode = -1;
        this.responseBody = null;
        this.url = null;
    }

    public ProxmoxAPIError(String message, int statusCode, String responseBody, String url) {
        super(message);
        this.statusCode = statusCode;
        this.responseBody = responseBody;
        this.url = url;
    }

    public ProxmoxAPIError(String message, int statusCode, String responseBody, String url, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
        this.responseBody = responseBody;
        this.url = url;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

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
