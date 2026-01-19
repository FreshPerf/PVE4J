package fr.freshperf.pve4j.entities.nodes.node.qemu.firewall.ipset;

import fr.freshperf.pve4j.entities.options.ParameterizedParamsConvertible;
import fr.freshperf.pve4j.util.ParamsHelpers;

import java.util.Map;

/**
 * Options for creating or renaming a firewall IP set.
 */
public class PveQemuFirewallIpSetCreateOptions implements ParameterizedParamsConvertible<String> {

    private String comment;
    private String digest;
    private String rename;

    /**
     * Creates a new builder for IP set creation options.
     *
     * @return a new PveQemuFirewallIpSetCreateOptions instance
     */
    public static PveQemuFirewallIpSetCreateOptions builder() {
        return new PveQemuFirewallIpSetCreateOptions();
    }

    @Override
    public void addRequiredParam(Map<String, Object> params, String name) {
        params.put("name", name);
    }

    @Override
    public void populateParams(Map<String, Object> params) {
        ParamsHelpers.put(params, "comment", comment);
        ParamsHelpers.put(params, "digest", digest);
        ParamsHelpers.put(params, "rename", rename);
    }

    public PveQemuFirewallIpSetCreateOptions comment(String comment) { this.comment = comment; return this; }
    public PveQemuFirewallIpSetCreateOptions digest(String digest) { this.digest = digest; return this; }
    public PveQemuFirewallIpSetCreateOptions rename(String rename) { this.rename = rename; return this; }
}

