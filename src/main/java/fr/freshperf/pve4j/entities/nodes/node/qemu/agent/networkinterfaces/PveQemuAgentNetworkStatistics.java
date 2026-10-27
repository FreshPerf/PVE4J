package fr.freshperf.pve4j.entities.nodes.node.qemu.agent.networkinterfaces;

import com.google.gson.annotations.SerializedName;

/**
 * Represents network statistics for a guest agent network interface.
 */
public class PveQemuAgentNetworkStatistics {
    @SerializedName("rx-errs")
    private long rxErrs;
    @SerializedName("tx-errs")
    private long txErrs;
    @SerializedName("rx-dropped")
    private long rxDropped;
    @SerializedName("tx-dropped")
    private long txDropped;
    @SerializedName("rx-packets")
    private long rxPackets;
    @SerializedName("tx-packets")
    private long txPackets;
    @SerializedName("rx-bytes")
    private long rxBytes;
    @SerializedName("tx-bytes")
    private long txBytes;

    public long getRxErrs() { return rxErrs; }
    public long getTxErrs() { return txErrs; }
    public long getRxDropped() { return rxDropped; }
    public long getTxDropped() { return txDropped; }
    public long getRxPackets() { return rxPackets; }
    public long getTxPackets() { return txPackets; }
    public long getRxBytes() { return rxBytes; }
    public long getTxBytes() { return txBytes; }

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
