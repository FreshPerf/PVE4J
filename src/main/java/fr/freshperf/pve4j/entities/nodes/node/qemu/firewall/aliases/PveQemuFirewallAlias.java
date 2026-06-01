package fr.freshperf.pve4j.entities.nodes.node.qemu.firewall.aliases;

/**
 * Represents a firewall IP/Network alias of a QEMU VM.
 */
public class PveQemuFirewallAlias {

    private String name;
    private String cidr;
    private String comment;
    private String digest;
    private Integer ipversion;

    /**
     * Gets the alias name.
     *
     * @return the alias name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the network/IP specification in CIDR format.
     *
     * @return the CIDR
     */
    public String getCidr() {
        return cidr;
    }

    /**
     * Gets the descriptive comment.
     *
     * @return the comment, or null
     */
    public String getComment() {
        return comment;
    }

    /**
     * Gets the configuration digest used for conflict detection.
     *
     * @return the digest
     */
    public String getDigest() {
        return digest;
    }

    /**
     * Gets the IP version (4 or 6) when reported by the API.
     *
     * @return the IP version, or null
     */
    public Integer getIpversion() {
        return ipversion;
    }

    @Override
    public String toString() {
        return "PveQemuFirewallAlias{" +
                "name='" + name + '\'' +
                ", cidr='" + cidr + '\'' +
                ", comment='" + comment + '\'' +
                ", digest='" + digest + '\'' +
                ", ipversion=" + ipversion +
                '}';
    }
}
