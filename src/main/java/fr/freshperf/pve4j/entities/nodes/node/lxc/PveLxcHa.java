package fr.freshperf.pve4j.entities.nodes.node.lxc;

/**
 * Represents High Availability configuration for an LXC container.
 */
public class PveLxcHa {

    private Integer managed;

    public Integer getManaged() {
        return managed;
    }

    public boolean isManaged() {
        return managed != null && managed == 1;
    }

    @Override
    public String toString() {
        return "PveLxcHa{" +
                "managed=" + managed +
                '}';
    }
}
