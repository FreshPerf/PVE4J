package fr.freshperf.pve4j.entities.nodes.node.lxc;

import com.google.gson.reflect.TypeToken;
import fr.freshperf.pve4j.entities.PveTask;
import fr.freshperf.pve4j.entities.nodes.node.lxc.feature.PveLxcFeatureAvailability;
import fr.freshperf.pve4j.entities.nodes.node.lxc.interfaces.PveLxcInterface;
import fr.freshperf.pve4j.entities.nodes.node.lxc.snapshot.PveLxcSnapshots;
import fr.freshperf.pve4j.request.ProxmoxHttpClient;
import fr.freshperf.pve4j.request.ProxmoxRequest;
import fr.freshperf.pve4j.request.TaskResponseTransformer;

import java.util.List;
import java.util.Set;

/**
 * Facade for managing a specific LXC container.
 */
public record PveLxcContainer(ProxmoxHttpClient client, String nodeName, int vmid) {

    private static final Set<String> ALLOWED_FEATURES = Set.of("snapshot", "clone", "copy");

    /**
     * Gets the current container status.
     *
     * @return a request returning the container status
     */
    public ProxmoxRequest<PveLxcStatus> getStatus() {
        return new ProxmoxRequest<>(() -> 
            client.get("nodes/" + nodeName + "/lxc/" + vmid + "/status/current")
                .execute(PveLxcStatus.class)
        );
    }

    /**
     * Gets the container configuration.
     *
     * @return a request returning the container configuration
     */
    public ProxmoxRequest<PveLxcConfig> getConfig() {
        return new ProxmoxRequest<>(() -> 
            client.get("nodes/" + nodeName + "/lxc/" + vmid + "/config")
                .execute(PveLxcConfig.class)
        );
    }

    /**
     * Gets the pending configuration changes for this container.
     *
     * @return a request returning pending changes
     */
    public ProxmoxRequest<List<PveLxcPendingChange>> getPending() {
        return new ProxmoxRequest<>(() ->
            client.get("nodes/" + nodeName + "/lxc/" + vmid + "/pending")
                .executeList(new TypeToken<List<PveLxcPendingChange>>() {})
        );
    }

    /**
     * Gets network interfaces reported for this container.
     *
     * @return a request returning container interfaces
     */
    public ProxmoxRequest<List<PveLxcInterface>> getInterfaces() {
        return new ProxmoxRequest<>(() ->
            client.get("nodes/" + nodeName + "/lxc/" + vmid + "/interfaces")
                .executeList(new TypeToken<List<PveLxcInterface>>() {})
        );
    }

    /**
     * Checks if a feature is available for this container.
     *
     * @param feature the feature to check (snapshot, clone, copy)
     * @return a request returning the feature availability
     */
    public ProxmoxRequest<PveLxcFeatureAvailability> getFeature(String feature) {
        return getFeature(feature, null);
    }

    /**
     * Checks if a feature is available for this container in the context of a snapshot.
     *
     * @param feature  the feature to check (snapshot, clone, copy)
     * @param snapname the snapshot name or null
     * @return a request returning the feature availability
     */
    public ProxmoxRequest<PveLxcFeatureAvailability> getFeature(String feature, String snapname) {
        validateFeature(feature);

        return new ProxmoxRequest<>(() -> {
            var builder = client.get("nodes/" + nodeName + "/lxc/" + vmid + "/feature")
                .param("feature", feature);

            if (snapname != null && !snapname.isBlank()) {
                builder.param("snapname", snapname);
            }

            return builder.execute(PveLxcFeatureAvailability.class);
        });
    }

    /**
     * Gets the snapshot management interface for this container.
     *
     * @return the snapshots API facade
     */
    public PveLxcSnapshots getSnapshots() {
        return new PveLxcSnapshots(client, nodeName, vmid);
    }

    /**
     * Updates container configuration.
     *
     * @param options configuration updates to apply
     * @return a request that completes when the update is done
     */
    public ProxmoxRequest<Void> updateConfig(PveLxcConfigUpdateOptions options) {
        if (options == null) {
            throw new IllegalArgumentException("options cannot be null");
        }

        return new ProxmoxRequest<>(() -> {
            client.put("nodes/" + nodeName + "/lxc/" + vmid + "/config")
                .params(options.toParams())
                .execute();
            return null;
        });
    }

