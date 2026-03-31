package fr.freshperf.pve4j.entities.nodes.node.qemu.agent.fsinfo;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * Represents a filesystem mounted inside the guest.
 */
public class PveQemuAgentFsInfo {
    private PveQemuAgentFsInfoDisk[] disk;
    private String mountpoint;
    private String name;
    @SerializedName("total-bytes")
    private Long totalBytes;
    @SerializedName("total-bytes-privileged")
    private Long totalBytesPrivileged;
    private String type;
    @SerializedName("used-bytes")
    private Long usedBytes;

    public PveQemuAgentFsInfoDisk[] getDisk() {
        return disk;
    }

    public String getMountpoint() {
        return mountpoint;
    }

    public String getName() {
        return name;
    }

    public Long getTotalBytes() {
        return totalBytes;
    }

    public Long getTotalBytesPrivileged() {
        return totalBytesPrivileged;
    }

    public String getType() {
        return type;
    }

    public Long getUsedBytes() {
        return usedBytes;
    }

    @Override
    public String toString() {
        return "PveQemuAgentFsInfo{" +
                "disk=" + Arrays.toString(disk) +
                ", mountpoint='" + mountpoint + '\'' +
                ", name='" + name + '\'' +
                ", totalBytes=" + totalBytes +
                ", totalBytesPrivileged=" + totalBytesPrivileged +
                ", type='" + type + '\'' +
                ", usedBytes=" + usedBytes +
                '}';
    }
}
