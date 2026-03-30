package fr.freshperf.pve4j.entities.nodes.node.qemu.agent.networkInterface;

import com.google.gson.annotations.SerializedName;

public class PveQemuAgentNetworkStatistics {
    @SerializedName("rx-Errs")
    private int rxErrs;
    @SerializedName("tx-Errs")
    private int txErrs;
    @SerializedName("rx-Dropped")
    private int rxDropped;
    @SerializedName("tx-Dropped")
    private int txDropped;
    @SerializedName("rx-Packets")
    private int rxPackets;
    @SerializedName("tx-Packets")
    private int txPackets;
    @SerializedName("rx-Bytes")
    private int rxBytes;
    @SerializedName("tx-Bytes")
    private int txBytes;

    public int getRxErrs() { return rxErrs; }
    public int getTxErrs() { return txErrs; }
    public int getRxDropped() { return rxDropped; }
    public int getTxDropped() { return txDropped; }
    public int getRxPackets() { return rxPackets; }
    public int getTxPackets() { return txPackets; }
    public int getRxBytes() { return rxBytes; }
    public int getTxBytes() { return txBytes; }

    @Override
    public String toString() {
        return "PveQemuAgentNetworkStatistics{"
                + "rxErrs=" + rxErrs
                + ", txErrs=" + txErrs
                + ", rxDropped=" + rxDropped
                + ", txDropped=" + txDropped
                + ", rxPackets=" + rxPackets
                + ", txPackets=" + txPackets
                + ", rxBytes=" + rxBytes
                + ", txBytes=" + txBytes + '}';
    }
}
