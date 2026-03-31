package fr.freshperf.pve4j.entities.nodes.node.qemu.agent.hostname;

import com.google.gson.annotations.SerializedName;

/**
 * Represents the hostname reported by the QEMU guest agent.
 */
public class PveQemuAgentHostName {
    @SerializedName("host-name")
    private String hostName;

    public String getHostName() {
        return hostName;
    }

    @Override
    public String toString() {
        return "PveQemuAgentHostName{" +
                "hostName='" + hostName + '\'' +
                '}';
    }
}
