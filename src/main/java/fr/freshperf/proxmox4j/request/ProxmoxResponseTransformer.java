package fr.freshperf.proxmox4j.request;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.HashMap;

/**
 * Default response transformer that handles Proxmox API response formatting.
 * Converts numeric boolean values (0/1) to actual booleans based on target class fields.
 */
public class ProxmoxResponseTransformer implements ResponseTransformer {

    private static final ConcurrentHashMap<Class<?>, ClassFieldCache> CLASS_CACHE = new ConcurrentHashMap<>();

    private static class ClassFieldCache {
        final Map<String, FieldInfo> fieldsByJsonName;

        ClassFieldCache(Map<String, FieldInfo> fieldsByJsonName) {
            this.fieldsByJsonName = fieldsByJsonName;
        }
    }

    private static class FieldInfo {
        final boolean isBoolean;
        final Class<?> fieldType;

        FieldInfo(boolean isBoolean, Class<?> fieldType) {
            this.isBoolean = isBoolean;
            this.fieldType = fieldType;
        }
    }

    /**
     * Transforms JSON elements, converting numeric booleans to actual booleans.
     *
     * @param jsonElement the JSON element to transform
     * @param targetClass the target class for field type detection
     * @return the transformed JSON element
     */
    @Override
    public JsonElement transform(JsonElement jsonElement, Class<?> targetClass) {
        if (jsonElement == null || jsonElement.isJsonNull()) {
            return jsonElement;
        }

        if (targetClass != null && targetClass != Object.class && targetClass != JsonObject.class) {
            ensureClassCached(targetClass);
        }

        if (jsonElement.isJsonObject()) {
            return transformObject(jsonElement.getAsJsonObject(), targetClass);
        }

        if (jsonElement.isJsonArray()) {
            return transformArray(jsonElement.getAsJsonArray(), targetClass);
        }

        if (jsonElement.isJsonPrimitive()) {
            return jsonElement;
        }

        return jsonElement;
    }

    private void ensureClassCached(Class<?> clazz) {
        CLASS_CACHE.computeIfAbsent(clazz, this::analyzeClass);
    }

    private ClassFieldCache analyzeClass(Class<?> clazz) {
        Map<String, FieldInfo> fieldsByJsonName = new HashMap<>();
        
        Class<?> currentClass = clazz;
        while (currentClass != null && currentClass != Object.class) {
            for (Field field : currentClass.getDeclaredFields()) {
                Class<?> fieldType = field.getType();
                boolean isBoolean = fieldType == boolean.class || fieldType == Boolean.class;
                
                fieldsByJsonName.putIfAbsent(field.getName(), new FieldInfo(isBoolean, fieldType));
                
                SerializedName annotation = field.getAnnotation(SerializedName.class);
                if (annotation != null) {
                    fieldsByJsonName.putIfAbsent(annotation.value(), new FieldInfo(isBoolean, fieldType));
                }
            }
            currentClass = currentClass.getSuperclass();
        }
        
        return new ClassFieldCache(fieldsByJsonName);
    }

    private JsonElement transformObject(JsonObject jsonObject, Class<?> targetClass) {
        JsonObject transformed = new JsonObject();
        
        Set<Map.Entry<String, JsonElement>> entries = jsonObject.entrySet();
        for (Map.Entry<String, JsonElement> entry : entries) {
            String key = entry.getKey();
            JsonElement value = entry.getValue();
            
            if (value.isJsonPrimitive()) {
                JsonPrimitive primitive = value.getAsJsonPrimitive();
                if (primitive.isNumber()) {
                    Number number = primitive.getAsNumber();
                    if (isBooleanField(key, targetClass)) {
                        transformed.add(key, new JsonPrimitive(number.intValue() == 1));
                    } else {
                        transformed.add(key, value);
                    }
                } else {
                    transformed.add(key, value);
                }
            } else if (value.isJsonObject()) {
                Class<?> nestedClass = getFieldType(key, targetClass);
                transformed.add(key, transform(value, nestedClass != null ? nestedClass : Object.class));
            } else if (value.isJsonArray()) {
                transformed.add(key, transform(value, targetClass));
            } else {
                transformed.add(key, value);
            }
        }
        
        return transformed;
    }

    private JsonElement transformArray(JsonArray jsonArray, Class<?> targetClass) {
        JsonArray transformed = new JsonArray();
        for (JsonElement element : jsonArray) {
            transformed.add(transform(element, targetClass));
        }
        return transformed;
    }

    private boolean isBooleanField(String fieldName, Class<?> targetClass) {
        if (targetClass == null || targetClass == Object.class || targetClass == JsonObject.class) {
            return false;
        }
        
        ClassFieldCache cache = CLASS_CACHE.get(targetClass);
        if (cache == null) {
            return false;
        }
        
        FieldInfo fieldInfo = cache.fieldsByJsonName.get(fieldName);
        return fieldInfo != null && fieldInfo.isBoolean;
    }

    private Class<?> getFieldType(String fieldName, Class<?> targetClass) {
        if (targetClass == null || targetClass == Object.class || targetClass == JsonObject.class) {
            return null;
        }
        
        ClassFieldCache cache = CLASS_CACHE.get(targetClass);
        if (cache == null) {
            return null;
        }
        
        FieldInfo fieldInfo = cache.fieldsByJsonName.get(fieldName);
        return fieldInfo != null ? fieldInfo.fieldType : null;
    }
}

