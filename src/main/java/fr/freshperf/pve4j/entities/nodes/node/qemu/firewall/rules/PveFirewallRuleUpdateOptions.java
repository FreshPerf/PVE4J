package fr.freshperf.pve4j.entities.nodes.node.qemu.firewall.rules;

import fr.freshperf.pve4j.entities.options.ParamsConvertible;
import fr.freshperf.pve4j.util.ParamsHelpers;

import java.util.Map;

/**
 * Options for updating an existing firewall rule (PUT on a rule position).
 * All fields are optional; only the configured ones are sent.
 *
 * <p>Unlike {@link PveFirewallRuleCreateOptions}, this supports the update-only
 * parameters {@code moveto} (relocate the rule) and {@code delete} (clear settings).</p>
 */
public class PveFirewallRuleUpdateOptions implements ParamsConvertible {

    private String type;
    private String action;
    private Boolean enable;
    private String source;
    private String dest;
    private String proto;
    private String sport;
    private String dport;
    private String comment;
    private String macro;
    private String iface;
    private String log;
    private String icmpType;
    private Integer moveto;
    private String delete;
    private String digest;

    /**
     * Creates a new builder for firewall rule update options.
     *
     * @return a new PveFirewallRuleUpdateOptions instance
     */
    public static PveFirewallRuleUpdateOptions builder() {
        return new PveFirewallRuleUpdateOptions();
    }

    @Override
    public void populateParams(Map<String, Object> params) {
        ParamsHelpers.put(params, "type", type);
        ParamsHelpers.put(params, "action", action);
        ParamsHelpers.putBool(params, "enable", enable);
        ParamsHelpers.put(params, "source", source);
        ParamsHelpers.put(params, "dest", dest);
        ParamsHelpers.put(params, "proto", proto);
        ParamsHelpers.put(params, "sport", sport);
        ParamsHelpers.put(params, "dport", dport);
        ParamsHelpers.put(params, "comment", comment);
        ParamsHelpers.put(params, "macro", macro);
        ParamsHelpers.put(params, "iface", iface);
        ParamsHelpers.put(params, "log", log);
        ParamsHelpers.put(params, "icmp-type", icmpType);
        ParamsHelpers.putInt(params, "moveto", moveto);
        ParamsHelpers.put(params, "delete", delete);
        ParamsHelpers.put(params, "digest", digest);
    }

    /**
     * Sets the rule type.
     *
     * @param type "in", "out", "forward", or "group"
     * @return this instance for method chaining
     */
    public PveFirewallRuleUpdateOptions type(String type) { this.type = type; return this; }

    /**
     * Sets the rule action.
     *
     * @param action "ACCEPT", "DROP", "REJECT", or a security group name
     * @return this instance for method chaining
     */
    public PveFirewallRuleUpdateOptions action(String action) { this.action = action; return this; }

    /**
     * Sets whether the rule is enabled.
     *
     * @param enable true to enable
     * @return this instance for method chaining
     */
    public PveFirewallRuleUpdateOptions enable(Boolean enable) { this.enable = enable; return this; }

    /**
     * Sets the source address/network.
     *
     * @param source IP, CIDR, IP set ("+name"), or alias
     * @return this instance for method chaining
     */
    public PveFirewallRuleUpdateOptions source(String source) { this.source = source; return this; }

    /**
     * Sets the destination address/network.
     *
     * @param dest IP, CIDR, IP set ("+name"), or alias
     * @return this instance for method chaining
     */
    public PveFirewallRuleUpdateOptions dest(String dest) { this.dest = dest; return this; }

    /**
     * Sets the protocol.
     *
     * @param proto "tcp", "udp", "icmp", etc.
     * @return this instance for method chaining
     */
    public PveFirewallRuleUpdateOptions proto(String proto) { this.proto = proto; return this; }

    /**
     * Sets the source port(s).
     *
     * @param sport port number or range
     * @return this instance for method chaining
     */
    public PveFirewallRuleUpdateOptions sport(String sport) { this.sport = sport; return this; }

    /**
     * Sets the destination port(s).
     *
     * @param dport port number or range
     * @return this instance for method chaining
     */
    public PveFirewallRuleUpdateOptions dport(String dport) { this.dport = dport; return this; }

    /**
     * Sets the rule comment.
     *
     * @param comment description of the rule
     * @return this instance for method chaining
     */
    public PveFirewallRuleUpdateOptions comment(String comment) { this.comment = comment; return this; }

    /**
     * Sets a predefined macro.
     *
     * @param macro macro name (e.g., "SSH", "HTTP", "Ping")
     * @return this instance for method chaining
     */
    public PveFirewallRuleUpdateOptions macro(String macro) { this.macro = macro; return this; }

    /**
     * Sets the network interface.
     *
     * @param iface interface name (e.g., "net0")
     * @return this instance for method chaining
     */
    public PveFirewallRuleUpdateOptions iface(String iface) { this.iface = iface; return this; }

    /**
     * Sets the log level.
     *
     * @param log "nolog", "debug", "info", "notice", "warning", "err", "crit", "alert", "emerg"
     * @return this instance for method chaining
     */
    public PveFirewallRuleUpdateOptions log(String log) { this.log = log; return this; }

    /**
     * Sets the ICMP type. Only valid if proto equals 'icmp' or 'icmpv6'/'ipv6-icmp'.
     *
     * @param icmpType the ICMP type
     * @return this instance for method chaining
     */
    public PveFirewallRuleUpdateOptions icmpType(String icmpType) { this.icmpType = icmpType; return this; }

    /**
     * Moves the rule to a new position. Other arguments are ignored by the API when set.
     *
     * @param moveto the new position (>= 0)
     * @return this instance for method chaining
     */
    public PveFirewallRuleUpdateOptions moveto(Integer moveto) { this.moveto = moveto; return this; }

    /**
     * Sets a comma-separated list of settings to delete from the rule.
     *
     * @param delete the settings to delete
     * @return this instance for method chaining
     */
    public PveFirewallRuleUpdateOptions delete(String delete) { this.delete = delete; return this; }

    /**
     * Sets the digest to prevent concurrent modifications.
     *
     * @param digest the configuration digest
     * @return this instance for method chaining
     */
    public PveFirewallRuleUpdateOptions digest(String digest) { this.digest = digest; return this; }

    /**
     * Builds and returns the options instance.
     *
     * @return this instance
     */
    public PveFirewallRuleUpdateOptions build() {
        return this;
    }
}
