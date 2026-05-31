package fr.freshperf.pve4j.entities.cluster.backup;

import fr.freshperf.pve4j.entities.options.ParamsConvertible;
import fr.freshperf.pve4j.util.ParamsHelpers;

import java.util.List;
import java.util.Map;

/**
 * Options for updating an existing vzdump backup job via {@code PUT /cluster/backup/{id}}.
 *
 * <p>The job {@code id} is part of the request path and is therefore not part of these
 * options. Use {@link #delete(List)} to clear specific settings (revert them to their
 * defaults).</p>
 */
public class PveBackupJobUpdateOptions implements ParamsConvertible {

    private String schedule;
    private String starttime;
    private String dow;
    private Boolean enabled;
    private String comment;
    private Boolean repeatMissed;
    private Boolean all;
    private String vmid;
    private String pool;
    private String exclude;
    private String node;
    private String storage;
    private String dumpdir;
    private String mode;
    private String compress;
    private Integer bwlimit;
    private Integer ionice;
    private Integer lockwait;
    private Integer stopwait;
    private Integer pigz;
    private Integer zstd;
    private Boolean remove;
    private Boolean protected_;
    private String notesTemplate;
    private String pruneBackups;
    private String performance;
    private String fleecing;
    private String script;
    private String tmpdir;
    private Boolean quiet;
    private Boolean stdexcludes;
    private Boolean stop;
    private String mailto;
    private String mailnotification;
    private String notificationMode;
    private String pbsChangeDetectionMode;
    private String delete;

    /**
     * Creates a new builder for backup job update options.
     *
     * @return a new PveBackupJobUpdateOptions instance
     */
    public static PveBackupJobUpdateOptions builder() {
        return new PveBackupJobUpdateOptions();
    }

    @Override
    public void populateParams(Map<String, Object> params) {
        ParamsHelpers.put(params, "schedule", schedule);
        ParamsHelpers.put(params, "starttime", starttime);
        ParamsHelpers.put(params, "dow", dow);
        ParamsHelpers.putBool(params, "enabled", enabled);
        ParamsHelpers.put(params, "comment", comment);
        ParamsHelpers.putBool(params, "repeat-missed", repeatMissed);
        ParamsHelpers.putBool(params, "all", all);
        ParamsHelpers.put(params, "vmid", vmid);
        ParamsHelpers.put(params, "pool", pool);
        ParamsHelpers.put(params, "exclude", exclude);
        ParamsHelpers.put(params, "node", node);
        ParamsHelpers.put(params, "storage", storage);
        ParamsHelpers.put(params, "dumpdir", dumpdir);
        ParamsHelpers.put(params, "mode", mode);
        ParamsHelpers.put(params, "compress", compress);
        ParamsHelpers.putInt(params, "bwlimit", bwlimit);
        ParamsHelpers.putInt(params, "ionice", ionice);
        ParamsHelpers.putInt(params, "lockwait", lockwait);
        ParamsHelpers.putInt(params, "stopwait", stopwait);
        ParamsHelpers.putInt(params, "pigz", pigz);
        ParamsHelpers.putInt(params, "zstd", zstd);
        ParamsHelpers.putBool(params, "remove", remove);
        ParamsHelpers.putBool(params, "protected", protected_);
        ParamsHelpers.put(params, "notes-template", notesTemplate);
        ParamsHelpers.put(params, "prune-backups", pruneBackups);
        ParamsHelpers.put(params, "performance", performance);
        ParamsHelpers.put(params, "fleecing", fleecing);
        ParamsHelpers.put(params, "script", script);
        ParamsHelpers.put(params, "tmpdir", tmpdir);
        ParamsHelpers.putBool(params, "quiet", quiet);
        ParamsHelpers.putBool(params, "stdexcludes", stdexcludes);
        ParamsHelpers.putBool(params, "stop", stop);
        ParamsHelpers.put(params, "mailto", mailto);
        ParamsHelpers.put(params, "mailnotification", mailnotification);
        ParamsHelpers.put(params, "notification-mode", notificationMode);
        ParamsHelpers.put(params, "pbs-change-detection-mode", pbsChangeDetectionMode);
        ParamsHelpers.put(params, "delete", delete);
    }

