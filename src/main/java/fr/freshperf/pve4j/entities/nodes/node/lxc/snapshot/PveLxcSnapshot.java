package fr.freshperf.pve4j.entities.nodes.node.lxc.snapshot;

/**
 * Represents an LXC container snapshot.
 */
public class PveLxcSnapshot {

    private String description;
    private String name;
    private String parent;
    private Long snaptime;

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getParent() {
        return parent;
    }

    public Long getSnaptime() {
        return snaptime;
    }

    @Override
    public String toString() {
        return "PveLxcSnapshot{" +
                "description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", parent='" + parent + '\'' +
                ", snaptime=" + snaptime +
                '}';
    }
}
