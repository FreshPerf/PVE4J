package fr.freshperf.pve4j;

import fr.freshperf.pve4j.entities.PveTask;
import fr.freshperf.pve4j.entities.PveTaskStatus;
import fr.freshperf.pve4j.entities.PveVersion;
import fr.freshperf.pve4j.entities.access.PveAccess;
import fr.freshperf.pve4j.entities.access.PveAccessTicket;
import fr.freshperf.pve4j.entities.cluster.PveCluster;
import fr.freshperf.pve4j.entities.nodes.PveNodes;
import fr.freshperf.pve4j.request.ProxmoxHttpClient;
import fr.freshperf.pve4j.request.ProxmoxRequest;
import fr.freshperf.pve4j.throwable.ProxmoxAPIError;
import fr.freshperf.pve4j.util.ProxmoxApiBaseUrlBuilder;

/**
 * Main entry point for the Proxmox VE API client.
 * Provides access to cluster, nodes, and access management endpoints.
 */
public class Proxmox {

    private final ProxmoxHttpClient httpClient;

    private final PveCluster pveCluster;
    private final PveNodes pveNodes;
    private final PveAccess pveAccess;

    /**
     * Creates a new Proxmox client with API key authentication.
     *
     * @param host           the Proxmox host address
     * @param port           the API port (usually 8006)
     * @param apikey         the API token for authentication
     * @param securityConfig SSL/TLS security configuration
     */
    private Proxmox(String host, int port, String apikey, SecurityConfig securityConfig) {
        this.httpClient = new ProxmoxHttpClient(
                ProxmoxApiBaseUrlBuilder.buildApiBaseUrl(host, port)
                , apikey, securityConfig);

        this.pveCluster = new PveCluster(httpClient);
        this.pveNodes = new PveNodes(httpClient);
        this.pveAccess = new PveAccess(httpClient);
    }

    /**
     * Creates a new Proxmox client with an existing HTTP client.
     *
     * @param httpClient the pre-configured HTTP client
     */
    private Proxmox(ProxmoxHttpClient httpClient) {
        this.httpClient = httpClient;
        this.pveCluster = new PveCluster(httpClient);
        this.pveNodes = new PveNodes(httpClient);
        this.pveAccess = new PveAccess(httpClient);
    }

    /**
     * Returns the underlying HTTP client.
     *
     * @return the HTTP client used for API requests
     */
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

    /**
     * Gets the Proxmox VE version information.
     *
     * @return a request that returns the Proxmox version details
     */
    public ProxmoxRequest<PveVersion> getVersion() {
        return new ProxmoxRequest<>(() -> {
            return httpClient.get("/version").execute(PveVersion.class);
        });
    }

    /**
     * Gets the status of a task.
     *
     * @param task the task to check (must have valid UPID and node)
     * @return a request that returns the task status
     * @throws IllegalArgumentException if task, UPID, or node is null
     */
    public ProxmoxRequest<PveTaskStatus> getTaskStatus(PveTask task) {
        if (task == null || task.getUpid() == null || task.getNode() == null) {
            throw new IllegalArgumentException("Task and its UPID and node must not be null");
        }
        return new ProxmoxRequest<>(() -> {
            return httpClient.get("nodes/" + task.getNode() + "/tasks/" + task.getUpid() + "/status")
                    .execute(PveTaskStatus.class);
        });
    }

    /**
     * Gets the status of a task by node and UPID.
     *
     * @param node the node name where the task is running
     * @param upid the unique process ID of the task
     * @return a request that returns the task status
     * @throws IllegalArgumentException if node or UPID is null
     */
    public ProxmoxRequest<PveTaskStatus> getTaskStatus(String node, String upid) {
        if (node == null || upid == null) {
            throw new IllegalArgumentException("Node and UPID must not be null");
        }
        return new ProxmoxRequest<>(() -> {
            return httpClient.get("nodes/" + node + "/tasks/" + upid + "/status")
                    .execute(PveTaskStatus.class);
        });
    }

    /**
     * Gets the cluster management interface.
     *
     * @return the cluster API facade
     */
    public PveCluster getCluster() {
        return pveCluster;
    }

    /**
     * Gets the nodes management interface.
     *
     * @return the nodes API facade
     */
    public PveNodes getNodes() {
        return pveNodes;
    }

    /**
     * Gets the access/authentication management interface.
     *
     * @return the access API facade
     */
    public PveAccess getAccess() {
        return pveAccess;
    }

    /**
     * Shuts down all shared thread resources gracefully.
     * Should be called once before application shutdown.
     * This method is static because threads are shared between all Proxmox instances.
     */
    public static void shutdownGlobalResources() {
        ProxmoxRequest.shutdownGlobalThreadManagers();
    }

}