    /** Sets the backup schedule (a subset of systemd calendar events). */
    public PveBackupJobUpdateOptions schedule(String schedule) {
        this.schedule = schedule;
        return this;
    }

    /** Sets the deprecated job start time ("HH:MM"); requires {@link #dow(String)}. */
    public PveBackupJobUpdateOptions starttime(String starttime) {
        this.starttime = starttime;
        return this;
    }

    /** Sets the deprecated day-of-week selection. */
    public PveBackupJobUpdateOptions dow(String dow) {
        this.dow = dow;
        return this;
    }

    /** Enables or disables the job. */
    public PveBackupJobUpdateOptions enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    /** Sets the job description. */
    public PveBackupJobUpdateOptions comment(String comment) {
        this.comment = comment;
        return this;
    }

    /** Sets whether the job runs as soon as possible if it was missed. */
    public PveBackupJobUpdateOptions repeatMissed(Boolean repeatMissed) {
        this.repeatMissed = repeatMissed;
        return this;
    }

    /** Sets whether to back up all known guest systems. */
    public PveBackupJobUpdateOptions all(Boolean all) {
        this.all = all;
        return this;
    }

    /** Sets the comma-separated list of guest IDs to back up. */
    public PveBackupJobUpdateOptions vmid(String vmid) {
        this.vmid = vmid;
        return this;
    }

    /** Backs up all guests included in the specified pool. */
    public PveBackupJobUpdateOptions pool(String pool) {
        this.pool = pool;
        return this;
    }

    /** Excludes the specified guest systems (implies {@link #all(Boolean)}). */
    public PveBackupJobUpdateOptions exclude(String exclude) {
        this.exclude = exclude;
        return this;
    }

    /** Restricts the job to run only on this node. */
    public PveBackupJobUpdateOptions node(String node) {
        this.node = node;
        return this;
    }

    /** Sets the target storage. */
    public PveBackupJobUpdateOptions storage(String storage) {
        this.storage = storage;
        return this;
    }

    /** Stores the resulting files to the specified directory (root@pam only). */
    public PveBackupJobUpdateOptions dumpdir(String dumpdir) {
        this.dumpdir = dumpdir;
        return this;
    }

    /** Sets the backup mode ("snapshot", "suspend" or "stop"). */
    public PveBackupJobUpdateOptions mode(String mode) {
        this.mode = mode;
        return this;
    }

    /** Sets the compression algorithm ("0", "1", "gzip", "lzo" or "zstd"). */
    public PveBackupJobUpdateOptions compress(String compress) {
        this.compress = compress;
        return this;
    }

    /** Sets the I/O bandwidth limit in KiB/s (0 for unlimited). */
    public PveBackupJobUpdateOptions bwlimit(Integer bwlimit) {
        this.bwlimit = bwlimit;
        return this;
    }

    /** Sets the I/O nice priority for the BFQ scheduler (0-8). */
    public PveBackupJobUpdateOptions ionice(Integer ionice) {
        this.ionice = ionice;
        return this;
    }

    /** Sets the maximal time to wait for the global lock, in minutes. */
    public PveBackupJobUpdateOptions lockwait(Integer lockwait) {
        this.lockwait = lockwait;
        return this;
    }

    /** Sets the maximal time to wait until a guest is stopped, in minutes. */
    public PveBackupJobUpdateOptions stopwait(Integer stopwait) {
        this.stopwait = stopwait;
        return this;
    }

    /** Uses pigz instead of gzip when N&gt;0. */
    public PveBackupJobUpdateOptions pigz(Integer pigz) {
        this.pigz = pigz;
        return this;
    }

