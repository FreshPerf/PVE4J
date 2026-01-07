package fr.freshperf.proxmox4j.entities.nodes.node.qemu.firewall.ipset;

import fr.freshperf.proxmox4j.util.ParamsHelpers;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Options for creating/renaming an IP set (POST /nodes/{node}/qemu/{vmid}/firewall/ipset).
 */
public class PveQemuFirewallIpSetCreateOptions {

    private String comment;
    private String digest;
    private String rename;

    public static PveQemuFirewallIpSetCreateOptions builder() {
        return new PveQemuFirewallIpSetCreateOptions();
    }

    public Map<String, Object> toParams(String name) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        ParamsHelpers.put(params, "comment", comment);
        ParamsHelpers.put(params, "digest", digest);
        ParamsHelpers.put(params, "rename", rename);
        return params;
    }

    public PveQemuFirewallIpSetCreateOptions comment(String comment) { this.comment = comment; return this; }
    public PveQemuFirewallIpSetCreateOptions digest(String digest) { this.digest = digest; return this; }
    public PveQemuFirewallIpSetCreateOptions rename(String rename) { this.rename = rename; return this; }
}
