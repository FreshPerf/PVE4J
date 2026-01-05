package fr.freshperf.proxmox4j;

import fr.freshperf.proxmox4j.entities.PveVersion;
import fr.freshperf.proxmox4j.entities.cluster.PveCluster;
import fr.freshperf.proxmox4j.request.ProxmoxHttpClient;
import fr.freshperf.proxmox4j.request.ProxmoxRequest;
import fr.freshperf.proxmox4j.throwable.ProxmoxAPIError;
import fr.freshperf.proxmox4j.util.ProxmoxApiBaseUrlBuilder;

import java.io.IOException;

public class Proxmox {

    private final ProxmoxHttpClient httpClient;

    private final PveCluster pveCluster;

    private Proxmox(String host, int port, String apikey, SecurityConfig securityConfig) {
        this.httpClient = new ProxmoxHttpClient(
                ProxmoxApiBaseUrlBuilder.buildApiBaseUrl(host, port)
                , apikey, securityConfig);

        this.pveCluster = new PveCluster(httpClient);
    }

    /**
     * Creates a Proxmox API client with custom security configuration.
     * 
     * @param host The Proxmox host address
     * @param port The Proxmox API port (usually 8006 or 443)
     * @param apikey The API token for authentication
     * @param securityConfig Security configuration for SSL/TLS
     * @return A new Proxmox instance
     */
    public static Proxmox create(String host, int port, String apikey, SecurityConfig securityConfig) {
        return new Proxmox(host, port, apikey, securityConfig);
    }

    /**
     * Creates a Proxmox API client with default secure settings.
     * 
     * @param host The Proxmox host address
     * @param port The Proxmox API port (usually 8006 or 443)
     * @param apikey The API token for authentication
     * @return A new Proxmox instance with all security checks enabled
     */
    public static Proxmox create(String host, int port, String apikey) {
        return new Proxmox(host, port, apikey, SecurityConfig.secure());
    }

    public ProxmoxRequest<PveVersion> getVersion() {
        return new ProxmoxRequest<>(() -> {
            return httpClient.get("/version").execute(PveVersion.class);
        });
    }

    public PveCluster getCluster() {
        return pveCluster;
    }

}
