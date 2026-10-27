package fr.freshperf.pve4j.entities.access;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads a list that Proxmox sends either as a JSON array or as a comma-separated string
 * (e.g. a user's groups: an array for one user, a string in the users list).
 */
final class StringListAdapter extends TypeAdapter<String[]> {

    @Override
    public void write(JsonWriter out, String[] value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        out.beginArray();
        for (String item : value) {
            out.value(item);
        }
        out.endArray();
    }

    @Override
    public String[] read(JsonReader in) throws IOException {
        JsonToken token = in.peek();
        if (token == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        if (token == JsonToken.BEGIN_ARRAY) {
            List<String> items = new ArrayList<>();
            in.beginArray();
            while (in.hasNext()) {
                items.add(in.nextString());
            }
            in.endArray();
            return items.toArray(new String[0]);
        }
        String value = in.nextString();
        return value.isEmpty() ? new String[0] : value.split(",");
    }
}
