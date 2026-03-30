package fr.freshperf.pve4j.entities.nodes.node.qemu;

import fr.freshperf.pve4j.entities.PveTask;
import fr.freshperf.pve4j.entities.nodes.node.qemu.agent.PveQemuAgent;
import fr.freshperf.pve4j.entities.nodes.node.qemu.firewall.PveQemuFirewall;
import fr.freshperf.pve4j.entities.nodes.node.qemu.snapshot.PveQemuSnapshots;
import fr.freshperf.pve4j.request.ProxmoxHttpClient;
import fr.freshperf.pve4j.request.ProxmoxRequest;
import fr.freshperf.pve4j.request.TaskResponseTransformer;

/**
 * Facade for managing a specific QEMU VM.
 */
public class PveQemuVm {

    private final ProxmoxHttpClient client;
    private final String nodeName;
    private final int vmid;

    private final PveQemuVmVnc pveQemuVmVnc;

    /**
     * Creates a new VM facade.
     *
     * @param client   the HTTP client
     * @param nodeName the node name
     * @param vmid     the VM ID
     */
    public PveQemuVm(ProxmoxHttpClient client, String nodeName, int vmid) {
        this.client = client;
        this.nodeName = nodeName;
        this.vmid = vmid;
        this.pveQemuVmVnc = new PveQemuVmVnc(client, nodeName, vmid);
    }

    /**
     * Gets the VNC interface for this VM.
     *
     * @return the VNC API facade
     */
    public PveQemuVmVnc getVnc() {
        return pveQemuVmVnc;
    }

    /**
     * Gets the firewall interface for this VM.
     *
     * @return the firewall API facade
     */
    public PveQemuFirewall getFirewall() {
        return new PveQemuFirewall(client, nodeName, vmid);
    }

    /**
     * Gets the snapshot management interface for this VM.
     *
     * @return the snapshots API facade
     */
    public PveQemuSnapshots getSnapshots() {
        return new PveQemuSnapshots(client, nodeName, vmid);
    }

    /**
     * Gets the agent interface for this VM.
     *
     * @return the agent interface
     */
    public PveQemuAgent getAgent() {
        return new PveQemuAgent(client, nodeName, vmid);
    }

    /**
     * Gets the current VM status.
     *
     * @return a request returning the VM status
     */
    public ProxmoxRequest<PveQemuStatus> getStatus() {
        return new ProxmoxRequest<>(() -> 
            client.get("nodes/" + nodeName + "/qemu/" + vmid + "/status/current")
                .execute(PveQemuStatus.class)
        );
    }
    /**
     * Gets the VM configuration.
     *
     * @return a request returning the VM configuration
     */
    public ProxmoxRequest<PveQemuConfig> getConfig() {
        return new ProxmoxRequest<>(() -> 
            client.get("nodes/" + nodeName + "/qemu/" + vmid + "/config")
                .execute(PveQemuConfig.class)
        );
    }

