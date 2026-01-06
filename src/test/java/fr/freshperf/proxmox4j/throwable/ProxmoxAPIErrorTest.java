package fr.freshperf.proxmox4j.throwable;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("ProxmoxAPIError Tests")
class ProxmoxAPIErrorTest {

    @Test
    @DisplayName("Should create ProxmoxAPIError with all parameters")
    void shouldCreateProxmoxAPIErrorWithAllParameters() {
        String message = "API Error occurred";
        int statusCode = 404;
        String responseBody = "{\"error\": \"Not found\"}";
        String url = "https://proxmox.local:8006/api2/json/nodes";

        ProxmoxAPIError error = new ProxmoxAPIError(message, statusCode, responseBody, url);

        assertThat(error).isNotNull();
        assertThat(error.getMessage()).contains(message);
        assertThat(error.getStatusCode()).isEqualTo(statusCode);
        assertThat(error.getResponseBody()).isEqualTo(responseBody);
        assertThat(error.getUrl()).isEqualTo(url);
    }

    @Test
    @DisplayName("Should handle 400 Bad Request error")
    void shouldHandle400BadRequestError() {
        ProxmoxAPIError error = new ProxmoxAPIError("Bad Request", 400, "Invalid parameters", "http://test.com");

        assertThat(error.getStatusCode()).isEqualTo(400);
        assertThat(error.getMessage()).contains("Bad Request");
    }

    @Test
    @DisplayName("Should handle 401 Unauthorized error")
    void shouldHandle401UnauthorizedError() {
        ProxmoxAPIError error = new ProxmoxAPIError("Unauthorized", 401, "Authentication required", "http://test.com");

        assertThat(error.getStatusCode()).isEqualTo(401);
        assertThat(error.getMessage()).contains("Unauthorized");
    }

    @Test
    @DisplayName("Should handle 403 Forbidden error")
    void shouldHandle403ForbiddenError() {
        ProxmoxAPIError error = new ProxmoxAPIError("Forbidden", 403, "Access denied", "http://test.com");

        assertThat(error.getStatusCode()).isEqualTo(403);
        assertThat(error.getMessage()).contains("Forbidden");
    }

    @Test
    @DisplayName("Should handle 404 Not Found error")
    void shouldHandle404NotFoundError() {
        ProxmoxAPIError error = new ProxmoxAPIError("Not Found", 404, "Resource not found", "http://test.com");

        assertThat(error.getStatusCode()).isEqualTo(404);
        assertThat(error.getMessage()).contains("Not Found");
    }

    @Test
    @DisplayName("Should handle 500 Internal Server Error")
    void shouldHandle500InternalServerError() {
        ProxmoxAPIError error = new ProxmoxAPIError("Internal Server Error", 500, "Server error", "http://test.com");

        assertThat(error.getStatusCode()).isEqualTo(500);
        assertThat(error.getMessage()).contains("Internal Server Error");
    }

    @Test
    @DisplayName("Should handle 503 Service Unavailable error")
    void shouldHandle503ServiceUnavailableError() {
        ProxmoxAPIError error = new ProxmoxAPIError("Service Unavailable", 503, "Temporarily unavailable", "http://test.com");

        assertThat(error.getStatusCode()).isEqualTo(503);
        assertThat(error.getMessage()).contains("Service Unavailable");
    }

    @Test
    @DisplayName("Should handle null response body")
    void shouldHandleNullResponseBody() {
        ProxmoxAPIError error = new ProxmoxAPIError("Error with no body", 500, null, "http://test.com");

        assertThat(error.getResponseBody()).isNull();
    }

    @Test
    @DisplayName("Should handle empty response body")
    void shouldHandleEmptyResponseBody() {
        ProxmoxAPIError error = new ProxmoxAPIError("Error with empty body", 500, "", "http://test.com");

        assertThat(error.getResponseBody()).isEmpty();
    }

    @Test
    @DisplayName("Should be throwable as exception")
    void shouldBeThrowableAsException() {
        ProxmoxAPIError error = new ProxmoxAPIError("Test error", 500, "Test body", "http://test.com");

        assertThatThrownBy(() -> {
            throw error;
        })
        .isInstanceOf(ProxmoxAPIError.class)
        .isInstanceOf(Exception.class)
        .hasMessageContaining("Test error");
    }

    @Test
    @DisplayName("Should preserve stack trace")
    void shouldPreserveStackTrace() {
        ProxmoxAPIError error = new ProxmoxAPIError("Test error", 500, "Test body", "http://test.com");

        assertThat(error.getStackTrace()).isNotEmpty();
    }

    @Test
    @DisplayName("Should create error with simple message constructor")
    void shouldCreateErrorWithSimpleMessageConstructor() {
        ProxmoxAPIError error = new ProxmoxAPIError("Simple error message");

        assertThat(error.getMessage()).isEqualTo("Simple error message");
        assertThat(error.getStatusCode()).isEqualTo(-1);
        assertThat(error.getResponseBody()).isNull();
        assertThat(error.getUrl()).isNull();
    }

    @Test
    @DisplayName("Should create error with throwable constructor")
    void shouldCreateErrorWithThrowableConstructor() {
        Exception cause = new Exception("Root cause");
        ProxmoxAPIError error = new ProxmoxAPIError(cause);

        assertThat(error.getCause()).isEqualTo(cause);
        assertThat(error.getStatusCode()).isEqualTo(-1);
    }

    @Test
    @DisplayName("Should create error with message and throwable")
    void shouldCreateErrorWithMessageAndThrowable() {
        Exception cause = new Exception("Root cause");
        ProxmoxAPIError error = new ProxmoxAPIError("Wrapper message", cause);

        assertThat(error.getMessage()).isEqualTo("Wrapper message");
        assertThat(error.getCause()).isEqualTo(cause);
        assertThat(error.getStatusCode()).isEqualTo(-1);
    }

    @Test
    @DisplayName("Should truncate long response body in message")
    void shouldTruncateLongResponseBodyInMessage() {
        String longBody = "x".repeat(250);
        ProxmoxAPIError error = new ProxmoxAPIError("Error", 500, longBody, "http://test.com");

        String message = error.getMessage();
        assertThat(message).contains("...");
        assertThat(message.length()).isLessThan(longBody.length() + 100);
    }
}

