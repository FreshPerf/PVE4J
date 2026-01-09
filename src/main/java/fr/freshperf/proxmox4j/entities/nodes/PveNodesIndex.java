package fr.freshperf.proxmox4j.entities.nodes;

/**
 * Represents node summary information in the nodes list.
 */
public class PveNodesIndex {

    private String node, level, ssl_fingerprint;
    private Status status;
    private float cpu;
    private long maxcpu,maxmem,mem,uptime;

    public String getNode() {
        return node;
    }

    public String getLevel() {
        return level;
    }

    public String getSsl_fingerprint() {
        return ssl_fingerprint;
    }

    public Status getStatus() {
        return status;
    }

    public float getCpu() {
        return cpu;
    }

    public long getMaxcpu() {
        return maxcpu;
    }

    public long getMaxmem() {
        return maxmem;
    }

    public long getMem() {
        return mem;
    }

    public long getUptime() {
        return uptime;
    }

    public enum Status {
        unknown,
        online,
        offline;
    }

    @Override
    public String toString() {
        return "PveNodesIndex{" +
                "node='" + node + '\'' +
                ", level='" + level + '\'' +
                ", ssl_fingerprint='" + ssl_fingerprint + '\'' +
                ", status=" + status +
                ", cpu=" + cpu +
                ", maxcpu=" + maxcpu +
                ", maxmem=" + maxmem +
                ", mem=" + mem +
                ", uptime=" + uptime +
                '}';
    }
}
