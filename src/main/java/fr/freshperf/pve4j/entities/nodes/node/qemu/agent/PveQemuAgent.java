package fr.freshperf.pve4j.entities.nodes.node.qemu.agent;

import fr.freshperf.pve4j.entities.nodes.node.qemu.agent.networkInterface.PveQemuAgentNetworkInterfaceResult;
import fr.freshperf.pve4j.request.ProxmoxHttpClient;
import fr.freshperf.pve4j.request.ProxmoxRequest;

/**
 * Facade for accessing all guest agent commands of a QEMU VM.
 */
public record PveQemuAgent (ProxmoxHttpClient client, String nodeName, int vmid) {

    private String path(String suffix) {
        return "nodes/" + nodeName + "/qemu/" + vmid + "/agent/" + suffix;
    }

    public ProxmoxRequest<PveQemuAgentNetworkInterfaceResult> getNetworkInterface() {
        return new ProxmoxRequest<>(() -> client.get(path("network-get-interfaces"))
                .execute(PveQemuAgentNetworkInterfaceResult.class));
    }

}
