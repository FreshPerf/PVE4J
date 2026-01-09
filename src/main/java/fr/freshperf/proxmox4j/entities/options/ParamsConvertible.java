package fr.freshperf.proxmox4j.entities.options;

import java.util.Map;

/**
 * Interface for objects that can be converted to API request parameters.
 * Implementing classes should override {@link #populateParams(Map)} to add their specific parameters.
 */
public interface ParamsConvertible {

    /**
     * Converts this object to a map of API parameters.
     * 
     * @return Map of parameter names to values
     */
    default Map<String, Object> toParams() {
        Map<String, Object> params = new java.util.HashMap<>();
        populateParams(params);
        return params;
    }

    /**
     * Populates the provided map with this object's parameters.
     * Implementations should use {@link fr.freshperf.proxmox4j.util.ParamsHelpers} 
     * to conditionally add parameters.
     * 
     * @param params The map to populate with parameters
     */
    void populateParams(Map<String, Object> params);
}

