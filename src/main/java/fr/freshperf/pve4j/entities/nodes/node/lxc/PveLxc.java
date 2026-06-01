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

    /**
     * Restores an LXC container from a backup archive.
     *
     * <p>This wraps {@code POST /nodes/{node}/lxc} in its restore mode (driven by the
     * {@code restore} flag, with the backup archive passed as {@code ostemplate}). Use
     * {@link PveLxcRestoreOptions#force(Boolean)} to overwrite an existing container with the
     * same VMID.</p>
     *
     * @param vmid    the target container ID (must be &gt;= 100)
     * @param options the restore options; {@link PveLxcRestoreOptions#archive(String)} is required
     * @return a request returning the task for tracking
     * @throws IllegalArgumentException if vmid is less than 100, options is null, or no archive is set
     */
    public ProxmoxRequest<PveTask> restore(int vmid, PveLxcRestoreOptions options) {
        if (vmid < 100) {
            throw new IllegalArgumentException("VMID must be >= 100");
        }
        if (options == null) {
            throw new IllegalArgumentException("options cannot be null");
        }
        if (options.getArchive() == null || options.getArchive().isBlank()) {
            throw new IllegalArgumentException("a backup archive must be set via options.archive(...)");
        }

        return new ProxmoxRequest<>(() ->
            client.post("nodes/" + nodeName + "/lxc")
                .params(options.toParams(vmid))
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }
}

