package fr.freshperf.pve4j.entities.cluster.firewall;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a predefined firewall macro (SSH, HTTP, Ping, ...)
 * returned by {@code GET /cluster/firewall/macros}.
 */
public class PveFirewallMacro {

    @SerializedName("macro")
    private String macro;

    @SerializedName("descr")
    private String description;

    /**
     * Gets the macro name, usable in the {@code macro} property of a firewall rule.
     *
     * @return the macro name
     */
    public String getMacro() {
        return macro;
    }

    /**
     * Gets the verbose description of the macro.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "PveFirewallMacro{" +
                "macro='" + macro + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
