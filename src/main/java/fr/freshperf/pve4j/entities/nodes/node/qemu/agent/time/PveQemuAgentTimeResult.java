package fr.freshperf.pve4j.entities.nodes.node.qemu.agent.time;

/**
 * Wraps the guest agent time response.
 */
public class PveQemuAgentTimeResult {
    private Long result;

    public Long getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "PveQemuAgentTimeResult{" +
                "result=" + result +
                '}';
    }
}
