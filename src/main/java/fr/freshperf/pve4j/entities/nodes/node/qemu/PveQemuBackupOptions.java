package fr.freshperf.pve4j.entities.nodes.node.qemu;

import fr.freshperf.pve4j.entities.options.ParamsConvertible;
import fr.freshperf.pve4j.util.ParamsHelpers;

import java.util.Map;

/**
 * Options for creating a VM backup.
 * Use the builder pattern to configure backup settings.
 */
public class PveQemuBackupOptions implements ParamsConvertible {

    private String storage;
    private String mode;
    private String compress;
    private String notesTemplate;
    private Boolean remove;
    private Integer bwlimit;
    private Integer ionice;
    private Boolean protected_;
    private String pruneBackups;
    private String performance;
    private String fleecing;
    private Integer pigz;
    private Integer zstd;
    private Integer lockwait;
    private Integer stopwait;
    private String script;
    private String tmpdir;
    private String dumpdir;
    private Boolean quiet;
    private Boolean stdexcludes;
    private String mailto;
    private String mailnotification;
    private String notificationMode;
    private String jobId;

    /**
     * Creates a new builder for backup options.
     *
     * @return a new PveQemuBackupOptions instance
     */
    public static PveQemuBackupOptions builder() {
        return new PveQemuBackupOptions();
    }

    @Override
    public void populateParams(Map<String, Object> params) {
        ParamsHelpers.put(params, "storage", storage);
        ParamsHelpers.put(params, "mode", mode);
        ParamsHelpers.put(params, "compress", compress);
        ParamsHelpers.put(params, "notes-template", notesTemplate);
        ParamsHelpers.putBool(params, "remove", remove);
        ParamsHelpers.putInt(params, "bwlimit", bwlimit);
        ParamsHelpers.putInt(params, "ionice", ionice);
        ParamsHelpers.putBool(params, "protected", protected_);
        ParamsHelpers.put(params, "prune-backups", pruneBackups);
        ParamsHelpers.put(params, "performance", performance);
        ParamsHelpers.put(params, "fleecing", fleecing);
        ParamsHelpers.putInt(params, "pigz", pigz);
        ParamsHelpers.putInt(params, "zstd", zstd);
        ParamsHelpers.putInt(params, "lockwait", lockwait);
        ParamsHelpers.putInt(params, "stopwait", stopwait);
        ParamsHelpers.put(params, "script", script);
        ParamsHelpers.put(params, "tmpdir", tmpdir);
        ParamsHelpers.put(params, "dumpdir", dumpdir);
        ParamsHelpers.putBool(params, "quiet", quiet);
        ParamsHelpers.putBool(params, "stdexcludes", stdexcludes);
        ParamsHelpers.put(params, "mailto", mailto);
        ParamsHelpers.put(params, "mailnotification", mailnotification);
        ParamsHelpers.put(params, "notification-mode", notificationMode);
        ParamsHelpers.put(params, "job-id", jobId);
    }

    /**
     * Sets the target storage for the backup.
     *
     * @param storage the storage ID
     * @return this instance for method chaining
     */
    public PveQemuBackupOptions storage(String storage) {
        this.storage = storage;
        return this;
    }

    /**
     * Sets the backup mode.
     *
     * @param mode "snapshot" (default), "suspend", or "stop"
     * @return this instance for method chaining
     */
    public PveQemuBackupOptions mode(String mode) {
        this.mode = mode;
        return this;
    }

    /**
     * Sets the compression algorithm.
     *
     * @param compress "0" (none), "1" (lzo), "gzip", or "zstd"
     * @return this instance for method chaining
     */
    public PveQemuBackupOptions compress(String compress) {
        this.compress = compress;
        return this;
    }

    /**
     * Sets the notes template for the backup.
     *
     * @param notesTemplate template with variables like {{guestname}}, {{vmid}}
     * @return this instance for method chaining
     */
    public PveQemuBackupOptions notesTemplate(String notesTemplate) {
        this.notesTemplate = notesTemplate;
        return this;
    }

    /**
     * Sets whether to remove old backups if storage is full.
     *
     * @param remove true to auto-remove old backups
     * @return this instance for method chaining
     */
    public PveQemuBackupOptions remove(Boolean remove) {
        this.remove = remove;
        return this;
    }

    /**
     * Sets the I/O bandwidth limit in KiB/s.
     *
     * @param bwlimit bandwidth limit (0 for unlimited)
     * @return this instance for method chaining
     */
    public PveQemuBackupOptions bwlimit(Integer bwlimit) {
        this.bwlimit = bwlimit;
        return this;
    }

    /**
     * Sets the I/O nice priority (0-8).
     *
     * @param ionice the ionice value
     * @return this instance for method chaining
     */
    public PveQemuBackupOptions ionice(Integer ionice) {
        this.ionice = ionice;
        return this;
    }

