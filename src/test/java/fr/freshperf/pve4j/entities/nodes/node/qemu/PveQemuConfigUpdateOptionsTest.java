package fr.freshperf.pve4j.entities.nodes.node.qemu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@DisplayName("PveQemuConfigUpdateOptions Tests")
class PveQemuConfigUpdateOptionsTest {

    @Test
    @DisplayName("Should send revert as a list of config keys (pve-configid-list), not as a boolean")
    void shouldSendRevertAsConfigKeyList() {
        Map<String, Object> params = PveQemuConfigUpdateOptions.builder()
                .revert("memory,net0")
                .toParams();

        assertThat(params).containsEntry("revert", "memory,net0");
    }
}
