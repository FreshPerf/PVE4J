package fr.freshperf.pve4j.entities.options;

import java.util.HashMap;
import java.util.Map;

/**
 * Interface for objects that can be converted to API request parameters 
 * with one required parameter.
 * 
 * @param <T> The type of the required parameter
 */
public interface ParameterizedParamsConvertible<T> {

    /**
     * Converts this object to a map of API parameters, including the required parameter.
     * 
     * @param requiredParam The required parameter to include
     * @return Map of parameter names to values
     */
    default Map<String, Object> toParams(T requiredParam) {
        Map<String, Object> params = new HashMap<>();
        addRequiredParam(params, requiredParam);
        populateParams(params);
        return params;
    }

    /**
     * Adds the required parameter to the params map.
     * 
     * @param params The map to add the required parameter to
     * @param requiredParam The required parameter value
     */
    void addRequiredParam(Map<String, Object> params, T requiredParam);

    /**
     * Populates the provided map with this object's optional parameters.
     * Implementations should use {@link fr.freshperf.pve4j.util.ParamsHelpers}
     * to conditionally add parameters.
     * 
     * @param params The map to populate with parameters
     */
    void populateParams(Map<String, Object> params);
}

