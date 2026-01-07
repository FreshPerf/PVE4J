package fr.freshperf.proxmox4j.entities.nodes.node.qemu.firewall.ipset;

import fr.freshperf.proxmox4j.util.ParamsHelpers;

import java.util.HashMap;
import java.util.Map;

/**
 * Options for adding a member to an IP set (POST /nodes/{node}/qemu/{vmid}/firewall/ipset/{name}).
 */
public class PveQemuFirewallIpSetMemberCreateOptions {

    private String comment;
    private Boolean nomatch;

    public static PveQemuFirewallIpSetMemberCreateOptions builder() {
        return new PveQemuFirewallIpSetMemberCreateOptions();
    }

    public Map<String, Object> toParams(String cidr) {
        Map<String, Object> params = new HashMap<>();
        params.put("cidr", cidr);
        ParamsHelpers.put(params, "comment", comment);
        ParamsHelpers.putBool(params, "nomatch", nomatch);
        return params;
    }

    public PveQemuFirewallIpSetMemberCreateOptions comment(String comment) { this.comment = comment; return this; }
    public PveQemuFirewallIpSetMemberCreateOptions nomatch(Boolean nomatch) { this.nomatch = nomatch; return this; }

}
