package fr.freshperf.pve4j.entities.nodes.node.qemu.agent.fsfreeze;

/**
 * Wraps the guest agent filesystem freeze status response.
 */
public class PveQemuAgentFsFreezeStatusResult {
    private PveQemuAgentFsFreezeStatus result;

    public PveQemuAgentFsFreezeStatus getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "PveQemuAgentFsFreezeStatusResult{" +
                "result=" + result +
                '}';
    }
}
