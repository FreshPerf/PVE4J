package fr.freshperf.pve4j.entities.nodes.node.qemu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("PveQemuIndex Entity Tests")
class PveQemuIndexTest {

    @Test
    @DisplayName("Should create PveQemuIndex instance")
    void shouldCreatePveQemuIndexInstance() {
        PveQemuIndex qemuIndex = new PveQemuIndex();
        
        assertThat(qemuIndex).isNotNull();
    }

    @Test
    @DisplayName("Should have valid toString representation")
    void shouldHaveValidToStringRepresentation() {
        PveQemuIndex qemuIndex = new PveQemuIndex();
        String toString = qemuIndex.toString();
        
        assertThat(toString).isNotNull();
        assertThat(toString).contains("PveQemuIndex");
        assertThat(toString).contains("vmid");
        assertThat(toString).contains("name");
        assertThat(toString).contains("status");
        assertThat(toString).contains("cpus");
        assertThat(toString).contains("cpu");
        assertThat(toString).contains("mem");
        assertThat(toString).contains("maxmem");
        assertThat(toString).contains("disk");
        assertThat(toString).contains("maxdisk");
    }

    @Test
    @DisplayName("Should have all getter methods")
    void shouldHaveAllGetterMethods() {
        PveQemuIndex qemuIndex = new PveQemuIndex();
        
        assertThatCode(() -> {
            qemuIndex.getVmid();
            qemuIndex.getCpus();
            qemuIndex.getMaxdisk();
            qemuIndex.getMaxmem();
            qemuIndex.getMem();
            qemuIndex.getDisk();
            qemuIndex.getNetin();
            qemuIndex.getNetout();
            qemuIndex.getDiskread();
            qemuIndex.getDiskwrite();
            qemuIndex.getUptime();
            qemuIndex.getCpu();
            qemuIndex.getName();
            qemuIndex.getStatus();
            qemuIndex.getTags();
            qemuIndex.getLock();
            qemuIndex.getPid();
            qemuIndex.getQmpstatus();
        }).doesNotThrowAnyException();
    }
}

