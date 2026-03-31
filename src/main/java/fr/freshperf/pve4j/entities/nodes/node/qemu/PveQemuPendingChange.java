package fr.freshperf.pve4j.entities.nodes.node.qemu;

/**
 * Represents a pending configuration change on a QEMU VM.
 */
public class PveQemuPendingChange {

    private int delete;
    private String key;
    private String pending;
    private String value;

    public int getDelete() {
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
        return "PveQemuPendingChange{" +
                "delete=" + delete +
                ", key='" + key + '\'' +
                ", pending='" + pending + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
