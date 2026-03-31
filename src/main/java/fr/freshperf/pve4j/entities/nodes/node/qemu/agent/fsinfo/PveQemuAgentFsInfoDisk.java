package fr.freshperf.pve4j.entities.nodes.node.qemu.agent.fsinfo;

import com.google.gson.annotations.SerializedName;

/**
 * Represents disk address information for a guest filesystem.
 */
public class PveQemuAgentFsInfoDisk {
    private Integer bus;
    @SerializedName("bus-type")
    private String busType;
    @SerializedName("ccw-address")
    private PveQemuAgentFsInfoCcwAddress ccwAddress;
    private String dev;
    @SerializedName("pci-controller")
    private PveQemuAgentFsInfoPciAddress pciController;
    private String serial;
    private Integer target;
    private Integer unit;

    public Integer getBus() {
        return bus;
    }

    public String getBusType() {
        return busType;
    }

    public PveQemuAgentFsInfoCcwAddress getCcwAddress() {
        return ccwAddress;
    }

    public String getDev() {
        return dev;
    }

    public PveQemuAgentFsInfoPciAddress getPciController() {
        return pciController;
    }

    public String getSerial() {
        return serial;
    }

    public Integer getTarget() {
        return target;
    }

    public Integer getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return "PveQemuAgentFsInfoDisk{" +
                "bus=" + bus +
                ", busType='" + busType + '\'' +
                ", ccwAddress=" + ccwAddress +
                ", dev='" + dev + '\'' +
                ", pciController=" + pciController +
                ", serial='" + serial + '\'' +
                ", target=" + target +
                ", unit=" + unit +
                '}';
    }
}
