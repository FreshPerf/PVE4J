package fr.freshperf.pve4j.entities.cluster.resources;

import fr.freshperf.pve4j.testsupport.PveJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("PveClusterResources Entity Tests")
class PveClusterResourcesTest {

    // Real `pvesh get /cluster/resources` entries (PVE 9.1).
    private static final String LIVE_JSON = "[" +
            "{\"cpu\":0.520995927820862,\"disk\":0,\"diskread\":70193241600,\"diskwrite\":224623393792,\"id\":\"qemu/1017\",\"maxcpu\":4,\"maxdisk\":42949672960,\"maxmem\":4294967296,\"mem\":2097221632,\"memhost\":4333651968,\"name\":\"vm1017\",\"netin\":51800008365,\"netout\":3983695180,\"node\":\"node1\",\"status\":\"running\",\"template\":0,\"type\":\"qemu\",\"uptime\":5510544,\"vmid\":1017}," +
            "{\"cgroup-mode\":2,\"cpu\":0.328959454139877,\"disk\":19667517440,\"id\":\"node/node1\",\"level\":\"\",\"maxcpu\":8,\"maxdisk\":26241896448,\"maxmem\":20972036096,\"mem\":6822412288,\"node\":\"node1\",\"status\":\"online\",\"type\":\"node\",\"uptime\":24542394}," +
            "{\"content\":\"backup\",\"disk\":5949546496,\"id\":\"storage/node1/pbs\",\"maxdisk\":295841169408,\"node\":\"node1\",\"plugintype\":\"pbs\",\"shared\":1,\"status\":\"available\",\"storage\":\"pbs\",\"type\":\"storage\"}" +
            "]";

    // Container entry as built by PVE::API2Tools::extract_vm_stats: maxcpu is the container's
    // `cores`, or its `cpulimit` (a number) when no cores are set (PVE::LXC::vmstatus).
    private static final String LXC_FRACTIONAL_CPULIMIT_JSON = "[" +
            "{\"cpu\":0,\"disk\":0,\"diskread\":0,\"diskwrite\":0,\"id\":\"lxc/200\",\"maxcpu\":1.5,\"maxdisk\":8589934592,\"maxmem\":536870912,\"mem\":0,\"name\":\"ct200\",\"netin\":0,\"netout\":0,\"node\":\"node1\",\"status\":\"stopped\",\"template\":0,\"type\":\"lxc\",\"uptime\":0,\"vmid\":200}" +
            "]";

    @Test
    @DisplayName("Should deserialize real cluster resources")
    void shouldDeserializeLiveResources() {
        List<PveClusterResources> resources = PveJson.parseList(LIVE_JSON, PveClusterResources.class);

        assertThat(resources).hasSize(3);
        PveClusterResources vm = resources.get(0);
        assertThat(vm.getVmid()).isEqualTo(1017);
        assertThat(vm.getMaxcpu()).isEqualTo(4.0);
        assertThat(resources.get(1).getMaxcpu()).isEqualTo(8.0);
        assertThat(resources.get(2).isShared()).isTrue();
        assertThat(resources.get(2).getPlugintype()).isEqualTo("pbs");
    }

    @Test
    @DisplayName("Should deserialize a container with a fractional CPU limit")
    void shouldDeserializeFractionalMaxcpu() {
        List<PveClusterResources> resources = PveJson.parseList(LXC_FRACTIONAL_CPULIMIT_JSON, PveClusterResources.class);

        assertThat(resources).hasSize(1);
        assertThat(resources.get(0).getType()).isEqualTo("lxc");
        assertThat(resources.get(0).getMaxcpu()).isEqualTo(1.5);
    }
}
