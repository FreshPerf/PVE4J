package fr.freshperf.pve4j.entities.nodes.node.qemu.agent.hostname;

/**
 * Wraps the guest agent hostname response.
 */
public class PveQemuAgentHostNameResult {
    private PveQemuAgentHostName result;

    public PveQemuAgentHostName getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "PveQemuAgentHostNameResult{" +
                "result=" + result +
                '}';
    }
}
