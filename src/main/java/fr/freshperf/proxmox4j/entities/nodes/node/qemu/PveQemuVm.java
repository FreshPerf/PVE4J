package fr.freshperf.proxmox4j.entities.nodes.node.qemu;

import fr.freshperf.proxmox4j.entities.PveTask;
import fr.freshperf.proxmox4j.entities.nodes.node.qemu.firewall.PveQemuFirewall;
import fr.freshperf.proxmox4j.request.ProxmoxHttpClient;
import fr.freshperf.proxmox4j.request.ProxmoxRequest;
import fr.freshperf.proxmox4j.request.TaskResponseTransformer;

public class PveQemuVm {

    private final ProxmoxHttpClient client;
    private final String nodeName;
    private final int vmid;

    private final PveQemuVmVnc pveQemuVmVnc;

    public PveQemuVm(ProxmoxHttpClient client, String nodeName, int vmid) {
        this.client = client;
        this.nodeName = nodeName;
        this.vmid = vmid;
        this.pveQemuVmVnc = new PveQemuVmVnc(client, nodeName, vmid);
    }

    public PveQemuVmVnc getVnc() {
        return pveQemuVmVnc;
    }

    public PveQemuFirewall getFirewall() {
        return new PveQemuFirewall(client, nodeName, vmid);
    }

    /**
     * Gets the current VM status
     */
    public ProxmoxRequest<PveQemuStatus> getStatus() {
        return new ProxmoxRequest<>(() -> 
            client.get("nodes/" + nodeName + "/qemu/" + vmid + "/status/current")
                .execute(PveQemuStatus.class)
        );
    }
    /**
     * Gets the VM configuration
     */
    public ProxmoxRequest<PveQemuConfig> getConfig() {
        return new ProxmoxRequest<>(() -> 
            client.get("nodes/" + nodeName + "/qemu/" + vmid + "/config")
                .execute(PveQemuConfig.class)
        );
    }

    /**
     * Starts the VM
     */
    public ProxmoxRequest<PveTask> start() {
        return new ProxmoxRequest<>(() ->
            client.post("nodes/" + nodeName + "/qemu/" + vmid + "/status/start")
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    /**
     * Stops the VM
     */
    public ProxmoxRequest<PveTask> stop() {
        return new ProxmoxRequest<>(() ->
            client.post("nodes/" + nodeName + "/qemu/" + vmid + "/status/stop")
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    /**
     * Shuts down the VM
     */
    public ProxmoxRequest<PveTask> shutdown() {
        return new ProxmoxRequest<>(() ->
            client.post("nodes/" + nodeName + "/qemu/" + vmid + "/status/shutdown")
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    /**
     * Resets the VM
     */
    public ProxmoxRequest<PveTask> reset() {
        return new ProxmoxRequest<>(() ->
            client.post("nodes/" + nodeName + "/qemu/" + vmid + "/status/reset")
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    /**
     * Suspends the VM
     */
    public ProxmoxRequest<PveTask> suspend() {
        return new ProxmoxRequest<>(() ->
            client.post("nodes/" + nodeName + "/qemu/" + vmid + "/status/suspend")
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    /**
     * Resumes the VM
     */
    public ProxmoxRequest<PveTask> resume() {
        return new ProxmoxRequest<>(() ->
            client.post("nodes/" + nodeName + "/qemu/" + vmid + "/status/resume")
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    /**
     * Reboots the VM
     */
    public ProxmoxRequest<PveTask> reboot() {
        return new ProxmoxRequest<>(() ->
            client.post("nodes/" + nodeName + "/qemu/" + vmid + "/status/reboot")
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    /**
     * Deletes the VM
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
     * Required: newid. Optional parameters are provided via {@link PveQemuCloneOptions}.
     */
    public ProxmoxRequest<PveTask> cloneVm(int newVmid) {
        return cloneVm(newVmid, null);
    }

    /**
     * Clones the VM/template to a new VMID with optional settings.
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
     * Resizes a disk of the VM. Size may be absolute (e.g. "20G") or relative (e.g. "+10G").
     * See {@link PveQemuResizeOptions} for optional parameters.
     */
    public ProxmoxRequest<PveTask> resize(String disk, String size) {
        return resize(disk, size, null);
    }

    /**
     * Resizes a disk of the VM with optional parameters.
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
     * Update VM configuration (asynchronous, POST)
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
     * Update VM configuration (synchronous, PUT)
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
}
