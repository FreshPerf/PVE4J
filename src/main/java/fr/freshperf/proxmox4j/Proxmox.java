package fr.freshperf.proxmox4j;

import fr.freshperf.proxmox4j.entities.PveTask;
import fr.freshperf.proxmox4j.entities.PveTaskStatus;
import fr.freshperf.proxmox4j.entities.PveVersion;
import fr.freshperf.proxmox4j.entities.access.PveAccess;
import fr.freshperf.proxmox4j.entities.access.PveAccessTicket;
import fr.freshperf.proxmox4j.entities.cluster.PveCluster;
import fr.freshperf.proxmox4j.entities.nodes.PveNodes;
import fr.freshperf.proxmox4j.request.ProxmoxHttpClient;
import fr.freshperf.proxmox4j.request.ProxmoxRequest;
import fr.freshperf.proxmox4j.throwable.ProxmoxAPIError;
import fr.freshperf.proxmox4j.util.ProxmoxApiBaseUrlBuilder;

public class Proxmox {

    private final ProxmoxHttpClient httpClient;

    private final PveCluster pveCluster;
    private final PveNodes pveNodes;
    private final PveAccess pveAccess;

    private Proxmox(String host, int port, String apikey, SecurityConfig securityConfig) {
        this.httpClient = new ProxmoxHttpClient(
                ProxmoxApiBaseUrlBuilder.buildApiBaseUrl(host, port)
                , apikey, securityConfig);

        this.pveCluster = new PveCluster(httpClient);
        this.pveNodes = new PveNodes(httpClient);
        this.pveAccess = new PveAccess(httpClient);
    }

    private Proxmox(ProxmoxHttpClient httpClient) {
        this.httpClient = httpClient;
        this.pveCluster = new PveCluster(httpClient);
        this.pveNodes = new PveNodes(httpClient);
        this.pveAccess = new PveAccess(httpClient);
    }

    public ProxmoxHttpClient getHttpClient() {
        return httpClient;
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

    /**
     * Creates a Proxmox API client using username and password authentication.
     * This method authenticates with the Proxmox server and obtains a ticket for subsequent requests.
     * 
     * @param host The Proxmox host address
     * @param port The Proxmox API port (usually 8006 or 443)
     * @param username The username for authentication
     * @param password The password for authentication
     * @param realm The authentication realm (default: pam)
     * @param securityConfig Security configuration for SSL/TLS
     * @return A new Proxmox instance authenticated with username/password
     * @throws ProxmoxAPIError If authentication fails
     * @throws InterruptedException If the request is interrupted
     */
    public static Proxmox createWithPassword(String host, int port, String username, String password, String realm, SecurityConfig securityConfig) throws ProxmoxAPIError, InterruptedException {
        String baseUrl = ProxmoxApiBaseUrlBuilder.buildApiBaseUrl(host, port);
        ProxmoxHttpClient tempClient = ProxmoxHttpClient.createUnauthenticated(baseUrl, securityConfig);
        PveAccess tempAccess = new PveAccess(tempClient);
        
        PveAccessTicket ticket = tempAccess.getTicket(username, password, realm).execute();
        
        ProxmoxHttpClient authenticatedClient = new ProxmoxHttpClient(
            baseUrl, 
            ticket.getTicket(), 
            ticket.getCSRFPreventionToken(), 
            securityConfig
        );
        
        return new Proxmox(authenticatedClient);
    }

    /**
     * Creates a Proxmox API client using username and password authentication with default PAM realm.
     * This method authenticates with the Proxmox server and obtains a ticket for subsequent requests.
     * 
     * @param host The Proxmox host address
     * @param port The Proxmox API port (usually 8006 or 443)
     * @param username The username for authentication
     * @param password The password for authentication
     * @param securityConfig Security configuration for SSL/TLS
     * @return A new Proxmox instance authenticated with username/password
     * @throws ProxmoxAPIError If authentication fails
     * @throws InterruptedException If the request is interrupted
     */
    public static Proxmox createWithPassword(String host, int port, String username, String password, SecurityConfig securityConfig) throws ProxmoxAPIError, InterruptedException {
        return createWithPassword(host, port, username, password, "pam", securityConfig);
    }

    /**
     * Creates a Proxmox API client using username and password authentication with default secure settings.
     * This method authenticates with the Proxmox server and obtains a ticket for subsequent requests.
     * 
     * @param host The Proxmox host address
     * @param port The Proxmox API port (usually 8006 or 443)
     * @param username The username for authentication
     * @param password The password for authentication
     * @return A new Proxmox instance authenticated with username/password
     * @throws ProxmoxAPIError If authentication fails
     * @throws InterruptedException If the request is interrupted
     */
    public static Proxmox createWithPassword(String host, int port, String username, String password) throws ProxmoxAPIError, InterruptedException {
        return createWithPassword(host, port, username, password, "pam", SecurityConfig.secure());
    }

    public ProxmoxRequest<PveVersion> getVersion() {
        return new ProxmoxRequest<>(() -> {
            return httpClient.get("/version").execute(PveVersion.class);
        });
    }

    public ProxmoxRequest<PveTaskStatus> getTaskStatus(PveTask task) {
        if (task == null || task.getUpid() == null || task.getNode() == null) {
            throw new IllegalArgumentException("Task and its UPID and node must not be null");
        }
        return new ProxmoxRequest<>(() -> {
            return httpClient.get("nodes/" + task.getNode() + "/tasks/" + task.getUpid() + "/status")
                    .execute(PveTaskStatus.class);
        });
    }

    public ProxmoxRequest<PveTaskStatus> getTaskStatus(String node, String upid) {
        if (node == null || upid == null) {
            throw new IllegalArgumentException("Node and UPID must not be null");
        }
        return new ProxmoxRequest<>(() -> {
            return httpClient.get("nodes/" + node + "/tasks/" + upid + "/status")
                    .execute(PveTaskStatus.class);
        });
    }

    public PveCluster getCluster() {
        return pveCluster;
    }

    public PveNodes getNodes() {
        return pveNodes;
    }

    public PveAccess getAccess() {
        return pveAccess;
    }

}
