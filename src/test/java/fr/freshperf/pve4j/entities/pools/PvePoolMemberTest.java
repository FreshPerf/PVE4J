package fr.freshperf.pve4j.entities.pools;

import fr.freshperf.pve4j.testsupport.PveJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("PvePoolMember Entity Tests")
class PvePoolMemberTest {

    @Test
    @DisplayName("Should deserialize a container member with a fractional CPU limit")
    void shouldDeserializeFractionalMaxcpu() {
        // Pool members are built by PVE::API2Tools::extract_vm_stats, like /cluster/resources.
        String json = "{\"cpu\":0,\"disk\":0,\"id\":\"lxc/200\",\"maxcpu\":1.5,\"maxdisk\":8589934592,\"maxmem\":536870912,\"mem\":0,\"name\":\"ct200\",\"node\":\"node1\",\"status\":\"stopped\",\"template\":0,\"type\":\"lxc\",\"uptime\":0,\"vmid\":200}";

        PvePoolMember member = PveJson.parse(json, PvePoolMember.class);

        assertThat(member.getMaxcpu()).isEqualTo(1.5);
        assertThat(member.getVmid()).isEqualTo(200);
    }

    @Test
    @DisplayName("Should return null maxcpu for storage members")
    void shouldReturnNullMaxcpuForStorage() {
        String json = "{\"content\":\"backup\",\"disk\":5949546496,\"id\":\"storage/node1/pbs\",\"maxdisk\":295841169408,\"node\":\"node1\",\"plugintype\":\"pbs\",\"shared\":1,\"status\":\"available\",\"storage\":\"pbs\",\"type\":\"storage\"}";

        PvePoolMember member = PveJson.parse(json, PvePoolMember.class);

        assertThat(member.getMaxcpu()).isNull();
        assertThat(member.getStorage()).isEqualTo("pbs");
    }
}
