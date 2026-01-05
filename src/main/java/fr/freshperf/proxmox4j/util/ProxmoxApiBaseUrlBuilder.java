package fr.freshperf.proxmox4j.util;

import java.net.URI;

public class ProxmoxApiBaseUrlBuilder {

    public static String buildApiBaseUrl(String host, int port) {
        if (host == null || host.isBlank()) {
            throw new IllegalArgumentException("Host cannot be null or blank");
        }

        String trimmedHost = host.trim();

        if (!trimmedHost.startsWith("http://") && !trimmedHost.startsWith("https://")) {
            trimmedHost = "https://" + trimmedHost;
        }

        URI uri = URI.create(trimmedHost);

        String scheme = uri.getScheme();
        String hostname = uri.getHost();

        if (hostname == null) {
            throw new IllegalArgumentException("Invalid host: " + host);
        }

        int finalPort;
        if (port > 0) {
            finalPort = port;
        } else if (uri.getPort() != -1) {
            finalPort = uri.getPort();
        } else {
            finalPort = "http".equalsIgnoreCase(scheme) ? 80 : 443;
        }

        boolean isDefaultPort =
                ("http".equalsIgnoreCase(scheme) && finalPort == 80) ||
                        ("https".equalsIgnoreCase(scheme) && finalPort == 443);

        StringBuilder baseUrl = new StringBuilder();
        baseUrl.append(scheme).append("://").append(hostname);

        if (!isDefaultPort) {
            baseUrl.append(":").append(finalPort);
        }

        baseUrl.append("/api2/json/");

        return baseUrl.toString();
    }

}
