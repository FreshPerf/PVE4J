package fr.freshperf.pve4j.entities.nodes.node.qemu.snapshot;

import com.google.gson.annotations.SerializedName;

/**
 * Represents the configuration stored with a QEMU VM snapshot.
 */
public class PveQemuSnapshotConfig {

    @SerializedName("description")
    private String description;

    // Property string, e.g. "4096" or "current=4096"
    @SerializedName("memory")
    private String memory;

    @SerializedName("cores")
    private Integer cores;

    @SerializedName("sockets")
    private Integer sockets;

    @SerializedName("name")
    private String name;

    @SerializedName("ostype")
    private String ostype;

    @SerializedName("boot")
    private String boot;

    @SerializedName("machine")
    private String machine;

    @SerializedName("scsihw")
    private String scsihw;

    /**
     * Gets the snapshot description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the memory size in MiB.
     * <p>{@code memory} is a property string whose default key is {@code current},
     * so both {@code "4096"} and {@code "current=4096"} are valid.
     *
     * @return the {@code current} memory size, or null if not set
     */
    public Integer getMemory() {
        if (memory == null || memory.isBlank()) {
            return null;
        }
        for (String part : memory.split(",")) {
            String token = part.trim();
            if (token.startsWith("current=")) {
                return Integer.parseInt(token.substring("current=".length()).trim());
            }
            if (!token.contains("=")) {
                return Integer.parseInt(token);
            }
        }
        return null;
    }

    /**
     * Gets the number of CPU cores.
     *
     * @return the number of cores
     */
    public Integer getCores() {
        return cores;
    }

    /**
     * Gets the number of CPU sockets.
     *
     * @return the number of sockets
     */
    public Integer getSockets() {
        return sockets;
    }

    /**
     * Gets the VM name at snapshot time.
     *
     * @return the VM name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the OS type.
     *
     * @return the OS type
     */
    public String getOstype() {
        return ostype;
    }

    /**
     * Gets the boot order configuration.
     *
     * @return the boot order
     */
    public String getBoot() {
        return boot;
    }

    /**
     * Gets the machine type.
     *
     * @return the machine type
     */
    public String getMachine() {
        return machine;
    }

    /**
     * Gets the SCSI hardware type.
     *
     * @return the SCSI hardware type
     */
    public String getScsihw() {
        return scsihw;
    }

    @Override
    public String toString() {
        return "PveQemuSnapshotConfig{" +
                "description='" + description + '\'' +
                ", memory='" + memory + '\'' +
                ", cores=" + cores +
                ", sockets=" + sockets +
                ", name='" + name + '\'' +
                ", ostype='" + ostype + '\'' +
                '}';
    }
}

