package fr.freshperf.pve4j.entities.cluster.firewall;

import com.google.gson.reflect.TypeToken;
import fr.freshperf.pve4j.entities.nodes.node.qemu.firewall.rules.PveFirewallRule;
import fr.freshperf.pve4j.entities.nodes.node.qemu.firewall.rules.PveFirewallRuleCreateOptions;
import fr.freshperf.pve4j.entities.nodes.node.qemu.firewall.rules.PveFirewallRuleUpdateOptions;
import fr.freshperf.pve4j.request.ProxmoxHttpClient;
import fr.freshperf.pve4j.request.ProxmoxRequest;

import java.util.List;

/**
 * Facade for managing the rules of a firewall security group
 * ({@code /cluster/firewall/groups/{group}}). Group rules share the same
 * schema as VM firewall rules, so the same rule entity and options are reused.
 */
public class PveClusterFirewallGroupRules {

    private final ProxmoxHttpClient client;
    private final String group;

    /**
     * Creates a new security group rules facade.
     *
     * @param client the HTTP client
     * @param group  the security group name
     */
    public PveClusterFirewallGroupRules(ProxmoxHttpClient client, String group) {
        this.client = client;
        this.group = group;
    }

    /**
     * Lists all rules of this security group.
     *
     * @return a request returning the list of rules
     */
    public ProxmoxRequest<List<PveFirewallRule>> list() {
        return new ProxmoxRequest<>(() ->
            client.get("cluster/firewall/groups/" + group)
                .executeList(new TypeToken<>() {})
        );
    }

    /**
     * Gets a specific rule of this security group by position.
     *
     * @param pos the rule position
     * @return a request returning the rule
     */
    public ProxmoxRequest<PveFirewallRule> get(int pos) {
        return new ProxmoxRequest<>(() ->
            client.get("cluster/firewall/groups/" + group + "/" + pos)
                .execute(PveFirewallRule.class)
        );
    }

    /**
     * Creates a new rule in this security group.
     *
     * @param options the rule options (type and action are required)
     * @return a request that completes when the rule is created
     * @throws IllegalArgumentException if options is null
     */
    public ProxmoxRequest<Void> create(PveFirewallRuleCreateOptions options) {
        if (options == null) {
            throw new IllegalArgumentException("options cannot be null");
        }
        return new ProxmoxRequest<>(() -> {
            client.post("cluster/firewall/groups/" + group)
                .params(options.toParams())
                .execute();
            return null;
        });
    }

    /**
     * Updates a rule of this security group.
     *
     * @param pos     the rule position
     * @param options the updated options (supports {@code moveto}, {@code delete}, {@code digest})
     * @return a request that completes when the rule is updated
     * @throws IllegalArgumentException if options is null
     */
    public ProxmoxRequest<Void> update(int pos, PveFirewallRuleUpdateOptions options) {
        if (options == null) {
            throw new IllegalArgumentException("options cannot be null");
        }
        return new ProxmoxRequest<>(() -> {
            client.put("cluster/firewall/groups/" + group + "/" + pos)
                .params(options.toParams())
                .execute();
            return null;
        });
    }

    /**
     * Deletes a rule of this security group.
     *
     * @param pos the rule position
     * @return a request that completes when the rule is deleted
     */
    public ProxmoxRequest<Void> delete(int pos) {
        return delete(pos, null);
    }

    /**
     * Deletes a rule of this security group, optionally guarding against concurrent modifications.
     *
     * @param pos    the rule position
     * @param digest optional configuration digest for conflict detection, or null
     * @return a request that completes when the rule is deleted
     */
    public ProxmoxRequest<Void> delete(int pos, String digest) {
        return new ProxmoxRequest<>(() -> {
            var request = client.delete("cluster/firewall/groups/" + group + "/" + pos);
            if (digest != null && !digest.isBlank()) {
                request.param("digest", digest);
            }
            request.execute();
            return null;
        });
    }
}
