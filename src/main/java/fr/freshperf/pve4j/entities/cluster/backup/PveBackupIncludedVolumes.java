package fr.freshperf.pve4j.entities.cluster.backup;

import java.util.List;

/**
 * Represents the tree returned by {@code GET /cluster/backup/{id}/included_volumes}.
 *
 * <p>The root holds a list of {@link Guest guests} covered by the job; each guest holds the
 * list of its {@link Volume volumes} along with whether they will be included in the backup
 * and the reason for that decision.</p>
 */
public class PveBackupIncludedVolumes {

    private List<Guest> children;

    /** @return the guests covered by the job (the children of the tree root). */
    public List<Guest> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "PveBackupIncludedVolumes{children=" + children + '}';
    }

    /**
     * A guest node of the included-volumes tree.
     */
    public static class Guest {
        private int id;
        private String name;
        private String type;
        private List<Volume> children;

        /** @return the VMID of the guest. */
        public int getId() {
            return id;
        }

        /** @return the name of the guest, or null. */
        public String getName() {
            return name;
        }

        /** @return the guest type ("qemu", "lxc" or "unknown" for removed-but-not-purged guests). */
        public String getType() {
            return type;
        }

        /** @return the volumes of the guest. */
        public List<Volume> getChildren() {
            return children;
        }

        @Override
        public String toString() {
            return "Guest{id=" + id + ", name='" + name + '\'' + ", type='" + type + '\'' +
                    ", children=" + children + '}';
        }
    }

    /**
     * A volume node of the included-volumes tree.
     */
    public static class Volume {
        private String id;
        private String name;
        // Stored raw to tolerate both numeric (0/1) and boolean (true/false) JSON encodings,
        // because the response transformer does not descend into deeply nested arrays.
        private String included;
        private String reason;

        /** @return the configuration key of the volume (e.g. "scsi0"). */
        public String getId() {
            return id;
        }

        /** @return the name of the volume. */
        public String getName() {
            return name;
        }

        /** @return whether the volume will be included in the backup. */
        public boolean isIncluded() {
            return "1".equals(included) || "true".equalsIgnoreCase(included);
        }

        /** @return the reason why the volume is included (or excluded). */
        public String getReason() {
            return reason;
        }

        @Override
        public String toString() {
            return "Volume{id='" + id + '\'' + ", name='" + name + '\'' +
                    ", included=" + isIncluded() + ", reason='" + reason + '\'' + '}';
        }
    }
}
