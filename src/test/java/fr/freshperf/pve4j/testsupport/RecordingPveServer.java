package fr.freshperf.pve4j.testsupport;

import com.sun.net.httpserver.HttpServer;
import fr.freshperf.pve4j.request.ProxmoxHttpClient;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Minimal local HTTP server standing in for the Proxmox API in tests.
 * Every request is answered with {@code {"data": <body>}} and recorded so tests can assert
 * on the method, raw path and parameters (query string and form body merged) that were sent.
 */
public final class RecordingPveServer implements AutoCloseable {

    /**
     * A request received by the server.
     *
     * @param method  the HTTP method
     * @param rawPath the raw (still percent-encoded) request path
     * @param params  query string and form body parameters, decoded
     */
    public record Recorded(String method, String rawPath, Map<String, String> params) {
    }

    private final HttpServer server;
    private final List<Recorded> requests = new CopyOnWriteArrayList<>();
    private volatile String data = "null";

    public RecordingPveServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/", exchange -> {
            Map<String, String> params = new HashMap<>();
            parseForm(exchange.getRequestURI().getRawQuery(), params);
            parseForm(new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8), params);
            requests.add(new Recorded(exchange.getRequestMethod(), exchange.getRequestURI().getRawPath(), params));

            byte[] body = ("{\"data\":" + data + "}").getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, body.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(body);
            }
        });
        server.start();
    }

    private static void parseForm(String raw, Map<String, String> into) {
        if (raw == null || raw.isEmpty() || raw.startsWith("{")) {
            return;
        }
        for (String pair : raw.split("&")) {
            int eq = pair.indexOf('=');
            String key = eq < 0 ? pair : pair.substring(0, eq);
            String value = eq < 0 ? "" : pair.substring(eq + 1);
            into.put(URLDecoder.decode(key, StandardCharsets.UTF_8), URLDecoder.decode(value, StandardCharsets.UTF_8));
        }
    }

    /**
     * Sets the JSON placed in the {@code data} field of every following response.
     *
     * @param json the JSON value
     * @return this server
     */
    public RecordingPveServer respondWith(String json) {
        this.data = json;
        return this;
    }

    /**
     * Creates an API-token client pointing at this server.
     *
     * @return the client
     */
    public ProxmoxHttpClient client() {
        return new ProxmoxHttpClient(baseUrl(), "user@pam!test=00000000-0000-0000-0000-000000000000");
    }

    /**
     * Returns the API base URL ({@code http://127.0.0.1:port/api2/json/}).
     *
     * @return the base URL
     */
    public String baseUrl() {
        return "http://127.0.0.1:" + server.getAddress().getPort() + "/api2/json/";
    }

    /**
     * Returns the last recorded request.
     *
     * @return the last request
     */
    public Recorded last() {
        return requests.get(requests.size() - 1);
    }

    @Override
    public void close() {
        server.stop(0);
    }
}
