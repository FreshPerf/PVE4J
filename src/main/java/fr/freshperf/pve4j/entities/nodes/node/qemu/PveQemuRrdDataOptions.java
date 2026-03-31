package fr.freshperf.pve4j.entities.nodes.node.qemu;

import fr.freshperf.pve4j.entities.options.ParameterizedParamsConvertible;
import fr.freshperf.pve4j.util.ParamsHelpers;

import java.util.Map;
import java.util.Set;

/**
 * Options for reading QEMU VM RRD data.
 */
public class PveQemuRrdDataOptions implements ParameterizedParamsConvertible<String> {

    private static final Set<String> ALLOWED_TIMEFRAMES = Set.of("hour", "day", "week", "month", "year");
    private static final Set<String> ALLOWED_CF = Set.of("AVERAGE", "MAX");

    private String cf;

    /**
     * Creates a new builder for RRD data options.
     *
     * @return a new PveQemuRrdDataOptions instance
     */
    public static PveQemuRrdDataOptions builder() {
        return new PveQemuRrdDataOptions();
    }

    @Override
    public void addRequiredParam(Map<String, Object> params, String timeframe) {
        if (timeframe == null || timeframe.isBlank()) {
            throw new IllegalArgumentException("timeframe cannot be null or empty");
        }
        if (!ALLOWED_TIMEFRAMES.contains(timeframe)) {
            throw new IllegalArgumentException("timeframe must be one of: " + String.join(", ", ALLOWED_TIMEFRAMES));
        }
        params.put("timeframe", timeframe);
    }

    @Override
    public void populateParams(Map<String, Object> params) {
        ParamsHelpers.put(params, "cf", cf);
    }

    /** Sets the RRD consolidation function. */
    public PveQemuRrdDataOptions cf(String cf) {
        if (cf != null && !cf.isBlank() && !ALLOWED_CF.contains(cf)) {
            throw new IllegalArgumentException("cf must be one of: " + String.join(", ", ALLOWED_CF));
        }
        this.cf = cf;
        return this;
    }
}
