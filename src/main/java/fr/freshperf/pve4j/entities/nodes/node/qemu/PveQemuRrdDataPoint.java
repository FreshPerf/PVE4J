package fr.freshperf.pve4j.entities.nodes.node.qemu;

/**
 * Represents a QEMU VM RRD data point.
 */
public class PveQemuRrdDataPoint {

    private Double cpu;
    private Long disk;
    private Long diskread;
    private Long diskwrite;
    private Long maxdisk;
    private Integer maxcpu;
    private Long maxmem;
    private Long mem;
    private Long netin;
    private Long netout;
    private Long time;
    private Long uptime;

    public Double getCpu() {
        return cpu;
    }

    public Long getDisk() {
        return disk;
    }

    public Long getDiskread() {
        return diskread;
    }

    public Long getDiskwrite() {
        return diskwrite;
    }

    public Long getMaxdisk() {
        return maxdisk;
    }

    public Integer getMaxcpu() {
        return maxcpu;
    }

    public Long getMaxmem() {
        return maxmem;
    }

    public Long getMem() {
        return mem;
    }

    public Long getNetin() {
        return netin;
    }

    public Long getNetout() {
        return netout;
    }

    public Long getTime() {
        return time;
    }

    public Long getUptime() {
        return uptime;
    }

    @Override
    public String toString() {
        return "PveQemuRrdDataPoint{" +
                "time=" + time +
                ", cpu=" + cpu +
                ", mem=" + mem +
                ", maxmem=" + maxmem +
                ", disk=" + disk +
                ", maxdisk=" + maxdisk +
                ", netin=" + netin +
                ", netout=" + netout +
                '}';
    }
}
