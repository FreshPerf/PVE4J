package fr.freshperf.pve4j.entities.nodes.node.lxc.snapshot;

import com.google.gson.reflect.TypeToken;
import fr.freshperf.pve4j.entities.PveTask;
import fr.freshperf.pve4j.request.ProxmoxHttpClient;
import fr.freshperf.pve4j.request.ProxmoxRequest;
import fr.freshperf.pve4j.request.TaskResponseTransformer;

import java.util.List;

/**
 * Facade for LXC snapshot management.
 */
public class PveLxcSnapshots {

    private final ProxmoxHttpClient client;
    private final String nodeName;
    private final int vmid;

    public PveLxcSnapshots(ProxmoxHttpClient client, String nodeName, int vmid) {
        this.client = client;
        this.nodeName = nodeName;
        this.vmid = vmid;
    }

    public ProxmoxRequest<List<PveLxcSnapshot>> list() {
        return new ProxmoxRequest<>(() ->
            client.get("nodes/" + nodeName + "/lxc/" + vmid + "/snapshot")
                .executeList(new TypeToken<List<PveLxcSnapshot>>() {})
        );
    }

    public ProxmoxRequest<PveTask> create(String snapname) {
        return create(snapname, null);
    }

    public ProxmoxRequest<PveTask> create(String snapname, PveLxcSnapshotCreateOptions options) {
        if (snapname == null || snapname.isBlank()) {
            throw new IllegalArgumentException("snapname cannot be null or empty");
        }

        PveLxcSnapshotCreateOptions effectiveOptions = options != null ? options : PveLxcSnapshotCreateOptions.builder();

        return new ProxmoxRequest<>(() ->
            client.post("nodes/" + nodeName + "/lxc/" + vmid + "/snapshot")
                .params(effectiveOptions.toParams(snapname))
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    public PveLxcSnapshotItem get(String snapname) {
        if (snapname == null || snapname.isBlank()) {
            throw new IllegalArgumentException("snapname cannot be null or empty");
        }

        return new PveLxcSnapshotItem(client, nodeName, vmid, snapname);
    }
}
