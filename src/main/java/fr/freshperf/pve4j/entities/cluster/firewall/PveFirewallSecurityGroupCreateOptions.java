package fr.freshperf.pve4j.entities.cluster.firewall;

import fr.freshperf.pve4j.entities.options.ParamsConvertible;
import fr.freshperf.pve4j.util.ParamsHelpers;

import java.util.Map;

/**
 * Options for creating, renaming or updating a firewall security group.
 * Use the builder pattern to configure the group settings.
 */
public class PveFirewallSecurityGroupCreateOptions implements ParamsConvertible {

    private String group;
    private String comment;
    private String rename;
    private String digest;

    /**
     * Creates a new builder for security group create options.
     *
     * @return a new PveFirewallSecurityGroupCreateOptions instance
     */
    public static PveFirewallSecurityGroupCreateOptions builder() {
        return new PveFirewallSecurityGroupCreateOptions();
    }

    @Override
    public void populateParams(Map<String, Object> params) {
        ParamsHelpers.put(params, "group", group);
        ParamsHelpers.put(params, "comment", comment);
        ParamsHelpers.put(params, "rename", rename);
        ParamsHelpers.put(params, "digest", digest);
    }

    /**
     * Sets the security group name (REQUIRED).
     *
     * @param group the group name
     * @return this instance for method chaining
     */
    public PveFirewallSecurityGroupCreateOptions group(String group) {
        this.group = group;
        return this;
    }

    /**
     * Sets the descriptive comment.
     *
     * @param comment the comment
     * @return this instance for method chaining
     */
    public PveFirewallSecurityGroupCreateOptions comment(String comment) {
        this.comment = comment;
        return this;
    }

    /**
     * Sets the name of an existing group to rename to the {@code group} value.
     * Set it to the same value as {@code group} to only update the comment.
     *
     * @param rename the existing group name
     * @return this instance for method chaining
     */
    public PveFirewallSecurityGroupCreateOptions rename(String rename) {
        this.rename = rename;
        return this;
    }

    /**
     * Sets the digest to prevent concurrent modifications.
     *
     * @param digest the configuration digest
     * @return this instance for method chaining
     */
    public PveFirewallSecurityGroupCreateOptions digest(String digest) {
        this.digest = digest;
        return this;
    }
}
