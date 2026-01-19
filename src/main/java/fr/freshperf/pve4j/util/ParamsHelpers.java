package fr.freshperf.pve4j.util;

import java.util.Map;

/**
 * Helper methods for building API request parameters.
 * All methods conditionally add parameters only when values are non-null/non-blank.
 */
public class ParamsHelpers {

    /**
     * Adds a string parameter if not null or blank.
     *
     * @param params the parameter map
     * @param key    the parameter name
     * @param value  the parameter value
     */
    public static void put(Map<String, Object> params, String key, String value) {
        if (value != null && !value.isBlank()) {
            params.put(key, value);
        }
    }

    /**
     * Adds a boolean parameter as "1" or "0" if not null.
     *
     * @param params the parameter map
     * @param key    the parameter name
     * @param value  the boolean value
     */
    public static void putBool(Map<String, Object> params, String key, Boolean value) {
        if (value != null) {
            params.put(key, value ? "1" : "0");
        }
    }

    /**
     * Adds an integer parameter if not null.
     *
     * @param params the parameter map
     * @param key    the parameter name
     * @param value  the integer value
     */
    public static void putInt(Map<String, Object> params, String key, Integer value) {
        if (value != null) {
            params.put(key, String.valueOf(value));
        }
    }

    /**
     * Adds a double parameter if not null.
     *
     * @param params the parameter map
     * @param key    the parameter name
     * @param value  the double value
     */
    public static void putDouble(Map<String, Object> params, String key, Double value) {
        if (value != null) {
            params.put(key, String.valueOf(value));
        }
    }

    /**
     * Adds an indexed value to a map if not null or blank.
     *
     * @param target the target map (index to value)
     * @param index  the index (must be >= 0)
     * @param value  the value
     * @throws IllegalArgumentException if index is negative
     */
    public static void putIndexed(Map<Integer, String> target, int index, String value) {
        if (index < 0) {
            throw new IllegalArgumentException("index must be >= 0");
        }
        if (value != null && !value.isBlank()) {
            target.put(index, value);
        }
    }
}
