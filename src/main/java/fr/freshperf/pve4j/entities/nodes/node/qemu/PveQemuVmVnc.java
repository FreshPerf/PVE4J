package fr.freshperf.pve4j.entities.nodes.node.qemu;

import fr.freshperf.pve4j.request.ProxmoxHttpClient;
import fr.freshperf.pve4j.request.ProxmoxRequest;
import fr.freshperf.pve4j.util.ProxmoxApiBaseUrlBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Facade for VNC access to a QEMU VM.
 */
public class PveQemuVmVnc {

    private final ProxmoxHttpClient client;
    private final String nodeName;
    private final int vmid;

    /**
     * Creates a new VNC facade.
     *
     * @param client   the HTTP client
     * @param nodeName the node name
     * @param vmid     the VM ID
     */
    public PveQemuVmVnc(ProxmoxHttpClient client, String nodeName, int vmid) {
        this.client = client;
        this.nodeName = nodeName;
        this.vmid = vmid;
    }

    /**
     * Creates a VNC proxy session.
     *
     * @return a request returning the VNC proxy details
     */
    public ProxmoxRequest<PveQemuVmVncProxy> getVncProxy() {
        return new ProxmoxRequest<>(() ->
                client.post("nodes/" + nodeName + "/qemu/" + vmid + "/vncproxy")
                        .execute(PveQemuVmVncProxy.class)
        );
    }

    /**
     * Builds the WebSocket URL for VNC access.
     *
     * @param proxy the VNC proxy details
     * @return the WebSocket URL
     */
    public String getVncWebsocketUrl(PveQemuVmVncProxy proxy) {
        return ProxmoxApiBaseUrlBuilder.buildWebsocketUrl(client, nodeName, vmid)+"?port="
                + URLEncoder.encode(String.valueOf(proxy.getPort()), StandardCharsets.UTF_8)
                + "&vncticket="
                + URLEncoder.encode(proxy.getTicket(), StandardCharsets.UTF_8);
    }

    /**
     * Opens a VNC WebSocket connection.
     *
     * @param vmVncProxy the VNC proxy details
     * @return a request returning the WebSocket details
     */
    public ProxmoxRequest<PveQemuVmVncWebsocket> openVncWebsocket(PveQemuVmVncProxy vmVncProxy) {
        return new ProxmoxRequest<>(() ->
                client.get("nodes/" + nodeName + "/qemu/" + vmid + "/vncwebsocket")
                        .param("port", vmVncProxy.getPort())
                        .param("vncticket", vmVncProxy.getTicket())
                        .execute(PveQemuVmVncWebsocket.class)
        );
    }

    /**
     * Builds the noVNC console URL.
     *
     * @param vmVncProxy the VNC proxy details
     * @return the console URL
     */
    public String getConsoleUrl(PveQemuVmVncProxy vmVncProxy) {
        return ProxmoxApiBaseUrlBuilder.buildConsoleUrl(client, nodeName, vmid, vmVncProxy);
    }
}
