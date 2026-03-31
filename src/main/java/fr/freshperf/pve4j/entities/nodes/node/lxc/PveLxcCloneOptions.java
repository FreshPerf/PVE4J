package fr.freshperf.pve4j.entities.nodes.node.lxc;

import fr.freshperf.pve4j.entities.options.ParameterizedParamsConvertible;
import fr.freshperf.pve4j.util.ParamsHelpers;

import java.util.Map;

/**
 * Options for cloning an LXC container.
 */
public class PveLxcCloneOptions implements ParameterizedParamsConvertible<Integer> {

    private Integer bwlimit;
    private String description;
    private Boolean full;
    private String hostname;
    private String pool;
    private String snapname;
    private String storage;
    private String target;

    public static PveLxcCloneOptions builder() {
        return new PveLxcCloneOptions();
    }

    @Override
    public void addRequiredParam(Map<String, Object> params, Integer newVmid) {
        params.put("newid", String.valueOf(newVmid));
    }

    @Override
    public void populateParams(Map<String, Object> params) {
        ParamsHelpers.putInt(params, "bwlimit", bwlimit);
        ParamsHelpers.put(params, "description", description);
        ParamsHelpers.putBool(params, "full", full);
        ParamsHelpers.put(params, "hostname", hostname);
        ParamsHelpers.put(params, "pool", pool);
        ParamsHelpers.put(params, "snapname", snapname);
        ParamsHelpers.put(params, "storage", storage);
        ParamsHelpers.put(params, "target", target);
    }

    public PveLxcCloneOptions bwlimit(Integer bwlimit) {
        if (bwlimit != null && bwlimit < 0) {
            throw new IllegalArgumentException("bwlimit must be >= 0");
        }
        this.bwlimit = bwlimit;
        return this;
    }

    public PveLxcCloneOptions description(String description) {
        this.description = description;
        return this;
    }

    public PveLxcCloneOptions full(Boolean full) {
        this.full = full;
        return this;
    }

    public PveLxcCloneOptions hostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    public PveLxcCloneOptions pool(String pool) {
        this.pool = pool;
        return this;
    }

    public PveLxcCloneOptions snapname(String snapname) {
        this.snapname = snapname;
        return this;
    }

    public PveLxcCloneOptions storage(String storage) {
        this.storage = storage;
        return this;
    }

    public PveLxcCloneOptions target(String target) {
        this.target = target;
        return this;
    }
}
