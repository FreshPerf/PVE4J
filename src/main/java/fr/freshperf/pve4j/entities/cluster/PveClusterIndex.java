package fr.freshperf.pve4j.entities.cluster;

/**
 * Represents an entry in the cluster API index.
 */
public class PveClusterIndex {

    private String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "PveClusterIndex{" +
                "name='" + name + '\'' +
                '}';
    }
}