    /**
     * Starts the VM.
     *
     * @return a request returning the task for tracking
     */
    public ProxmoxRequest<PveTask> start() {
        return new ProxmoxRequest<>(() ->
            client.post("nodes/" + nodeName + "/qemu/" + vmid + "/status/start")
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    /**
     * Stops the VM (hard stop).
     *
     * @return a request returning the task for tracking
     */
    public ProxmoxRequest<PveTask> stop() {
        return new ProxmoxRequest<>(() ->
            client.post("nodes/" + nodeName + "/qemu/" + vmid + "/status/stop")
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    /**
     * Shuts down the VM gracefully via ACPI.
     *
     * @return a request returning the task for tracking
     */
    public ProxmoxRequest<PveTask> shutdown() {
        return new ProxmoxRequest<>(() ->
            client.post("nodes/" + nodeName + "/qemu/" + vmid + "/status/shutdown")
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    /**
     * Resets the VM (hard reset).
     *
     * @return a request returning the task for tracking
     */
    public ProxmoxRequest<PveTask> reset() {
        return new ProxmoxRequest<>(() ->
            client.post("nodes/" + nodeName + "/qemu/" + vmid + "/status/reset")
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    /**
     * Suspends the VM to RAM or disk.
     *
     * @return a request returning the task for tracking
     */
    public ProxmoxRequest<PveTask> suspend() {
        return new ProxmoxRequest<>(() ->
            client.post("nodes/" + nodeName + "/qemu/" + vmid + "/status/suspend")
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    /**
     * Resumes a suspended VM.
     *
     * @return a request returning the task for tracking
     */
    public ProxmoxRequest<PveTask> resume() {
        return new ProxmoxRequest<>(() ->
            client.post("nodes/" + nodeName + "/qemu/" + vmid + "/status/resume")
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    /**
     * Reboots the VM gracefully via ACPI.
     *
     * @return a request returning the task for tracking
     */
    public ProxmoxRequest<PveTask> reboot() {
        return new ProxmoxRequest<>(() ->
            client.post("nodes/" + nodeName + "/qemu/" + vmid + "/status/reboot")
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    /**
     * Deletes the VM and all associated resources.
     *
     * @return a request returning the task for tracking
     */
    public ProxmoxRequest<PveTask> delete() {
        return new ProxmoxRequest<>(() ->
            client.delete("nodes/" + nodeName + "/qemu/" + vmid)
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    /**
     * Clones the VM/template to a new VMID.
     *
     * @param newVmid the target VM ID (must be >= 100)
     * @return a request returning the task for tracking
     */
    public ProxmoxRequest<PveTask> cloneVm(int newVmid) {
        return cloneVm(newVmid, null);
    }

    /**
     * Clones the VM/template to a new VMID with options.
     *
     * @param newVmid the target VM ID (must be >= 100)
     * @param options clone options (name, storage, etc.) or null
     * @return a request returning the task for tracking
     * @throws IllegalArgumentException if newVmid lower than 100
     */
    public ProxmoxRequest<PveTask> cloneVm(int newVmid, PveQemuCloneOptions options) {
        if (newVmid < 100) {
            throw new IllegalArgumentException("newVmid must be >= 100");
        }

        PveQemuCloneOptions effectiveOptions = options != null ? options : PveQemuCloneOptions.builder();
        
        return new ProxmoxRequest<>(() ->
            client.post("nodes/" + nodeName + "/qemu/" + vmid + "/clone")
                .params(effectiveOptions.toParams(newVmid))
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    /**
     * Resizes a disk of the VM.
     *
     * @param disk the disk to resize (e.g., "scsi0", "virtio0")
     * @param size absolute size (e.g., "20G") or relative (e.g., "+10G")
     * @return a request returning the task for tracking
     */
    public ProxmoxRequest<PveTask> resize(String disk, String size) {
        return resize(disk, size, null);
    }

    /**
     * Resizes a disk of the VM with options.
     *
     * @param disk    the disk to resize (e.g., "scsi0", "virtio0")
     * @param size    absolute size (e.g., "20G") or relative (e.g., "+10G")
     * @param options resize options or null
     * @return a request returning the task for tracking
     */
    public ProxmoxRequest<PveTask> resize(String disk, String size, PveQemuResizeOptions options) {
        PveQemuResizeOptions effectiveOptions = options != null ? options : PveQemuResizeOptions.builder();
        
        return new ProxmoxRequest<>(() ->
            client.put("nodes/" + nodeName + "/qemu/" + vmid + "/resize")
                .params(effectiveOptions.toParams(disk, size))
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    /**
     * Updates VM configuration asynchronously (POST).
     *
     * @param options the configuration options to update
     * @return a request returning the task for tracking
     * @throws IllegalArgumentException if options is null
     */
    public ProxmoxRequest<PveTask> updateConfig(PveQemuConfigUpdateOptions options) {
        if (options == null) {
            throw new IllegalArgumentException("options cannot be null");
        }
        return new ProxmoxRequest<>(() ->
            client.post("nodes/" + nodeName + "/qemu/" + vmid + "/config")
                .params(options.toParams())
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    /**
     * Updates VM configuration synchronously (PUT).
     *
     * @param options the configuration options to update
     * @return a request returning the task for tracking
     * @throws IllegalArgumentException if options is null
     */
    public ProxmoxRequest<PveTask> updateConfigSync(PveQemuConfigUpdateOptions options) {
        if (options == null) {
            throw new IllegalArgumentException("options cannot be null");
        }
        return new ProxmoxRequest<>(() ->
            client.put("nodes/" + nodeName + "/qemu/" + vmid + "/config")
                .params(options.toParams())
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    /**
     * Creates a backup of this VM.
     *
     * @return a request returning the task for tracking
     */
    public ProxmoxRequest<PveTask> backup() {
        return backup(null);
    }

    /**
     * Creates a backup of this VM with options.
     *
     * @param options backup options or null for defaults
     * @return a request returning the task for tracking
     */
    public ProxmoxRequest<PveTask> backup(PveQemuBackupOptions options) {
        return new ProxmoxRequest<>(() -> {
            var builder = client.post("nodes/" + nodeName + "/vzdump")
                .param("vmid", String.valueOf(vmid));
            
            if (options != null) {
                builder.params(options.toParams());
            }
            
            return builder.transformer(new TaskResponseTransformer())
                .execute(PveTask.class);
        });
    }

    /**
     * Migrates this VM to another node.
     *
     * @param targetNode the target node name
     * @return a request returning the task for tracking
     */
    public ProxmoxRequest<PveTask> migrate(String targetNode) {
        return migrate(targetNode, false, null);
    }

    /**
     * Migrates this VM to another node with options.
     *
     * @param targetNode the target node name
     * @param online     true for live migration
     * @param targetStorage target storage (null for same storage)
     * @return a request returning the task for tracking
     */
    public ProxmoxRequest<PveTask> migrate(String targetNode, boolean online, String targetStorage) {
        if (targetNode == null || targetNode.isBlank()) {
            throw new IllegalArgumentException("targetNode cannot be null or empty");
        }
        return new ProxmoxRequest<>(() -> {
            var builder = client.post("nodes/" + nodeName + "/qemu/" + vmid + "/migrate")
                .param("target", targetNode)
                .param("online", online ? "1" : "0");
            
            if (targetStorage != null && !targetStorage.isBlank()) {
                builder.param("targetstorage", targetStorage);
            }
            
            return builder.transformer(new TaskResponseTransformer())
                .execute(PveTask.class);
        });
    }

    /**
     * Converts this VM to a template.
     *
     * @return a request returning the task for tracking
     */
    public ProxmoxRequest<PveTask> template() {
        return new ProxmoxRequest<>(() ->
            client.post("nodes/" + nodeName + "/qemu/" + vmid + "/template")
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }
}
