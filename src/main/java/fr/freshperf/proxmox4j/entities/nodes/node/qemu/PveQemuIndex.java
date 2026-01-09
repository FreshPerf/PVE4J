package fr.freshperf.proxmox4j.entities.nodes.node.qemu;

/**
 * Represents VM summary information in the QEMU list.
 */
public class PveQemuIndex {

    private int vmid, cpus;
    private long maxdisk, maxmem, mem, disk, netin, netout, diskread, diskwrite, uptime;
    private float cpu;
    private String name, status, tags, lock;
    private int pid;
    private String qmpstatus;

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

    public int getPid() {
        return pid;
    }

    public String getQmpstatus() {
        return qmpstatus;
    }

    @Override
    public String toString() {
        return "PveQemuIndex{" +
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

