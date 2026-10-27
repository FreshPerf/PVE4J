package fr.freshperf.pve4j.entities.nodes.node.qemu.snapshot;

import fr.freshperf.pve4j.testsupport.PveJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("PveQemuSnapshotConfig Entity Tests")
class PveQemuSnapshotConfigTest {

    // GET .../snapshot/{snapname}/config returns the raw snapshot section, values as strings
    @Test
    @DisplayName("Should read memory written as a property string (current=...)")
    void shouldReadMemoryPropertyString() {
        String json = "{\"cores\":\"2\",\"sockets\":\"1\",\"memory\":\"current=2048\",\"name\":\"vm\","
                + "\"snaptime\":\"1793110000\",\"description\":\"before upgrade\"}";

        PveQemuSnapshotConfig config = PveJson.parse(json, PveQemuSnapshotConfig.class);

        assertThat(config.getMemory()).isEqualTo(2048);
        assertThat(config.getCores()).isEqualTo(2);
    }

    @Test
    @DisplayName("Should read plain memory values and missing memory")
    void shouldReadPlainAndMissingMemory() {
        assertThat(PveJson.parse("{\"memory\":\"4096\"}", PveQemuSnapshotConfig.class).getMemory()).isEqualTo(4096);
        assertThat(PveJson.parse("{\"memory\":4096}", PveQemuSnapshotConfig.class).getMemory()).isEqualTo(4096);
        assertThat(PveJson.parse("{}", PveQemuSnapshotConfig.class).getMemory()).isNull();
        assertThat(PveJson.parse("{\"memory\":\"current=abc\"}", PveQemuSnapshotConfig.class).getMemory()).isNull();
    }
}
