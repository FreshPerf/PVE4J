package fr.freshperf.pve4j.entities.nodes.node.qemu.agent.networkInterface;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class PveQemuAgentNetworkInterface {
    private String name;
    @SerializedName("hardware-address")
    private String hardwareAddress;
    @SerializedName("ip-addresses")
    private PveQemuAgentNetworkIpAddress[] ipAddresses;
    private PveQemuAgentNetworkStatistics statistics;

    public String getName() { return name; }
    public String getHardwareAddress() { return hardwareAddress; }
    public PveQemuAgentNetworkIpAddress[] getIpAddresses() { return ipAddresses; }
    public PveQemuAgentNetworkStatistics getStatistics() { return statistics; }

    @Override
    public String toString() {
        return "PveQemuAgentNetworkInterface{" +
                "name='" + name + '\'' +
                ", hardwareAddress='" + hardwareAddress + '\'' +
                ", ipAddresses=" + Arrays.toString(ipAddresses) +
                ", statistics=" + statistics +
                '}';
    }

}
