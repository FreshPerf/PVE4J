package fr.freshperf.pve4j.entities.nodes.node.lxc;

import com.google.gson.JsonObject;
import fr.freshperf.pve4j.request.ProxmoxHttpClient;
import fr.freshperf.pve4j.request.ProxmoxRequest;

/**
 * Facade for managing a specific LXC container.
 */
public record PveLxcContainer(ProxmoxHttpClient client, String nodeName, int vmid) {

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
     * Starts the container.
     *
     * @return a request returning the task result
     */
    public ProxmoxRequest<JsonObject> start() {
        return new ProxmoxRequest<>(() -> 
            client.post("nodes/" + nodeName + "/lxc/" + vmid + "/status/start")
                .execute()
        );
    }

    /**
     * Stops the container (hard stop).
     *
     * @return a request returning the task result
     */
    public ProxmoxRequest<JsonObject> stop() {
        return new ProxmoxRequest<>(() -> 
            client.post("nodes/" + nodeName + "/lxc/" + vmid + "/status/stop")
                .execute()
        );
    }

    /**
     * Shuts down the container gracefully.
     *
     * @return a request returning the task result
     */
    public ProxmoxRequest<JsonObject> shutdown() {
        return new ProxmoxRequest<>(() -> 
            client.post("nodes/" + nodeName + "/lxc/" + vmid + "/status/shutdown")
                .execute()
        );
    }

    /**
     * Reboots the container.
     *
     * @return a request returning the task result
     */
    public ProxmoxRequest<JsonObject> reboot() {
        return new ProxmoxRequest<>(() -> 
            client.post("nodes/" + nodeName + "/lxc/" + vmid + "/status/reboot")
                .execute()
        );
    }

    /**
     * Suspends the container.
     *
     * @return a request returning the task result
     */
    public ProxmoxRequest<JsonObject> suspend() {
        return new ProxmoxRequest<>(() -> 
            client.post("nodes/" + nodeName + "/lxc/" + vmid + "/status/suspend")
                .execute()
        );
    }

    /**
     * Resumes a suspended container.
     *
     * @return a request returning the task result
     */
    public ProxmoxRequest<JsonObject> resume() {
        return new ProxmoxRequest<>(() -> 
            client.post("nodes/" + nodeName + "/lxc/" + vmid + "/status/resume")
                .execute()
        );
    }

    /**
     * Deletes the container and all associated resources.
     *
     * @return a request returning the task result
     */
    public ProxmoxRequest<JsonObject> delete() {
        return new ProxmoxRequest<>(() -> 
            client.delete("nodes/" + nodeName + "/lxc/" + vmid)
                .execute()
        );
    }
}

