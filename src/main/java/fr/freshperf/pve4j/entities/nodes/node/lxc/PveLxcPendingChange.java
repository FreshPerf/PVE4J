package fr.freshperf.pve4j.entities.nodes.node.lxc;

/**
 * Represents a pending configuration change on an LXC container.
 */
public class PveLxcPendingChange {

    private Integer delete;
    private String key;
    private String pending;
    private String value;

    public Integer getDelete() {
        return delete;
    }

    public String getKey() {
        return key;
    }

    public String getPending() {
        return pending;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "PveLxcPendingChange{" +
                "delete=" + delete +
                ", key='" + key + '\'' +
                ", pending='" + pending + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
