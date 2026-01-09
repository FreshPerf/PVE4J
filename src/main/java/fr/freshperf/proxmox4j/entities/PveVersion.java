package fr.freshperf.proxmox4j.entities;

/**
 * Represents Proxmox VE version information.
 */
public class PveVersion {

    private String release, repoid, version, console;

    /** @return the release version (e.g., "8.1") */
    public String getRelease() {
        return release;
    }

    /** @return the repository ID */
    public String getRepoid() {
        return repoid;
    }

    /** @return the full version string */
    public String getVersion() {
        return version;
    }

    /** @return the console type */
    public String getConsole() {
        return console;
    }

    @Override
    public String toString() {
        return "PveVersion{" +
                "release='" + release + '\'' +
                ", repoid='" + repoid + '\'' +
                ", version='" + version + '\'' +
                ", console='" + console + '\'' +
                '}';
    }
}
