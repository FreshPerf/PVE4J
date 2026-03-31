package fr.freshperf.pve4j.entities.nodes.node.qemu.agent.info;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a guest agent command advertised in the info response.
 */
public class PveQemuAgentSupportedCommand {
    private boolean enabled;
    private String name;
    @SerializedName("success-response")
    private boolean successResponse;

    public boolean isEnabled() {
        return enabled;
    }

    public String getName() {
        return name;
    }

    public boolean isSuccessResponse() {
        return successResponse;
    }

    @Override
    public String toString() {
        return "PveQemuAgentSupportedCommand{" +
                "enabled=" + enabled +
                ", name='" + name + '\'' +
                ", successResponse=" + successResponse +
                '}';
    }
}
