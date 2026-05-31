package fr.freshperf.pve4j.entities.cluster.backup;

/**
 * Represents a guest returned by {@code GET /cluster/backup-info/not-backed-up},
 * i.e. a guest that is not covered by any backup job.
 */
public class PveBackupGuest {

    private int vmid;
    private String name;
    private String type;

    /** @return the VMID of the guest. */
    public int getVmid() {
        return vmid;
    }

    /** @return the name of the guest, or null. */
    public String getName() {
        return name;
    }

    /** @return the type of the guest ("qemu" or "lxc"). */
    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "PveBackupGuest{" +
                "vmid=" + vmid +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
