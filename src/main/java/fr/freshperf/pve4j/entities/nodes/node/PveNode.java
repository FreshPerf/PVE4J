package fr.freshperf.pve4j.entities.nodes.node;

import com.google.gson.reflect.TypeToken;
import fr.freshperf.pve4j.entities.nodes.node.qemu.PveQemu;
import fr.freshperf.pve4j.entities.nodes.node.lxc.PveLxc;
import fr.freshperf.pve4j.entities.nodes.node.storage.PveStorage;
import fr.freshperf.pve4j.entities.nodes.node.vzdump.PveVzdump;
import fr.freshperf.pve4j.request.ProxmoxHttpClient;
import fr.freshperf.pve4j.request.ProxmoxRequest;

import java.util.List;

/**
 * Facade for a specific Proxmox node's endpoints.
 */
public class PveNode {

    private final ProxmoxHttpClient client;
    private final String nodeName;

    private final PveQemu pveQemu;
    private final PveLxc pveLxc;
    private final PveStorage pveStorage;
    private final PveVzdump pveVzdump;

    /**
     * Creates a new node facade.
     *
     * @param client   the HTTP client
     * @param nodeName the node name
     */
    public PveNode(ProxmoxHttpClient client, String nodeName) {
        this.client = client;
        this.nodeName = nodeName;

        this.pveQemu = new PveQemu(client, nodeName);
        this.pveLxc = new PveLxc(client, nodeName);
        this.pveStorage = new PveStorage(client, nodeName);
        this.pveVzdump = new PveVzdump(client, nodeName);
    }

    /**
     * Gets the node index (list of available resources).
     *
     * @return a request returning the node index
     */
    public ProxmoxRequest<List<PveNodeIndex>> getIndex() {
        return new ProxmoxRequest<>(() -> 
            client.get("nodes/" + nodeName).executeList(new TypeToken<List<PveNodeIndex>>(){})
        );
    }

    /**
     * Gets the node status.
     *
     * @return a request returning the node status
     */
    public ProxmoxRequest<PveNodeStatus> getStatus() {
        return new ProxmoxRequest<>(() -> 
            client.get("nodes/" + nodeName + "/status").execute(PveNodeStatus.class)
        );
    }

    /**
     * Gets the node version.
     *
     * @return a request returning the node version
     */
    public ProxmoxRequest<PveNodeVersion> getVersion() {
        return new ProxmoxRequest<>(() -> 
            client.get("nodes/" + nodeName + "/version").execute(PveNodeVersion.class)
        );
    }

    /**
     * Gets the node network statistics.
     *
     * @return a list of network interface statistics for all vms on this node
     */
    public ProxmoxRequest<List<PveNodeNetstat>> getNetstat() {
        return new ProxmoxRequest<>(() ->
            client.get("nodes/" + nodeName + "/netstat").executeList(new TypeToken<List<PveNodeNetstat>>() {
        }));
    }

    /**
     * Gets the QEMU VM management interface for this node.
     *
     * @return the QEMU API facade
     */
    public PveQemu getQemu() {
        return pveQemu;
    }

    /**
     * Gets the LXC container management interface for this node.
     *
     * @return the LXC API facade
     */
    public PveLxc getLxc() {
        return pveLxc;
    }

    /**
     * Gets the storage management interface for this node.
     *
     * @return the storage API facade
     */
    public PveStorage getStorage() {
        return pveStorage;
    }

    /**
     * Gets the vzdump backup management interface for this node.
     *
     * @return the vzdump API facade
     */
    public PveVzdump getVzdump() {
        return pveVzdump;
    }

    @Override
    public String toString() {
        return "PveNode{" +
                "client=" + client +
                ", nodeName='" + nodeName + '\'' +
                ", pveQemu=" + pveQemu +
                ", pveLxc=" + pveLxc +
                ", pveStorage=" + pveStorage +
                ", pveVzdump=" + pveVzdump +
                '}';
    }
}

