package fr.freshperf.proxmox4j.util;

import java.util.Map;

public class ParamsHelpers {

    public static void put(Map<String, Object> params, String key, String value) {
        if (value != null && !value.isBlank()) {
            params.put(key, value);
        }
    }

    public static void putBool(Map<String, Object> params, String key, Boolean value) {
        if (value != null) {
            params.put(key, value ? "1" : "0");
        }
    }

    public static void putInt(Map<String, Object> params, String key, Integer value) {
        if (value != null) {
            params.put(key, String.valueOf(value));
        }
    }

    public static void putDouble(Map<String, Object> params, String key, Double value) {
        if (value != null) {
            params.put(key, String.valueOf(value));
        }
    }

    public static void putIndexed(Map<Integer, String> target, int index, String value) {
        if (index < 0) {
            throw new IllegalArgumentException("index must be >= 0");
        }
        if (value != null && !value.isBlank()) {
            target.put(index, value);
        }
    }
}
