package fr.freshperf.pve4j.entities.cluster.backup;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a vzdump backup job definition, as returned by
 * {@code GET /cluster/backup} and {@code GET /cluster/backup/{id}}.
 */
public class PveBackupJob {

    private String id;
    private boolean enabled;
    private String comment;
    private String schedule;
    private String starttime;
    private String dow;
    @SerializedName("repeat-missed")
    private boolean repeatMissed;
    @SerializedName("next-run")
    private long nextRun;
    private String type;
    private String node;
    private String vmid;
    private boolean all;
    private String pool;
    private String exclude;
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
    private Fleecing fleecing;
    private String mailto;
    private String mailnotification;
    @SerializedName("notification-mode")
    private String notificationMode;
    @SerializedName("pbs-change-detection-mode")
    private String pbsChangeDetectionMode;
    private String script;
    private String dumpdir;
    private String tmpdir;
    private boolean quiet;
    private boolean stdexcludes;
    private boolean stop;

    /** @return the job ID. */
    public String getId() {
        return id;
    }

    /** @return whether the job is enabled. */
    public boolean isEnabled() {
        return enabled;
    }

    /** @return the job description, or null. */
    public String getComment() {
        return comment;
    }

    /** @return the backup schedule (systemd calendar event subset), or null. */
    public String getSchedule() {
        return schedule;
    }

    /** @return the deprecated job start time ("HH:MM"), or null. */
    public String getStarttime() {
        return starttime;
    }

    /** @return the deprecated day-of-week selection, or null. */
    public String getDow() {
        return dow;
    }

    /** @return whether the job runs as soon as possible if it was missed. */
    public boolean isRepeatMissed() {
        return repeatMissed;
    }

    /** @return the UNIX timestamp when this job will next be executed (0 if unknown). */
    public long getNextRun() {
        return nextRun;
    }

    /** @return the guest type filter, or null. */
    public String getType() {
        return type;
    }

    /** @return the node this job is restricted to, or null. */
    public String getNode() {
        return node;
    }

    /** @return the comma-separated list of guest IDs to back up, or null. */
    public String getVmid() {
        return vmid;
    }

    /** @return whether all known guest systems are backed up. */
    public boolean isAll() {
        return all;
    }

    /** @return the pool whose guests are backed up, or null. */
    public String getPool() {
        return pool;
    }

    /** @return the comma-separated list of excluded guest IDs, or null. */
    public String getExclude() {
        return exclude;
    }

    /** @return the target storage, or null. */
    public String getStorage() {
        return storage;
    }

    /** @return the backup mode ("snapshot", "suspend" or "stop"). */
    public String getMode() {
        return mode;
    }

    /** @return the compression algorithm. */
    public String getCompress() {
        return compress;
    }

    /** @return the I/O bandwidth limit in KiB/s (0 means unlimited). */
    public int getBwlimit() {
        return bwlimit;
    }

    /** @return the I/O nice priority (0-8). */
    public int getIonice() {
        return ionice;
    }

    /** @return the maximal time to wait for the global lock, in minutes. */
    public int getLockwait() {
        return lockwait;
    }

    /** @return the maximal time to wait until a guest is stopped, in minutes. */
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

    /** @return whether resulting backups are marked as protected. */
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

    /** @return the backup fleecing settings, or null. */
    public Fleecing getFleecing() {
        return fleecing;
    }

    /** @return the deprecated comma-separated list of mail recipients, or null. */
    public String getMailto() {
        return mailto;
    }

    /** @return the deprecated mail notification trigger ("always" or "failure"). */
    public String getMailnotification() {
        return mailnotification;
    }

    /** @return the notification mode, or null. */
    public String getNotificationMode() {
        return notificationMode;
    }

    /** @return the PBS change detection mode, or null. */
    public String getPbsChangeDetectionMode() {
        return pbsChangeDetectionMode;
    }

    /** @return the hook script path, or null. */
    public String getScript() {
        return script;
    }

    /** @return the directory where resulting files are stored, or null. */
    public String getDumpdir() {
        return dumpdir;
    }

    /** @return the directory used for temporary files, or null. */
    public String getTmpdir() {
        return tmpdir;
    }

    /** @return whether quiet mode is enabled. */
    public boolean isQuiet() {
        return quiet;
    }

    /** @return whether temporary files and logs are excluded. */
    public boolean isStdexcludes() {
        return stdexcludes;
    }

    /** @return whether running backup jobs are stopped. */
    public boolean isStop() {
        return stop;
    }

    @Override
    public String toString() {
        return "PveBackupJob{" +
                "id='" + id + '\'' +
                ", enabled=" + enabled +
                ", schedule='" + schedule + '\'' +
                ", storage='" + storage + '\'' +
                ", mode='" + mode + '\'' +
                ", vmid='" + vmid + '\'' +
                ", all=" + all +
                ", pool='" + pool + '\'' +
                ", comment='" + comment + '\'' +
                ", nextRun=" + nextRun +
                '}';
    }

    /**
     * Backup fleecing settings of a job (VM only).
     */
    public static class Fleecing {
        private boolean enabled;
        private String storage;

        /** @return whether backup fleecing is enabled. */
        public boolean isEnabled() {
            return enabled;
        }

        /** @return the storage used for fleecing images, or null. */
        public String getStorage() {
            return storage;
        }

        @Override
        public String toString() {
            return "Fleecing{enabled=" + enabled + ", storage='" + storage + '\'' + '}';
        }
    }
}
