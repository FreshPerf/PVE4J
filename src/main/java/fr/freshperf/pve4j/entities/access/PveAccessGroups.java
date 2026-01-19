package fr.freshperf.pve4j.entities.access;

import com.google.gson.reflect.TypeToken;
import fr.freshperf.pve4j.request.ProxmoxHttpClient;
import fr.freshperf.pve4j.request.ProxmoxRequest;

import java.util.List;

/**
 * Facade for Proxmox group management endpoints.
 */
public record PveAccessGroups(ProxmoxHttpClient httpClient) {

    /**
     * Lists all groups.
     *
     * @return a request returning the list of groups
     */
    public ProxmoxRequest<List<PveAccessGroup>> getIndex() {
        return new ProxmoxRequest<>(() -> 
            httpClient.get("access/groups").executeList(new TypeToken<List<PveAccessGroup>>(){})
        );
    }

    /**
     * Gets a specific group by ID.
     *
     * @param groupid the group ID
     * @return a request returning the group details
     * @throws IllegalArgumentException if groupid is null or empty
     */
    public ProxmoxRequest<PveAccessGroup> get(String groupid) {
        if (groupid == null || groupid.isEmpty()) {
            throw new IllegalArgumentException("Group ID cannot be null or empty");
        }
        return new ProxmoxRequest<>(() -> 
            httpClient.get("access/groups/" + groupid).execute(PveAccessGroup.class)
        );
    }
}

