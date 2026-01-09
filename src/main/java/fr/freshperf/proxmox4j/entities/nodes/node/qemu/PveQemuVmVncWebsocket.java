package fr.freshperf.proxmox4j.entities.nodes.node.qemu;

/**
 * Represents VNC WebSocket connection details.
 */
public class PveQemuVmVncWebsocket {

    private String port;

    public String getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "PveQemuVmVncWebsocket{" +
                "port='" + port + '\'' +
                '}';
    }
}
