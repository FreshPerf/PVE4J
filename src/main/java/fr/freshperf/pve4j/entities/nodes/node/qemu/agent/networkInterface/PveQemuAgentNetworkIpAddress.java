package fr.freshperf.pve4j.entities.nodes.node.qemu.agent.networkInterface;

import com.google.gson.annotations.SerializedName;

public class PveQemuAgentNetworkIpAddress {
    private int prefix;
    @SerializedName("ip-address")
    private String ipAddress;
    @SerializedName("ip-address-type")
    private String ipAddressType;

    public int getPrefix() { return prefix; }
    public String getIpAddress() { return ipAddress; }
    public String getIpAddressType() { return ipAddressType; }

    @Override
    public String toString() {
        return "PveQemuAgentNetworkIpAddress{prefix=" + prefix + ", ipAddress=" + ipAddress +
                ", ipAddressType=" + ipAddressType + '}';
    }
}
