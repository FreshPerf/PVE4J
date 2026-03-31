package fr.freshperf.pve4j.entities.nodes.node.lxc;

/**
 * Represents the configuration of an LXC container.
 */
public class PveLxcConfig {

    private String hostname, ostype, arch, cmode;
    private Boolean console;
    private Integer tty;
    private Integer cores, memory, swap;
    private Boolean onboot, protection, unprivileged, template;
    private String description, tags, digest;
    
    // Network interfaces
    private String net0, net1, net2, net3, net4, net5;
    
    // Mount points (disks/storage)
    private String rootfs, mp0, mp1, mp2, mp3, mp4, mp5;
    
    // Features
    private String features, startup;
    
    // Nameserver and search domain
    private String nameserver, searchdomain;

    public String getHostname() {
        return hostname;
    }

    public String getOstype() {
        return ostype;
    }

    public String getArch() {
        return arch;
    }

    public String getCmode() {
        return cmode;
    }

    public Boolean getConsole() {
        return console;
    }

    public boolean hasConsole() {
        return Boolean.TRUE.equals(console);
    }

    public Integer getTty() {
        return tty;
    }

    public Integer getCores() {
        return cores;
    }

    public Integer getMemory() {
        return memory;
    }

    public Integer getSwap() {
        return swap;
    }

    public Boolean getOnboot() {
        return onboot;
    }

    public boolean isOnboot() {
        return Boolean.TRUE.equals(onboot);
    }

    public Boolean getProtection() {
        return protection;
    }

    public boolean isProtected() {
        return Boolean.TRUE.equals(protection);
    }

    public Boolean getUnprivileged() {
        return unprivileged;
    }

    public boolean isUnprivileged() {
        return Boolean.TRUE.equals(unprivileged);
    }

    public Boolean getTemplate() {
        return template;
    }

    public boolean isTemplate() {
        return Boolean.TRUE.equals(template);
    }

    public String getDescription() {
        return description;
    }

    public String getTags() {
        return tags;
    }

    public String getDigest() {
        return digest;
    }

    public String getNet0() {
        return net0;
    }

    public String getNet1() {
        return net1;
    }

    public String getNet2() {
        return net2;
    }

    public String getNet3() {
        return net3;
    }

    public String getNet4() {
        return net4;
    }

    public String getNet5() {
        return net5;
    }

    public String getRootfs() {
        return rootfs;
    }

    public String getMp0() {
        return mp0;
    }

    public String getMp1() {
        return mp1;
    }

    public String getMp2() {
        return mp2;
    }

    public String getMp3() {
        return mp3;
    }

    public String getMp4() {
        return mp4;
    }

    public String getMp5() {
        return mp5;
    }

    public String getFeatures() {
        return features;
    }

    public String getStartup() {
        return startup;
    }

    public String getNameserver() {
        return nameserver;
    }

    public String getSearchdomain() {
        return searchdomain;
    }

    @Override
    public String toString() {
        return "PveLxcConfig{" +
                "hostname='" + hostname + '\'' +
                ", ostype='" + ostype + '\'' +
                ", cores=" + cores +
                ", memory=" + memory +
                ", arch='" + arch + '\'' +
                ", unprivileged=" + unprivileged +
                ", template=" + template +
                ", rootfs='" + rootfs + '\'' +
                '}';
    }
}

