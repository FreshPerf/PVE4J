package fr.freshperf.pve4j.entities.nodes.node.vzdump;

import fr.freshperf.pve4j.entities.options.ParamsConvertible;
import fr.freshperf.pve4j.util.ParamsHelpers;

import java.util.Map;

/**
 * Options for creating a backup via {@code POST /nodes/{node}/vzdump}.
 *
 * <p>This is the node-level, guest-agnostic backup entry point: the guests to back up are
 * selected with {@link #vmid(String)}, {@link #all(Boolean)}, {@link #pool(String)} and
 * {@link #exclude(String)}. To back up a single QEMU VM, prefer
 * {@code node.getQemu().get(vmid).backup(...)} which sets the VMID automatically.</p>
 *
 * <p>Use the builder pattern to configure backup settings. The {@code exclude-path} array
 * parameter (container-only) is intentionally not exposed, as the HTTP layer does not
 * support array-typed parameters.</p>
 */
public class PveVzdumpOptions implements ParamsConvertible {

    private String vmid;
    private Boolean all;
    private String pool;
    private String exclude;
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
    private Boolean stdout;
    private String mailto;
    private String mailnotification;
    private String notificationMode;
    private String pbsChangeDetectionMode;
    private String jobId;

    /**
     * Creates a new builder for vzdump backup options.
     *
     * @return a new PveVzdumpOptions instance
     */
    public static PveVzdumpOptions builder() {
        return new PveVzdumpOptions();
    }

    @Override
    public void populateParams(Map<String, Object> params) {
        ParamsHelpers.put(params, "vmid", vmid);
        ParamsHelpers.putBool(params, "all", all);
        ParamsHelpers.put(params, "pool", pool);
        ParamsHelpers.put(params, "exclude", exclude);
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
        ParamsHelpers.putBool(params, "stdout", stdout);
        ParamsHelpers.put(params, "mailto", mailto);
        ParamsHelpers.put(params, "mailnotification", mailnotification);
        ParamsHelpers.put(params, "notification-mode", notificationMode);
        ParamsHelpers.put(params, "pbs-change-detection-mode", pbsChangeDetectionMode);
        ParamsHelpers.put(params, "job-id", jobId);
    }

    /**
     * Sets the guest ID(s) to back up (comma-separated list of VMIDs).
     *
     * @param vmid the VMID list (e.g. "100" or "100,101,102")
     * @return this instance for method chaining
     */
    public PveVzdumpOptions vmid(String vmid) {
        this.vmid = vmid;
        return this;
    }

    /**
     * Sets whether to back up all known guest systems on this host.
     *
     * @param all true to back up everything
     * @return this instance for method chaining
     */
    public PveVzdumpOptions all(Boolean all) {
        this.all = all;
        return this;
    }

    /**
     * Backs up all known guest systems included in the specified pool.
     *
     * @param pool the pool ID
     * @return this instance for method chaining
     */
    public PveVzdumpOptions pool(String pool) {
        this.pool = pool;
        return this;
    }

    /**
     * Excludes the specified guest systems (implies {@link #all(Boolean)}).
     *
     * @param exclude comma-separated list of VMIDs to exclude
     * @return this instance for method chaining
     */
    public PveVzdumpOptions exclude(String exclude) {
        this.exclude = exclude;
        return this;
    }

    /**
     * Sets the target storage for the backup.
     *
     * @param storage the storage ID
     * @return this instance for method chaining
     */
    public PveVzdumpOptions storage(String storage) {
        this.storage = storage;
        return this;
    }

    /**
     * Stores the resulting files to the specified directory (restricted to root@pam).
     *
     * @param dumpdir the target directory
     * @return this instance for method chaining
     */
    public PveVzdumpOptions dumpdir(String dumpdir) {
        this.dumpdir = dumpdir;
        return this;
    }

    /**
     * Sets the backup mode.
     *
     * @param mode "snapshot" (default), "suspend", or "stop"
     * @return this instance for method chaining
     */
    public PveVzdumpOptions mode(String mode) {
        this.mode = mode;
        return this;
    }

    /**
     * Sets the compression algorithm.
     *
     * @param compress "0" (none), "1" (lzo), "gzip", "lzo", or "zstd"
     * @return this instance for method chaining
     */
    public PveVzdumpOptions compress(String compress) {
        this.compress = compress;
        return this;
    }

    /**
     * Sets the I/O bandwidth limit in KiB/s.
     *
     * @param bwlimit bandwidth limit (0 for unlimited)
     * @return this instance for method chaining
     */
    public PveVzdumpOptions bwlimit(Integer bwlimit) {
        this.bwlimit = bwlimit;
        return this;
    }

    /**
     * Sets the I/O nice priority for the BFQ scheduler (0-8).
     *
     * @param ionice the ionice value
     * @return this instance for method chaining
     */
    public PveVzdumpOptions ionice(Integer ionice) {
        this.ionice = ionice;
        return this;
    }

    /**
     * Sets the maximal time to wait for the global lock, in minutes.
     *
     * @param lockwait the lock wait in minutes
     * @return this instance for method chaining
     */
    public PveVzdumpOptions lockwait(Integer lockwait) {
        this.lockwait = lockwait;
        return this;
    }

    /**
     * Sets the maximal time to wait until a guest system is stopped, in minutes.
     *
     * @param stopwait the stop wait in minutes
     * @return this instance for method chaining
     */
    public PveVzdumpOptions stopwait(Integer stopwait) {
        this.stopwait = stopwait;
        return this;
    }

