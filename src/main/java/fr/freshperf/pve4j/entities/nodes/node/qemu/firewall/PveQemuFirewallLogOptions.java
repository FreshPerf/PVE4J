package fr.freshperf.pve4j.entities.nodes.node.qemu.firewall;

import fr.freshperf.pve4j.entities.options.ParamsConvertible;
import fr.freshperf.pve4j.util.ParamsHelpers;

import java.util.Map;

/**
 * Options for reading the QEMU VM firewall log.
 * Use the builder pattern to configure pagination and time filtering.
 */
public class PveQemuFirewallLogOptions implements ParamsConvertible {

    private Integer start;
    private Integer limit;
    private Integer since;
    private Integer until;

    /**
     * Creates a new builder for firewall log options.
     *
     * @return a new PveQemuFirewallLogOptions instance
     */
    public static PveQemuFirewallLogOptions builder() {
        return new PveQemuFirewallLogOptions();
    }

    @Override
    public void populateParams(Map<String, Object> params) {
        ParamsHelpers.putInt(params, "start", start);
        ParamsHelpers.putInt(params, "limit", limit);
        ParamsHelpers.putInt(params, "since", since);
        ParamsHelpers.putInt(params, "until", until);
    }

    /**
     * Sets the offset of the first log line to return.
     *
     * @param start the start offset (>= 0)
     * @return this instance for method chaining
     */
    public PveQemuFirewallLogOptions start(Integer start) { this.start = start; return this; }

    /**
     * Sets the maximum number of log lines to return.
     *
     * @param limit the limit (>= 0)
     * @return this instance for method chaining
     */
    public PveQemuFirewallLogOptions limit(Integer limit) { this.limit = limit; return this; }

    /**
     * Displays the log since this UNIX epoch.
     *
     * @param since the UNIX epoch (>= 0)
     * @return this instance for method chaining
     */
    public PveQemuFirewallLogOptions since(Integer since) { this.since = since; return this; }

    /**
     * Displays the log until this UNIX epoch.
     *
     * @param until the UNIX epoch (>= 0)
     * @return this instance for method chaining
     */
    public PveQemuFirewallLogOptions until(Integer until) { this.until = until; return this; }
}
