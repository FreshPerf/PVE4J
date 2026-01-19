package fr.freshperf.pve4j.entities.nodes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.*;

@DisplayName("PveNodesIndex Entity Tests")
class PveNodesIndexTest {

    @Test
    @DisplayName("Should create PveNodesIndex instance")
    void shouldCreatePveNodesIndexInstance() {
        PveNodesIndex nodesIndex = new PveNodesIndex();
        
        assertThat(nodesIndex).isNotNull();
    }

    @Test
    @DisplayName("Should have valid toString representation")
    void shouldHaveValidToStringRepresentation() {
        PveNodesIndex nodesIndex = new PveNodesIndex();
        String toString = nodesIndex.toString();
        
        assertThat(toString).isNotNull();
        assertThat(toString).contains("PveNodesIndex");
        assertThat(toString).contains("node");
        assertThat(toString).contains("status");
        assertThat(toString).contains("cpu");
        assertThat(toString).contains("maxcpu");
        assertThat(toString).contains("mem");
        assertThat(toString).contains("maxmem");
    }

    @ParameterizedTest
    @EnumSource(PveNodesIndex.Status.class)
    @DisplayName("Should support all Status enum values")
    void shouldSupportAllStatusEnumValues(PveNodesIndex.Status status) {
        assertThat(status).isNotNull();
        assertThat(status.name()).isIn("unknown", "online", "offline");
    }

    @Test
    @DisplayName("Status enum should have three values")
    void statusEnumShouldHaveThreeValues() {
        PveNodesIndex.Status[] statuses = PveNodesIndex.Status.values();
        
        assertThat(statuses).hasSize(3);
        assertThat(statuses).containsExactlyInAnyOrder(
            PveNodesIndex.Status.unknown,
            PveNodesIndex.Status.online,
            PveNodesIndex.Status.offline
        );
    }
}

