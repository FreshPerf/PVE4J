package fr.freshperf.proxmox4j.entities.nodes.node.lxc;

/**
 * Represents the configuration of an LXC container.
 */
public class PveLxcConfig {

    private String hostname, ostype, arch, cmode, console, tty;
    private int cores, memory, swap, onboot, protection, unprivileged, template;
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

    public String getConsole() {
        return console;
    }

    public String getTty() {
        return tty;
    }

    public int getCores() {
        return cores;
    }

    public int getMemory() {
        return memory;
    }

    public int getSwap() {
        return swap;
    }

    public int getOnboot() {
        return onboot;
    }

    public int getProtection() {
        return protection;
    }

    public int getUnprivileged() {
        return unprivileged;
    }

    public int getTemplate() {
        return template;
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
                ", rootfs='" + rootfs + '\'' +
                '}';
    }
}

