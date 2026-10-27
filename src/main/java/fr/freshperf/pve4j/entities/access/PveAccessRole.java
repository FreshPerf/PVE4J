package fr.freshperf.pve4j.entities.access;

import java.util.List;

/**
 * Represents a Proxmox role with its privileges.
 */
public class PveAccessRole {

    private String roleid;
    private boolean special;
    // Comma-separated privilege list, as returned by GET /access/roles
    private String privs;

    public PveAccessRole() {
    }

    PveAccessRole(String roleid, String privs) {
        this.roleid = roleid;
        this.privs = privs;
    }

    public String getRoleid() {
        return roleid;
    }

    /**
     * Whether this is a built-in role. Only reported by the roles index
     * ({@link PveAccessRoles#getIndex()}); always {@code false} for {@link PveAccessRoles#get(String)}.
     *
     * @return true for built-in roles
     */
    public boolean isSpecial() {
        return special;
    }

    /**
     * Gets the privileges granted by this role (e.g. "VM.Audit", "Datastore.Allocate").
     *
     * @return the privilege names, empty if the role grants none, or null if not reported
     */
    public List<String> getPrivs() {
        if (privs == null) {
            return null;
        }
        if (privs.isEmpty()) {
            return List.of();
        }
        return List.of(privs.split(","));
    }

    @Override
    public String toString() {
        List<String> privList = getPrivs();
        return "PveAccessRole{" +
                "roleid='" + roleid + '\'' +
                ", special=" + special +
                ", privs=" + (privList != null ? privList.size() : 0) +
                '}';
    }
}
