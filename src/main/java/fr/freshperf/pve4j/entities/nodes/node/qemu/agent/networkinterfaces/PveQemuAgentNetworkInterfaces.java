package fr.freshperf.pve4j.entities.nodes.node.qemu.agent.networkinterfaces;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * Represents a network interface reported by the guest agent.
 */
public class PveQemuAgentNetworkInterfaces {
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
        return "PveQemuAgentNetworkInterfaces{" +
                "name='" + name + '\'' +
                ", hardwareAddress='" + hardwareAddress + '\'' +
                ", ipAddresses=" + Arrays.toString(ipAddresses) +
                ", statistics=" + statistics +
                '}';
    }

}
