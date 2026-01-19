package fr.freshperf.pve4j.request;

import com.google.gson.JsonElement;

/**
 * Interface for transforming JSON responses before deserialization.
 */
public interface ResponseTransformer {

    /**
     * Transforms a JSON element before it is deserialized.
     *
     * @param jsonElement the original JSON element
     * @param targetClass the target class for deserialization
     * @return the transformed JSON element
     */
    JsonElement transform(JsonElement jsonElement, Class<?> targetClass);
}

