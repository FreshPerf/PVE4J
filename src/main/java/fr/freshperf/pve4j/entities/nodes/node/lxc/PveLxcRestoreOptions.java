package fr.freshperf.pve4j.entities.nodes.node.lxc;

import fr.freshperf.pve4j.entities.options.ParameterizedParamsConvertible;
import fr.freshperf.pve4j.util.ParamsHelpers;

import java.util.Map;

/**
 * Options for restoring an LXC container from a backup archive via
 * {@code POST /nodes/{node}/lxc} (the restore mode, triggered by {@code restore=1} together
 * with the backup archive passed as {@code ostemplate}).
 *
 * <p>The {@link #archive(String)} value is mandatory and must be a Proxmox storage backup
 * volume identifier (e.g. {@code local:backup/vzdump-lxc-100-2024_01_01-00_00_00.tar.zst}),
 * exactly as returned by {@code PveStorageItem.getBackups(...)}.</p>
 *
 * <p>Use the builder pattern to configure the restore.</p>
 */
public class PveLxcRestoreOptions implements ParameterizedParamsConvertible<Integer> {

    private String archive;
    private Boolean force;
    private Boolean unique;
    private String storage;
    private Integer bwlimit;
    private String pool;
    private Boolean start;
    private String hostname;
    private String password;
    private String description;

    /**
     * Creates a new builder for restore options.
     *
     * @return a new PveLxcRestoreOptions instance
     */
    public static PveLxcRestoreOptions builder() {
        return new PveLxcRestoreOptions();
    }

    @Override
    public void addRequiredParam(Map<String, Object> params, Integer vmid) {
        params.put("vmid", String.valueOf(vmid));
    }

    @Override
    public void populateParams(Map<String, Object> params) {
        // This endpoint is shared with container creation; restore=1 flags it as a restore task
        // and the backup archive is passed through ostemplate.
        ParamsHelpers.putBool(params, "restore", true);
        ParamsHelpers.put(params, "ostemplate", archive);
        ParamsHelpers.putBool(params, "force", force);
        ParamsHelpers.putBool(params, "unique", unique);
        ParamsHelpers.put(params, "storage", storage);
        ParamsHelpers.putInt(params, "bwlimit", bwlimit);
        ParamsHelpers.put(params, "pool", pool);
        ParamsHelpers.putBool(params, "start", start);
        ParamsHelpers.put(params, "hostname", hostname);
        ParamsHelpers.put(params, "password", password);
        ParamsHelpers.put(params, "description", description);
    }

    /**
     * @return the configured backup archive volume identifier, or null if not set.
     */
    public String getArchive() {
        return archive;
    }

    /**
     * Sets the backup archive to restore from (mandatory).
     *
     * <p>This must be a Proxmox storage backup <strong>volume identifier</strong> in the form
     * {@code <storage>:backup/<filename>} (e.g.
     * {@code DATA:backup/vzdump-lxc-100-2024_01_01-00_00_00.tar.zst}), exactly as returned by
     * {@code PveStorageItem.getBackups(...)} ({@code PveStorageContent.getVolid()}). Passing a
     * bare filename or an absolute path is rejected by Proxmox with
     * <em>"Only root can pass arbitrary filesystem paths"</em>; use
     * {@link #archive(String, String)} if you only have the storage id and the file name.</p>
     *
     * @param archive a Proxmox storage backup volume identifier
     * @return this instance for method chaining
     */
    public PveLxcRestoreOptions archive(String archive) {
        this.archive = archive;
        return this;
    }

    /**
     * Sets the backup archive from a storage id and a backup file name, building the
     * {@code <storage>:backup/<filename>} volume identifier for you.
     *
     * @param storageId the storage holding the backup (e.g. "DATA")
     * @param fileName  the backup file name (e.g. "vzdump-lxc-100-2024_01_01-00_00_00.tar.zst")
     * @return this instance for method chaining
     * @throws IllegalArgumentException if storageId or fileName is null or blank
     */
    public PveLxcRestoreOptions archive(String storageId, String fileName) {
        if (storageId == null || storageId.isBlank()) {
            throw new IllegalArgumentException("storageId cannot be null or empty");
        }
        if (fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException("fileName cannot be null or empty");
        }
        this.archive = storageId + ":backup/" + fileName;
        return this;
    }

    /**
     * Allows overwriting an existing container with the same VMID.
     *
     * @param force true to overwrite an existing container
     * @return this instance for method chaining
     */
    public PveLxcRestoreOptions force(Boolean force) {
        this.force = force;
        return this;
    }

    /**
     * Assigns a unique random ethernet address to the restored container.
     *
     * @param unique true to randomize MAC addresses
     * @return this instance for method chaining
     */
    public PveLxcRestoreOptions unique(Boolean unique) {
        this.unique = unique;
        return this;
    }

    /**
     * Sets the default storage used for the restored container volumes.
     *
     * @param storage the storage ID
     * @return this instance for method chaining
     */
    public PveLxcRestoreOptions storage(String storage) {
        this.storage = storage;
        return this;
    }

    /**
     * Overrides the I/O bandwidth limit (in KiB/s) for the restore.
     *
     * @param bwlimit the bandwidth limit (0 for unlimited)
     * @return this instance for method chaining
     */
    public PveLxcRestoreOptions bwlimit(Integer bwlimit) {
        this.bwlimit = bwlimit;
        return this;
    }

    /**
     * Adds the restored container to the specified pool.
     *
     * @param pool the pool ID
     * @return this instance for method chaining
     */
    public PveLxcRestoreOptions pool(String pool) {
        this.pool = pool;
        return this;
    }

    /**
     * Starts the container after it has been restored successfully.
     *
     * @param start true to start the container after restore
     * @return this instance for method chaining
     */
    public PveLxcRestoreOptions start(Boolean start) {
        this.start = start;
        return this;
    }

    /**
     * Overrides the hostname of the restored container.
     *
     * @param hostname the container hostname
     * @return this instance for method chaining
     */
    public PveLxcRestoreOptions hostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    /**
     * Sets the root password inside the restored container.
     *
     * @param password the root password (at least 5 characters)
     * @return this instance for method chaining
     */
    public PveLxcRestoreOptions password(String password) {
        this.password = password;
        return this;
    }

    /**
     * Overrides the description (notes) of the restored container.
     *
     * @param description the container description
     * @return this instance for method chaining
     */
    public PveLxcRestoreOptions description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Builds and returns the options instance.
     *
     * @return this instance
     */
    public PveLxcRestoreOptions build() {
        return this;
    }
}
