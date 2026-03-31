package fr.freshperf.pve4j.entities.nodes.node.qemu.agent.users;

import java.util.Arrays;

/**
 * Wraps the guest agent users response.
 */
public class PveQemuAgentUsersResult {
    private PveQemuAgentUser[] result;

    public PveQemuAgentUser[] getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "PveQemuAgentUsersResult{" +
                "result=" + Arrays.toString(result) +
                '}';
    }
}
