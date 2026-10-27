package fr.freshperf.pve4j.entities.nodes.node.vzdump;

import fr.freshperf.pve4j.testsupport.PveJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("PveVzdumpDefaults Entity Tests")
class PveVzdumpDefaultsTest {

    @Test
    @DisplayName("Should deserialize real vzdump defaults with fleecing and performance objects")
    void shouldDeserializeLiveDefaults() {
        // Real `pvesh get /nodes/{node}/vzdump/defaults` output (PVE 9.1): PVE::VZDump::read_vzdump_defaults
        // always returns fleecing and performance as objects, prune-backups as a property string.
        String json = "{\"all\":0,\"bwlimit\":0,\"compress\":\"0\",\"dumpdir\":\"/var/lib/vz/dump\",\"fleecing\":{\"enabled\":0}," +
                "\"ionice\":7,\"lockwait\":180,\"mailnotification\":\"always\",\"mode\":\"snapshot\",\"notification-mode\":\"auto\"," +
                "\"performance\":{\"max-workers\":16,\"pbs-entries-max\":1048576},\"pigz\":0,\"prune-backups\":\"keep-all=1\"," +
                "\"quiet\":0,\"remove\":1,\"stdexcludes\":1,\"stop\":0,\"stopwait\":10,\"storage\":\"local\",\"zstd\":1}";

        PveVzdumpDefaults defaults = PveJson.parse(json, PveVzdumpDefaults.class);

        assertThat(defaults.getStorage()).isEqualTo("local");
        assertThat(defaults.getMode()).isEqualTo("snapshot");
        assertThat(defaults.getFleecing()).isNotNull();
        assertThat(defaults.getFleecing().isEnabled()).isFalse();
        assertThat(defaults.getFleecing().getStorage()).isNull();
        assertThat(defaults.getPerformance().getMaxWorkers()).isEqualTo(16);
        assertThat(defaults.getPerformance().getPbsEntriesMax()).isEqualTo(1048576);
        assertThat(defaults.getPruneBackups()).isEqualTo("keep-all=1");
        assertThat(defaults.isRemove()).isTrue();
        assertThat(defaults.isStdexcludes()).isTrue();
        assertThat(defaults.getLockwait()).isEqualTo(180);
    }

    @Test
    @DisplayName("Should deserialize fleecing and performance parsed from /etc/vzdump.conf")
    void shouldDeserializeConfiguredDefaults() {
        // Values parsed from vzdump.conf property strings keep integers as JSON strings.
        String json = "{\"fleecing\":{\"enabled\":1,\"storage\":\"local-lvm\"},\"performance\":{\"max-workers\":\"8\",\"pbs-entries-max\":1048576}}";

        PveVzdumpDefaults defaults = PveJson.parse(json, PveVzdumpDefaults.class);

        assertThat(defaults.getFleecing().isEnabled()).isTrue();
        assertThat(defaults.getFleecing().getStorage()).isEqualTo("local-lvm");
        assertThat(defaults.getPerformance().getMaxWorkers()).isEqualTo(8);
    }
}
