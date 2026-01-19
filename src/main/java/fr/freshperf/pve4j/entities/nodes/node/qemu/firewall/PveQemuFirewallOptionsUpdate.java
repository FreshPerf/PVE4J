package fr.freshperf.pve4j.entities.nodes.node.qemu.firewall;

import fr.freshperf.pve4j.entities.options.ParamsConvertible;
import fr.freshperf.pve4j.util.ParamsHelpers;

import java.util.Map;

/**
 * Options for updating QEMU VM firewall settings.
 * Use the builder pattern to configure firewall options.
 */
public class PveQemuFirewallOptionsUpdate implements ParamsConvertible {

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

    /**
     * Creates a new builder for firewall options update.
     *
     * @return a new PveQemuFirewallOptionsUpdate instance
     */
    public static PveQemuFirewallOptionsUpdate builder() {
        return new PveQemuFirewallOptionsUpdate();
    }

    @Override
    public void populateParams(Map<String, Object> params) {
        ParamsHelpers.putBool(params, "dhcp", dhcp);
        ParamsHelpers.putBool(params, "enable", enable);
        ParamsHelpers.putBool(params, "ipfilter", ipfilter);
        ParamsHelpers.put(params, "log_level_in", logLevelIn);
        ParamsHelpers.put(params, "log_level_out", logLevelOut);
        ParamsHelpers.putBool(params, "macfilter", macfilter);
        ParamsHelpers.putBool(params, "ndp", ndp);
        ParamsHelpers.put(params, "policy_in", policyIn);
        ParamsHelpers.put(params, "policy_out", policyOut);
        ParamsHelpers.putBool(params, "radv", radv);
        ParamsHelpers.put(params, "digest", digest);
        ParamsHelpers.put(params, "delete", delete);
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

}
