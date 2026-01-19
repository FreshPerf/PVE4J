package fr.freshperf.pve4j.entities.nodes.node.qemu.firewall;

import fr.freshperf.pve4j.entities.PveTask;
import fr.freshperf.pve4j.entities.nodes.node.qemu.firewall.ipset.PveQemuFirewallIpSet;
import fr.freshperf.pve4j.request.ProxmoxHttpClient;
import fr.freshperf.pve4j.request.ProxmoxRequest;
import fr.freshperf.pve4j.request.TaskResponseTransformer;

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
}
