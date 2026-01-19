package fr.freshperf.pve4j.util;

import fr.freshperf.pve4j.entities.nodes.node.qemu.PveQemuVmVncProxy;
import fr.freshperf.pve4j.request.ProxmoxHttpClient;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Utility class for building Proxmox API URLs.
 */
public class ProxmoxApiBaseUrlBuilder {

    /**
     * Builds a console URL for VNC access to a VM.
     *
     * @param client             the HTTP client
     * @param nodeName           the node name
     * @param vmid               the VM ID
     * @param pveQemuVmVncProxy  the VNC proxy information
     * @return the console URL
     */
    public static String buildConsoleUrl(ProxmoxHttpClient client, String nodeName, int vmid, PveQemuVmVncProxy pveQemuVmVncProxy) {
        return client.getBaseUrl()
                .replace("/api2/json/",
                        "/?console=kvm&novnc=1&node=" + nodeName + "&vmid=" + vmid
                                + "&path="+URLEncoder.encode("/api2/json/nodes/"+nodeName+"/qemu/"+vmid+"/vncwebsocket?port="+
                        pveQemuVmVncProxy.getPort()+"&vncticket="+ pveQemuVmVncProxy.getTicket(), StandardCharsets.UTF_8));
    }

    /**
     * Builds the base API URL from host and port.
     *
     * @param host the Proxmox host address
     * @param port the API port (use 0 to auto-detect from scheme)
     * @return the base API URL ending with /api2/json/
     * @throws IllegalArgumentException if host is null or blank
     */
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

    /**
     * Builds a WebSocket URL for VNC connections.
     *
     * @param client   the HTTP client
     * @param nodeName the node name
     * @param vmid     the VM ID
     * @return the WebSocket URL for VNC
     */
    public static String buildWebsocketUrl(ProxmoxHttpClient client, String nodeName, int vmid) {
        return client.getBaseUrl()
                .replaceFirst("^https?://", "wss://") + "nodes/"+nodeName+"/qemu/"+vmid+"/vncwebsocket";
    }
}
