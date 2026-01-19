package fr.freshperf.pve4j.entities.access;

/**
 * Represents a Proxmox user group.
 */
public class PveAccessGroup {

    private String groupid, comment;
    private String[] users;

    public String getGroupid() {
        return groupid;
    }

    public String getComment() {
        return comment;
    }

    public String[] getUsers() {
        return users;
    }

    @Override
    public String toString() {
        return "PveAccessGroup{" +
                "groupid='" + groupid + '\'' +
                ", comment='" + comment + '\'' +
                ", users=" + (users != null ? users.length : 0) +
                '}';
    }
}

