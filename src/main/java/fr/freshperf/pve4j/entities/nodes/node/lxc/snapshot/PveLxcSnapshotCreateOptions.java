package fr.freshperf.pve4j.entities.nodes.node.lxc.snapshot;

import fr.freshperf.pve4j.entities.options.ParameterizedParamsConvertible;
import fr.freshperf.pve4j.util.ParamsHelpers;

import java.util.Map;

/**
 * Options for creating an LXC snapshot.
 */
public class PveLxcSnapshotCreateOptions implements ParameterizedParamsConvertible<String> {

    private String description;

    public static PveLxcSnapshotCreateOptions builder() {
        return new PveLxcSnapshotCreateOptions();
    }

    @Override
    public void addRequiredParam(Map<String, Object> params, String snapname) {
        if (snapname == null || snapname.isBlank()) {
            throw new IllegalArgumentException("snapname cannot be null or empty");
        }
        params.put("snapname", snapname);
    }

    @Override
    public void populateParams(Map<String, Object> params) {
        ParamsHelpers.put(params, "description", description);
    }

    public PveLxcSnapshotCreateOptions description(String description) {
        this.description = description;
        return this;
    }
}
