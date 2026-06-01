package fr.freshperf.pve4j.entities.nodes.node.qemu.firewall;

/**
 * Represents a single line of a QEMU VM firewall log.
 */
public class PveQemuFirewallLogEntry {

    private int n;
    private String t;

    /**
     * Gets the line number.
     *
     * @return the line number
     */
    public int getLineNumber() {
        return n;
    }

    /**
     * Gets the line text.
     *
     * @return the line text
     */
    public String getText() {
        return t;
    }

    @Override
    public String toString() {
        return "PveQemuFirewallLogEntry{" +
                "n=" + n +
                ", t='" + t + '\'' +
                '}';
    }
}
