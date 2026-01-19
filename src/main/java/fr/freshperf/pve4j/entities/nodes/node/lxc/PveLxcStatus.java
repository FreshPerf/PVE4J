package fr.freshperf.pve4j.entities.nodes.node.lxc;

/**
 * Represents the current status of an LXC container.
 */
public class PveLxcStatus {

    private int vmid, cpus;
    private long maxdisk, maxmem, mem, disk, netin, netout, diskread, diskwrite, uptime, maxswap, swap;
    private float cpu;
    private String name, status, ha, tags, lock, type;

    public int getVmid() {
        return vmid;
    }

    public int getCpus() {
        return cpus;
    }

    public long getMaxdisk() {
        return maxdisk;
    }

    public long getMaxmem() {
        return maxmem;
    }

    public long getMem() {
        return mem;
    }

    public long getDisk() {
        return disk;
    }

    public long getNetin() {
        return netin;
    }

    public long getNetout() {
        return netout;
    }

    public long getDiskread() {
        return diskread;
    }

    public long getDiskwrite() {
        return diskwrite;
    }

    public long getUptime() {
        return uptime;
    }

    public long getMaxswap() {
        return maxswap;
    }

    public long getSwap() {
        return swap;
    }

    public float getCpu() {
        return cpu;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getHa() {
        return ha;
    }

    public String getTags() {
        return tags;
    }

    public String getLock() {
        return lock;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "PveLxcStatus{" +
                "vmid=" + vmid +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", cpus=" + cpus +
                ", cpu=" + cpu +
                ", maxmem=" + maxmem +
                ", mem=" + mem +
                ", uptime=" + uptime +
                '}';
    }
}

