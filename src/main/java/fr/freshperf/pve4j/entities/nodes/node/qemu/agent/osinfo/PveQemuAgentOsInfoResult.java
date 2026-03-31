package fr.freshperf.pve4j.entities.nodes.node.qemu.agent.osinfo;

/**
 * Wraps the guest agent operating system information response.
 */
public class PveQemuAgentOsInfoResult {
    private PveQemuAgentOsInfo result;

    public PveQemuAgentOsInfo getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "PveQemuAgentOsInfoResult{" +
                "result=" + result +
                '}';
    }
}
