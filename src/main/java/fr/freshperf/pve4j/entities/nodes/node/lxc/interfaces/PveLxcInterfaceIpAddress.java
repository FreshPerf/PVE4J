package fr.freshperf.pve4j.entities.nodes.node.lxc.interfaces;

import com.google.gson.annotations.SerializedName;

/**
 * Represents an IP address assigned to an LXC container interface.
 */
public class PveLxcInterfaceIpAddress {

    @SerializedName("ip-address")
    private String ipAddress;
    @SerializedName("ip-address-type")
    private String ipAddressType;
    private Integer prefix;

    public String getIpAddress() {
        return ipAddress;
    }

    public String getIpAddressType() {
        return ipAddressType;
    }

    public Integer getPrefix() {
        return prefix;
    }

    @Override
    public String toString() {
        return "PveLxcInterfaceIpAddress{" +
                "ipAddress='" + ipAddress + '\'' +
                ", ipAddressType='" + ipAddressType + '\'' +
                ", prefix=" + prefix +
                '}';
    }
}
