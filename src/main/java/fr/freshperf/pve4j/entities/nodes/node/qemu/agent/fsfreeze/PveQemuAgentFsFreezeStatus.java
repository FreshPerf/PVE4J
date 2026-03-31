package fr.freshperf.pve4j.entities.nodes.node.qemu.agent.fsfreeze;

import com.google.gson.annotations.SerializedName;

/**
 * Enumerates filesystem freeze states reported by the guest agent.
 */
public enum PveQemuAgentFsFreezeStatus {
    @SerializedName("thawed")
    THAWED,
    @SerializedName("frozen")
    FROZEN
}
