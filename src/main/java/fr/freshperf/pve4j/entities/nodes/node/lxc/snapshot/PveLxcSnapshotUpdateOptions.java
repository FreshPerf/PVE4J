package fr.freshperf.pve4j.entities.nodes.node.lxc.snapshot;

import fr.freshperf.pve4j.entities.options.ParamsConvertible;
import fr.freshperf.pve4j.util.ParamsHelpers;

import java.util.Map;

/**
 * Options for updating LXC snapshot metadata.
 */
public class PveLxcSnapshotUpdateOptions implements ParamsConvertible {

    private String description;

    public static PveLxcSnapshotUpdateOptions builder() {
        return new PveLxcSnapshotUpdateOptions();
    }

    @Override
    public void populateParams(Map<String, Object> params) {
        ParamsHelpers.put(params, "description", description);
    }

    public PveLxcSnapshotUpdateOptions description(String description) {
        this.description = description;
        return this;
    }
}
