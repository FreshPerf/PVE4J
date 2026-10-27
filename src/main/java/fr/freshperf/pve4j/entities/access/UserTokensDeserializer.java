package fr.freshperf.pve4j.entities.access;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Reads a user's API tokens, which Proxmox returns as an object keyed by token ID for a single
 * user and as an array of objects with a {@code tokenid} field in the users list (full=1).
 */
final class UserTokensDeserializer implements JsonDeserializer<Map<String, PveAccessUserToken>> {

    @Override
    public Map<String, PveAccessUserToken> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        if (json == null || json.isJsonNull()) {
            return null;
        }
        Map<String, PveAccessUserToken> tokens = new LinkedHashMap<>();
        if (json.isJsonArray()) {
            for (JsonElement element : json.getAsJsonArray()) {
                JsonObject token = element.getAsJsonObject();
                tokens.put(token.get("tokenid").getAsString(), context.deserialize(token, PveAccessUserToken.class));
            }
        } else {
            for (Map.Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
                tokens.put(entry.getKey(), context.deserialize(entry.getValue(), PveAccessUserToken.class));
            }
        }
        return tokens;
    }
}
