package fr.freshperf.pve4j.entities.pools;

import fr.freshperf.pve4j.testsupport.RecordingPveServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Request and deserialization tests for the pools endpoints (shapes from PVE/API2/Pool.pm and
 * PVE::API2Tools::extract_vm_stats on PVE 9.1.4).
 */
@DisplayName("PvePools Tests")
class PvePoolsTest {

    private RecordingPveServer server;

    @BeforeEach
    void setUp() throws Exception {
        server = new RecordingPveServer();
    }

    @AfterEach
    void tearDown() {
        server.close();
    }

    @Test
    @DisplayName("Should use the poolid parameter endpoints for nested pools")
    void shouldUsePoolidParameterForNestedPools() throws Exception {
        PvePoolItem pool = new PvePools(server.client()).get("dev/team1");

        server.respondWith("[{\"comment\":\"Team 1\",\"members\":[],\"poolid\":\"dev/team1\"}]");
        PvePoolDetails details = pool.getDetails("qemu").execute();
        assertThat(server.last().method()).isEqualTo("GET");
        assertThat(server.last().rawPath()).isEqualTo("/api2/json/pools");
        assertThat(server.last().params()).containsEntry("poolid", "dev/team1").containsEntry("type", "qemu");
        assertThat(details.getComment()).isEqualTo("Team 1");
        assertThat(details.getMembers()).isEmpty();

        server.respondWith("null");
        pool.addVms(List.of(100, 101)).execute();
        assertThat(server.last().method()).isEqualTo("PUT");
        assertThat(server.last().rawPath()).isEqualTo("/api2/json/pools");
        assertThat(server.last().params()).containsEntry("poolid", "dev/team1").containsEntry("vms", "100,101");

        pool.delete().execute();
        assertThat(server.last().method()).isEqualTo("DELETE");
        assertThat(server.last().rawPath()).isEqualTo("/api2/json/pools");
        assertThat(server.last().params()).containsEntry("poolid", "dev/team1");
    }

    @Test
    @DisplayName("Should keep the path endpoints for flat pools")
    void shouldUsePathEndpointsForFlatPools() throws Exception {
        PvePoolItem pool = new PvePools(server.client()).get("dev");

        server.respondWith("{\"comment\":\"Dev\",\"members\":[]}");
        pool.getDetails().execute();
        assertThat(server.last().rawPath()).isEqualTo("/api2/json/pools/dev");
        assertThat(server.last().params()).isEmpty();

        server.respondWith("null");
        pool.delete().execute();
        assertThat(server.last().method()).isEqualTo("DELETE");
        assertThat(server.last().rawPath()).isEqualTo("/api2/json/pools/dev");
    }
}
