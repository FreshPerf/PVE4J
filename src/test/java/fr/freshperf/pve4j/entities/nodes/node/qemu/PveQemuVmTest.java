package fr.freshperf.pve4j.entities.nodes.node.qemu;

import fr.freshperf.pve4j.entities.PveTask;
import fr.freshperf.pve4j.request.ProxmoxHttpClient;
import fr.freshperf.pve4j.request.ProxmoxRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("PveQemuVm Tests")
class PveQemuVmTest {

    private final PveQemuVm vm = new PveQemuVm(null, "node1", 100);

    @Test
    @DisplayName("Should reject null monitor command")
    void shouldRejectNullMonitorCommand() {
        assertThatThrownBy(() -> vm.monitor(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("command");
    }

    @Test
    @DisplayName("Should reject empty monitor command")
    void shouldRejectEmptyMonitorCommand() {
        assertThatThrownBy(() -> vm.monitor(""))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("command");
    }

    @Test
    @DisplayName("Should reject blank monitor command")
    void shouldRejectBlankMonitorCommand() {
        assertThatThrownBy(() -> vm.monitor("   "))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("command");
    }

    @Test
    @DisplayName("Should create monitor request for valid command")
    void shouldCreateMonitorRequestForValidCommand() {
        ProxmoxRequest<String> request = vm.monitor("info status");

        assertThat(request).isNotNull();
    }

    @Test
    @DisplayName("Should reject POST-only options on the synchronous PUT config endpoint")
    void shouldRejectAsyncOnlyOptionsOnSyncConfigUpdate() {
        assertThatThrownBy(() -> vm.updateConfigSync(PveQemuConfigUpdateOptions.builder().cores(2).backgroundDelay(5)))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("background_delay");
        assertThatThrownBy(() -> vm.updateConfigSync(PveQemuConfigUpdateOptions.builder().importWorkingStorage("local")))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("import-working-storage");
        assertThat(vm.updateConfigSync(PveQemuConfigUpdateOptions.builder().cores(2))).isNotNull();
        assertThat(vm.updateConfig(PveQemuConfigUpdateOptions.builder().cores(2).backgroundDelay(5))).isNotNull();
    }

    @Test
    @DisplayName("Should send the migration to the migrate endpoint with the target node")
    @SuppressWarnings("unchecked")
    void shouldSendMigrateToTargetNode() throws Exception {
        ProxmoxHttpClient client = mock(ProxmoxHttpClient.class);
        ProxmoxHttpClient.RequestBuilder builder = mock(ProxmoxHttpClient.RequestBuilder.class, RETURNS_SELF);
        when(client.post("nodes/node1/qemu/100/migrate")).thenReturn(builder);
        doReturn(new PveTask()).when(builder).execute(PveTask.class);

        new PveQemuVm(client, "node1", 100).migrate("node2", PveQemuMigrateOptions.builder().online(true)).execute();

        ArgumentCaptor<Map<String, Object>> params = ArgumentCaptor.forClass(Map.class);
        verify(builder).params(params.capture());
        assertThat(params.getValue()).containsEntry("target", "node2");
    }

    @Test
    @DisplayName("Should reject migration without target node")
    void shouldRejectMigrationWithoutTarget() {
        assertThatThrownBy(() -> vm.migrate(" ", PveQemuMigrateOptions.builder()))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("targetNode");
    }
}
