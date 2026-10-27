package fr.freshperf.pve4j.entities.cluster.backup;

import fr.freshperf.pve4j.testsupport.PveJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("PveBackupJob Entity Tests")
class PveBackupJobTest {

    // GET /cluster/backup entries as returned for jobs.cfg: PVE::VZDump::JobBase::decode_value parses
    // fleecing, performance and prune-backups into objects (integers stay strings, booleans are 0/1),
    // and SectionConfig numifies booleans and integers.
    private static final String JOBS_JSON = "[" +
            "{\"all\":0,\"compress\":\"zstd\",\"enabled\":1,\"fleecing\":{\"enabled\":1,\"storage\":\"local-lvm\"},\"id\":\"backup-5f8e3c1a-2b4d\"," +
            "\"mode\":\"snapshot\",\"next-run\":1793145600,\"notes-template\":\"{{guestname}}\",\"performance\":{\"max-workers\":\"8\",\"pbs-entries-max\":\"2000000\"}," +
            "\"prune-backups\":{\"keep-daily\":\"7\",\"keep-last\":\"3\"},\"repeat-missed\":0,\"schedule\":\"21:00\",\"storage\":\"pbs\",\"type\":\"vzdump\",\"vmid\":\"1017,1018\"}," +
            "{\"all\":1,\"enabled\":0,\"id\":\"backup-a91c\",\"mode\":\"stop\",\"prune-backups\":{\"keep-all\":1},\"schedule\":\"sat 02:00\",\"storage\":\"local\",\"type\":\"vzdump\"}" +
            "]";

    @Test
    @DisplayName("Should deserialize backup jobs with prune-backups, performance and fleecing objects")
    void shouldDeserializeJobs() {
        List<PveBackupJob> jobs = PveJson.parseList(JOBS_JSON, PveBackupJob.class);

        assertThat(jobs).hasSize(2);
        PveBackupJob job = jobs.get(0);
        assertThat(job.getId()).isEqualTo("backup-5f8e3c1a-2b4d");
        assertThat(job.isEnabled()).isTrue();
        assertThat(job.getNextRun()).isEqualTo(1793145600L);
        assertThat(job.getPruneBackups().getKeepLast()).isEqualTo(3);
        assertThat(job.getPruneBackups().getKeepDaily()).isEqualTo(7);
        assertThat(job.getPruneBackups().getKeepWeekly()).isNull();
        assertThat(job.getPruneBackups().getKeepAll()).isNull();
        assertThat(job.getPerformance().getMaxWorkers()).isEqualTo(8);
        assertThat(job.getPerformance().getPbsEntriesMax()).isEqualTo(2000000);
        assertThat(job.getFleecing().isEnabled()).isTrue();
        assertThat(job.getFleecing().getStorage()).isEqualTo("local-lvm");

        PveBackupJob keepAll = jobs.get(1);
        assertThat(keepAll.isEnabled()).isFalse();
        assertThat(keepAll.isAll()).isTrue();
        assertThat(keepAll.getPruneBackups().getKeepAll()).isTrue();
        assertThat(keepAll.getPerformance()).isNull();
        assertThat(keepAll.getFleecing()).isNull();
    }
}
