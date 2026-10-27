package fr.freshperf.pve4j.entities.nodes.node.qemu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@DisplayName("PveQemuMigrateOptions Tests")
class PveQemuMigrateOptionsTest {

    @Test
    @DisplayName("Should map every option to its API parameter name")
    void shouldMapAllOptions() {
        Map<String, Object> params = PveQemuMigrateOptions.builder()
                .online(true)
                .withLocalDisks(true)
                .withConntrackState(false)
                .targetstorage("local-lvm:DATA")
                .bwlimit(51200)
                .migrationNetwork("10.0.0.0/24")
                .migrationType("insecure")
                .force(true)
                .toParams("node2");

        assertThat(params).containsOnly(
                entry("target", "node2"),
                entry("online", "1"),
                entry("with-local-disks", "1"),
                entry("with-conntrack-state", "0"),
                entry("targetstorage", "local-lvm:DATA"),
                entry("bwlimit", "51200"),
                entry("migration_network", "10.0.0.0/24"),
                entry("migration_type", "insecure"),
                entry("force", "1"));
    }

    @Test
    @DisplayName("Should only send the target when no option is set")
    void shouldOnlySendTarget() {
        assertThat(PveQemuMigrateOptions.builder().toParams("node2")).containsOnly(entry("target", "node2"));
    }

    @Test
    @DisplayName("Should reject a negative bandwidth limit")
    void shouldRejectNegativeBwlimit() {
        assertThatThrownBy(() -> PveQemuMigrateOptions.builder().bwlimit(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
