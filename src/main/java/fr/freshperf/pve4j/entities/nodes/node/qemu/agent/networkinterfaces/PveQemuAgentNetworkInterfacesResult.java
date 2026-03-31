package fr.freshperf.pve4j.entities.nodes.node.qemu.agent.networkinterfaces;

import java.util.Arrays;

/**
 * Wraps the guest agent network interfaces response.
 */
public class PveQemuAgentNetworkInterfacesResult {
    private PveQemuAgentNetworkInterfaces[] result;

    public PveQemuAgentNetworkInterfaces[] getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "PveQemuAgentNetworkInterfacesResult{" +
                "result=" + Arrays.toString(result) +
                '}';
    }
}
