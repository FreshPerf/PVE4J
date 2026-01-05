package fr.freshperf.proxmox4j.entities.cluster;

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
