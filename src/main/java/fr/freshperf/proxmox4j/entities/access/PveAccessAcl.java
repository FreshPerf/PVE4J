package fr.freshperf.proxmox4j.entities.access;

/**
 * Represents a Proxmox Access Control List entry.
 */
public class PveAccessAcl {

    private String path, type, ugid, roleid;
    private int propagate;

    public String getPath() {
        return path;
    }

    public String getType() {
        return type;
    }

    public String getUgid() {
        return ugid;
    }

    public String getRoleid() {
        return roleid;
    }

    public int getPropagate() {
        return propagate;
    }

    @Override
    public String toString() {
        return "PveAccessAcl{" +
                "path='" + path + '\'' +
                ", type='" + type + '\'' +
                ", ugid='" + ugid + '\'' +
                ", roleid='" + roleid + '\'' +
                ", propagate=" + propagate +
                '}';
    }
}

