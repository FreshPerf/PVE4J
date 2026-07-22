package fr.freshperf.pve4j.entities.cluster.firewall;

import fr.freshperf.pve4j.entities.options.ParamsConvertible;
import fr.freshperf.pve4j.util.ParamsHelpers;

import java.util.Map;

/**
 * Options for updating the datacenter-level firewall settings.
 * Use the builder pattern to configure firewall options.
 */
public class PveClusterFirewallOptionsUpdate implements ParamsConvertible {

    private Boolean enable;
    private Boolean ebtables;
    private String logRatelimit;
    private String policyIn;
    private String policyOut;
    private String policyForward;
    private String digest;
    private String delete;

    /**
     * Creates a new builder for cluster firewall options update.
     *
     * @return a new PveClusterFirewallOptionsUpdate instance
     */
    public static PveClusterFirewallOptionsUpdate builder() {
        return new PveClusterFirewallOptionsUpdate();
    }

    @Override
    public void populateParams(Map<String, Object> params) {
        ParamsHelpers.putBool(params, "enable", enable);
        ParamsHelpers.putBool(params, "ebtables", ebtables);
        ParamsHelpers.put(params, "log_ratelimit", logRatelimit);
        ParamsHelpers.put(params, "policy_in", policyIn);
        ParamsHelpers.put(params, "policy_out", policyOut);
        ParamsHelpers.put(params, "policy_forward", policyForward);
        ParamsHelpers.put(params, "digest", digest);
        ParamsHelpers.put(params, "delete", delete);
    }

    public PveClusterFirewallOptionsUpdate enable(Boolean enable) { this.enable = enable; return this; }
    public PveClusterFirewallOptionsUpdate ebtables(Boolean ebtables) { this.ebtables = ebtables; return this; }
    public PveClusterFirewallOptionsUpdate logRatelimit(String logRatelimit) { this.logRatelimit = logRatelimit; return this; }
    public PveClusterFirewallOptionsUpdate policyIn(String policyIn) { this.policyIn = policyIn; return this; }
    public PveClusterFirewallOptionsUpdate policyOut(String policyOut) { this.policyOut = policyOut; return this; }
    public PveClusterFirewallOptionsUpdate policyForward(String policyForward) { this.policyForward = policyForward; return this; }
    public PveClusterFirewallOptionsUpdate digest(String digest) { this.digest = digest; return this; }
    public PveClusterFirewallOptionsUpdate delete(String delete) { this.delete = delete; return this; }

}
