package fr.freshperf.proxmox4j.entities.cluster;

import fr.freshperf.proxmox4j.request.ProxmoxHttpClient;
import fr.freshperf.proxmox4j.request.ProxmoxRequest;
import fr.freshperf.proxmox4j.throwable.ProxmoxAPIError;

public record PveCluster(ProxmoxHttpClient httpClient) {

    public ProxmoxRequest<PveClusterStatus> getStatus() {
        return new ProxmoxRequest<>(() -> httpClient.get("cluster/status").execute(PveClusterStatus.class));
    }

}
