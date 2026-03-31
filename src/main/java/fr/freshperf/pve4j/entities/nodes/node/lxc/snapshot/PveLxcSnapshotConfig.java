package fr.freshperf.pve4j.entities.nodes.node.lxc.snapshot;

/**
 * Represents the configuration stored with an LXC snapshot.
 */
public class PveLxcSnapshotConfig {

    private String arch;
    private String description;
    private String features;
    private String hostname;
    private Integer memory;
    private String nameserver;
    private String ostype;
    private String rootfs;
    private String searchdomain;
    private Integer swap;

    public String getArch() {
        return arch;
    }

    public String getDescription() {
        return description;
    }

    public String getFeatures() {
        return features;
    }

    public String getHostname() {
        return hostname;
    }

    public Integer getMemory() {
        return memory;
    }

    public String getNameserver() {
        return nameserver;
    }

    public String getOstype() {
        return ostype;
    }

    public String getRootfs() {
        return rootfs;
    }

    public String getSearchdomain() {
        return searchdomain;
    }

    public Integer getSwap() {
        return swap;
    }

    @Override
    public String toString() {
        return "PveLxcSnapshotConfig{" +
                "arch='" + arch + '\'' +
                ", description='" + description + '\'' +
                ", hostname='" + hostname + '\'' +
                ", memory=" + memory +
                ", ostype='" + ostype + '\'' +
                ", rootfs='" + rootfs + '\'' +
                '}';
    }
}
