package fr.freshperf.proxmox4j.entities.cluster;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;
import fr.freshperf.proxmox4j.entities.cluster.resources.PveClusterResources;
import fr.freshperf.proxmox4j.request.ProxmoxHttpClient;
import fr.freshperf.proxmox4j.request.ProxmoxRequest;
import fr.freshperf.proxmox4j.throwable.ProxmoxAPIError;

import java.util.List;

/**
 * Facade for Proxmox cluster management endpoints.
 */
public record PveCluster(ProxmoxHttpClient httpClient) {

    /**
     * Gets the cluster index (list of available endpoints).
     *
     * @return a request returning the cluster index
     */
    public ProxmoxRequest<List<PveClusterIndex>> getIndex() {
        return new ProxmoxRequest<>(() -> httpClient.get("cluster/").executeList(new TypeToken<List<PveClusterIndex>>(){}));
    }

    /**
     * Gets cluster status information.
     *
     * @return a request returning the cluster status
     */
    public ProxmoxRequest<List<PveClusterStatus>> getStatus() {
        return new ProxmoxRequest<>(() -> httpClient.get("cluster/status").executeList(new TypeToken<List<PveClusterStatus>>(){}));
    }

    /**
     * Gets the next available VMID.
     *
     * @return a request returning the next free VMID
     */
    public ProxmoxRequest<Integer> getNextId() {
        return new ProxmoxRequest<>(() -> httpClient.get("cluster/nextid").execute(Integer.class));
    }

    /**
     * Gets all cluster resources (VMs, containers, nodes, storages).
     *
     * @return a request returning all cluster resources
     */
    public ProxmoxRequest<List<PveClusterResources>> getResources() {
        return new ProxmoxRequest<>(() -> 
            httpClient.get("cluster/resources").executeList(new TypeToken<List<PveClusterResources>>(){})
        );
    }

    /**
     * Gets cluster resources filtered by type.
     *
     * @param type resource type: "vm", "storage", "node", or null for all
     * @return a request returning filtered cluster resources
     */
    public ProxmoxRequest<List<PveClusterResources>> getResources(String type) {
        return new ProxmoxRequest<>(() -> {
            var builder = httpClient.get("cluster/resources");
            if (type != null && !type.isEmpty()) {
                builder.param("type", type);
            }
            return builder.executeList(new TypeToken<List<PveClusterResources>>(){});
        });
    }

}
