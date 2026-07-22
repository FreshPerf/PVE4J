package fr.freshperf.pve4j.entities.cluster.firewall;

import com.google.gson.annotations.SerializedName;

/**
 * Represents the datacenter-level firewall options
 * returned by {@code GET /cluster/firewall/options}.
 */
public class PveClusterFirewallOptions {

    @SerializedName("enable")
    private Integer enable;

    @SerializedName("ebtables")
    private Integer ebtables;

    @SerializedName("log_ratelimit")
    private String logRatelimit;

    @SerializedName("policy_in")
    private String policyIn;

    @SerializedName("policy_out")
    private String policyOut;

    @SerializedName("policy_forward")
    private String policyForward;

    @SerializedName("digest")
    private String digest;

    /**
     * Gets the cluster-wide firewall master toggle. Nothing is enforced anywhere
     * (including VM-level rules) while this is 0.
     *
     * @return 1 if enabled, 0 if disabled
     */
    public Integer getEnable() {
        return enable;
    }

    /**
     * Checks if the firewall is enabled cluster wide.
     *
     * @return true if enabled
     */
    public boolean isEnabled() {
        return enable != null && enable == 1;
    }

    /**
     * Gets the cluster-wide ebtables toggle.
     *
     * @return 1 if enabled, 0 if disabled
     */
    public Integer getEbtables() {
        return ebtables;
    }

    /**
     * Gets the log rate limiting settings (e.g. {@code "enable=1,rate=1/second,burst=5"}).
     *
     * @return the log ratelimit specification
     */
    public String getLogRatelimit() {
        return logRatelimit;
    }

    /**
     * Gets the input policy ("ACCEPT", "REJECT", "DROP").
     *
     * @return the input policy
     */
    public String getPolicyIn() {
        return policyIn;
    }

    /**
     * Gets the output policy ("ACCEPT", "REJECT", "DROP").
     *
     * @return the output policy
     */
    public String getPolicyOut() {
        return policyOut;
    }

    /**
     * Gets the forward policy ("ACCEPT", "DROP").
     *
     * @return the forward policy
     */
    public String getPolicyForward() {
        return policyForward;
    }

    /**
     * Gets the options configuration digest, for concurrency guards on updates.
     *
     * @return the digest
     */
    public String getDigest() {
        return digest;
    }

    @Override
    public String toString() {
        return "PveClusterFirewallOptions{" +
                "enable=" + enable +
                ", ebtables=" + ebtables +
                ", logRatelimit='" + logRatelimit + '\'' +
                ", policyIn='" + policyIn + '\'' +
                ", policyOut='" + policyOut + '\'' +
                ", policyForward='" + policyForward + '\'' +
                ", digest='" + digest + '\'' +
                '}';
    }
}
