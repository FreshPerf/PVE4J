package fr.freshperf.pve4j.testsupport;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import fr.freshperf.pve4j.request.ProxmoxResponseTransformer;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Deserializes the {@code data} part of an API response the same way ProxmoxHttpClient does:
 * the default response transformer first, then Gson. Also loads the real
 * {@code pvesh get ... --output-format json} captures kept under {@code src/test/resources/fixtures}.
 */
public final class PveJson {

    private PveJson() {
    }

    public static <T> T parse(String json, Class<T> clazz) {
        return parse(JsonParser.parseString(json), clazz);
    }

    public static <T> T parse(JsonElement json, Class<T> clazz) {
        return new Gson().fromJson(transform(json, clazz), clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> parseList(String json, Class<T> clazz) {
        return (List<T>) new Gson().fromJson(transform(JsonParser.parseString(json), clazz),
                TypeToken.getParameterized(List.class, clazz).getType());
    }

    /**
     * Reads a fixture from the test classpath.
     *
     * @param path the path below {@code /fixtures/}, e.g. {@code qemu/status-current-running.json}
     * @return the fixture content
     */
    public static String fixture(String path) {
        try (InputStream in = PveJson.class.getResourceAsStream("/fixtures/" + path)) {
            if (in == null) {
                throw new IllegalArgumentException("missing fixture " + path);
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static JsonObject fixtureObject(String path) {
        return JsonParser.parseString(fixture(path)).getAsJsonObject();
    }

    private static JsonElement transform(JsonElement json, Class<?> clazz) {
        return new ProxmoxResponseTransformer().transform(json, clazz);
    }
}
