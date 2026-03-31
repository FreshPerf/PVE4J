package fr.freshperf.pve4j.entities.nodes.node.qemu;

import fr.freshperf.pve4j.entities.options.ParameterizedParamsConvertible;
import fr.freshperf.pve4j.util.ParamsHelpers;

import java.util.Map;

/**
 * Options for unlinking QEMU VM disks.
 */
public class PveQemuUnlinkOptions implements ParameterizedParamsConvertible<String> {

    private Boolean force;

    /**
     * Creates a new builder for unlink options.
     *
     * @return a new PveQemuUnlinkOptions instance
     */
    public static PveQemuUnlinkOptions builder() {
        return new PveQemuUnlinkOptions();
    }

    @Override
    public void addRequiredParam(Map<String, Object> params, String idlist) {
        if (idlist == null || idlist.isBlank()) {
            throw new IllegalArgumentException("idlist cannot be null or empty");
        }
        params.put("idlist", idlist);
    }

    @Override
    public void populateParams(Map<String, Object> params) {
        ParamsHelpers.putBool(params, "force", force);
    }

    /** Sets whether to force physical removal. */
    public PveQemuUnlinkOptions force(Boolean force) {
        this.force = force;
        return this;
    }
}
