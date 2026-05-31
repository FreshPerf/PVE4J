package fr.freshperf.pve4j.entities.nodes.node.storage;

import fr.freshperf.pve4j.entities.options.ParamsConvertible;
import fr.freshperf.pve4j.util.ParamsHelpers;

import java.util.Map;

/**
 * Options for the prune-backups endpoints
 * ({@code GET}/{@code DELETE /nodes/{node}/storage/{storage}/prunebackups}).
 *
 * <p>The same options are used for the dry-run preview and the actual prune. When no
 * retention string is given via {@link #pruneBackups(String)}, the storage configuration's
 * retention settings are used.</p>
 */
public class PvePruneBackupsOptions implements ParamsConvertible {

    private String pruneBackups;
    private String type;
    private Integer vmid;

    /**
     * Creates a new builder for prune-backups options.
     *
     * @return a new PvePruneBackupsOptions instance
     */
    public static PvePruneBackupsOptions builder() {
        return new PvePruneBackupsOptions();
    }

    @Override
    public void populateParams(Map<String, Object> params) {
        ParamsHelpers.put(params, "prune-backups", pruneBackups);
        ParamsHelpers.put(params, "type", type);
        ParamsHelpers.putInt(params, "vmid", vmid);
    }

    /**
     * Sets the retention options to use instead of those from the storage configuration.
     *
     * @param pruneBackups e.g. "keep-last=3,keep-daily=7,keep-weekly=4"
     * @return this instance for method chaining
     */
    public PvePruneBackupsOptions pruneBackups(String pruneBackups) {
        this.pruneBackups = pruneBackups;
        return this;
    }

    /**
     * Restricts pruning to backups of the given guest type.
     *
     * @param type "qemu" or "lxc"
     * @return this instance for method chaining
     */
    public PvePruneBackupsOptions type(String type) {
        this.type = type;
        return this;
    }

    /**
     * Restricts pruning to backups of the given guest.
     *
     * @param vmid the VMID
     * @return this instance for method chaining
     */
    public PvePruneBackupsOptions vmid(Integer vmid) {
        this.vmid = vmid;
        return this;
    }

    /**
     * Builds and returns the options instance.
     *
     * @return this instance
     */
    public PvePruneBackupsOptions build() {
        return this;
    }
}
