package fr.freshperf.pve4j.request;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * A transformer that wraps a string UPID response into a PveTask object structure.
 * Used when Proxmox returns a task ID as a simple string instead of an object.
 */
public class TaskResponseTransformer implements ResponseTransformer {

    /**
     * Transforms a string UPID into a JSON object with an "upid" field.
     *
     * @param jsonElement the original JSON element (may be a string UPID)
     * @param targetClass the target class (typically PveTask)
     * @return a JSON object with the UPID, or the original element if not a string
     */
    @Override
    public JsonElement transform(JsonElement jsonElement, Class<?> targetClass) {
        // If the response is a string (UPID), wrap it in an object
        if (jsonElement != null && jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isString()) {
            JsonObject wrapper = new JsonObject();
            wrapper.add("upid", jsonElement);
            return wrapper;
        }

        // Otherwise, return as-is
        return jsonElement;
    }
}

