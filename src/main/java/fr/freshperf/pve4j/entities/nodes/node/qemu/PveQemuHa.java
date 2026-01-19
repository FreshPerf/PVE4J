package fr.freshperf.pve4j.entities.nodes.node.qemu;

/**
 * Represents High Availability configuration for a QEMU VM.
 */
public class PveQemuHa {

    private int managed;

    public int getManaged() {
        return managed;
    }

    public boolean isManaged() {
        return managed == 1;
    }

    @Override
    public String toString() {
        return "PveQemuHa{" +
                "managed=" + managed +
                '}';
    }
}

