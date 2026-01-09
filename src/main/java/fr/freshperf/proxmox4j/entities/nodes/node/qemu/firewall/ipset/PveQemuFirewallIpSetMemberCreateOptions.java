package fr.freshperf.proxmox4j.entities.nodes.node.qemu.firewall.ipset;

import fr.freshperf.proxmox4j.entities.options.ParameterizedParamsConvertible;
import fr.freshperf.proxmox4j.util.ParamsHelpers;

import java.util.Map;

/**
 * Options for adding a member to an IP set (POST /nodes/{node}/qemu/{vmid}/firewall/ipset/{name}).
 */
public class PveQemuFirewallIpSetMemberCreateOptions implements ParameterizedParamsConvertible<String> {

    private String comment;
    private Boolean nomatch;

    public static PveQemuFirewallIpSetMemberCreateOptions builder() {
        return new PveQemuFirewallIpSetMemberCreateOptions();
    }

    @Override
    public void addRequiredParam(Map<String, Object> params, String cidr) {
        params.put("cidr", cidr);
    }

    @Override
    public void populateParams(Map<String, Object> params) {
        ParamsHelpers.put(params, "comment", comment);
        ParamsHelpers.putBool(params, "nomatch", nomatch);
    }

    public PveQemuFirewallIpSetMemberCreateOptions comment(String comment) { this.comment = comment; return this; }
    public PveQemuFirewallIpSetMemberCreateOptions nomatch(Boolean nomatch) { this.nomatch = nomatch; return this; }

}

