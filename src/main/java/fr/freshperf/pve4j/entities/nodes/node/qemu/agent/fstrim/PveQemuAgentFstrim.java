package fr.freshperf.pve4j.entities.nodes.node.qemu.agent.fstrim;

import java.util.Arrays;

/**
 * Represents the guest agent fstrim response payload.
 */
public class PveQemuAgentFstrim {
    private PveQemuAgentFstrimPath[] paths;

    public PveQemuAgentFstrimPath[] getPaths() {
        return paths;
    }

    @Override
    public String toString() {
        return "PveQemuAgentFstrim{" +
                "paths=" + Arrays.toString(paths) +
                '}';
    }
}
