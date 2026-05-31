package fr.freshperf.pve4j.entities.nodes.node.qemu;

import fr.freshperf.pve4j.entities.options.ParameterizedParamsConvertible;
import fr.freshperf.pve4j.util.ParamsHelpers;

import java.util.Map;

/**
 * Options for restoring a QEMU VM from a backup archive via
 * {@code POST /nodes/{node}/qemu} (the {@code qmrestore} mode, triggered by the
 * {@code archive} parameter).
 *
 * <p>The {@link #archive(String)} value is mandatory and must be a Proxmox storage backup
 * volume identifier (e.g. {@code local:backup/vzdump-qemu-100-2024_01_01-00_00_00.vma.zst}).
 * Piping data from stdin ({@code -}) is not supported through this client.</p>
 *
 * <p>Use the builder pattern to configure the restore.</p>
 */
public class PveQemuRestoreOptions implements ParameterizedParamsConvertible<Integer> {

    private String archive;
    private Boolean force;
    private Boolean unique;
    private Boolean liveRestore;
    private String storage;
    private Integer bwlimit;
    private String pool;
    private Boolean start;
    private String name;
    private String description;

    /**
     * Creates a new builder for restore options.
     *
     * @return a new PveQemuRestoreOptions instance
     */
    public static PveQemuRestoreOptions builder() {
        return new PveQemuRestoreOptions();
    }

    @Override
    public void addRequiredParam(Map<String, Object> params, Integer vmid) {
        params.put("vmid", String.valueOf(vmid));
    }

    @Override
    public void populateParams(Map<String, Object> params) {
        ParamsHelpers.put(params, "archive", archive);
        ParamsHelpers.putBool(params, "force", force);
        ParamsHelpers.putBool(params, "unique", unique);
        ParamsHelpers.putBool(params, "live-restore", liveRestore);
        ParamsHelpers.put(params, "storage", storage);
        ParamsHelpers.putInt(params, "bwlimit", bwlimit);
        ParamsHelpers.put(params, "pool", pool);
        ParamsHelpers.putBool(params, "start", start);
        ParamsHelpers.put(params, "name", name);
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
     * {@code DATA:backup/vzdump-qemu-100-2024_01_01-00_00_00.vma.zst}), exactly as returned by
     * {@code PveStorageItem.getBackups(...)} ({@code PveStorageContent.getVolid()}). Passing a
     * bare filename or an absolute path is rejected by Proxmox with
     * <em>"Only root can pass arbitrary filesystem paths"</em>; use
     * {@link #archive(String, String)} if you only have the storage id and the file name.</p>
     *
     * @param archive a Proxmox storage backup volume identifier
     * @return this instance for method chaining
     */
    public PveQemuRestoreOptions archive(String archive) {
        this.archive = archive;
        return this;
    }

    /**
     * Sets the backup archive from a storage id and a backup file name, building the
     * {@code <storage>:backup/<filename>} volume identifier for you.
     *
     * @param storageId the storage holding the backup (e.g. "DATA")
     * @param fileName  the backup file name (e.g. "vzdump-qemu-100-2024_01_01-00_00_00.vma.zst")
     * @return this instance for method chaining
     * @throws IllegalArgumentException if storageId or fileName is null or blank
     */
    public PveQemuRestoreOptions archive(String storageId, String fileName) {
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
     * Allows overwriting an existing VM with the same VMID.
     *
     * @param force true to overwrite an existing VM
     * @return this instance for method chaining
     */
    public PveQemuRestoreOptions force(Boolean force) {
        this.force = force;
        return this;
    }

    /**
     * Assigns a unique random ethernet address to the restored VM.
     *
     * @param unique true to randomize MAC addresses
     * @return this instance for method chaining
     */
    public PveQemuRestoreOptions unique(Boolean unique) {
        this.unique = unique;
        return this;
    }

    /**
     * Starts the VM immediately while restoring in the background.
     *
     * @param liveRestore true to enable live restore
     * @return this instance for method chaining
     */
    public PveQemuRestoreOptions liveRestore(Boolean liveRestore) {
        this.liveRestore = liveRestore;
        return this;
    }

    /**
     * Sets the default storage used for the restored disks.
     *
     * @param storage the storage ID
     * @return this instance for method chaining
     */
    public PveQemuRestoreOptions storage(String storage) {
        this.storage = storage;
        return this;
    }

    /**
     * Overrides the I/O bandwidth limit (in KiB/s) for the restore.
     *
     * @param bwlimit the bandwidth limit (0 for unlimited)
     * @return this instance for method chaining
     */
    public PveQemuRestoreOptions bwlimit(Integer bwlimit) {
        this.bwlimit = bwlimit;
        return this;
    }

    /**
     * Adds the restored VM to the specified pool.
     *
     * @param pool the pool ID
     * @return this instance for method chaining
     */
    public PveQemuRestoreOptions pool(String pool) {
        this.pool = pool;
        return this;
    }

    /**
     * Starts the VM after it has been restored successfully.
     *
     * @param start true to start the VM after restore
     * @return this instance for method chaining
     */
    public PveQemuRestoreOptions start(Boolean start) {
        this.start = start;
        return this;
    }

    /**
     * Overrides the name of the restored VM.
     *
     * @param name the VM name
     * @return this instance for method chaining
     */
    public PveQemuRestoreOptions name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Overrides the description of the restored VM.
     *
     * @param description the VM description
     * @return this instance for method chaining
     */
    public PveQemuRestoreOptions description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Builds and returns the options instance.
     *
     * @return this instance
     */
    public PveQemuRestoreOptions build() {
        return this;
    }
}