    /** Sets the number of zstd threads. */
    public PveBackupJobUpdateOptions zstd(Integer zstd) {
        this.zstd = zstd;
        return this;
    }

    /** Sets whether to prune older backups according to {@code prune-backups}. */
    public PveBackupJobUpdateOptions remove(Boolean remove) {
        this.remove = remove;
        return this;
    }

    /** Sets whether to mark resulting backups as protected (requires a storage). */
    public PveBackupJobUpdateOptions protected_(Boolean protected_) {
        this.protected_ = protected_;
        return this;
    }

    /** Sets the notes template for the backups (requires a storage). */
    public PveBackupJobUpdateOptions notesTemplate(String notesTemplate) {
        this.notesTemplate = notesTemplate;
        return this;
    }

    /** Sets the retention options to use instead of those from the storage configuration. */
    public PveBackupJobUpdateOptions pruneBackups(String pruneBackups) {
        this.pruneBackups = pruneBackups;
        return this;
    }

    /** Sets other performance-related settings. */
    public PveBackupJobUpdateOptions performance(String performance) {
        this.performance = performance;
        return this;
    }

    /** Sets the backup fleecing options (VM only), e.g. "enabled=1,storage=local-lvm". */
    public PveBackupJobUpdateOptions fleecing(String fleecing) {
        this.fleecing = fleecing;
        return this;
    }

    /** Uses the specified hook script (root@pam only). */
    public PveBackupJobUpdateOptions script(String script) {
        this.script = script;
        return this;
    }

    /** Stores temporary files to the specified directory (root@pam only). */
    public PveBackupJobUpdateOptions tmpdir(String tmpdir) {
        this.tmpdir = tmpdir;
        return this;
    }

    /** Sets whether to be quiet. */
    public PveBackupJobUpdateOptions quiet(Boolean quiet) {
        this.quiet = quiet;
        return this;
    }

    /** Sets whether to exclude temporary files and logs. */
    public PveBackupJobUpdateOptions stdexcludes(Boolean stdexcludes) {
        this.stdexcludes = stdexcludes;
        return this;
    }

    /** Sets whether to stop running backup jobs on this host. */
    public PveBackupJobUpdateOptions stop(Boolean stop) {
        this.stop = stop;
        return this;
    }

    /** Sets the deprecated comma-separated list of email recipients. */
    public PveBackupJobUpdateOptions mailto(String mailto) {
        this.mailto = mailto;
        return this;
    }

    /** Sets the deprecated mail notification trigger ("always" or "failure"). */
    public PveBackupJobUpdateOptions mailnotification(String mailnotification) {
        this.mailnotification = mailnotification;
        return this;
    }

    /** Sets which notification system to use ("auto", "legacy-sendmail" or "notification-system"). */
    public PveBackupJobUpdateOptions notificationMode(String notificationMode) {
        this.notificationMode = notificationMode;
        return this;
    }

    /** Sets the PBS change detection mode ("legacy", "data" or "metadata"). */
    public PveBackupJobUpdateOptions pbsChangeDetectionMode(String pbsChangeDetectionMode) {
        this.pbsChangeDetectionMode = pbsChangeDetectionMode;
        return this;
    }

    /**
     * Sets the list of settings to delete (reset to their defaults).
     *
     * @param settings the configuration keys to delete (e.g. "comment", "mailto")
     * @return this instance for method chaining
     */
    public PveBackupJobUpdateOptions delete(List<String> settings) {
        this.delete = (settings == null || settings.isEmpty()) ? null : String.join(",", settings);
        return this;
    }

    /**
     * Sets the comma-separated list of settings to delete (reset to their defaults).
     *
     * @param settings the configuration keys to delete
     * @return this instance for method chaining
     */
    public PveBackupJobUpdateOptions delete(String settings) {
        this.delete = settings;
        return this;
    }

    /**
     * Builds and returns the options instance.
     *
     * @return this instance
     */
    public PveBackupJobUpdateOptions build() {
        return this;
    }
}
