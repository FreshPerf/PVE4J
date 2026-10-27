package fr.freshperf.pve4j.entities;

import fr.freshperf.pve4j.testsupport.PveJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("PveTaskStatus Entity Tests")
class PveTaskStatusTest {

    private static PveTaskStatus status(String status, String exitstatus) {
        String json = "{\"exitstatus\":" + (exitstatus == null ? "null" : "\"" + exitstatus + "\"") +
                ",\"id\":\"1014\",\"node\":\"node1\",\"pid\":3967235,\"pstart\":2420321023,\"starttime\":1792770896," +
                "\"status\":\"" + status + "\",\"type\":\"qmshutdown\"," +
                "\"upid\":\"UPID:node1:003C8903:90432AFF:6ADB8350:qmshutdown:1014:root@pam!automation:\",\"user\":\"root@pam\"}";
        return PveJson.parse(json, PveTaskStatus.class);
    }

    @Test
    @DisplayName("Should deserialize a real finished task status")
    void shouldDeserializeLiveStatus() {
        // Real `pvesh get /nodes/{node}/tasks/{upid}/status` output (PVE 9.1).
        String json = "{\"exitstatus\":\"OK\",\"id\":\"\",\"node\":\"node1\",\"pid\":1834990,\"pstart\":2454234451,\"starttime\":1793110030," +
                "\"status\":\"stopped\",\"type\":\"vncshell\",\"upid\":\"UPID:node1:001BFFEE:9248A553:6AE0B00E:vncshell::root@pam:\",\"user\":\"root@pam\"}";

        PveTaskStatus status = PveJson.parse(json, PveTaskStatus.class);

        assertThat(status.isCompleted()).isTrue();
        assertThat(status.isSuccessful()).isTrue();
        assertThat(status.hasWarnings()).isFalse();
        assertThat(status.getPid()).isEqualTo("1834990");
    }

    @Test
    @DisplayName("Should treat a task finished with warnings as successful")
    void shouldTreatWarningsAsSuccessful() {
        // PVE::UPID::read_status returns "WARNINGS: <n>" for a task that ended with "TASK WARNINGS: <n>";
        // PVE::UPID::status_is_error treats it as a success.
        PveTaskStatus status = status("stopped", "WARNINGS: 2");

        assertThat(status.isSuccessful()).isTrue();
        assertThat(status.hasWarnings()).isTrue();
    }

    @Test
    @DisplayName("Should treat an error exit status as a failure")
    void shouldTreatErrorAsFailure() {
        // Real exit status of a failed task (PVE 9.1).
        PveTaskStatus status = status("stopped", "VM quit/powerdown failed - got timeout");

        assertThat(status.isSuccessful()).isFalse();
        assertThat(status.hasWarnings()).isFalse();
    }

    @Test
    @DisplayName("Should not report a running task as successful")
    void shouldNotReportRunningTaskAsSuccessful() {
        PveTaskStatus status = status("running", null);

        assertThat(status.isRunning()).isTrue();
        assertThat(status.isSuccessful()).isFalse();
        assertThat(status.hasWarnings()).isFalse();
    }

    @Test
    @DisplayName("Should not mistake an error mentioning warnings for warnings")
    void shouldNotMistakeErrorMentioningWarnings() {
        PveTaskStatus status = status("stopped", "WARNINGS: disk full");

        assertThat(status.isSuccessful()).isFalse();
        assertThat(status.hasWarnings()).isFalse();
    }
}
