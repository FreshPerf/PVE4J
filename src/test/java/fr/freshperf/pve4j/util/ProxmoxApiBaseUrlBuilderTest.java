package fr.freshperf.pve4j.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

@DisplayName("ProxmoxApiBaseUrlBuilder Tests")
class ProxmoxApiBaseUrlBuilderTest {

    @Test
    @DisplayName("Should build URL with standard HTTPS port (443)")
    void shouldBuildUrlWithStandardHttpsPort() {
        String url = ProxmoxApiBaseUrlBuilder.buildApiBaseUrl("proxmox.example.com", 443);

        // Port 443 is default for HTTPS, so it should be omitted
        assertThat(url).isEqualTo("https://proxmox.example.com/api2/json/");
    }

    @Test
    @DisplayName("Should build URL with custom port")
    void shouldBuildUrlWithCustomPort() {
        String url = ProxmoxApiBaseUrlBuilder.buildApiBaseUrl("192.168.1.100", 8006);

        assertThat(url).isEqualTo("https://192.168.1.100:8006/api2/json/");
    }

    @ParameterizedTest
    @CsvSource({
        "localhost, 8006, https://localhost:8006/api2/json/",
        "10.0.0.1, 443, https://10.0.0.1/api2/json/",
        "pve.local, 8007, https://pve.local:8007/api2/json/",
        "proxmox-node-1, 9999, https://proxmox-node-1:9999/api2/json/"
    })
    @DisplayName("Should build URLs for various hosts and ports")
    void shouldBuildUrlsForVariousHostsAndPorts(String host, int port, String expectedUrl) {
        String url = ProxmoxApiBaseUrlBuilder.buildApiBaseUrl(host, port);

        assertThat(url).isEqualTo(expectedUrl);
    }

    @Test
    @DisplayName("Should include HTTPS protocol by default")
    void shouldIncludeHttpsProtocol() {
        String url = ProxmoxApiBaseUrlBuilder.buildApiBaseUrl("test.com", 8006);

        assertThat(url).startsWith("https://");
    }

    @Test
    @DisplayName("Should include /api2/json/ path")
    void shouldIncludeApi2JsonPath() {
        String url = ProxmoxApiBaseUrlBuilder.buildApiBaseUrl("test.com", 8006);

        assertThat(url).endsWith("/api2/json/");
    }

    @Test
    @DisplayName("Should handle domain with subdomains")
    void shouldHandleDomainWithSubdomains() {
        String url = ProxmoxApiBaseUrlBuilder.buildApiBaseUrl("proxmox.internal.example.com", 443);

        // Port 443 is default for HTTPS, so it should be omitted
        assertThat(url).isEqualTo("https://proxmox.internal.example.com/api2/json/");
    }

    @Test
    @DisplayName("Should handle host with existing https:// prefix")
    void shouldHandleHostWithHttpsPrefix() {
        String url = ProxmoxApiBaseUrlBuilder.buildApiBaseUrl("https://pve.local", 8006);

        assertThat(url).isEqualTo("https://pve.local:8006/api2/json/");
    }

    @Test
    @DisplayName("Should throw exception for null host")
    void shouldThrowExceptionForNullHost() {
        assertThatThrownBy(() -> ProxmoxApiBaseUrlBuilder.buildApiBaseUrl(null, 8006))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Host cannot be null or blank");
    }

    @Test
    @DisplayName("Should throw exception for blank host")
    void shouldThrowExceptionForBlankHost() {
        assertThatThrownBy(() -> ProxmoxApiBaseUrlBuilder.buildApiBaseUrl("   ", 8006))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Host cannot be null or blank");
    }

    @Test
    @DisplayName("Should handle port 80 with http scheme")
    void shouldHandlePort80WithHttpScheme() {
        String url = ProxmoxApiBaseUrlBuilder.buildApiBaseUrl("http://test.com", 80);

        // Port 80 is default for HTTP, so it should be omitted
        assertThat(url).isEqualTo("http://test.com/api2/json/");
    }

    @Test
    @DisplayName("Should include custom port even with http scheme")
    void shouldIncludeCustomPortWithHttpScheme() {
        String url = ProxmoxApiBaseUrlBuilder.buildApiBaseUrl("http://test.com", 8080);

        assertThat(url).isEqualTo("http://test.com:8080/api2/json/");
    }
}

