package fr.freshperf.pve4j.entities.nodes.node.lxc;

/**
 * Represents container summary information in the LXC list.
 */
public class PveLxcIndex {

    private int vmid;
    // A number, not an integer: falls back to the container's cpulimit, which can be fractional.
    private double cpus;
    private long maxdisk, maxmem, mem, disk, netin, netout, diskread, diskwrite, uptime, maxswap, swap;
    private float cpu;
    private String name, status, tags, lock, type;

    public int getVmid() {
        return vmid;
    }

    /**
     * Gets the maximum usable CPUs.
     *
     * @return the maximum usable CPUs: the container's {@code cores}, or its (possibly
     *         fractional) {@code cpulimit} when no cores are set, or the host CPU count
     */
    public double getCpus() {
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
        return "PveLxcIndex{" +
                "vmid=" + vmid +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", cpus=" + cpus +
                ", cpu=" + cpu +
                ", maxmem=" + maxmem +
                ", mem=" + mem +
                ", maxdisk=" + maxdisk +
                ", disk=" + disk +
                ", uptime=" + uptime +
                '}';
    }
}

