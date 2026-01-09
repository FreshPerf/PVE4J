package fr.freshperf.proxmox4j.entities.nodes.node.storage;

/**
 * Represents detailed storage status information.
 */
public class PveStorageStatus {

    private String storage, type, content;
    private long total, used, avail;
    private boolean active, enabled, shared;
    private float used_fraction;

    public String getStorage() {
        return storage;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public long getTotal() {
        return total;
    }

    public long getUsed() {
        return used;
    }

    public long getAvail() {
        return avail;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isShared() {
        return shared;
    }

    public float getUsed_fraction() {
        return used_fraction;
    }

    @Override
    public String toString() {
        return "PveStorageStatus{" +
                "storage='" + storage + '\'' +
                ", type='" + type + '\'' +
                ", active=" + active +
                ", total=" + total +
                ", used=" + used +
                ", avail=" + avail +
                '}';
    }
}