    /**
     * Starts the container.
     *
     * @return a request returning the task for tracking
     */
    public ProxmoxRequest<PveTask> start() {
        return new ProxmoxRequest<>(() -> 
            client.post("nodes/" + nodeName + "/lxc/" + vmid + "/status/start")
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    /**
     * Stops the container (hard stop).
     *
     * @return a request returning the task for tracking
     */
    public ProxmoxRequest<PveTask> stop() {
        return new ProxmoxRequest<>(() -> 
            client.post("nodes/" + nodeName + "/lxc/" + vmid + "/status/stop")
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    /**
     * Shuts down the container gracefully.
     *
     * @return a request returning the task for tracking
     */
    public ProxmoxRequest<PveTask> shutdown() {
        return new ProxmoxRequest<>(() -> 
            client.post("nodes/" + nodeName + "/lxc/" + vmid + "/status/shutdown")
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    /**
     * Reboots the container.
     *
     * @return a request returning the task for tracking
     */
    public ProxmoxRequest<PveTask> reboot() {
        return new ProxmoxRequest<>(() -> 
            client.post("nodes/" + nodeName + "/lxc/" + vmid + "/status/reboot")
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    /**
     * Suspends the container.
     *
     * @return a request returning the task for tracking
     */
    public ProxmoxRequest<PveTask> suspend() {
        return new ProxmoxRequest<>(() -> 
            client.post("nodes/" + nodeName + "/lxc/" + vmid + "/status/suspend")
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    /**
     * Resumes a suspended container.
     *
     * @return a request returning the task for tracking
     */
    public ProxmoxRequest<PveTask> resume() {
        return new ProxmoxRequest<>(() -> 
            client.post("nodes/" + nodeName + "/lxc/" + vmid + "/status/resume")
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    /**
     * Clones this container to a new VMID.
     *
     * @param newVmid the target container ID
     * @return a request returning the task for tracking
     */
    public ProxmoxRequest<PveTask> cloneContainer(int newVmid) {
        return cloneContainer(newVmid, null);
    }

    /**
     * Clones this container to a new VMID with options.
     *
     * @param newVmid the target container ID
     * @param options clone options or null
     * @return a request returning the task for tracking
     */
    public ProxmoxRequest<PveTask> cloneContainer(int newVmid, PveLxcCloneOptions options) {
        if (newVmid < 100) {
            throw new IllegalArgumentException("newVmid must be >= 100");
        }

        PveLxcCloneOptions effectiveOptions = options != null ? options : PveLxcCloneOptions.builder();

        return new ProxmoxRequest<>(() ->
            client.post("nodes/" + nodeName + "/lxc/" + vmid + "/clone")
                .params(effectiveOptions.toParams(newVmid))
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    /**
     * Migrates this container to another node.
     *
     * @param targetNode the target node name
     * @return a request returning the task for tracking
     */
    public ProxmoxRequest<PveTask> migrate(String targetNode) {
        return migrate(targetNode, null);
    }

    /**
     * Migrates this container to another node with options.
     *
     * @param targetNode the target node name
     * @param options migration options or null
     * @return a request returning the task for tracking
     */
    public ProxmoxRequest<PveTask> migrate(String targetNode, PveLxcMigrateOptions options) {
        if (targetNode == null || targetNode.isBlank()) {
            throw new IllegalArgumentException("targetNode cannot be null or empty");
        }

        PveLxcMigrateOptions effectiveOptions = options != null ? options : PveLxcMigrateOptions.builder();

        return new ProxmoxRequest<>(() ->
            client.post("nodes/" + nodeName + "/lxc/" + vmid + "/migrate")
                .params(effectiveOptions.toParams(targetNode))
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    /**
     * Converts this container into a template.
     *
     * @return a request that completes when the operation is done
     */
    public ProxmoxRequest<Void> template() {
        return new ProxmoxRequest<>(() -> {
            client.post("nodes/" + nodeName + "/lxc/" + vmid + "/template")
                .execute();
            return null;
        });
    }

    /**
     * Deletes the container and all associated resources.
     *
     * @return a request returning the task for tracking
     */
    public ProxmoxRequest<PveTask> delete() {
        return new ProxmoxRequest<>(() -> 
            client.delete("nodes/" + nodeName + "/lxc/" + vmid)
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    private static void validateFeature(String feature) {
        if (feature == null || feature.isBlank()) {
            throw new IllegalArgumentException("feature cannot be null or empty");
        }
        if (!ALLOWED_FEATURES.contains(feature)) {
            throw new IllegalArgumentException("feature must be one of: " + String.join(", ", ALLOWED_FEATURES));
        }
    }
}

