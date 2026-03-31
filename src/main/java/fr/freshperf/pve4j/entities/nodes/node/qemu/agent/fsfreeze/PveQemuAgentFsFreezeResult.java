package fr.freshperf.pve4j.entities.nodes.node.qemu.agent.fsfreeze;

/**
 * Wraps a guest agent filesystem freeze/thaw count response.
 */
public class PveQemuAgentFsFreezeResult {
    private Integer result;

    public Integer getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "PveQemuAgentFsFreezeResult{" +
                "result=" + result +
                '}';
    }
}
