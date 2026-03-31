package fr.freshperf.pve4j.entities.nodes.node.lxc;

/**
 * Represents the current status of an LXC container.
 */
public class PveLxcStatus {

    private Integer vmid;
    private Integer cpus;
    private Long maxdisk;
    private Long maxmem;
    private Long mem;
    private Long disk;
    private Long netin;
    private Long netout;
    private Long diskread;
    private Long diskwrite;
    private Long uptime;
    private Long maxswap;
    private Long swap;
    private Double cpu;
    private String name;
    private String status;
    private PveLxcHa ha;
    private String tags;
    private String lock;
    private String type;
    private Boolean template;

    public Integer getVmid() {
        return vmid;
    }

    public Integer getCpus() {
        return cpus;
    }

    public Long getMaxdisk() {
        return maxdisk;
    }

    public Long getMaxmem() {
        return maxmem;
    }

    public Long getMem() {
        return mem;
    }

    public Long getDisk() {
        return disk;
    }

    public Long getNetin() {
        return netin;
    }

    public Long getNetout() {
        return netout;
    }

    public Long getDiskread() {
        return diskread;
    }

    public Long getDiskwrite() {
        return diskwrite;
    }

    public Long getUptime() {
        return uptime;
    }

    public Long getMaxswap() {
        return maxswap;
    }

    public Long getSwap() {
        return swap;
    }

    public Double getCpu() {
        return cpu;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public PveLxcHa getHa() {
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

    public Boolean getTemplate() {
        return template;
    }

    public boolean isTemplate() {
        return Boolean.TRUE.equals(template);
    }

    @Override
    public String toString() {
        return "PveLxcStatus{" +
                "vmid=" + vmid +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", template=" + template +
                ", cpus=" + cpus +
                ", cpu=" + cpu +
                ", maxmem=" + maxmem +
                ", mem=" + mem +
                ", uptime=" + uptime +
                '}';
    }
}

