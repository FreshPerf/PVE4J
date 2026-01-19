package fr.freshperf.pve4j.entities.access;

import com.google.gson.reflect.TypeToken;
import fr.freshperf.pve4j.request.ProxmoxHttpClient;
import fr.freshperf.pve4j.request.ProxmoxRequest;

import java.util.List;

/**
 * Facade for Proxmox user management endpoints.
 */
public record PveAccessUsers(ProxmoxHttpClient httpClient) {

    /**
     * Lists all users.
     *
     * @return a request returning the list of users
     */
    public ProxmoxRequest<List<PveAccessUser>> getIndex() {
        return new ProxmoxRequest<>(() -> 
            httpClient.get("access/users").executeList(new TypeToken<List<PveAccessUser>>(){})
        );
    }

    /**
     * Gets a specific user by ID.
     *
     * @param userid the user ID (e.g., "root@pam")
     * @return a request returning the user details
     * @throws IllegalArgumentException if userid is null or empty
     */
    public ProxmoxRequest<PveAccessUser> get(String userid) {
        if (userid == null || userid.isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        return new ProxmoxRequest<>(() -> 
            httpClient.get("access/users/" + userid).execute(PveAccessUser.class)
        );
    }
}

