package fr.freshperf.proxmox4j;

import fr.freshperf.proxmox4j.entities.PveTask;
import fr.freshperf.proxmox4j.entities.PveTaskStatus;
import fr.freshperf.proxmox4j.entities.PveVersion;
import fr.freshperf.proxmox4j.entities.access.PveAccess;
import fr.freshperf.proxmox4j.entities.cluster.PveCluster;
import fr.freshperf.proxmox4j.entities.nodes.PveNodes;
import fr.freshperf.proxmox4j.request.ProxmoxHttpClient;
import fr.freshperf.proxmox4j.throwable.ProxmoxAPIError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Proxmox Client Tests")
class ProxmoxTest {

    private Proxmox proxmox;
    
    @BeforeEach
    void setUp() {
        proxmox = Proxmox.create("test.example.com", 8006, "test-api-key", SecurityConfig.insecure());
    }

    @Test
    @DisplayName("Should create Proxmox instance with custom security config")
    void shouldCreateProxmoxWithCustomSecurityConfig() {
        Proxmox instance = Proxmox.create("192.168.1.100", 443, "api-key", SecurityConfig.insecure());
        
        assertThat(instance).isNotNull();
        assertThat(instance.getHttpClient()).isNotNull();
        assertThat(instance.getCluster()).isNotNull();
        assertThat(instance.getNodes()).isNotNull();
        assertThat(instance.getAccess()).isNotNull();
    }

    @Test
    @DisplayName("Should create Proxmox instance with default secure config")
    void shouldCreateProxmoxWithDefaultSecureConfig() {
        Proxmox instance = Proxmox.create("proxmox.local", 8006, "secure-key");
        
        assertThat(instance).isNotNull();
        assertThat(instance.getHttpClient()).isNotNull();
    }

    @Test
    @DisplayName("Should return valid PveCluster instance")
    void shouldReturnValidPveCluster() {
        PveCluster cluster = proxmox.getCluster();
        
        assertThat(cluster).isNotNull();
    }

    @Test
    @DisplayName("Should return valid PveNodes instance")
    void shouldReturnValidPveNodes() {
        PveNodes nodes = proxmox.getNodes();
        
        assertThat(nodes).isNotNull();
    }

    @Test
    @DisplayName("Should return valid PveAccess instance")
    void shouldReturnValidPveAccess() {
        PveAccess access = proxmox.getAccess();
        
        assertThat(access).isNotNull();
    }

    @Test
    @DisplayName("Should return valid ProxmoxHttpClient")
    void shouldReturnValidHttpClient() {
        ProxmoxHttpClient client = proxmox.getHttpClient();
        
        assertThat(client).isNotNull();
    }

    @Test
    @DisplayName("Should create getVersion request")
    void shouldCreateGetVersionRequest() {
        var versionRequest = proxmox.getVersion();
        
        assertThat(versionRequest).isNotNull();
    }

    @Test
    @DisplayName("Should throw exception when getting task status with null task")
    void shouldThrowExceptionWhenGettingTaskStatusWithNullTask() {
        assertThatThrownBy(() -> proxmox.getTaskStatus((PveTask) null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Task and its UPID and node must not be null");
    }

    @Test
    @DisplayName("Should throw exception when getting task status with null UPID")
    void shouldThrowExceptionWhenGettingTaskStatusWithNullUpid() {
        assertThatThrownBy(() -> proxmox.getTaskStatus((String) null, "UPID:123"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Node and UPID must not be null");
    }

    @Test
    @DisplayName("Should throw exception when getting task status with null node")
    void shouldThrowExceptionWhenGettingTaskStatusWithNullNode() {
        assertThatThrownBy(() -> proxmox.getTaskStatus("node1", null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Node and UPID must not be null");
    }

    @Test
    @DisplayName("Should create task status request with valid node and UPID")
    void shouldCreateTaskStatusRequestWithValidNodeAndUpid() {
        var taskStatusRequest = proxmox.getTaskStatus("pve-node", "UPID:123456");
        
        assertThat(taskStatusRequest).isNotNull();
    }
}

