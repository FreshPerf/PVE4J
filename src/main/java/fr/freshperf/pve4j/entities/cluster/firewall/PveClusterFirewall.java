package fr.freshperf.pve4j.entities.cluster.firewall;

import com.google.gson.reflect.TypeToken;
import fr.freshperf.pve4j.request.ProxmoxHttpClient;
import fr.freshperf.pve4j.request.ProxmoxRequest;

import java.util.List;

/**
 * Facade for the datacenter-level firewall ({@code /cluster/firewall}).
 */
public record PveClusterFirewall(ProxmoxHttpClient httpClient) {

    /**
     * Lists the predefined firewall macros (SSH, HTTP, Ping, ...).
     * Accessible to any authenticated user.
     *
     * @return a request returning the list of macros
     */
    public ProxmoxRequest<List<PveFirewallMacro>> getMacros() {
        return new ProxmoxRequest<>(() ->
            httpClient.get("cluster/firewall/macros")
                .executeList(new TypeToken<List<PveFirewallMacro>>() {})
        );
    }

    /**
     * Gets the datacenter-level firewall options. Note that the {@code enable}
     * option is the cluster-wide master toggle: no firewall rule (including
     * VM-level ones) is enforced while it is disabled.
     *
     * @return a request returning the firewall options
     */
    public ProxmoxRequest<PveClusterFirewallOptions> getOptions() {
        return new ProxmoxRequest<>(() ->
            httpClient.get("cluster/firewall/options")
                .execute(PveClusterFirewallOptions.class)
        );
    }

    /**
     * Updates the datacenter-level firewall options.
     *
     * @param options the options to update
     * @return a request that completes when the options are updated
     * @throws IllegalArgumentException if options is null
     */
    public ProxmoxRequest<Void> updateOptions(PveClusterFirewallOptionsUpdate options) {
        if (options == null) {
            throw new IllegalArgumentException("options cannot be null");
        }
        return new ProxmoxRequest<>(() -> {
            httpClient.put("cluster/firewall/options")
                .params(options.toParams())
                .execute();
            return null;
        });
    }

    /**
     * Lists the security groups. Accessible to any authenticated user.
     *
     * @return a request returning the list of security groups
     */
    public ProxmoxRequest<List<PveFirewallSecurityGroup>> getGroups() {
        return new ProxmoxRequest<>(() ->
            httpClient.get("cluster/firewall/groups")
                .executeList(new TypeToken<List<PveFirewallSecurityGroup>>() {})
        );
    }

    /**
     * Creates a new security group.
     *
     * @param group the security group name
     * @return a request that completes when the group is created
     * @throws IllegalArgumentException if group is null or blank
     */
    public ProxmoxRequest<Void> createGroup(String group) {
        requireGroup(group);
        return createGroup(PveFirewallSecurityGroupCreateOptions.builder().group(group));
    }

    /**
     * Creates, renames or updates a security group.
     *
     * @param options the group options (group is required; use {@code rename} to rename/update an existing group)
     * @return a request that completes when the group is created or updated
     * @throws IllegalArgumentException if options is null
     */
    public ProxmoxRequest<Void> createGroup(PveFirewallSecurityGroupCreateOptions options) {
        if (options == null) {
            throw new IllegalArgumentException("options cannot be null");
        }
        return new ProxmoxRequest<>(() -> {
            httpClient.post("cluster/firewall/groups")
                .params(options.toParams())
                .execute();
            return null;
        });
    }

    /**
     * Deletes a security group. The group must be empty (no rules); Proxmox does
     * not check whether {@code type=group} rules still reference it, so callers
     * should verify that themselves to avoid dangling references.
     *
     * @param group the security group name
     * @return a request that completes when the group is deleted
     * @throws IllegalArgumentException if group is null or blank
     */
    public ProxmoxRequest<Void> deleteGroup(String group) {
        requireGroup(group);
        return new ProxmoxRequest<>(() -> {
            httpClient.delete("cluster/firewall/groups/" + group).execute();
            return null;
        });
    }

    /**
     * Gets the rules management interface of a security group.
     *
     * @param group the security group name
     * @return the group rules API facade
     * @throws IllegalArgumentException if group is null or blank
     */
    public PveClusterFirewallGroupRules getGroupRules(String group) {
        requireGroup(group);
        return new PveClusterFirewallGroupRules(httpClient, group);
    }

    private static void requireGroup(String group) {
        if (group == null || group.isBlank()) {
            throw new IllegalArgumentException("group cannot be null or blank");
        }
    }
}
