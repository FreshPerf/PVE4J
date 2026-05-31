package fr.freshperf.pve4j.entities.nodes.node.vzdump;

import fr.freshperf.pve4j.entities.PveTask;
import fr.freshperf.pve4j.request.ProxmoxHttpClient;
import fr.freshperf.pve4j.request.ProxmoxRequest;
import fr.freshperf.pve4j.request.TaskResponseTransformer;

/**
 * Facade for the {@code vzdump} backup endpoints of a node
 * ({@code /nodes/{node}/vzdump}).
 *
 * <p>This exposes the node-level backup operations: launching a backup of one or more
 * guests, reading the currently configured backup defaults, and extracting a guest
 * configuration from an existing backup archive.</p>
 */
public record PveVzdump(ProxmoxHttpClient client, String nodeName) {

    /**
     * Creates a backup of the guest(s) selected by the given options.
     *
     * @param options the backup options (must not be null)
     * @return a request returning the task for tracking
     * @throws IllegalArgumentException if options is null
     */
    public ProxmoxRequest<PveTask> backup(PveVzdumpOptions options) {
        if (options == null) {
            throw new IllegalArgumentException("options cannot be null");
        }
        return new ProxmoxRequest<>(() ->
            client.post("nodes/" + nodeName + "/vzdump")
                .params(options.toParams())
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    /**
     * Gets the currently configured vzdump defaults for this node.
     *
     * @return a request returning the configured defaults
     */
    public ProxmoxRequest<PveVzdumpDefaults> getDefaults() {
        return getDefaults(null);
    }

    /**
     * Gets the currently configured vzdump defaults for a specific storage.
     *
     * @param storage the storage identifier, or null to use the default storage
     * @return a request returning the configured defaults
     */
    public ProxmoxRequest<PveVzdumpDefaults> getDefaults(String storage) {
        return new ProxmoxRequest<>(() -> {
            var builder = client.get("nodes/" + nodeName + "/vzdump/defaults");
            if (storage != null && !storage.isBlank()) {
                builder.param("storage", storage);
            }
            return builder.execute(PveVzdumpDefaults.class);
        });
    }

    /**
     * Extracts the guest configuration from a vzdump backup archive.
     *
     * @param volume the backup volume identifier (e.g. "local:backup/vzdump-qemu-100-...vma.zst")
     * @return a request returning the raw configuration text of the archived guest
     * @throws IllegalArgumentException if volume is null or blank
     */
    public ProxmoxRequest<String> extractConfig(String volume) {
        if (volume == null || volume.isBlank()) {
            throw new IllegalArgumentException("volume cannot be null or empty");
        }
        return new ProxmoxRequest<>(() ->
            client.get("nodes/" + nodeName + "/vzdump/extractconfig")
                .param("volume", volume)
                .execute(String.class)
        );
    }
}
