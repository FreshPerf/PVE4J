package fr.freshperf.pve4j.entities.access;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Reads Proxmox booleans sent either as 0/1 or as true/false.
 * Needed for fields nested in maps, which the response transformer cannot convert.
 */
final class NumericBooleanAdapter extends TypeAdapter<Boolean> {

    @Override
    public void write(JsonWriter out, Boolean value) throws IOException {
        out.value(value);
    }

    @Override
    public Boolean read(JsonReader in) throws IOException {
        JsonToken token = in.peek();
        return switch (token) {
            case NULL -> {
                in.nextNull();
                yield null;
            }
            case BOOLEAN -> in.nextBoolean();
            case NUMBER -> in.nextDouble() != 0;
            default -> {
                String value = in.nextString();
                yield value.equals("1") || value.equalsIgnoreCase("true");
            }
        };
    }
}
