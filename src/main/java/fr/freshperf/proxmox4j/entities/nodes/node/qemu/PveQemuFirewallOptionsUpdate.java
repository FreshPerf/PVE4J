package fr.freshperf.proxmox4j.entities.nodes.node.qemu;

import java.util.HashMap;
import java.util.Map;

/**
 * Options for updating QEMU firewall options (PUT /nodes/{node}/qemu/{vmid}/firewall/options).
 */
public class PveQemuFirewallOptionsUpdate {

    private Boolean dhcp;
    private Boolean enable;
    private Boolean ipfilter;
    private String logLevelIn;
    private String logLevelOut;
    private Boolean macfilter;
    private Boolean ndp;
    private String policyIn;
    private String policyOut;
    private Boolean radv;
    private String digest;
    private String delete;

    public static PveQemuFirewallOptionsUpdate builder() {
        return new PveQemuFirewallOptionsUpdate();
    }

    public Map<String, String> toParams() {
        Map<String, String> params = new HashMap<>();
        putBool(params, "dhcp", dhcp);
        putBool(params, "enable", enable);
        putBool(params, "ipfilter", ipfilter);
        put(params, "log_level_in", logLevelIn);
        put(params, "log_level_out", logLevelOut);
        putBool(params, "macfilter", macfilter);
        putBool(params, "ndp", ndp);
        put(params, "policy_in", policyIn);
        put(params, "policy_out", policyOut);
        putBool(params, "radv", radv);
        put(params, "digest", digest);
        put(params, "delete", delete);
        return params;
    }

    public PveQemuFirewallOptionsUpdate dhcp(Boolean dhcp) { this.dhcp = dhcp; return this; }
    public PveQemuFirewallOptionsUpdate enable(Boolean enable) { this.enable = enable; return this; }
    public PveQemuFirewallOptionsUpdate ipfilter(Boolean ipfilter) { this.ipfilter = ipfilter; return this; }
    public PveQemuFirewallOptionsUpdate logLevelIn(String logLevelIn) { this.logLevelIn = logLevelIn; return this; }
    public PveQemuFirewallOptionsUpdate logLevelOut(String logLevelOut) { this.logLevelOut = logLevelOut; return this; }
    public PveQemuFirewallOptionsUpdate macfilter(Boolean macfilter) { this.macfilter = macfilter; return this; }
    public PveQemuFirewallOptionsUpdate ndp(Boolean ndp) { this.ndp = ndp; return this; }
    public PveQemuFirewallOptionsUpdate policyIn(String policyIn) { this.policyIn = policyIn; return this; }
    public PveQemuFirewallOptionsUpdate policyOut(String policyOut) { this.policyOut = policyOut; return this; }
    public PveQemuFirewallOptionsUpdate radv(Boolean radv) { this.radv = radv; return this; }
    public PveQemuFirewallOptionsUpdate digest(String digest) { this.digest = digest; return this; }
    public PveQemuFirewallOptionsUpdate delete(String delete) { this.delete = delete; return this; }

    private static void put(Map<String, String> params, String key, String value) {
        if (value != null && !value.isBlank()) {
            params.put(key, value);
        }
    }

    private static void putBool(Map<String, String> params, String key, Boolean value) {
        if (value != null) {
            params.put(key, value ? "1" : "0");
        }
    }
}

