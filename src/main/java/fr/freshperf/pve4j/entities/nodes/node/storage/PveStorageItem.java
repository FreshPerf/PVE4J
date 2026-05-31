package fr.freshperf.pve4j.entities.nodes.node.storage;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import fr.freshperf.pve4j.entities.PveTask;
import fr.freshperf.pve4j.request.ProxmoxHttpClient;
import fr.freshperf.pve4j.request.ProxmoxRequest;
import fr.freshperf.pve4j.request.TaskResponseTransformer;
import fr.freshperf.pve4j.util.ParamsHelpers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * Lists content on this storage, optionally filtered by content type and/or owner VM.
     *
     * @param content only list content of this type (e.g. "backup", "images", "iso"), or null for all
     * @param vmid    only list content owned by this VM, or null for all
     * @return a request returning the list of storage content
     */
    public ProxmoxRequest<List<PveStorageContent>> getContent(String content, Integer vmid) {
        return new ProxmoxRequest<>(() -> {
            var builder = client.get("nodes/" + nodeName + "/storage/" + storageId + "/content");
            Map<String, Object> params = new HashMap<>();
            ParamsHelpers.put(params, "content", content);
            ParamsHelpers.putInt(params, "vmid", vmid);
            return builder.params(params).executeList(new TypeToken<List<PveStorageContent>>(){});
        });
    }

    /**
     * Lists all backups stored on this storage.
     *
     * @return a request returning the list of backup volumes
     */
    public ProxmoxRequest<List<PveStorageContent>> getBackups() {
        return getContent("backup", null);
    }

    /**
     * Lists the backups stored on this storage for a specific VM.
     *
     * @param vmid the owner VMID
     * @return a request returning the list of backup volumes for that VM
     */
    public ProxmoxRequest<List<PveStorageContent>> getBackups(int vmid) {
        return getContent("backup", vmid);
    }

    /**
     * Gets the attributes of a single volume (e.g. a backup file).
     *
     * @param volid the volume identifier (e.g. "local:backup/vzdump-qemu-100-...vma.zst")
     * @return a request returning the volume attributes
     * @throws IllegalArgumentException if volid is null or blank
     */
    public ProxmoxRequest<PveStorageVolume> getVolume(String volid) {
        if (volid == null || volid.isBlank()) {
            throw new IllegalArgumentException("volid cannot be null or empty");
        }
        return new ProxmoxRequest<>(() ->
            client.get("nodes/" + nodeName + "/storage/" + storageId + "/content/" + volid)
                .execute(PveStorageVolume.class)
        );
    }

    /**
     * Updates the editable attributes of a volume. Currently only the notes and protection
     * status are supported, and only for backups. Null arguments are left unchanged.
     *
     * @param volid       the volume identifier
     * @param notes       the new notes, or null to leave unchanged
     * @param protected_  the new protection status, or null to leave unchanged
     * @return a request that completes when the attributes have been updated
     * @throws IllegalArgumentException if volid is null or blank
     */
    public ProxmoxRequest<Void> updateVolumeAttributes(String volid, String notes, Boolean protected_) {
        if (volid == null || volid.isBlank()) {
            throw new IllegalArgumentException("volid cannot be null or empty");
        }
        return new ProxmoxRequest<Void>(() -> {
            Map<String, Object> params = new HashMap<>();
            ParamsHelpers.put(params, "notes", notes);
            ParamsHelpers.putBool(params, "protected", protected_);
            client.put("nodes/" + nodeName + "/storage/" + storageId + "/content/" + volid)
                .params(params)
                .execute(JsonObject.class);
            return null;
        });
    }

    /**
     * Deletes a volume (e.g. a backup file) from this storage.
     *
     * @param volid the volume identifier
     * @return a request returning the task for tracking (may resolve to a null task if the
     *         deletion finished synchronously)
     * @throws IllegalArgumentException if volid is null or blank
     */
    public ProxmoxRequest<PveTask> deleteVolume(String volid) {
        if (volid == null || volid.isBlank()) {
            throw new IllegalArgumentException("volid cannot be null or empty");
        }
        return new ProxmoxRequest<>(() ->
            client.delete("nodes/" + nodeName + "/storage/" + storageId + "/content/" + volid)
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    /**
     * Previews which backups would be kept or removed by a prune operation (dry-run).
     * This does not modify anything.
     *
     * @param options the prune options (must not be null)
     * @return a request returning the list of prune preview entries
     * @throws IllegalArgumentException if options is null
     */
    public ProxmoxRequest<List<PvePruneBackupEntry>> getPruneBackups(PvePruneBackupsOptions options) {
        if (options == null) {
            throw new IllegalArgumentException("options cannot be null");
        }
        return new ProxmoxRequest<>(() ->
            client.get("nodes/" + nodeName + "/storage/" + storageId + "/prunebackups")
                .params(options.toParams())
                .executeList(new TypeToken<List<PvePruneBackupEntry>>(){})
        );
    }

    /**
     * Prunes backups according to the given retention options. Only backups using the
     * standard naming scheme are considered; protected backups are never removed.
     *
     * @param options the prune options (must not be null)
     * @return a request returning the task for tracking
     * @throws IllegalArgumentException if options is null
     */
    public ProxmoxRequest<PveTask> pruneBackups(PvePruneBackupsOptions options) {
        if (options == null) {
            throw new IllegalArgumentException("options cannot be null");
        }
        return new ProxmoxRequest<>(() ->
            client.delete("nodes/" + nodeName + "/storage/" + storageId + "/prunebackups")
                .params(options.toParams())
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
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

