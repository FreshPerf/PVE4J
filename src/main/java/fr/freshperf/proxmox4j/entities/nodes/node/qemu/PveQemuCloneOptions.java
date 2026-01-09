package fr.freshperf.proxmox4j.entities.nodes.node.qemu;

import fr.freshperf.proxmox4j.entities.options.ParameterizedParamsConvertible;
import fr.freshperf.proxmox4j.util.ParamsHelpers;

import java.util.Map;

/**
 * Options for cloning a QEMU VM/template.
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

    public PveQemuCloneOptions name(String name) {
        this.name = name;
        return this;
    }

    public PveQemuCloneOptions description(String description) {
        this.description = description;
        return this;
    }

    public PveQemuCloneOptions target(String target) {
        this.target = target;
        return this;
    }

    public PveQemuCloneOptions storage(String storage) {
        this.storage = storage;
        return this;
    }

    public PveQemuCloneOptions pool(String pool) {
        this.pool = pool;
        return this;
    }

    public PveQemuCloneOptions snapname(String snapname) {
        this.snapname = snapname;
        return this;
    }

    public PveQemuCloneOptions format(String format) {
        if (format != null && !format.equals("raw") && !format.equals("qcow2") && !format.equals("vmdk")) {
            throw new IllegalArgumentException("format must be one of: raw, qcow2, vmdk");
        }
        this.format = format;
        return this;
    }

    public PveQemuCloneOptions bwlimit(Integer bwlimit) {
        if (bwlimit != null && bwlimit < 0) {
            throw new IllegalArgumentException("bwlimit must be >= 0");
        }
        this.bwlimit = bwlimit;
        return this;
    }

    public PveQemuCloneOptions full(Boolean full) {
        this.full = full;
        return this;
    }
}

