package fr.freshperf.pve4j.entities.nodes.node.lxc;

import fr.freshperf.pve4j.entities.options.ParameterizedParamsConvertible;
import fr.freshperf.pve4j.util.ParamsHelpers;

import java.util.Map;

/**
 * Options for migrating an LXC container.
 */
public class PveLxcMigrateOptions implements ParameterizedParamsConvertible<String> {

    private Integer bwlimit;
    private Boolean online;
    private Boolean restart;
    private String targetStorage;
    private Integer timeout;

    public static PveLxcMigrateOptions builder() {
        return new PveLxcMigrateOptions();
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
        ParamsHelpers.putInt(params, "bwlimit", bwlimit);
        ParamsHelpers.putBool(params, "online", online);
        ParamsHelpers.putBool(params, "restart", restart);
        ParamsHelpers.put(params, "target-storage", targetStorage);
        ParamsHelpers.putInt(params, "timeout", timeout);
    }

    public PveLxcMigrateOptions bwlimit(Integer bwlimit) {
        if (bwlimit != null && bwlimit < 0) {
            throw new IllegalArgumentException("bwlimit must be >= 0");
        }
        this.bwlimit = bwlimit;
        return this;
    }

    public PveLxcMigrateOptions online(Boolean online) {
        this.online = online;
        return this;
    }

    public PveLxcMigrateOptions restart(Boolean restart) {
        this.restart = restart;
        return this;
    }

    public PveLxcMigrateOptions targetStorage(String targetStorage) {
        this.targetStorage = targetStorage;
        return this;
    }

    public PveLxcMigrateOptions timeout(Integer timeout) {
        if (timeout != null && timeout < 0) {
            throw new IllegalArgumentException("timeout must be >= 0");
        }
        this.timeout = timeout;
        return this;
    }
}
