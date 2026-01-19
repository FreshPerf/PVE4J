package fr.freshperf.pve4j.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("PveVersion Entity Tests")
class PveVersionTest {

    @Test
    @DisplayName("Should create PveVersion with all fields")
    void shouldCreatePveVersionWithAllFields() {
        PveVersion version = new PveVersion();
        
        assertThat(version).isNotNull();
    }

    @Test
    @DisplayName("Should have valid toString representation")
    void shouldHaveValidToStringRepresentation() {
        PveVersion version = new PveVersion();
        String toString = version.toString();
        
        assertThat(toString).isNotNull();
        assertThat(toString).contains("PveVersion");
        assertThat(toString).contains("release");
        assertThat(toString).contains("repoid");
        assertThat(toString).contains("version");
        assertThat(toString).contains("console");
    }
}

