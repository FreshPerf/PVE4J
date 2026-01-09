package fr.freshperf.proxmox4j.entities.nodes.node.qemu.firewall.ipset;

/**
 * Represents a member of a firewall IP set.
 */
public class PveQemuFirewallIpSetMember {

    private String cidr;
    private String digest;
    private String comment;
    private int nomatch;

    public String getCidr() {
        return cidr;
    }

    public String getDigest() {
        return digest;
    }

    public String getComment() {
        return comment;
    }

    public int getNomatch() {
        return nomatch;
    }

    @Override
    public String toString() {
        return "PveQemuFirewallIpSetMember{" +
                "cidr='" + cidr + '\'' +
                ", digest='" + digest + '\'' +
                ", comment='" + comment + '\'' +
                ", nomatch=" + nomatch +
                '}';
    }
}
