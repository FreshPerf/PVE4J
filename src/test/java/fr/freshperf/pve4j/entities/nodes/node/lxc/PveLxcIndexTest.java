package fr.freshperf.pve4j.entities.nodes.node.lxc;

import fr.freshperf.pve4j.testsupport.PveJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * PVE::LXC::vmstatus sets {@code cpus = cores || cpulimit || host CPU count}; cpulimit is a
 * number (e.g. 1.5), so {@code cpus} is fractional for a container with a fractional cpulimit
 * and no cores.
 */
@DisplayName("PveLxcIndex Entity Tests")
class PveLxcIndexTest {

    // GET /nodes/{node}/lxc entry built from PVE::LXC::vmstatus (stopped container, cpulimit: 1.5).
    private static final String INDEX_JSON = "[" +
            "{\"cpu\":0,\"cpus\":1.5,\"disk\":0,\"diskread\":0,\"diskwrite\":0,\"maxdisk\":8589934592,\"maxmem\":536870912,\"maxswap\":536870912,\"mem\":0,\"name\":\"ct200\",\"netin\":0,\"netout\":0,\"status\":\"stopped\",\"swap\":0,\"type\":\"lxc\",\"uptime\":0,\"vmid\":200}," +
            "{\"cpu\":0,\"cpus\":2,\"disk\":0,\"diskread\":0,\"diskwrite\":0,\"maxdisk\":8589934592,\"maxmem\":536870912,\"maxswap\":536870912,\"mem\":0,\"name\":\"ct201\",\"netin\":0,\"netout\":0,\"status\":\"stopped\",\"swap\":0,\"type\":\"lxc\",\"uptime\":0,\"vmid\":201}" +
            "]";

    @Test
    @DisplayName("Should deserialize container list with fractional cpus")
    void shouldDeserializeIndexWithFractionalCpus() {
        List<PveLxcIndex> index = PveJson.parseList(INDEX_JSON, PveLxcIndex.class);

        assertThat(index).hasSize(2);
        assertThat(index.get(0).getCpus()).isEqualTo(1.5);
        assertThat(index.get(1).getCpus()).isEqualTo(2.0);
    }
}
