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

public record PveCluster(ProxmoxHttpClient httpClient) {

    public ProxmoxRequest<List<PveClusterIndex>> getIndex() {
        return new ProxmoxRequest<>(() -> httpClient.get("cluster/").executeList(new TypeToken<List<PveClusterIndex>>(){}));
    }

    public ProxmoxRequest<List<PveClusterStatus>> getStatus() {
        return new ProxmoxRequest<>(() -> httpClient.get("cluster/status").executeList(new TypeToken<List<PveClusterStatus>>(){}));
    }

    public ProxmoxRequest<Integer> getNextId() {
        return new ProxmoxRequest<>(() -> httpClient.get("cluster/nextid").execute(Integer.class));
    }

    /**
     * Gets cluster resources (all VMs, containers, nodes, storages)
     */
    public ProxmoxRequest<List<PveClusterResources>> getResources() {
        return new ProxmoxRequest<>(() -> 
            httpClient.get("cluster/resources").executeList(new TypeToken<List<PveClusterResources>>(){})
        );
    }

    /**
     * Gets cluster resources filtered by type
     * @param type Resource type: "vm", "storage", "node", or null for all
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
