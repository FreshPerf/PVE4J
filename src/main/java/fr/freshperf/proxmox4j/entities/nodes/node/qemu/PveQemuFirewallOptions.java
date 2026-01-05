package fr.freshperf.proxmox4j.entities.nodes.node.qemu;

import com.google.gson.annotations.SerializedName;

public class PveQemuFirewallOptions {

    private int dhcp;
    private int enable;
    private int ipfilter;
    @SerializedName("log_level_in")
    private String logLevelIn;
    @SerializedName("log_level_out")
    private String logLevelOut;
    private int macfilter;
    private int ndp;
    @SerializedName("policy_in")
    private String policyIn;
    @SerializedName("policy_out")
    private String policyOut;
    private int radv;
    private String digest;

    public int getDhcp() {
        return dhcp;
    }

    public int getEnable() {
        return enable;
    }

    public int getIpfilter() {
        return ipfilter;
    }

    public String getLogLevelIn() {
        return logLevelIn;
    }

    public String getLogLevelOut() {
        return logLevelOut;
    }

    public int getMacfilter() {
        return macfilter;
    }

    public int getNdp() {
        return ndp;
    }

    public String getPolicyIn() {
        return policyIn;
    }

    public String getPolicyOut() {
        return policyOut;
    }

    public int getRadv() {
        return radv;
    }

    public String getDigest() {
        return digest;
    }

    @Override
    public String toString() {
        return "PveQemuFirewallOptions{" +
                "dhcp=" + dhcp +
                ", enable=" + enable +
                ", ipfilter=" + ipfilter +
                ", logLevelIn='" + logLevelIn + '\'' +
                ", logLevelOut='" + logLevelOut + '\'' +
                ", macfilter=" + macfilter +
                ", ndp=" + ndp +
                ", policyIn='" + policyIn + '\'' +
                ", policyOut='" + policyOut + '\'' +
                ", radv=" + radv +
                ", digest='" + digest + '\'' +
                '}';
    }
}