    /**
     * Sets whether to mark backup as protected.
     *
     * @param protected_ true to protect backup from deletion
     * @return this instance for method chaining
     */
    public PveQemuBackupOptions protected_(Boolean protected_) {
        this.protected_ = protected_;
        return this;
    }

    /**
     * Sets the retention options to use instead of those from the storage configuration.
     *
     * @param pruneBackups e.g. "keep-last=3,keep-daily=7"
     * @return this instance for method chaining
     */
    public PveQemuBackupOptions pruneBackups(String pruneBackups) {
        this.pruneBackups = pruneBackups;
        return this;
    }

    /**
     * Sets other performance-related settings.
     *
     * @param performance e.g. "max-workers=2,pbs-entries-max=1048576"
     * @return this instance for method chaining
     */
    public PveQemuBackupOptions performance(String performance) {
        this.performance = performance;
        return this;
    }

    /**
     * Sets the backup fleecing options.
     *
     * @param fleecing e.g. "enabled=1,storage=local-lvm"
     * @return this instance for method chaining
     */
    public PveQemuBackupOptions fleecing(String fleecing) {
        this.fleecing = fleecing;
        return this;
    }

    /**
     * Uses pigz instead of gzip when N&gt;0. N=1 uses half the cores, N&gt;1 uses N as thread count.
     *
     * @param pigz the pigz thread count
     * @return this instance for method chaining
     */
    public PveQemuBackupOptions pigz(Integer pigz) {
        this.pigz = pigz;
        return this;
    }

    /**
     * Sets the number of zstd threads (0 uses half of the available cores).
     *
     * @param zstd the zstd thread count
     * @return this instance for method chaining
     */
    public PveQemuBackupOptions zstd(Integer zstd) {
        this.zstd = zstd;
        return this;
    }

    /**
     * Sets the maximal time to wait for the global lock, in minutes.
     *
     * @param lockwait the lock wait in minutes
     * @return this instance for method chaining
     */
    public PveQemuBackupOptions lockwait(Integer lockwait) {
        this.lockwait = lockwait;
        return this;
    }

    /**
     * Sets the maximal time to wait until the guest is stopped, in minutes.
     *
     * @param stopwait the stop wait in minutes
     * @return this instance for method chaining
     */
    public PveQemuBackupOptions stopwait(Integer stopwait) {
        this.stopwait = stopwait;
        return this;
    }

    /**
     * Uses the specified hook script (restricted to root@pam).
     *
     * @param script the script path
     * @return this instance for method chaining
     */
    public PveQemuBackupOptions script(String script) {
        this.script = script;
        return this;
    }

    /**
     * Stores temporary files to the specified directory (restricted to root@pam).
     *
     * @param tmpdir the temporary directory
     * @return this instance for method chaining
     */
    public PveQemuBackupOptions tmpdir(String tmpdir) {
        this.tmpdir = tmpdir;
        return this;
    }

    /**
     * Stores the resulting files to the specified directory (restricted to root@pam).
     *
     * @param dumpdir the target directory
     * @return this instance for method chaining
     */
    public PveQemuBackupOptions dumpdir(String dumpdir) {
        this.dumpdir = dumpdir;
        return this;
    }

    /**
     * Sets whether to be quiet.
     *
     * @param quiet true to suppress output
     * @return this instance for method chaining
     */
    public PveQemuBackupOptions quiet(Boolean quiet) {
        this.quiet = quiet;
        return this;
    }

    /**
     * Sets whether to exclude temporary files and logs.
     *
     * @param stdexcludes true to exclude temporary files and logs
     * @return this instance for method chaining
     */
    public PveQemuBackupOptions stdexcludes(Boolean stdexcludes) {
        this.stdexcludes = stdexcludes;
        return this;
    }

    /**
     * Sets the deprecated comma-separated list of email recipients.
     *
     * @param mailto the recipients
     * @return this instance for method chaining
     */
    public PveQemuBackupOptions mailto(String mailto) {
        this.mailto = mailto;
        return this;
    }

    /**
     * Sets the deprecated mail notification trigger.
     *
     * @param mailnotification "always" or "failure"
     * @return this instance for method chaining
     */
    public PveQemuBackupOptions mailnotification(String mailnotification) {
        this.mailnotification = mailnotification;
        return this;
    }

    /**
     * Sets which notification system to use.
     *
     * @param notificationMode "auto", "legacy-sendmail", or "notification-system"
     * @return this instance for method chaining
     */
    public PveQemuBackupOptions notificationMode(String notificationMode) {
        this.notificationMode = notificationMode;
        return this;
    }

    /**
     * Sets the backup job ID metadata (restricted to root@pam).
     *
     * @param jobId the backup job ID
     * @return this instance for method chaining
     */
    public PveQemuBackupOptions jobId(String jobId) {
        this.jobId = jobId;
        return this;
    }

    /**
     * Builds and returns the options instance.
     *
     * @return this instance
     */
    public PveQemuBackupOptions build() {
        return this;
    }
}

