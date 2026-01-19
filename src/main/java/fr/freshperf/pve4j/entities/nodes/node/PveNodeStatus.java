package fr.freshperf.pve4j.entities.nodes.node;

/**
 * Represents detailed node status information.
 */
public class PveNodeStatus {

    private String uptime, wait, idle, pveversion, kversion;
    private float cpu, loadavg[];
    private long memory, rootfs, swap;
    private int cpuinfo;

    public String getUptime() {
        return uptime;
    }

    public String getWait() {
        return wait;
    }

    public String getIdle() {
        return idle;
    }

    public String getPveversion() {
        return pveversion;
    }

    public String getKversion() {
        return kversion;
    }

    public float getCpu() {
        return cpu;
    }

    public float[] getLoadavg() {
        return loadavg;
    }

    public long getMemory() {
        return memory;
    }

    public long getRootfs() {
        return rootfs;
    }

    public long getSwap() {
        return swap;
    }

    public int getCpuinfo() {
        return cpuinfo;
    }

    @Override
    public String toString() {
        return "PveNodeStatus{" +
                "uptime='" + uptime + '\'' +
                ", wait='" + wait + '\'' +
                ", idle='" + idle + '\'' +
                ", pveversion='" + pveversion + '\'' +
                ", kversion='" + kversion + '\'' +
                ", cpu=" + cpu +
                ", memory=" + memory +
                ", rootfs=" + rootfs +
                ", swap=" + swap +
                ", cpuinfo=" + cpuinfo +
                '}';
    }
}

