package fr.freshperf.pve4j.entities.nodes.node.qemu.firewall;

import com.google.gson.reflect.TypeToken;
import fr.freshperf.pve4j.entities.PveTask;
import fr.freshperf.pve4j.entities.nodes.node.qemu.firewall.aliases.PveQemuFirewallAliases;
import fr.freshperf.pve4j.entities.nodes.node.qemu.firewall.ipset.PveQemuFirewallIpSet;
import fr.freshperf.pve4j.entities.nodes.node.qemu.firewall.rules.PveFirewallRules;
import fr.freshperf.pve4j.request.ProxmoxHttpClient;
import fr.freshperf.pve4j.request.ProxmoxRequest;
import fr.freshperf.pve4j.request.TaskResponseTransformer;

import java.util.List;

/**
 * Facade for managing firewall settings of a QEMU VM.
 */
public record PveQemuFirewall (ProxmoxHttpClient client, String nodeName, int vmid) {

    /**
     * Gets the IP set management interface.
     *
     * @return the IP set API facade
     */
    public PveQemuFirewallIpSet getIpSet() {
        return new PveQemuFirewallIpSet(client, nodeName, vmid);
    }

    /**
     * Gets the firewall rules management interface.
     *
     * @return the firewall rules API facade
     */
    public PveFirewallRules getRules() {
        return new PveFirewallRules(client, nodeName, vmid);
    }

    /**
     * Gets the firewall aliases management interface.
     *
     * @return the firewall aliases API facade
     */
    public PveQemuFirewallAliases getAliases() {
        return new PveQemuFirewallAliases(client, nodeName, vmid);
    }

    private String path(String suffix) {
        return "nodes/" + nodeName + "/qemu/" + vmid + suffix;
    }

    /**
     * Gets the firewall options for this VM.
     *
     * @return a request returning the firewall options
     */
    public ProxmoxRequest<PveQemuFirewallOptions> getOptions() {
        return new ProxmoxRequest<>(() ->
            client.get(path("/firewall/options"))
                .execute(PveQemuFirewallOptions.class)
        );
    }

    /**
     * Updates the firewall options for this VM.
     *
     * @param options the options to update
     * @return a request returning the task for tracking
     * @throws IllegalArgumentException if options is null
     */
    public ProxmoxRequest<PveTask> updateOptions(PveQemuFirewallOptionsUpdate options) {
        if (options == null) {
            throw new IllegalArgumentException("options cannot be null");
        }
        return new ProxmoxRequest<>(() ->
            client.put(path("/firewall/options"))
                .params(options.toParams())
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    /**
     * Reads the firewall log for this VM.
     *
     * @return a request returning the list of log lines
     */
    public ProxmoxRequest<List<PveQemuFirewallLogEntry>> getLog() {
        return new ProxmoxRequest<>(() ->
            client.get(path("/firewall/log"))
                .executeList(new TypeToken<List<PveQemuFirewallLogEntry>>() {})
        );
    }

    /**
     * Reads the firewall log for this VM with pagination/time filtering.
     *
     * @param options the log options (start, limit, since, until)
     * @return a request returning the list of log lines
     * @throws IllegalArgumentException if options is null
     */
    public ProxmoxRequest<List<PveQemuFirewallLogEntry>> getLog(PveQemuFirewallLogOptions options) {
        if (options == null) {
            throw new IllegalArgumentException("options cannot be null");
        }
        return new ProxmoxRequest<>(() ->
            client.get(path("/firewall/log"))
                .params(options.toParams())
                .executeList(new TypeToken<List<PveQemuFirewallLogEntry>>() {})
        );
    }

    /**
     * Lists possible IPSet/Alias references allowed in rule source/dest properties.
     *
     * @return a request returning the list of references
     */
    public ProxmoxRequest<List<PveQemuFirewallRef>> getRefs() {
        return getRefs(null);
    }

    /**
     * Lists possible IPSet/Alias references, optionally filtered by type.
     *
     * @param type only list references of this type ("alias" or "ipset"), or null for all
     * @return a request returning the list of references
     */
    public ProxmoxRequest<List<PveQemuFirewallRef>> getRefs(String type) {
        return new ProxmoxRequest<>(() -> {
            var request = client.get(path("/firewall/refs"));
            if (type != null && !type.isBlank()) {
                request.param("type", type);
            }
            return request.executeList(new TypeToken<List<PveQemuFirewallRef>>() {});
        });
    }
}
