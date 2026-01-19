package fr.freshperf.pve4j.integration;

import fr.freshperf.pve4j.Proxmox;
import fr.freshperf.pve4j.SecurityConfig;
import fr.freshperf.pve4j.entities.PveVersion;
import fr.freshperf.pve4j.entities.access.PveAccessUser;
import fr.freshperf.pve4j.entities.cluster.resources.PveClusterResources;
import fr.freshperf.pve4j.entities.nodes.PveNodesIndex;
import fr.freshperf.pve4j.entities.nodes.node.PveNode;
import fr.freshperf.pve4j.entities.nodes.node.lxc.PveLxcIndex;
import fr.freshperf.pve4j.entities.nodes.node.qemu.PveQemuConfig;
import fr.freshperf.pve4j.entities.nodes.node.qemu.PveQemuIndex;
import fr.freshperf.pve4j.entities.nodes.node.qemu.PveQemuStatus;
import fr.freshperf.pve4j.entities.nodes.node.qemu.PveQemuVm;
import fr.freshperf.pve4j.entities.nodes.node.storage.PveStorageIndex;
import fr.freshperf.pve4j.throwable.ProxmoxAPIError;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Integration tests for Proxmox4J library.
 * These tests require a real Proxmox server to be available.
 * 
 * Set the following environment variables to run these tests:
 * - PROXMOX_HOST: Proxmox server hostname or IP
 * - PROXMOX_PORT: Proxmox API port (default: 8006)
 * - PROXMOX_TOKEN: API token for authentication
 * - PROXMOX_NODE: Node name to test against
 * - PROXMOX_VMID: VM ID to test against
 */
