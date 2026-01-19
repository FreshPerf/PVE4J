package fr.freshperf.pve4j.entities.nodes.node;

/**
 * Represents an entry in the node API index.
 */
public class PveNodeIndex {

    private String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "PveNodeResource{" +
                "name='" + name + '\'' +
                '}';
    }
}

