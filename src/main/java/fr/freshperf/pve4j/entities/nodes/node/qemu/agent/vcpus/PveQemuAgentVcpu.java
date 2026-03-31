package fr.freshperf.pve4j.entities.nodes.node.qemu.agent.vcpus;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a guest vCPU entry reported by the guest agent.
 */
public class PveQemuAgentVcpu {
    @SerializedName("can-offline")
    private Boolean canOffline;
    @SerializedName("logical-id")
    private Integer logicalId;
    private Boolean online;

    public Boolean getCanOffline() {
        return canOffline;
    }

    public Integer getLogicalId() {
        return logicalId;
    }

    public Boolean getOnline() {
        return online;
    }

    @Override
    public String toString() {
        return "PveQemuAgentVcpu{" +
                "canOffline=" + canOffline +
                ", logicalId=" + logicalId +
                ", online=" + online +
                '}';
    }
}
