package fr.freshperf.pve4j.entities.cluster.backup;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import fr.freshperf.pve4j.request.ProxmoxHttpClient;
import fr.freshperf.pve4j.request.ProxmoxRequest;

import java.util.List;

/**
 * Facade for the cluster-wide vzdump backup job endpoints
 * ({@code /cluster/backup} and {@code /cluster/backup-info}).
 *
 * <p>Backup jobs are guest-agnostic schedules that periodically back up the selected guests
 * (QEMU VMs and/or LXC containers). This facade exposes their full lifecycle as well as the
 * helper endpoints for inspecting which volumes a job covers and which guests are not covered
 * by any job.</p>
 */
public record PveClusterBackup(ProxmoxHttpClient httpClient) {

    /**
     * Lists all configured backup jobs.
     *
     * @return a request returning the list of backup jobs
     */
    public ProxmoxRequest<List<PveBackupJob>> getJobs() {
        return new ProxmoxRequest<>(() ->
            httpClient.get("cluster/backup").executeList(new TypeToken<List<PveBackupJob>>(){})
        );
    }

    /**
     * Reads a single backup job definition.
     *
     * @param id the job ID
     * @return a request returning the backup job
     * @throws IllegalArgumentException if id is null or blank
     */
    public ProxmoxRequest<PveBackupJob> getJob(String id) {
        requireId(id);
        return new ProxmoxRequest<>(() ->
            httpClient.get("cluster/backup/" + id).execute(PveBackupJob.class)
        );
    }

    /**
     * Creates a new backup job.
     *
     * @param options the job creation options (must not be null)
     * @return a request that completes when the job has been created
     * @throws IllegalArgumentException if options is null
     */
    public ProxmoxRequest<Void> createJob(PveBackupJobCreateOptions options) {
        if (options == null) {
            throw new IllegalArgumentException("options cannot be null");
        }
        return new ProxmoxRequest<Void>(() -> {
            httpClient.post("cluster/backup").params(options.toParams()).execute(JsonObject.class);
            return null;
        });
    }

    /**
     * Updates an existing backup job.
     *
     * @param id      the job ID
     * @param options the job update options (must not be null)
     * @return a request that completes when the job has been updated
     * @throws IllegalArgumentException if id is null/blank or options is null
     */
    public ProxmoxRequest<Void> updateJob(String id, PveBackupJobUpdateOptions options) {
        requireId(id);
        if (options == null) {
            throw new IllegalArgumentException("options cannot be null");
        }
        return new ProxmoxRequest<Void>(() -> {
            httpClient.put("cluster/backup/" + id).params(options.toParams()).execute(JsonObject.class);
            return null;
        });
    }

    /**
     * Deletes a backup job.
     *
     * @param id the job ID
     * @return a request that completes when the job has been deleted
     * @throws IllegalArgumentException if id is null or blank
     */
    public ProxmoxRequest<Void> deleteJob(String id) {
        requireId(id);
        return new ProxmoxRequest<Void>(() -> {
            httpClient.delete("cluster/backup/" + id).execute(JsonObject.class);
            return null;
        });
    }

    /**
     * Returns the guests covered by a job along with the backup status of their volumes.
     *
     * @param id the job ID
     * @return a request returning the included-volumes tree
     * @throws IllegalArgumentException if id is null or blank
     */
    public ProxmoxRequest<PveBackupIncludedVolumes> getIncludedVolumes(String id) {
        requireId(id);
        return new ProxmoxRequest<>(() ->
            httpClient.get("cluster/backup/" + id + "/included_volumes")
                .execute(PveBackupIncludedVolumes.class)
        );
    }

    /**
     * Lists all guests which are not covered by any backup job.
     *
     * @return a request returning the list of uncovered guests
     */
    public ProxmoxRequest<List<PveBackupGuest>> getGuestsNotBackedUp() {
        return new ProxmoxRequest<>(() ->
            httpClient.get("cluster/backup-info/not-backed-up")
                .executeList(new TypeToken<List<PveBackupGuest>>(){})
        );
    }

    private static void requireId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("job id cannot be null or empty");
        }
    }
}
