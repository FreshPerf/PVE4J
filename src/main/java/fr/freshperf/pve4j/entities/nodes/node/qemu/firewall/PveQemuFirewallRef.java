package fr.freshperf.pve4j.entities.nodes.node.qemu.firewall;

/**
 * Represents an IPSet/Alias reference that can be used in firewall rule
 * source/dest properties (returned by {@code GET /firewall/refs}).
 */
public class PveQemuFirewallRef {

    private String type;
    private String name;
    private String ref;
    private String scope;
    private String comment;

    /**
     * Gets the reference type ("alias" or "ipset").
     *
     * @return the reference type
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the reference name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the fully qualified reference string usable in source/dest.
     *
     * @return the reference
     */
    public String getRef() {
        return ref;
    }

    /**
     * Gets the scope of the reference.
     *
     * @return the scope
     */
    public String getScope() {
        return scope;
    }

    /**
     * Gets the descriptive comment.
     *
     * @return the comment, or null
     */
    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return "PveQemuFirewallRef{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", ref='" + ref + '\'' +
                ", scope='" + scope + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
