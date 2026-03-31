package fr.freshperf.pve4j.entities.nodes.node.qemu.agent.vcpus;

import java.util.Arrays;

/**
 * Wraps the guest agent vCPU response.
 */
public class PveQemuAgentVcpusResult {
    private PveQemuAgentVcpu[] result;

    public PveQemuAgentVcpu[] getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "PveQemuAgentVcpusResult{" +
                "result=" + Arrays.toString(result) +
                '}';
    }
}
