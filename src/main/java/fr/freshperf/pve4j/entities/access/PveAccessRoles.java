package fr.freshperf.pve4j.entities.access;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import fr.freshperf.pve4j.request.ProxmoxHttpClient;
import fr.freshperf.pve4j.request.ProxmoxRequest;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Facade for Proxmox role management endpoints.
 */
public record PveAccessRoles(ProxmoxHttpClient httpClient) {

    /**
     * Lists all roles.
     *
     * @return a request returning the list of roles
     */
    public ProxmoxRequest<List<PveAccessRole>> getIndex() {
        return new ProxmoxRequest<>(() -> 
            httpClient.get("access/roles").executeList(new TypeToken<List<PveAccessRole>>(){})
        );
    }

    /**
     * Gets a specific role by ID.
     *
     * @param roleid the role ID
     * @return a request returning the role details
     * @throws IllegalArgumentException if roleid is null or empty
     */
    public ProxmoxRequest<PveAccessRole> get(String roleid) {
        if (roleid == null || roleid.isEmpty()) {
            throw new IllegalArgumentException("Role ID cannot be null or empty");
        }
        return new ProxmoxRequest<>(() -> {
            // The API returns the privileges as a map of privilege name -> 1
            JsonObject privs = httpClient.get("access/roles/" + roleid).execute(JsonObject.class);
            String privList = privs.keySet().stream().sorted().collect(Collectors.joining(","));
            return new PveAccessRole(roleid, privList);
        });
    }
}

