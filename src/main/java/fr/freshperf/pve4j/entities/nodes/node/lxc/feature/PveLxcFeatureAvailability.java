package fr.freshperf.pve4j.entities.nodes.node.lxc.feature;

/**
 * Represents feature availability for an LXC container.
 */
public class PveLxcFeatureAvailability {

    private Boolean hasFeature;

    public Boolean getHasFeature() {
        return hasFeature;
    }

    public boolean isAvailable() {
        return Boolean.TRUE.equals(hasFeature);
    }

    @Override
    public String toString() {
        return "PveLxcFeatureAvailability{" +
                "hasFeature=" + hasFeature +
                '}';
    }
}
