package fr.freshperf.pve4j.entities.cluster;

/**
 * Represents cluster or node status information.
 */
public class PveClusterStatus {

    private String id, name, ip, level;
    private boolean local,online,quorate;
    private int nodeid,nodes,version;
    private Type type;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public String getLevel() {
        return level;
    }

    public boolean isLocal() {
        return local;
    }

    public boolean isOnline() {
        return online;
    }

    public boolean isQuorate() {
        return quorate;
    }

    public int getNodeid() {
        return nodeid;
    }

    public int getNodes() {
        return nodes;
    }

    public int getVersion() {
        return version;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        cluster,
        node;
    }

    @Override
    public String toString() {
        return "PveClusterStatus{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", ip='" + ip + '\'' +
                ", level='" + level + '\'' +
                ", local=" + local +
                ", online=" + online +
                ", quorate=" + quorate +
                ", nodeid=" + nodeid +
                ", nodes=" + nodes +
                ", version=" + version +
                ", type=" + type +
                '}';
    }
}
