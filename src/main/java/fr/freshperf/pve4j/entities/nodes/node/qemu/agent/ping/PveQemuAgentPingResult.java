package fr.freshperf.pve4j.entities.nodes.node.qemu.agent.ping;

import com.google.gson.JsonElement;

/**
 * Wraps the guest agent ping response.
 */
public class PveQemuAgentPingResult {
    private JsonElement result;

    public JsonElement getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "PveQemuAgentPingResult{" +
                "result=" + result +
                '}';
    }
}
