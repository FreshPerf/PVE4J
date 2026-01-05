package fr.freshperf.proxmox4j.entities.nodes.node;

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

