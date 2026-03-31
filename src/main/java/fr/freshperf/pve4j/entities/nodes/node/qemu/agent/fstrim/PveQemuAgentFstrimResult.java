package fr.freshperf.pve4j.entities.nodes.node.qemu.agent.fstrim;

/**
 * Wraps the guest agent fstrim response.
 */
public class PveQemuAgentFstrimResult {
    private PveQemuAgentFstrim result;

    public PveQemuAgentFstrim getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "PveQemuAgentFstrimResult{" +
                "result=" + result +
                '}';
    }
}
