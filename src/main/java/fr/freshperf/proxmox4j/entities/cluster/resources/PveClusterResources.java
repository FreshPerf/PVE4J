package fr.freshperf.proxmox4j.entities.cluster.resources;

/**
 * Represents a cluster resource (VM, container, node, or storage).
 */
public class PveClusterResources {

    private String type, id, node, status;
    private int vmid, maxcpu;
    private long maxmem, mem, maxdisk, disk, uptime;
    private float cpu;
    private String name, pool, hastate, tags, lock;
    private String storage, content, plugintype;
    private boolean shared;
    private long total, used, avail;
    private boolean active;
    private String level, ssl_fingerprint;

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getNode() {
        return node;
    }

    public String getStatus() {
        return status;
    }

    public int getVmid() {
        return vmid;
    }

    public int getMaxcpu() {
        return maxcpu;
    }

    public long getMaxmem() {
        return maxmem;
    }

    public long getMem() {
        return mem;
    }

    public long getMaxdisk() {
        return maxdisk;
    }

    public long getDisk() {
        return disk;
    }

    public long getUptime() {
        return uptime;
    }

    public float getCpu() {
        return cpu;
    }

    public String getName() {
        return name;
    }

    public String getPool() {
        return pool;
    }

    public String getHastate() {
        return hastate;
    }

    public String getTags() {
        return tags;
    }

    public String getLock() {
        return lock;
    }

    public String getStorage() {
        return storage;
    }

    public String getContent() {
        return content;
    }

    public String getPlugintype() {
        return plugintype;
    }

    public boolean isShared() {
        return shared;
    }

    public long getTotal() {
        return total;
    }

    public long getUsed() {
        return used;
    }

    public long getAvail() {
        return avail;
    }

    public boolean isActive() {
        return active;
    }

    public String getLevel() {
        return level;
    }

    public String getSsl_fingerprint() {
        return ssl_fingerprint;
    }

    @Override
    public String toString() {
        return "PveClusterResources{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", node='" + node + '\'' +
                ", status='" + status + '\'' +
                ", vmid=" + vmid +
                ", name='" + name + '\'' +
                ", cpu=" + cpu +
                ", maxcpu=" + maxcpu +
                ", mem=" + mem +
                ", maxmem=" + maxmem +
                '}';
    }
}

