package fr.freshperf.pve4j.request;

import fr.freshperf.pve4j.throwable.ProxmoxAPIError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.*;

@DisplayName("ProxmoxRequest Tests")
class ProxmoxRequestTest {

    @Test
    @DisplayName("Should execute request successfully")
    void shouldExecuteRequestSuccessfully() throws ProxmoxAPIError, InterruptedException {
        String expectedResult = "Success";
        ProxmoxRequest<String> request = new ProxmoxRequest<>(() -> expectedResult);

        String result = request.execute();

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("Should propagate ProxmoxAPIError")
    void shouldPropagateProxmoxAPIError() {
        ProxmoxAPIError expectedError = new ProxmoxAPIError("Test error", 500, "Error body", "http://test.com");
        ProxmoxRequest<String> request = new ProxmoxRequest<>(() -> {
            throw expectedError;
        });

        assertThatThrownBy(() -> request.execute())
            .isInstanceOf(ProxmoxAPIError.class)
            .hasMessageContaining("Test error");
    }

    @Test
    @DisplayName("Should propagate InterruptedException")
    void shouldPropagateInterruptedException() {
        ProxmoxRequest<String> request = new ProxmoxRequest<>(() -> {
            throw new InterruptedException("Thread interrupted");
        });

        assertThatThrownBy(() -> request.execute())
            .isInstanceOf(InterruptedException.class)
            .hasMessage("Thread interrupted");
    }

    @Test
    @DisplayName("Should retry on failure")
    void shouldRetryOnFailure() throws ProxmoxAPIError, InterruptedException {
        AtomicInteger attempts = new AtomicInteger(0);
        ProxmoxRequest<String> request = new ProxmoxRequest<>(() -> {
            int attempt = attempts.incrementAndGet();
            if (attempt < 3) {
                throw new ProxmoxAPIError("Temporary error", 503, "Service unavailable", "http://test.com");
            }
            return "Success after retries";
        });

        String result = request.retry(3).execute();

        assertThat(result).isEqualTo("Success after retries");
        assertThat(attempts.get()).isEqualTo(3);
    }

    @Test
    @DisplayName("Should fail after max retries")
    void shouldFailAfterMaxRetries() {
        AtomicInteger attempts = new AtomicInteger(0);
        ProxmoxRequest<String> request = new ProxmoxRequest<>(() -> {
            attempts.incrementAndGet();
            throw new ProxmoxAPIError("Persistent error", 500, "Internal error", "http://test.com");
        });

        assertThatThrownBy(() -> request.retry(3).execute())
            .isInstanceOf(ProxmoxAPIError.class)
            .hasMessageContaining("Persistent error");
        
        assertThat(attempts.get()).isEqualTo(4); // Initial attempt + 3 retries
    }

    @Test
    @DisplayName("Should set custom retry delay")
    void shouldSetCustomRetryDelay() {
        ProxmoxRequest<String> request = new ProxmoxRequest<>(() -> "Success");

        ProxmoxRequest<String> requestWithDelay = request.retryDelay(1000);

        assertThat(requestWithDelay).isNotNull();
    }

    @Test
    @DisplayName("Should handle null result")
    void shouldHandleNullResult() throws ProxmoxAPIError, InterruptedException {
        ProxmoxRequest<String> request = new ProxmoxRequest<>(() -> null);

        String result = request.execute();

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("Should set task check delay")
    void shouldSetTaskCheckDelay() {
        ProxmoxRequest<String> request = new ProxmoxRequest<>(() -> "Success");

        ProxmoxRequest<String> requestWithDelay = request.taskCheckDelay(500);

        assertThat(requestWithDelay).isNotNull();
    }
}

