package fr.freshperf.proxmox4j.util;

import fr.freshperf.proxmox4j.Proxmox;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ProxmoxApiBaseUrlBuilder {

    public static String buildConsoleUrl(Proxmox proxmox, String nodeName, int vmid, String ticket) {
        String url = proxmox.getHttpClient().getBaseUrl()
                .replace("/api2/json/", "/?console=kvm&novnc=1&vmid="+vmid+"&node=" + nodeName + "&resize=off&vncticket="+URLEncoder.encode(ticket, StandardCharsets.UTF_8));
        return url;
    }

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
