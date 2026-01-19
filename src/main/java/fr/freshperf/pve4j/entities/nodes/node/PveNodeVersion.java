package fr.freshperf.pve4j.entities.nodes.node;

/**
 * Represents node version information.
 */
public class PveNodeVersion {

    private String release, repoid, version;

    public String getRelease() {
        return release;
    }

    public String getRepoid() {
        return repoid;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "PveNodeVersion{" +
                "release='" + release + '\'' +
                ", repoid='" + repoid + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}

