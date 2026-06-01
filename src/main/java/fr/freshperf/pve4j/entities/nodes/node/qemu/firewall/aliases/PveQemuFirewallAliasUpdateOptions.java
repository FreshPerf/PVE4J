package fr.freshperf.pve4j.entities.nodes.node.qemu.firewall.aliases;

import fr.freshperf.pve4j.entities.options.ParameterizedParamsConvertible;
import fr.freshperf.pve4j.util.ParamsHelpers;

import java.util.Map;

/**
 * Options for updating a firewall IP/Network alias.
 * The CIDR is required by the API and is supplied when converting to params.
 */
public class PveQemuFirewallAliasUpdateOptions implements ParameterizedParamsConvertible<String> {

    private String comment;
    private String rename;
    private String digest;

    /**
     * Creates a new builder for alias update options.
     *
     * @return a new PveQemuFirewallAliasUpdateOptions instance
     */
    public static PveQemuFirewallAliasUpdateOptions builder() {
        return new PveQemuFirewallAliasUpdateOptions();
    }

    @Override
    public void addRequiredParam(Map<String, Object> params, String cidr) {
        params.put("cidr", cidr);
    }

    @Override
    public void populateParams(Map<String, Object> params) {
        ParamsHelpers.put(params, "comment", comment);
        ParamsHelpers.put(params, "rename", rename);
        ParamsHelpers.put(params, "digest", digest);
    }

    /**
     * Sets a descriptive comment.
     *
     * @param comment the comment
     * @return this instance for method chaining
     */
    public PveQemuFirewallAliasUpdateOptions comment(String comment) { this.comment = comment; return this; }

    /**
     * Renames the existing alias.
     *
     * @param rename the new alias name
     * @return this instance for method chaining
     */
    public PveQemuFirewallAliasUpdateOptions rename(String rename) { this.rename = rename; return this; }

    /**
     * Sets the digest to prevent concurrent modifications.
     *
     * @param digest the configuration digest
     * @return this instance for method chaining
     */
    public PveQemuFirewallAliasUpdateOptions digest(String digest) { this.digest = digest; return this; }
}
