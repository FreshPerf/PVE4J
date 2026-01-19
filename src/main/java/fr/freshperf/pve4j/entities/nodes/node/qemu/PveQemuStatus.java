package fr.freshperf.pve4j.entities.nodes.node.qemu;

/**
 * Represents the current status of a QEMU VM.
 */
public class PveQemuStatus {

    private int vmid, cpus, pid;
    private long maxdisk, maxmem, mem, disk, netin, netout, diskread, diskwrite, uptime;
    private float cpu;
    private String name, status, qmpstatus, tags, lock, agent;
    private PveQemuHa ha;
    private PveQemuBalloonInfo ballooninfo;
    private boolean running;

    public int getVmid() {
        return vmid;
    }

    public int getCpus() {
        return cpus;
    }

    public int getPid() {
        return pid;
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

    public PveQemuBalloonInfo getBallooninfo() {
        return ballooninfo;
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

    public String getQmpstatus() {
        return qmpstatus;
    }

    public PveQemuHa getHa() {
        return ha;
    }

    public String getTags() {
        return tags;
    }

    public String getLock() {
        return lock;
    }

    public String getAgent() {
        return agent;
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public String toString() {
        return "PveQemuStatus{" +
                "vmid=" + vmid +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", running=" + running +
                ", cpus=" + cpus +
                ", cpu=" + cpu +
                ", maxmem=" + maxmem +
                ", mem=" + mem +
                ", uptime=" + uptime +
                '}';
    }
}

