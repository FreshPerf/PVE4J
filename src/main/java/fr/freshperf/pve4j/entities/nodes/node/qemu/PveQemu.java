package fr.freshperf.pve4j.entities.nodes.node.qemu;

import com.google.gson.reflect.TypeToken;
import fr.freshperf.pve4j.request.ProxmoxHttpClient;
import fr.freshperf.pve4j.request.ProxmoxRequest;

import java.util.List;

/**
 * Facade for QEMU VM management on a node.
 */
public record PveQemu(ProxmoxHttpClient client, String nodeName) {

    /**
     * Lists all QEMU VMs on this node.
     *
     * @return a request returning the list of VMs
     */
    public ProxmoxRequest<List<PveQemuIndex>> getIndex() {
        return new ProxmoxRequest<>(() -> 
            client.get("nodes/" + nodeName + "/qemu").executeList(new TypeToken<>() {
            })
        );
    }

    /**
     * Gets a specific VM by VMID.
     *
     * @param vmid the VM ID (must be positive)
     * @return the VM API facade
     * @throws IllegalArgumentException if vmid is not positive
     */
    public PveQemuVm get(int vmid) {
        if (vmid <= 0) {
            throw new IllegalArgumentException("VMID must be a positive integer");
        }
        return new PveQemuVm(client, nodeName, vmid);
    }
}

