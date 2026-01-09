package fr.freshperf.proxmox4j.entities.access;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import fr.freshperf.proxmox4j.request.ProxmoxHttpClient;
import fr.freshperf.proxmox4j.request.ProxmoxRequest;

import java.util.List;

/**
 * Facade for Proxmox access/authentication management endpoints.
 */
public record PveAccess(ProxmoxHttpClient httpClient) {

    /**
     * Gets the access index (list of available endpoints).
     *
     * @return a request returning the list of access endpoints
     */
    public ProxmoxRequest<List<PveAccessIndex>> getIndex() {
        return new ProxmoxRequest<>(() -> 
            httpClient.get("access/").executeList(new TypeToken<List<PveAccessIndex>>(){})
        );
    }

    /**
     * Gets an authentication ticket using username/password.
     *
     * @param username the username
     * @param password the password
     * @param realm    the authentication realm (e.g., "pam", "pve")
     * @return a request returning the authentication ticket
     */
    public ProxmoxRequest<PveAccessTicket> getTicket(String username, String password, String realm) {
        return new ProxmoxRequest<>(() -> {
            var builder = httpClient.post("access/ticket")
                .param("username", username)
                .param("password", password);
            if (realm != null && !realm.isEmpty()) {
                builder.param("realm", realm);
            }
            return builder.execute(PveAccessTicket.class);
        });
    }

    /**
     * Gets an authentication ticket using the default PAM realm.
     *
     * @param username the username
     * @param password the password
     * @return a request returning the authentication ticket
     */
    public ProxmoxRequest<PveAccessTicket> getTicket(String username, String password) {
        return getTicket(username, password, "pam");
    }

    /**
     * Gets the users management interface.
     *
     * @return the users API facade
     */
    public PveAccessUsers getUsers() {
        return new PveAccessUsers(httpClient);
    }

    /**
     * Gets the groups management interface.
     *
     * @return the groups API facade
     */
    public PveAccessGroups getGroups() {
        return new PveAccessGroups(httpClient);
    }

    /**
     * Gets the roles management interface.
     *
     * @return the roles API facade
     */
    public PveAccessRoles getRoles() {
        return new PveAccessRoles(httpClient);
    }

    /**
     * Gets the Access Control List.
     *
     * @return a request returning the list of ACL entries
     */
    public ProxmoxRequest<List<PveAccessAcl>> getAcl() {
        return new ProxmoxRequest<>(() -> 
            httpClient.get("access/acl").executeList(new TypeToken<List<PveAccessAcl>>(){})
        );
    }

    /**
     * Gets available authentication domains/realms.
     *
     * @return a request returning the list of domains
     */
    public ProxmoxRequest<List<PveAccessDomain>> getDomains() {
        return new ProxmoxRequest<>(() -> 
            httpClient.get("access/domains").executeList(new TypeToken<List<PveAccessDomain>>(){})
        );
    }
}

