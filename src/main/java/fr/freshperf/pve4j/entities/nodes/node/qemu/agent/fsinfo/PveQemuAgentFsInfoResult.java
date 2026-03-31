package fr.freshperf.pve4j.entities.nodes.node.qemu.agent.fsinfo;

import java.util.Arrays;

/**
 * Wraps the guest agent filesystem information response.
 */
public class PveQemuAgentFsInfoResult {
    private PveQemuAgentFsInfo[] result;

    public PveQemuAgentFsInfo[] getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "PveQemuAgentFsInfoResult{" +
                "result=" + Arrays.toString(result) +
                '}';
    }
}
