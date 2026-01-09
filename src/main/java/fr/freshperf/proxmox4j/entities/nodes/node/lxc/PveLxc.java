package fr.freshperf.proxmox4j.entities.nodes.node.lxc;

import com.google.gson.reflect.TypeToken;
import fr.freshperf.proxmox4j.request.ProxmoxHttpClient;
import fr.freshperf.proxmox4j.request.ProxmoxRequest;

import java.util.List;

/**
 * Facade for LXC container management on a node.
 */
public record PveLxc(ProxmoxHttpClient client, String nodeName) {

    /**
     * Lists all LXC containers on this node.
     *
     * @return a request returning the list of containers
     */
    public ProxmoxRequest<List<PveLxcIndex>> getIndex() {
        return new ProxmoxRequest<>(() -> 
            client.get("nodes/" + nodeName + "/lxc").executeList(new TypeToken<List<PveLxcIndex>>(){})
        );
    }

    /**
     * Gets a specific container by VMID.
     *
     * @param vmid the container ID (must be positive)
     * @return the container API facade
     * @throws IllegalArgumentException if vmid is not positive
     */
    public PveLxcContainer get(int vmid) {
        if (vmid <= 0) {
            throw new IllegalArgumentException("VMID must be a positive integer");
        }
        return new PveLxcContainer(client, nodeName, vmid);
    }
}

