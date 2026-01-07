package fr.freshperf.proxmox4j.entities.nodes.node.qemu.firewall.ipset;

import fr.freshperf.proxmox4j.util.ParamsHelpers;

import java.util.HashMap;
import java.util.Map;

/**
 * Options for updating a firewall IP set member (/firewall/ipset/{name}/{cidr}, PUT).
 */
public class PveQemuFirewallIpSetMemberUpdateOptions {

    private String comment;
    private Boolean nomatch;
    private String digest;

    public static PveQemuFirewallIpSetMemberUpdateOptions builder() {
        return new PveQemuFirewallIpSetMemberUpdateOptions();
    }

    public Map<String, Object> toParams(String cidr) {
        Map<String, Object> params = new HashMap<>();
        params.put("cidr", cidr);
        ParamsHelpers.put(params, "comment", comment);
        ParamsHelpers.putBool(params, "nomatch", nomatch);
        ParamsHelpers.put(params, "digest", digest);
        return params;
    }

    public PveQemuFirewallIpSetMemberUpdateOptions comment(String comment) { this.comment = comment; return this; }
    public PveQemuFirewallIpSetMemberUpdateOptions nomatch(Boolean nomatch) { this.nomatch = nomatch; return this; }
    public PveQemuFirewallIpSetMemberUpdateOptions digest(String digest) { this.digest = digest; return this; }

}
