package fr.freshperf.pve4j.entities.nodes.node.vzdump;

import com.google.gson.annotations.SerializedName;

/**
 * Represents the currently configured vzdump defaults for a node, as returned by
 * {@code GET /nodes/{node}/vzdump/defaults}.
 *
 * <p>These are the effective default backup parameters that would be used if a backup
 * were launched without overriding them. Some properties are only populated when the
 * caller has {@code Sys.Audit} permission on the node.</p>
 */
public class PveVzdumpDefaults {

    private String storage;
    private String mode;
    private String compress;
    private int bwlimit;
    private int ionice;
    private int lockwait;
    private int stopwait;
    private int pigz;
    private int zstd;
    private boolean remove;
    @SerializedName("protected")
    private boolean isProtected;
    @SerializedName("notes-template")
    private String notesTemplate;
    @SerializedName("prune-backups")
    private String pruneBackups;
    private String performance;
    private String fleecing;
    private String dumpdir;
    private String mailnotification;
    private String mailto;
    @SerializedName("notification-mode")
    private String notificationMode;
    @SerializedName("pbs-change-detection-mode")
    private String pbsChangeDetectionMode;
    private boolean all;
    private String pool;
    private String exclude;
    private String script;
    private String tmpdir;
    private boolean quiet;
    private boolean stdexcludes;
    private boolean stop;

    /** @return the default target storage identifier, or null if none is configured. */
    public String getStorage() {
        return storage;
    }

    /** @return the default backup mode ("snapshot", "suspend" or "stop"). */
    public String getMode() {
        return mode;
    }

    /** @return the default compression algorithm ("0", "1", "gzip", "lzo" or "zstd"). */
    public String getCompress() {
        return compress;
    }

    /** @return the default I/O bandwidth limit in KiB/s (0 means unlimited). */
    public int getBwlimit() {
        return bwlimit;
    }

    /** @return the default I/O nice priority for the BFQ scheduler (0-8). */
    public int getIonice() {
        return ionice;
    }

    /** @return the maximal time to wait for the global lock, in minutes. */
    public int getLockwait() {
        return lockwait;
    }

    /** @return the maximal time to wait until a guest system is stopped, in minutes. */
    public int getStopwait() {
        return stopwait;
    }

    /** @return the pigz thread setting. */
    public int getPigz() {
        return pigz;
    }

    /** @return the zstd thread setting. */
    public int getZstd() {
        return zstd;
    }

    /** @return whether older backups are pruned according to {@code prune-backups}. */
    public boolean isRemove() {
        return remove;
    }

    /** @return whether resulting backups are marked as protected by default. */
    public boolean isProtected() {
        return isProtected;
    }

    /** @return the notes template applied to backups, or null. */
    public String getNotesTemplate() {
        return notesTemplate;
    }

    /** @return the prune-backups retention options string, or null. */
    public String getPruneBackups() {
        return pruneBackups;
    }

    /** @return the performance-related settings string, or null. */
    public String getPerformance() {
        return performance;
    }

    /** @return the backup fleecing options string, or null. */
    public String getFleecing() {
        return fleecing;
    }

    /** @return the directory where resulting files are stored, or null. */
    public String getDumpdir() {
        return dumpdir;
    }

    /** @return the deprecated mail notification setting ("always" or "failure"). */
    public String getMailnotification() {
        return mailnotification;
    }

    /** @return the deprecated comma-separated list of mail recipients, or null. */
    public String getMailto() {
        return mailto;
    }

    /** @return the notification mode ("auto", "legacy-sendmail" or "notification-system"). */
    public String getNotificationMode() {
        return notificationMode;
    }

    /** @return the PBS change detection mode ("legacy", "data" or "metadata"), or null. */
    public String getPbsChangeDetectionMode() {
        return pbsChangeDetectionMode;
    }

    /** @return whether all known guest systems are backed up by default. */
    public boolean isAll() {
        return all;
    }

    /** @return the pool whose guests are backed up by default, or null. */
    public String getPool() {
        return pool;
    }

    /** @return the comma-separated list of excluded guest IDs, or null. */
    public String getExclude() {
        return exclude;
    }

    /** @return the hook script path, or null. */
    public String getScript() {
        return script;
    }

    /** @return the directory used for temporary files, or null. */
    public String getTmpdir() {
        return tmpdir;
    }

    /** @return whether quiet mode is enabled by default. */
    public boolean isQuiet() {
        return quiet;
    }

    /** @return whether temporary files and logs are excluded by default. */
    public boolean isStdexcludes() {
        return stdexcludes;
    }

    /** @return whether running backup jobs are stopped by default. */
    public boolean isStop() {
        return stop;
    }

    @Override
    public String toString() {
        return "PveVzdumpDefaults{" +
                "storage='" + storage + '\'' +
                ", mode='" + mode + '\'' +
                ", compress='" + compress + '\'' +
                ", bwlimit=" + bwlimit +
                ", remove=" + remove +
                ", isProtected=" + isProtected +
                ", pruneBackups='" + pruneBackups + '\'' +
                ", notesTemplate='" + notesTemplate + '\'' +
                '}';
    }
}
