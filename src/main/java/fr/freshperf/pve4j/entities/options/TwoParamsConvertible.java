package fr.freshperf.pve4j.entities.options;

import java.util.HashMap;
import java.util.Map;

/**
 * Interface for objects that can be converted to API request parameters 
 * with two required parameters.
 * 
 * @param <T1> The type of the first required parameter
 * @param <T2> The type of the second required parameter
 */
public interface TwoParamsConvertible<T1, T2> {

    /**
     * Converts this object to a map of API parameters, including the required parameters.
     * 
     * @param param1 The first required parameter
     * @param param2 The second required parameter
     * @return Map of parameter names to values
     */
    default Map<String, Object> toParams(T1 param1, T2 param2) {
        Map<String, Object> params = new HashMap<>();
        addRequiredParams(params, param1, param2);
        populateParams(params);
        return params;
    }

    /**
     * Adds the required parameters to the params map.
     * 
     * @param params The map to add the required parameters to
     * @param param1 The first required parameter value
     * @param param2 The second required parameter value
     */
    void addRequiredParams(Map<String, Object> params, T1 param1, T2 param2);

    /**
     * Populates the provided map with this object's optional parameters.
     * Implementations should use {@link fr.freshperf.pve4j.util.ParamsHelpers}
     * to conditionally add parameters.
     * 
     * @param params The map to populate with parameters
     */
    void populateParams(Map<String, Object> params);
}

