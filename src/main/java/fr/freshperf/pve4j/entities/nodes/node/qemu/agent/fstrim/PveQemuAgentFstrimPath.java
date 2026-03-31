package fr.freshperf.pve4j.entities.nodes.node.qemu.agent.fstrim;

/**
 * Represents a filesystem trim result for one guest path.
 */
public class PveQemuAgentFstrimPath {
    private String error;
    private Long minimum;
    private String path;
    private Long trimmed;

    public String getError() {
        return error;
    }

    public Long getMinimum() {
        return minimum;
    }

    public String getPath() {
        return path;
    }

    public Long getTrimmed() {
        return trimmed;
    }

    @Override
    public String toString() {
        return "PveQemuAgentFstrimPath{" +
                "error='" + error + '\'' +
                ", minimum=" + minimum +
                ", path='" + path + '\'' +
                ", trimmed=" + trimmed +
                '}';
    }
}
