package fr.freshperf.pve4j.entities.nodes.node;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("PveNodeNetstat Entity Tests")
class PveNodeNetstatTest {

    @Test
    @DisplayName("Should create PveNodeNetstat instance")
    void shouldCreatePveNodeNetstatInstance() {
        PveNodeNetstat netstat = new PveNodeNetstat();

        assertThat(netstat).isNotNull();
    }

    @Test
    @DisplayName("Should deserialize netstat response with string-encoded numbers")
    void shouldDeserializeNetstatResponse() {
        String json = "[{\"dev\":\"tap100i0\",\"in\":\"123456789\",\"out\":\"987654321\",\"vmid\":\"100\"}," +
                "{\"dev\":\"veth101i0\",\"in\":42,\"out\":7,\"vmid\":101}]";

        List<PveNodeNetstat> stats = new Gson().fromJson(json, new TypeToken<List<PveNodeNetstat>>(){}.getType());

        assertThat(stats).hasSize(2);
        assertThat(stats.get(0).getDev()).isEqualTo("tap100i0");
        assertThat(stats.get(0).getVmid()).isEqualTo(100L);
        assertThat(stats.get(0).getIn()).isEqualTo(123456789L);
        assertThat(stats.get(0).getOut()).isEqualTo(987654321L);
        assertThat(stats.get(1).getDev()).isEqualTo("veth101i0");
        assertThat(stats.get(1).getVmid()).isEqualTo(101L);
        assertThat(stats.get(1).getIn()).isEqualTo(42L);
        assertThat(stats.get(1).getOut()).isEqualTo(7L);
    }

    @Test
    @DisplayName("Should have valid toString representation")
    void shouldHaveValidToStringRepresentation() {
        PveNodeNetstat netstat = new PveNodeNetstat();
        String toString = netstat.toString();

        assertThat(toString).isNotNull();
        assertThat(toString).contains("PveNodeNetstat");
        assertThat(toString).contains("vmid");
        assertThat(toString).contains("dev");
        assertThat(toString).contains("in");
        assertThat(toString).contains("out");
    }
}
