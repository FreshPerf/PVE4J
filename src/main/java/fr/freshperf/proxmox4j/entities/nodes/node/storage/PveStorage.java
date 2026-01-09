package fr.freshperf.proxmox4j.entities.nodes.node.storage;

import com.google.gson.reflect.TypeToken;
import fr.freshperf.proxmox4j.request.ProxmoxHttpClient;
import fr.freshperf.proxmox4j.request.ProxmoxRequest;

import java.util.List;

/**
 * Facade for storage management on a node.
 */
public record PveStorage(ProxmoxHttpClient client, String nodeName) {

    /**
     * Lists all storages on this node.
     *
     * @return a request returning the list of storages
     */
    public ProxmoxRequest<List<PveStorageIndex>> getIndex() {
        return new ProxmoxRequest<>(() -> 
            client.get("nodes/" + nodeName + "/storage").executeList(new TypeToken<List<PveStorageIndex>>(){})
        );
    }

    /**
     * Gets a specific storage by ID.
     *
     * @param storageId the storage ID
     * @return the storage API facade
     * @throws IllegalArgumentException if storageId is null or empty
     */
    public PveStorageItem get(String storageId) {
        if (storageId == null || storageId.isEmpty()) {
            throw new IllegalArgumentException("Storage ID cannot be null or empty");
        }
        return new PveStorageItem(client, nodeName, storageId);
    }
}

