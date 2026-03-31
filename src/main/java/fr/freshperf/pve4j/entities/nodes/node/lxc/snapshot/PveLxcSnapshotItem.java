package fr.freshperf.pve4j.entities.nodes.node.lxc.snapshot;

import fr.freshperf.pve4j.entities.PveTask;
import fr.freshperf.pve4j.request.ProxmoxHttpClient;
import fr.freshperf.pve4j.request.ProxmoxRequest;
import fr.freshperf.pve4j.request.TaskResponseTransformer;

/**
 * Facade for managing a specific LXC snapshot.
 */
public class PveLxcSnapshotItem {

    private final ProxmoxHttpClient client;
    private final String nodeName;
    private final int vmid;
    private final String snapname;

    public PveLxcSnapshotItem(ProxmoxHttpClient client, String nodeName, int vmid, String snapname) {
        this.client = client;
        this.nodeName = nodeName;
        this.vmid = vmid;
        this.snapname = snapname;
    }

    public ProxmoxRequest<PveLxcSnapshotConfig> getConfig() {
        return new ProxmoxRequest<>(() ->
            client.get("nodes/" + nodeName + "/lxc/" + vmid + "/snapshot/" + snapname + "/config")
                .execute(PveLxcSnapshotConfig.class)
        );
    }

    public ProxmoxRequest<Void> updateConfig(PveLxcSnapshotUpdateOptions options) {
        if (options == null) {
            throw new IllegalArgumentException("options cannot be null");
        }

        return new ProxmoxRequest<>(() -> {
            client.put("nodes/" + nodeName + "/lxc/" + vmid + "/snapshot/" + snapname + "/config")
                .params(options.toParams())
                .execute();
            return null;
        });
    }

    public ProxmoxRequest<PveTask> delete() {
        return new ProxmoxRequest<>(() ->
            client.delete("nodes/" + nodeName + "/lxc/" + vmid + "/snapshot/" + snapname)
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    public ProxmoxRequest<PveTask> delete(boolean force) {
        return new ProxmoxRequest<>(() ->
            client.delete("nodes/" + nodeName + "/lxc/" + vmid + "/snapshot/" + snapname)
                .param("force", force ? "1" : "0")
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    public ProxmoxRequest<PveTask> rollback() {
        return rollback(false);
    }

    public ProxmoxRequest<PveTask> rollback(boolean start) {
        return new ProxmoxRequest<>(() ->
            client.post("nodes/" + nodeName + "/lxc/" + vmid + "/snapshot/" + snapname + "/rollback")
                .param("start", start ? "1" : "0")
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }
}
