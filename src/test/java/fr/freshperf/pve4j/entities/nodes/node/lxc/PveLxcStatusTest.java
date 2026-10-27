package fr.freshperf.pve4j.entities.nodes.node.lxc;

import fr.freshperf.pve4j.testsupport.PveJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * PVE::LXC::vmstatus sets {@code cpus = cores || cpulimit || host CPU count}; cpulimit is a
 * number (e.g. 0.5), so {@code cpus} is fractional for a container with a fractional cpulimit
 * and no cores.
 */
@DisplayName("PveLxcStatus Entity Tests")
class PveLxcStatusTest {

    // GET /nodes/{node}/lxc/{vmid}/status/current built from PVE::LXC::vmstatus.
    private static final String STATUS_JSON =
            "{\"cpu\":0,\"cpus\":0.5,\"disk\":0,\"diskread\":0,\"diskwrite\":0,\"ha\":{\"managed\":0},\"maxdisk\":8589934592,\"maxmem\":536870912,\"maxswap\":536870912,\"mem\":0,\"name\":\"ct200\",\"netin\":0,\"netout\":0,\"status\":\"stopped\",\"swap\":0,\"type\":\"lxc\",\"uptime\":0,\"vmid\":200}";

    @Test
    @DisplayName("Should deserialize container status with fractional cpus")
    void shouldDeserializeStatusWithFractionalCpus() {
        PveLxcStatus status = PveJson.parse(STATUS_JSON, PveLxcStatus.class);

        assertThat(status.getCpus()).isEqualTo(0.5);
        assertThat(status.getVmid()).isEqualTo(200);
    }

    @Test
    @DisplayName("Should return null cpus when absent")
    void shouldReturnNullCpusWhenAbsent() {
        PveLxcStatus status = PveJson.parse("{\"vmid\":200}", PveLxcStatus.class);

        assertThat(status.getCpus()).isNull();
    }
}
