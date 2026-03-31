package fr.freshperf.pve4j.entities.nodes.node.lxc;

import com.google.gson.reflect.TypeToken;
import fr.freshperf.pve4j.entities.PveTask;
import fr.freshperf.pve4j.request.ProxmoxHttpClient;
import fr.freshperf.pve4j.request.ProxmoxRequest;
import fr.freshperf.pve4j.request.TaskResponseTransformer;

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

    /**
     * Creates a new LXC container.
     *
     * @param vmid    the container ID (must be >= 100)
     * @param options container creation options (ostemplate is required)
     * @return a request returning the task for tracking
     * @throws IllegalArgumentException if vmid is less than 100 or options is null
     */
    public ProxmoxRequest<PveTask> create(int vmid, PveLxcCreateOptions options) {
        if (vmid < 100) {
            throw new IllegalArgumentException("VMID must be >= 100");
        }
        if (options == null) {
            throw new IllegalArgumentException("options cannot be null - ostemplate is required");
        }
        if (!options.hasOstemplate()) {
            throw new IllegalArgumentException("options.ostemplate is required");
        }

        return new ProxmoxRequest<>(() ->
            client.post("nodes/" + nodeName + "/lxc")
                .params(options.toParams(vmid))
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }
}

