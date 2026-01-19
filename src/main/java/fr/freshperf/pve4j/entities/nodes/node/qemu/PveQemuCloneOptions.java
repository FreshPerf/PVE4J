package fr.freshperf.pve4j.entities.nodes.node.qemu;

import fr.freshperf.pve4j.entities.options.ParameterizedParamsConvertible;
import fr.freshperf.pve4j.util.ParamsHelpers;

import java.util.Map;

/**
 * Options for cloning a QEMU VM/template.
 * Use the builder pattern to configure clone settings.
 */
public class PveQemuCloneOptions implements ParameterizedParamsConvertible<Integer> {

    private String name;
    private String description;
    private String target;
    private String storage;
    private String pool;
    private String snapname;
    private String format;
    private Integer bwlimit;
    private Boolean full;

    /**
     * Creates a new builder for clone options.
     *
     * @return a new PveQemuCloneOptions instance
     */
    public static PveQemuCloneOptions builder() {
        return new PveQemuCloneOptions();
    }

    @Override
    public void addRequiredParam(Map<String, Object> params, Integer newVmid) {
        params.put("newid", String.valueOf(newVmid));
    }

    @Override
    public void populateParams(Map<String, Object> params) {
        ParamsHelpers.put(params, "name", name);
        ParamsHelpers.put(params, "description", description);
        ParamsHelpers.put(params, "target", target);
        ParamsHelpers.put(params, "storage", storage);
        ParamsHelpers.put(params, "pool", pool);
        ParamsHelpers.put(params, "snapname", snapname);
        ParamsHelpers.put(params, "format", format);
        ParamsHelpers.putInt(params, "bwlimit", bwlimit);
        ParamsHelpers.putBool(params, "full", full);
    }

    /** Sets the new VM name. */
    public PveQemuCloneOptions name(String name) {
        this.name = name;
        return this;
    }

    /** Sets the VM description. */
    public PveQemuCloneOptions description(String description) {
        this.description = description;
        return this;
    }

    /** Sets the target node for the clone. */
    public PveQemuCloneOptions target(String target) {
        this.target = target;
        return this;
    }

    /** Sets the target storage for the clone. */
    public PveQemuCloneOptions storage(String storage) {
        this.storage = storage;
        return this;
    }

    /** Sets the resource pool for the clone. */
    public PveQemuCloneOptions pool(String pool) {
        this.pool = pool;
        return this;
    }

    /** Sets the snapshot name to clone from. */
    public PveQemuCloneOptions snapname(String snapname) {
        this.snapname = snapname;
        return this;
    }

    /**
     * Sets the disk format (raw, qcow2, vmdk).
     *
     * @throws IllegalArgumentException if format is invalid
     */
    public PveQemuCloneOptions format(String format) {
        if (format != null && !format.equals("raw") && !format.equals("qcow2") && !format.equals("vmdk")) {
            throw new IllegalArgumentException("format must be one of: raw, qcow2, vmdk");
        }
        this.format = format;
        return this;
    }

    /**
     * Sets the bandwidth limit in KiB/s.
     *
     * @throws IllegalArgumentException if bwlimit is negative
     */
    public PveQemuCloneOptions bwlimit(Integer bwlimit) {
        if (bwlimit != null && bwlimit < 0) {
            throw new IllegalArgumentException("bwlimit must be >= 0");
        }
        this.bwlimit = bwlimit;
        return this;
    }

    /** Sets whether to create a full clone (true) or linked clone (false). */
    public PveQemuCloneOptions full(Boolean full) {
        this.full = full;
        return this;
    }
}

