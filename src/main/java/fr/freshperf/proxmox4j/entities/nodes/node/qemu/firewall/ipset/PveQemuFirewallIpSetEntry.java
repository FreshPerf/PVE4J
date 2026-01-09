package fr.freshperf.proxmox4j.entities.nodes.node.qemu.firewall.ipset;

/**
 * Represents a firewall IP set entry.
 */
public class PveQemuFirewallIpSetEntry {

    private String digest;
    private String name;
    private String comment;

    public String getDigest() {
        return digest;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return "PveQemuFirewallIpSetEntry{" +
                "digest='" + digest + '\'' +
                ", name='" + name + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