@DisplayName("Proxmox Integration Tests")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProxmoxIntegrationTest {

    private Proxmox proxmox;
    private String testNodeName;
    private Integer testVmId;
    private boolean integrationTestsEnabled;

    @BeforeAll
    void setUp() {
        String host = System.getenv("PROXMOX_HOST");
        String portStr = System.getenv("PROXMOX_PORT");
        String token = System.getenv("PROXMOX_TOKEN");
        testNodeName = System.getenv("PROXMOX_NODE");
        String vmIdStr = System.getenv("PROXMOX_VMID");

        integrationTestsEnabled = host != null && token != null;

        if (integrationTestsEnabled) {
            int port = portStr != null ? Integer.parseInt(portStr) : 8006;
            testVmId = vmIdStr != null ? Integer.parseInt(vmIdStr) : null;
            
            proxmox = Proxmox.create(host, port, token, SecurityConfig.insecure());
        }
    }

    @Test
    @DisplayName("Integration: Should get Proxmox version")
    void shouldGetProxmoxVersion() throws ProxmoxAPIError, InterruptedException {
        assumeTrue(integrationTestsEnabled, "Integration tests are not configured");

        PveVersion version = proxmox.getVersion().execute();

        assertThat(version).isNotNull();
        assertThat(version.getVersion()).isNotNull().isNotEmpty();
        assertThat(version.getRelease()).isNotNull();
        assertThat(version.getRepoid()).isNotNull();
        
        System.out.println("Proxmox Version: " + version.getVersion());
        System.out.println("Release: " + version.getRelease());
    }

    @Test
    @DisplayName("Integration: Should list cluster nodes")
    void shouldListClusterNodes() throws ProxmoxAPIError, InterruptedException {
        assumeTrue(integrationTestsEnabled, "Integration tests are not configured");

        List<PveNodesIndex> nodes = proxmox.getNodes().getIndex().execute();

        assertThat(nodes).isNotNull().isNotEmpty();
        
        for (PveNodesIndex node : nodes) {
            assertThat(node.getNode()).isNotNull().isNotEmpty();
            assertThat(node.getStatus()).isNotNull();
            assertThat(node.getCpu()).isGreaterThanOrEqualTo(0);
            assertThat(node.getMaxcpu()).isGreaterThan(0);
            assertThat(node.getMaxmem()).isGreaterThan(0);
            
            System.out.printf("Node: %s - Status: %s, CPU: %.2f%%, Memory: %d MB%n",
                    node.getNode(), node.getStatus(), node.getCpu() * 100,
                    node.getMem() / (1024 * 1024));
        }
    }

    @Test
    @DisplayName("Integration: Should list virtual machines")
    void shouldListVirtualMachines() throws ProxmoxAPIError, InterruptedException {
        assumeTrue(integrationTestsEnabled && testNodeName != null, 
                "Integration tests are not configured or node name not provided");

        PveNode node = proxmox.getNodes().get(testNodeName);
        List<PveQemuIndex> vms = node.getQemu().getIndex().execute();

        assertThat(vms).isNotNull();
        
        for (PveQemuIndex vm : vms) {
            assertThat(vm.getVmid()).isGreaterThan(0);
            assertThat(vm.getName()).isNotNull();
            assertThat(vm.getStatus()).isNotNull();
            
            System.out.printf("VM %d: %s - Status: %s, CPU: %.2f%%, Memory: %d MB%n",
                    vm.getVmid(), vm.getName(), vm.getStatus(), 
                    vm.getCpu() * 100, vm.getMem() / (1024 * 1024));
        }
    }

    @Test
    @DisplayName("Integration: Should get VM status")
    void shouldGetVmStatus() throws ProxmoxAPIError, InterruptedException {
        assumeTrue(integrationTestsEnabled && testNodeName != null && testVmId != null,
                "Integration tests are not configured or VM details not provided");

        PveQemuVm vm = proxmox.getNodes().get(testNodeName).getQemu().get(testVmId);
        PveQemuStatus status = vm.getStatus().execute();

        assertThat(status).isNotNull();
        assertThat(status.getStatus()).isNotNull();
        
        System.out.printf("VM %d Status: %s, Running: %s%n",
                testVmId, status.getStatus(), status.isRunning());
    }

    @Test
    @DisplayName("Integration: Should get VM configuration")
    void shouldGetVmConfiguration() throws ProxmoxAPIError, InterruptedException {
        assumeTrue(integrationTestsEnabled && testNodeName != null && testVmId != null,
                "Integration tests are not configured or VM details not provided");

        PveQemuVm vm = proxmox.getNodes().get(testNodeName).getQemu().get(testVmId);
        PveQemuConfig config = vm.getConfig().execute();

        assertThat(config).isNotNull();
        assertThat(config.getName()).isNotNull();
        
        System.out.printf("VM %d Configuration: Name=%s, OS=%s, Cores=%d, Memory=%d MB%n",
                testVmId, config.getName(), config.getOstype(),
                config.getCores(), config.getMemory());
    }

    @Test
    @DisplayName("Integration: Should list containers")
    void shouldListContainers() throws ProxmoxAPIError, InterruptedException {
        assumeTrue(integrationTestsEnabled && testNodeName != null,
                "Integration tests are not configured or node name not provided");

        PveNode node = proxmox.getNodes().get(testNodeName);
        List<PveLxcIndex> containers = node.getLxc().getIndex().execute();

        assertThat(containers).isNotNull();
        
        for (PveLxcIndex ct : containers) {
            assertThat(ct.getVmid()).isGreaterThan(0);
            
            System.out.printf("Container %d: %s - Status: %s%n",
                    ct.getVmid(), ct.getName(), ct.getStatus());
        }
    }

    @Test
    @DisplayName("Integration: Should list storage")
    void shouldListStorage() throws ProxmoxAPIError, InterruptedException {
        assumeTrue(integrationTestsEnabled && testNodeName != null,
                "Integration tests are not configured or node name not provided");

        PveNode node = proxmox.getNodes().get(testNodeName);
        List<PveStorageIndex> storages = node.getStorage().getIndex().execute();

        assertThat(storages).isNotNull();
        
        for (PveStorageIndex storage : storages) {
            assertThat(storage.getStorage()).isNotNull();
            assertThat(storage.getType()).isNotNull();
            
            System.out.printf("Storage: %s (%s) - Active: %s, Used: %.1f%%%n",
                    storage.getStorage(), storage.getType(),
                    storage.isActive(), storage.getUsed_fraction() * 100);
        }
    }

    @Test
    @DisplayName("Integration: Should get cluster resources")
    void shouldGetClusterResources() throws ProxmoxAPIError, InterruptedException {
        assumeTrue(integrationTestsEnabled, "Integration tests are not configured");

        List<PveClusterResources> resources = proxmox.getCluster().getResources().execute();

        assertThat(resources).isNotNull().isNotEmpty();
        
        long nodes = resources.stream().filter(r -> "node".equals(r.getType())).count();
        long vms = resources.stream().filter(r -> "qemu".equals(r.getType())).count();
        long containers = resources.stream().filter(r -> "lxc".equals(r.getType())).count();
        
        System.out.printf("Cluster Resources - Nodes: %d, VMs: %d, Containers: %d%n",
                nodes, vms, containers);
    }

    @Test
    @DisplayName("Integration: Should filter cluster resources by type")
    void shouldFilterClusterResourcesByType() throws ProxmoxAPIError, InterruptedException {
        assumeTrue(integrationTestsEnabled, "Integration tests are not configured");

        List<PveClusterResources> vmResources = proxmox.getCluster().getResources("vm").execute();

        assertThat(vmResources).isNotNull();
        
        for (PveClusterResources vm : vmResources) {
            assertThat(vm.getType()).isIn("qemu", "lxc");
            
            if ("running".equals(vm.getStatus())) {
                System.out.printf("Running VM: %s (VMID: %d) on %s%n",
                        vm.getName(), vm.getVmid(), vm.getNode());
            }
        }
    }

    @Test
    @DisplayName("Integration: Should list users")
    void shouldListUsers() throws ProxmoxAPIError, InterruptedException {
        assumeTrue(integrationTestsEnabled, "Integration tests are not configured");

        List<PveAccessUser> users = proxmox.getAccess().getUsers().getIndex().execute();

        assertThat(users).isNotNull().isNotEmpty();
        
        for (PveAccessUser user : users) {
            assertThat(user.getUserid()).isNotNull();
            
            System.out.printf("User: %s - Enabled: %s%n",
                    user.getUserid(), user.isEnable());
        }
    }
}

