package fr.freshperf.pve4j.entities.nodes.node.qemu.firewall.rules;

import com.google.gson.reflect.TypeToken;
import fr.freshperf.pve4j.request.ProxmoxHttpClient;
import fr.freshperf.pve4j.request.ProxmoxRequest;

import java.util.List;

/**
 * Facade for managing firewall rules on a VM.
 */
public class PveFirewallRules {

    private final ProxmoxHttpClient client;
    private final String nodeName;
    private final int vmid;

    /**
     * Creates a new firewall rules facade.
     *
     * @param client   the HTTP client
     * @param nodeName the node name
     * @param vmid     the VM ID
     */
    public PveFirewallRules(ProxmoxHttpClient client, String nodeName, int vmid) {
        this.client = client;
        this.nodeName = nodeName;
        this.vmid = vmid;
    }

    /**
     * Lists all firewall rules for this VM.
     *
     * @return a request returning the list of rules
     */
    public ProxmoxRequest<List<PveFirewallRule>> list() {
        return new ProxmoxRequest<>(() ->
            client.get("nodes/" + nodeName + "/qemu/" + vmid + "/firewall/rules")
                .executeList(new TypeToken<>() {})
        );
    }

    /**
     * Gets a specific firewall rule by position.
     *
     * @param pos the rule position
     * @return a request returning the rule
     */
    public ProxmoxRequest<PveFirewallRule> get(int pos) {
        return new ProxmoxRequest<>(() ->
            client.get("nodes/" + nodeName + "/qemu/" + vmid + "/firewall/rules/" + pos)
                .execute(PveFirewallRule.class)
        );
    }

    /**
     * Creates a new firewall rule.
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
            client.post("nodes/" + nodeName + "/qemu/" + vmid + "/firewall/rules")
                .params(options.toParams())
                .execute();
            return null;
        });
    }

    /**
     * Updates a firewall rule.
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
            client.put("nodes/" + nodeName + "/qemu/" + vmid + "/firewall/rules/" + pos)
                .params(options.toParams())
                .execute();
            return null;
        });
    }

    /**
     * Deletes a firewall rule.
     *
     * @param pos the rule position
     * @return a request that completes when the rule is deleted
     */
    public ProxmoxRequest<Void> delete(int pos) {
        return delete(pos, null);
    }

    /**
     * Deletes a firewall rule, optionally guarding against concurrent modifications.
     *
     * @param pos    the rule position
     * @param digest optional configuration digest for conflict detection, or null
     * @return a request that completes when the rule is deleted
     */
    public ProxmoxRequest<Void> delete(int pos, String digest) {
        return new ProxmoxRequest<>(() -> {
            var request = client.delete("nodes/" + nodeName + "/qemu/" + vmid + "/firewall/rules/" + pos);
            if (digest != null && !digest.isBlank()) {
                request.param("digest", digest);
            }
            request.execute();
            return null;
        });
    }
}

