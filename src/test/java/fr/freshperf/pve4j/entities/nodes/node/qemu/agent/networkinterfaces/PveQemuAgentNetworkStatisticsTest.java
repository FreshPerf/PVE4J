package fr.freshperf.pve4j.entities.nodes.node.qemu.agent.networkinterfaces;

import fr.freshperf.pve4j.testsupport.PveJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("PveQemuAgentNetworkStatistics Entity Tests")
class PveQemuAgentNetworkStatisticsTest {

    @Test
    @DisplayName("Should read guest agent interface statistics (lower-case keys, 64-bit counters)")
    void shouldReadInterfaceStatistics() {
        PveQemuAgentNetworkInterfacesResult result = PveJson.parse(
                PveJson.fixture("qemu/agent-network-get-interfaces.json"), PveQemuAgentNetworkInterfacesResult.class);

        PveQemuAgentNetworkInterfaces ethernet = result.getResult()[0];
        assertThat(ethernet.getName()).isEqualTo("Ethernet");
        PveQemuAgentNetworkStatistics stats = ethernet.getStatistics();
        assertThat(stats.getRxBytes()).isEqualTo(51561492310L);
        assertThat(stats.getRxPackets()).isEqualTo(28922328L);
        assertThat(stats.getRxDropped()).isEqualTo(3169511L);
        assertThat(stats.getRxErrs()).isEqualTo(0L);
        assertThat(stats.getTxBytes()).isEqualTo(3983778475L);
        assertThat(stats.getTxPackets()).isEqualTo(17407301L);
        assertThat(stats.getTxDropped()).isEqualTo(0L);
        assertThat(stats.getTxErrs()).isEqualTo(0L);
    }
}
