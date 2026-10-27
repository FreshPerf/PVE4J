package fr.freshperf.pve4j.entities.nodes.node.qemu;

import fr.freshperf.pve4j.entities.options.ParameterizedParamsConvertible;
import fr.freshperf.pve4j.util.ParamsHelpers;

import java.util.Map;

/**
 * Options for migrating a QEMU VM to another node.
 * Use the builder pattern to configure migration settings.
 */
public class PveQemuMigrateOptions implements ParameterizedParamsConvertible<String> {

    private Boolean online;
    private Boolean withLocalDisks;
    private Boolean withConntrackState;
    private String targetstorage;
    private Integer bwlimit;
    private String migrationNetwork;
    private String migrationType;
    private Boolean force;

    /**
     * Creates empty migrate options; {@link #builder()} is the usual entry point.
     */
    public PveQemuMigrateOptions() {
    }

    /**
     * Creates a new builder for migrate options.
     *
     * @return a new PveQemuMigrateOptions instance
     */
    public static PveQemuMigrateOptions builder() {
        return new PveQemuMigrateOptions();
    }

    @Override
    public void addRequiredParam(Map<String, Object> params, String targetNode) {
        if (targetNode == null || targetNode.isBlank()) {
            throw new IllegalArgumentException("targetNode cannot be null or empty");
        }
        params.put("target", targetNode);
    }

    @Override
    public void populateParams(Map<String, Object> params) {
        ParamsHelpers.putBool(params, "online", online);
        ParamsHelpers.putBool(params, "with-local-disks", withLocalDisks);
        ParamsHelpers.putBool(params, "with-conntrack-state", withConntrackState);
        ParamsHelpers.put(params, "targetstorage", targetstorage);
        ParamsHelpers.putInt(params, "bwlimit", bwlimit);
        ParamsHelpers.put(params, "migration_network", migrationNetwork);
        ParamsHelpers.put(params, "migration_type", migrationType);
        ParamsHelpers.putBool(params, "force", force);
    }

    /**
     * Sets whether to live migrate a running VM.
     *
     * @param online true for a live migration
     * @return this instance for method chaining
     */
    public PveQemuMigrateOptions online(Boolean online) {
        this.online = online;
        return this;
    }

    /**
     * Sets whether to migrate local disks along with the VM.
     * Required to live migrate a running VM that has disks on local storage.
     *
     * @param withLocalDisks true to migrate local disks
     * @return this instance for method chaining
     */
    public PveQemuMigrateOptions withLocalDisks(Boolean withLocalDisks) {
        this.withLocalDisks = withLocalDisks;
        return this;
    }

    /**
     * Sets whether to migrate the conntrack state (online migration only).
     *
     * @param withConntrackState true to migrate the conntrack state
     * @return this instance for method chaining
     */
    public PveQemuMigrateOptions withConntrackState(Boolean withConntrackState) {
        this.withConntrackState = withConntrackState;
        return this;
    }

    /**
     * Sets the target storage mapping for local disks.
     * A single storage ID maps all source storages, or use "source:target" pairs separated by commas.
     *
     * @param targetstorage the target storage ID or mapping
     * @return this instance for method chaining
     */
    public PveQemuMigrateOptions targetstorage(String targetstorage) {
        this.targetstorage = targetstorage;
        return this;
    }

    /**
     * Sets the I/O bandwidth limit in KiB/s.
     *
     * @param bwlimit the bandwidth limit (at least 0)
     * @return this instance for method chaining
     * @throws IllegalArgumentException if bwlimit is negative
     */
    public PveQemuMigrateOptions bwlimit(Integer bwlimit) {
        if (bwlimit != null && bwlimit < 0) {
            throw new IllegalArgumentException("bwlimit must be >= 0");
        }
        this.bwlimit = bwlimit;
        return this;
    }

    /**
     * Sets the CIDR of the (sub)network used for migration.
     *
     * @param migrationNetwork the migration network CIDR
     * @return this instance for method chaining
     */
    public PveQemuMigrateOptions migrationNetwork(String migrationNetwork) {
        this.migrationNetwork = migrationNetwork;
        return this;
    }

    /**
     * Sets the migration traffic type ("secure" or "insecure").
     *
     * @param migrationType the migration type
     * @return this instance for method chaining
     */
    public PveQemuMigrateOptions migrationType(String migrationType) {
        this.migrationType = migrationType;
        return this;
    }

    /**
     * Allows migration of VMs using local devices (root only).
     *
     * @param force true to allow local devices
     * @return this instance for method chaining
     */
    public PveQemuMigrateOptions force(Boolean force) {
        this.force = force;
        return this;
    }
}
