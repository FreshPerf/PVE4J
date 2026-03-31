package fr.freshperf.pve4j.entities.nodes.node.qemu.agent.osinfo;

import com.google.gson.annotations.SerializedName;

/**
 * Represents operating system information reported by the guest agent.
 */
public class PveQemuAgentOsInfo {
    private String id;
    @SerializedName("kernel-release")
    private String kernelRelease;
    @SerializedName("kernel-version")
    private String kernelVersion;
    private String machine;
    private String name;
    @SerializedName("pretty-name")
    private String prettyName;
    private String version;
    @SerializedName("version-id")
    private String versionId;

    public String getId() {
        return id;
    }

    public String getKernelRelease() {
        return kernelRelease;
    }

    public String getKernelVersion() {
        return kernelVersion;
    }

    public String getMachine() {
        return machine;
    }

    public String getName() {
        return name;
    }

    public String getPrettyName() {
        return prettyName;
    }

    public String getVersion() {
        return version;
    }

    public String getVersionId() {
        return versionId;
    }

    @Override
    public String toString() {
        return "PveQemuAgentOsInfo{" +
                "id='" + id + '\'' +
                ", kernelRelease='" + kernelRelease + '\'' +
                ", kernelVersion='" + kernelVersion + '\'' +
                ", machine='" + machine + '\'' +
                ", name='" + name + '\'' +
                ", prettyName='" + prettyName + '\'' +
                ", version='" + version + '\'' +
                ", versionId='" + versionId + '\'' +
                '}';
    }
}
