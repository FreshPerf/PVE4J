package fr.freshperf.pve4j.entities.nodes.node.qemu.agent.timezone;

/**
 * Wraps the guest agent timezone response.
 */
public class PveQemuAgentTimezoneResult {
    private PveQemuAgentTimezone result;

    public PveQemuAgentTimezone getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "PveQemuAgentTimezoneResult{" +
                "result=" + result +
                '}';
    }
}
