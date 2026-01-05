package fr.freshperf.proxmox4j.entities.cluster;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
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

}
