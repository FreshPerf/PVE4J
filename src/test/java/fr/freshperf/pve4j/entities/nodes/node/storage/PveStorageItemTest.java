package fr.freshperf.pve4j.entities.nodes.node.storage;

import fr.freshperf.pve4j.testsupport.RecordingPveServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("PveStorageItem Tests")
class PveStorageItemTest {

    // Real `pvesh get /nodes/{node}/storage/local/rrddata --timeframe hour` entries (PVE 9.1):
    // consolidated values can be fractional.
    private static final String RRDDATA_JSON = "[{\"time\":1793106720,\"total\":26241896448,\"used\":19667288746.6667}," +
            "{\"time\":1793106780,\"total\":26241896448,\"used\":19667292160}," +
            "{\"time\":1793106840,\"total\":26241896448,\"used\":19667292160}]";

    private RecordingPveServer server;
    private PveStorageItem storage;

    @BeforeEach
    void setUp() throws Exception {
        server = new RecordingPveServer().respondWith(RRDDATA_JSON);
        storage = new PveStorageItem(server.client(), "node1", "local");
    }

    @AfterEach
    void tearDown() {
        server.close();
    }

    @Test
    @DisplayName("Should read RRD data points from the rrddata endpoint")
    void shouldGetRrdData() throws Exception {
        List<PveStorageRrdDataPoint> points = storage.getRrdData("hour", "AVERAGE").execute();

        assertThat(server.last().method()).isEqualTo("GET");
        assertThat(server.last().rawPath()).isEqualTo("/api2/json/nodes/node1/storage/local/rrddata");
        assertThat(server.last().params()).containsOnly(entry("timeframe", "hour"), entry("cf", "AVERAGE"));
        assertThat(points).hasSize(3);
        assertThat(points.get(0).getTime()).isEqualTo(1793106720L);
        assertThat(points.get(0).getTotal()).isEqualTo(26241896448d);
        assertThat(points.get(0).getUsed()).isEqualTo(19667288746.6667d);
    }

    @Test
    @DisplayName("Should send only the timeframe by default")
    void shouldGetRrdDataWithDefaultCf() throws Exception {
        storage.getRrdData("day").execute();

        assertThat(server.last().params()).containsOnly(entry("timeframe", "day"));
    }

    @Test
    @DisplayName("Should reject invalid RRD parameters")
    void shouldRejectInvalidRrdParameters() {
        assertThatThrownBy(() -> storage.getRrdData(null)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> storage.getRrdData("minute")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> storage.getRrdData("hour", "MIN")).isInstanceOf(IllegalArgumentException.class);
    }
}
