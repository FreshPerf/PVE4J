package fr.freshperf.pve4j.entities.nodes.node.qemu;

import fr.freshperf.pve4j.request.ProxmoxRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

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
}
