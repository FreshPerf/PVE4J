package fr.freshperf.pve4j.entities.access;

import com.google.gson.reflect.TypeToken;
import fr.freshperf.pve4j.request.ProxmoxHttpClient;
import fr.freshperf.pve4j.request.ProxmoxRequest;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
        // User names may contain characters such as '#' or '?' that must be escaped in a path
        String encodedUserid = URLEncoder.encode(userid, StandardCharsets.UTF_8).replace("+", "%20");
        return new ProxmoxRequest<>(() -> {
            PveAccessUser user = httpClient.get("access/users/" + encodedUserid).execute(PveAccessUser.class);
            // The user ID is not part of the response
            user.setUserid(userid);
            return user;
        });
    }
}

