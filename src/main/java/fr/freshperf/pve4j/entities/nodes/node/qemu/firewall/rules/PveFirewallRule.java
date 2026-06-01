package fr.freshperf.pve4j.entities.nodes.node.qemu.firewall.rules;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a firewall rule.
 */
public class PveFirewallRule {

    @SerializedName("pos")
    private Integer pos;

    @SerializedName("type")
    private String type;

    @SerializedName("action")
    private String action;

    @SerializedName("enable")
    private Integer enable;

    @SerializedName("source")
    private String source;

    @SerializedName("dest")
    private String dest;

    @SerializedName("proto")
    private String proto;

    @SerializedName("sport")
    private String sport;

    @SerializedName("dport")
    private String dport;

    @SerializedName("comment")
    private String comment;

    @SerializedName("macro")
    private String macro;

    @SerializedName("iface")
    private String iface;

    @SerializedName("log")
    private String log;

    @SerializedName("icmp-type")
    private String icmpType;

    @SerializedName("ipversion")
    private Integer ipversion;

    /**
     * Gets the rule position.
     *
     * @return the position
     */
    public Integer getPos() {
        return pos;
    }

    /**
     * Gets the rule type ("in", "out", "forward", "group").
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the action ("ACCEPT", "DROP", "REJECT").
     *
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * Checks if the rule is enabled.
     *
     * @return 1 if enabled, 0 if disabled
     */
    public Integer getEnable() {
        return enable;
    }

    /**
     * Checks if the rule is enabled.
     *
     * @return true if enabled
     */
    public boolean isEnabled() {
        return enable != null && enable == 1;
    }

    /**
     * Gets the source address/network.
     *
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * Gets the destination address/network.
     *
     * @return the destination
     */
    public String getDest() {
        return dest;
    }

    /**
     * Gets the protocol (tcp, udp, icmp, etc.).
     *
     * @return the protocol
     */
    public String getProto() {
        return proto;
    }

    /**
     * Gets the source port.
     *
     * @return the source port
     */
    public String getSport() {
        return sport;
    }

    /**
     * Gets the destination port.
     *
     * @return the destination port
     */
    public String getDport() {
        return dport;
    }

    /**
     * Gets the rule comment.
     *
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Gets the macro name if using a predefined rule.
     *
     * @return the macro name
     */
    public String getMacro() {
        return macro;
    }

    /**
     * Gets the interface this rule applies to.
     *
     * @return the interface
     */
    public String getIface() {
        return iface;
    }

    /**
     * Gets the log level.
     *
     * @return the log level
     */
    public String getLog() {
        return log;
    }

    /**
     * Gets the ICMP type (valid only for icmp/icmpv6 protocols).
     *
     * @return the ICMP type, or null
     */
    public String getIcmpType() {
        return icmpType;
    }

    /**
     * Gets the IP version (4 or 6), automatically determined from source/dest.
     *
     * @return the IP version, or null
     */
    public Integer getIpversion() {
        return ipversion;
    }

    @Override
    public String toString() {
        return "PveFirewallRule{" +
                "pos=" + pos +
                ", type='" + type + '\'' +
                ", action='" + action + '\'' +
                ", enable=" + enable +
                ", proto='" + proto + '\'' +
                ", dport='" + dport + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}

