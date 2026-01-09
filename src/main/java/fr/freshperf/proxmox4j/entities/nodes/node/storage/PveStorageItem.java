package fr.freshperf.proxmox4j.entities.nodes.node.storage;

import com.google.gson.reflect.TypeToken;
import fr.freshperf.proxmox4j.request.ProxmoxHttpClient;
import fr.freshperf.proxmox4j.request.ProxmoxRequest;

import java.util.List;

/**
 * Facade for managing a specific storage.
 */
public record PveStorageItem(ProxmoxHttpClient client, String nodeName, String storageId) {

    /**
     * Gets the storage status.
     *
     * @return a request returning the storage status
     */
    public ProxmoxRequest<PveStorageStatus> getStatus() {
        return new ProxmoxRequest<>(() -> 
            client.get("nodes/" + nodeName + "/storage/" + storageId + "/status")
                .execute(PveStorageStatus.class)
        );
    }

    /**
     * Lists content on this storage (images, ISOs, etc.).
     *
     * @return a request returning the list of storage content
     */
    public ProxmoxRequest<List<PveStorageContent>> getContent() {
        return new ProxmoxRequest<>(() -> 
            client.get("nodes/" + nodeName + "/storage/" + storageId + "/content")
                .executeList(new TypeToken<List<PveStorageContent>>(){})
        );
    }

    /**
     * Gets RRD statistics (graph data).
     *
     * @return a request returning the RRD data
     */
    public ProxmoxRequest<PveStorageRrd> getRrd() {
        return new ProxmoxRequest<>(() -> 
            client.get("nodes/" + nodeName + "/storage/" + storageId + "/rrd")
                .execute(PveStorageRrd.class)
        );
    }
}

