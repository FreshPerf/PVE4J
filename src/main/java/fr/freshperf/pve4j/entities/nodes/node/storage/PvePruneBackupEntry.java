package fr.freshperf.pve4j.entities.nodes.node.storage;

/**
 * Represents a single entry of a prune-backups preview/result, as returned by
 * {@code GET /nodes/{node}/storage/{storage}/prunebackups} (dry-run).
 *
 * <p>Each entry describes a backup and whether it would be kept or removed when applying
 * the configured retention options.</p>
 */
public class PvePruneBackupEntry {

    private String volid;
    private int vmid;
    private String type;
    private long ctime;
    private String mark;

    /** @return the backup volume identifier. */
    public String getVolid() {
        return volid;
    }

    /** @return the VMID the backup belongs to. */
    public int getVmid() {
        return vmid;
    }

    /** @return the guest type ("qemu", "lxc", "openvz" or "unknown"). */
    public String getType() {
        return type;
    }

    /** @return the creation time of the backup (seconds since the UNIX epoch). */
    public long getCtime() {
        return ctime;
    }

    /**
     * @return whether the backup would be kept or removed: one of "keep", "remove",
     *         "protected" or "renamed". Protected backups and those not using the standard
     *         naming scheme are not removed.
     */
    public String getMark() {
        return mark;
    }

    @Override
    public String toString() {
        return "PvePruneBackupEntry{" +
                "volid='" + volid + '\'' +
                ", vmid=" + vmid +
                ", type='" + type + '\'' +
                ", ctime=" + ctime +
                ", mark='" + mark + '\'' +
                '}';
    }
}
