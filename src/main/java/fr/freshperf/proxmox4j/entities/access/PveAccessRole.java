package fr.freshperf.proxmox4j.entities.access;

import java.util.Map;

/**
 * Represents a Proxmox role with its privileges.
 */
public class PveAccessRole {

    private String roleid;
    private boolean special;
    private Map<String, Integer> privs;

    public String getRoleid() {
        return roleid;
    }

    public boolean isSpecial() {
        return special;
    }

    public Map<String, Integer> getPrivs() {
        return privs;
    }

    @Override
    public String toString() {
        return "PveAccessRole{" +
                "roleid='" + roleid + '\'' +
                ", special=" + special +
                ", privs=" + (privs != null ? privs.size() : 0) +
                '}';
    }
}

