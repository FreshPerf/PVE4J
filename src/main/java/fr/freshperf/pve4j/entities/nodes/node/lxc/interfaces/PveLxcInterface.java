package fr.freshperf.pve4j.entities.nodes.node.lxc.interfaces;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * Represents a network interface of an LXC container.
 */
public class PveLxcInterface {

    @SerializedName("hardware-address")
    private String hardwareAddress;
    private String hwaddr;
    private String inet;
    private String inet6;
    @SerializedName("ip-addresses")
    private PveLxcInterfaceIpAddress[] ipAddresses;
    private String name;

    public String getHardwareAddress() {
        return hardwareAddress;
    }

    public String getHwaddr() {
        return hwaddr;
    }

    public String getInet() {
        return inet;
    }

    public String getInet6() {
        return inet6;
    }

    public PveLxcInterfaceIpAddress[] getIpAddresses() {
        return ipAddresses;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "PveLxcInterface{" +
                "hardwareAddress='" + hardwareAddress + '\'' +
                ", hwaddr='" + hwaddr + '\'' +
                ", inet='" + inet + '\'' +
                ", inet6='" + inet6 + '\'' +
                ", ipAddresses=" + Arrays.toString(ipAddresses) +
                ", name='" + name + '\'' +
                '}';
    }
}
