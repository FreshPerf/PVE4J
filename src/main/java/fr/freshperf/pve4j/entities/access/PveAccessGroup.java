package fr.freshperf.pve4j.entities.access;

/**
 * Represents a Proxmox user group.
 */
public class PveAccessGroup {

    private String groupid, comment;
    // GET /access/groups returns the members as a comma-separated string,
    // GET /access/groups/{groupid} returns them as an array named "members"
    private String users;
    private String[] members;

    public String getGroupid() {
        return groupid;
    }

    public String getComment() {
        return comment;
    }

    /**
     * Gets the IDs of the users belonging to this group.
     *
     * @return the user IDs (e.g. "john@pve"), or null if not reported
     */
    public String[] getUsers() {
        if (members != null) {
            return members;
        }
        if (users == null) {
            return null;
        }
        return users.isEmpty() ? new String[0] : users.split(",");
    }

    void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    @Override
    public String toString() {
        String[] userList = getUsers();
        return "PveAccessGroup{" +
                "groupid='" + groupid + '\'' +
                ", comment='" + comment + '\'' +
                ", users=" + (userList != null ? userList.length : 0) +
                '}';
    }
}
