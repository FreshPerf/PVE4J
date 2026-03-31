package fr.freshperf.pve4j.entities.nodes.node.qemu.agent.info;

/**
 * Wraps the guest agent info response.
 */
public class PveQemuAgentInfoResult {
    private PveQemuAgentInfo result;

    public PveQemuAgentInfo getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "PveQemuAgentInfoResult{" +
                "result=" + result +
                '}';
    }
}
