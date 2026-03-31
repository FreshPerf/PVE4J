package fr.freshperf.pve4j.entities.nodes.node.qemu.agent.timezone;

/**
 * Represents timezone information reported by the guest agent.
 */
public class PveQemuAgentTimezone {
    private Integer offset;
    private String zone;

    public Integer getOffset() {
        return offset;
    }

    public String getZone() {
        return zone;
    }

    @Override
    public String toString() {
        return "PveQemuAgentTimezone{" +
                "offset=" + offset +
                ", zone='" + zone + '\'' +
                '}';
    }
}
