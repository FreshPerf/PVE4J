package fr.freshperf.pve4j.entities.nodes.node.qemu;

import fr.freshperf.pve4j.testsupport.PveJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("PveQemuStatus Entity Tests")
class PveQemuStatusTest {

    @Test
    @DisplayName("Should report a running VM as running (status/current has no 'running' key)")
    void shouldReportRunningVmAsRunning() {
        PveQemuStatus status = PveJson.parse(PveJson.fixture("qemu/status-current-running.json"), PveQemuStatus.class);

        assertThat(status.getVmid()).isEqualTo(1017);
        assertThat(status.getStatus()).isEqualTo("running");
        assertThat(status.getQmpstatus()).isEqualTo("running");
        assertThat(status.isRunning()).isTrue();
        assertThat(status.getCpus()).isEqualTo(4);
        assertThat(status.getPid()).isEqualTo(2184736);
        assertThat(status.getNetin()).isEqualTo(51799946316L);
        assertThat(status.getBallooninfo().getMem_swapped_in()).isEqualTo(107370835968L);
    }

    @Test
    @DisplayName("Should report a stopped VM as not running")
    void shouldReportStoppedVmAsNotRunning() {
        PveQemuStatus status = PveJson.parse(PveJson.fixture("qemu/status-current-stopped.json"), PveQemuStatus.class);

        assertThat(status.getStatus()).isEqualTo("stopped");
        assertThat(status.isRunning()).isFalse();
        assertThat(status.getCpus()).isEqualTo(4);
    }
}
