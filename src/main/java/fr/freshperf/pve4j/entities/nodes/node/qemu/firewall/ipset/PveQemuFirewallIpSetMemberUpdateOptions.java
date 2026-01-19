package fr.freshperf.pve4j.entities.nodes.node.qemu.firewall.ipset;

import fr.freshperf.pve4j.entities.options.ParameterizedParamsConvertible;
import fr.freshperf.pve4j.util.ParamsHelpers;

import java.util.Map;

/**
 * Options for updating a firewall IP set member.
 */
public class PveQemuFirewallIpSetMemberUpdateOptions implements ParameterizedParamsConvertible<String> {

    private String comment;
    private Boolean nomatch;
    private String digest;

    /**
     * Creates a new builder for IP set member update options.
     *
     * @return a new PveQemuFirewallIpSetMemberUpdateOptions instance
     */
    public static PveQemuFirewallIpSetMemberUpdateOptions builder() {
        return new PveQemuFirewallIpSetMemberUpdateOptions();
    }

    @Override
    public void addRequiredParam(Map<String, Object> params, String cidr) {
        params.put("cidr", cidr);
    }

    @Override
    public void populateParams(Map<String, Object> params) {
        ParamsHelpers.put(params, "comment", comment);
        ParamsHelpers.putBool(params, "nomatch", nomatch);
        ParamsHelpers.put(params, "digest", digest);
    }

    public PveQemuFirewallIpSetMemberUpdateOptions comment(String comment) { this.comment = comment; return this; }
    public PveQemuFirewallIpSetMemberUpdateOptions nomatch(Boolean nomatch) { this.nomatch = nomatch; return this; }
    public PveQemuFirewallIpSetMemberUpdateOptions digest(String digest) { this.digest = digest; return this; }

}

