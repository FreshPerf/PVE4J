package fr.freshperf.pve4j.entities.nodes.node.qemu.agent.info;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * Represents general guest agent information.
 */
public class PveQemuAgentInfo {
    @SerializedName("supported_commands")
    private PveQemuAgentSupportedCommand[] supportedCommands;
    private String version;

    public PveQemuAgentSupportedCommand[] getSupportedCommands() {
        return supportedCommands;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "PveQemuAgentInfo{" +
                "supportedCommands=" + Arrays.toString(supportedCommands) +
                ", version='" + version + '\'' +
                '}';
    }
}