    /**
     * Uses pigz instead of gzip when N&gt;0. N=1 uses half the cores, N&gt;1 uses N as thread count.
     *
     * @param pigz the pigz thread count
     * @return this instance for method chaining
     */
    public PveVzdumpOptions pigz(Integer pigz) {
        this.pigz = pigz;
        return this;
    }

    /**
     * Sets the number of zstd threads (0 uses half of the available cores).
     *
     * @param zstd the zstd thread count
     * @return this instance for method chaining
     */
    public PveVzdumpOptions zstd(Integer zstd) {
        this.zstd = zstd;
        return this;
    }

    /**
     * Sets whether to prune older backups according to {@code prune-backups}.
     *
     * @param remove true to prune older backups
     * @return this instance for method chaining
     */
    public PveVzdumpOptions remove(Boolean remove) {
        this.remove = remove;
        return this;
    }

    /**
     * Sets whether to mark the resulting backup(s) as protected (requires a storage).
     *
     * @param protected_ true to protect the backup from deletion
     * @return this instance for method chaining
     */
    public PveVzdumpOptions protected_(Boolean protected_) {
        this.protected_ = protected_;
        return this;
    }

    /**
     * Sets the notes template for the backup (requires a storage).
     *
     * @param notesTemplate template with variables like {@code {{guestname}}}, {@code {{vmid}}}
     * @return this instance for method chaining
     */
    public PveVzdumpOptions notesTemplate(String notesTemplate) {
        this.notesTemplate = notesTemplate;
        return this;
    }

    /**
     * Sets the retention options to use instead of those from the storage configuration.
     *
     * @param pruneBackups e.g. "keep-last=3,keep-daily=7"
     * @return this instance for method chaining
     */
    public PveVzdumpOptions pruneBackups(String pruneBackups) {
        this.pruneBackups = pruneBackups;
        return this;
    }

    /**
     * Sets other performance-related settings.
     *
     * @param performance e.g. "max-workers=2,pbs-entries-max=1048576"
     * @return this instance for method chaining
     */
    public PveVzdumpOptions performance(String performance) {
        this.performance = performance;
        return this;
    }

    /**
     * Sets the backup fleecing options (VM only).
     *
     * @param fleecing e.g. "enabled=1,storage=local-lvm"
     * @return this instance for method chaining
     */
    public PveVzdumpOptions fleecing(String fleecing) {
        this.fleecing = fleecing;
        return this;
    }

    /**
     * Uses the specified hook script (restricted to root@pam).
     *
     * @param script the script path
     * @return this instance for method chaining
     */
    public PveVzdumpOptions script(String script) {
        this.script = script;
        return this;
    }

    /**
     * Stores temporary files to the specified directory (restricted to root@pam).
     *
     * @param tmpdir the temporary directory
     * @return this instance for method chaining
     */
    public PveVzdumpOptions tmpdir(String tmpdir) {
        this.tmpdir = tmpdir;
        return this;
    }

    /**
     * Sets whether to be quiet.
     *
     * @param quiet true to suppress output
     * @return this instance for method chaining
     */
    public PveVzdumpOptions quiet(Boolean quiet) {
        this.quiet = quiet;
        return this;
    }

    /**
     * Sets whether to exclude temporary files and logs.
     *
     * @param stdexcludes true to exclude temporary files and logs
     * @return this instance for method chaining
     */
    public PveVzdumpOptions stdexcludes(Boolean stdexcludes) {
        this.stdexcludes = stdexcludes;
        return this;
    }

    /**
     * Sets whether to stop running backup jobs on this host.
     *
     * @param stop true to stop running backup jobs
     * @return this instance for method chaining
     */
    public PveVzdumpOptions stop(Boolean stop) {
        this.stop = stop;
        return this;
    }

    /**
     * Sets whether to write the tar to stdout instead of to a file.
     *
     * @param stdout true to write to stdout
     * @return this instance for method chaining
     */
    public PveVzdumpOptions stdout(Boolean stdout) {
        this.stdout = stdout;
        return this;
    }

    /**
     * Sets the deprecated comma-separated list of email recipients.
     *
     * @param mailto the recipients
     * @return this instance for method chaining
     */
    public PveVzdumpOptions mailto(String mailto) {
        this.mailto = mailto;
        return this;
    }

    /**
     * Sets the deprecated mail notification trigger.
     *
     * @param mailnotification "always" or "failure"
     * @return this instance for method chaining
     */
    public PveVzdumpOptions mailnotification(String mailnotification) {
        this.mailnotification = mailnotification;
        return this;
    }

    /**
     * Sets which notification system to use.
     *
     * @param notificationMode "auto", "legacy-sendmail", or "notification-system"
     * @return this instance for method chaining
     */
    public PveVzdumpOptions notificationMode(String notificationMode) {
        this.notificationMode = notificationMode;
        return this;
    }

    /**
     * Sets the PBS change detection mode (container backups).
     *
     * @param pbsChangeDetectionMode "legacy", "data", or "metadata"
     * @return this instance for method chaining
     */
    public PveVzdumpOptions pbsChangeDetectionMode(String pbsChangeDetectionMode) {
        this.pbsChangeDetectionMode = pbsChangeDetectionMode;
        return this;
    }

    /**
     * Sets the backup job ID metadata (restricted to root@pam).
     *
     * @param jobId the backup job ID
     * @return this instance for method chaining
     */
    public PveVzdumpOptions jobId(String jobId) {
        this.jobId = jobId;
        return this;
    }

    /**
     * Builds and returns the options instance.
     *
     * @return this instance
     */
    public PveVzdumpOptions build() {
        return this;
    }
}
