package fr.freshperf.pve4j.request;

import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import fr.freshperf.pve4j.Proxmox;
import fr.freshperf.pve4j.SecurityConfig;
import fr.freshperf.pve4j.entities.PveTask;
import fr.freshperf.pve4j.entities.PveVersion;
import fr.freshperf.pve4j.throwable.ProxmoxAPIError;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.GZIPOutputStream;

import static org.assertj.core.api.Assertions.*;

@DisplayName("ProxmoxHttpClient Tests")
class ProxmoxHttpClientTest {

    private static final String VERSION_JSON =
            "{\"data\":{\"version\":\"9.1.4\",\"release\":\"9.1\",\"repoid\":\"5ac30304265fbd8e\"}}";

    private HttpServer server;
    private String baseUrl;
    private final List<Map<String, String>> requestHeaders = new CopyOnWriteArrayList<>();
    private final Map<String, Responder> routes = new ConcurrentHashMap<>();
    private final CountDownLatch releaseHandlers = new CountDownLatch(1);

    @FunctionalInterface
    interface Responder {
        void respond(HttpExchange exchange) throws IOException;
    }

    @BeforeEach
    void startServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/", exchange -> {
            Map<String, String> headers = new ConcurrentHashMap<>();
            exchange.getRequestHeaders().forEach((k, v) -> headers.put(k.toLowerCase(), String.join(",", v)));
            headers.put(":path", exchange.getRequestURI().getRawPath());
            headers.put(":method", exchange.getRequestMethod());
            headers.put(":query", String.valueOf(exchange.getRequestURI().getRawQuery()));
            headers.put(":body", new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8));
            headers.put(":port", String.valueOf(exchange.getRemoteAddress().getPort()));
            requestHeaders.add(headers);
            // pveproxy collapses duplicate slashes (e.g. "/version" appended to the base URL)
            Responder responder = routes.get(exchange.getRequestURI().getRawPath().replaceAll("/{2,}", "/"));
            if (responder == null) {
                send(exchange, 404, "{\"data\":null}", false);
            } else {
                responder.respond(exchange);
            }
        });
        server.start();
        baseUrl = "http://127.0.0.1:" + server.getAddress().getPort() + "/api2/json/";
    }

    @AfterEach
    void stopServer() {
        releaseHandlers.countDown();
        server.stop(0);
    }

    private static void send(HttpExchange exchange, int status, String body, boolean gzip) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json;charset=UTF-8");
        if (gzip) {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            try (GZIPOutputStream out = new GZIPOutputStream(buffer)) {
                out.write(bytes);
            }
            bytes = buffer.toByteArray();
            exchange.getResponseHeaders().set("Content-Encoding", "gzip");
        }
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream out = exchange.getResponseBody()) {
            out.write(bytes);
        }
    }

    @Test
    @DisplayName("Should not request compression by default")
    void shouldNotRequestCompressionByDefault() throws Exception {
        routes.put("/api2/json/version", ex -> send(ex, 200, VERSION_JSON, false));
        ProxmoxHttpClient client = new ProxmoxHttpClient(baseUrl, "user@pam!t=secret");

        client.get("version").execute(PveVersion.class);

        assertThat(client.isCompressionEnabled()).isFalse();
        assertThat(requestHeaders).singleElement()
                .satisfies(h -> assertThat(h).doesNotContainKey("accept-encoding"));
    }

    @Test
    @DisplayName("Should advertise gzip support when compression is enabled")
    void shouldAdvertiseGzipWhenEnabled() throws Exception {
        routes.put("/api2/json/version", ex -> send(ex, 200, VERSION_JSON, false));
        ProxmoxHttpClient client = new ProxmoxHttpClient(baseUrl, "user@pam!t=secret");
        client.setCompressionEnabled(true);

        client.get("version").execute(PveVersion.class);

        assertThat(requestHeaders).singleElement()
                .satisfies(h -> assertThat(h.get("accept-encoding")).isEqualTo("gzip"));
    }

    @Test
    @DisplayName("Should decode plain and gzip responses identically")
    void shouldDecodeGzipResponses() throws Exception {
        routes.put("/api2/json/plain", ex -> send(ex, 200, VERSION_JSON, false));
        routes.put("/api2/json/gzip", ex -> send(ex, 200, VERSION_JSON, true));
        ProxmoxHttpClient client = new ProxmoxHttpClient(baseUrl, "user@pam!t=secret");

        PveVersion plain = client.get("plain").execute(PveVersion.class);
        PveVersion gzip = client.get("gzip").execute(PveVersion.class);

        assertThat(gzip.getVersion()).isEqualTo("9.1.4");
        assertThat(gzip).usingRecursiveComparison().isEqualTo(plain);
    }

    @Test
    @DisplayName("Should decode non-ASCII characters in gzip responses")
    void shouldDecodeUtf8InGzipResponses() throws Exception {
        routes.put("/api2/json/utf8", ex -> send(ex, 200, "{\"data\":{\"comment\":\"Hôte été ✓\"}}", true));

        JsonObject result = new ProxmoxHttpClient(baseUrl, "user@pam!t=secret").get("utf8").execute();

        assertThat(result.get("comment").getAsString()).isEqualTo("Hôte été ✓");
    }

    @Test
    @DisplayName("Should expose the decoded body of a gzip error response")
    void shouldDecodeGzipErrorBody() {
        String errorBody = "{\"data\":null,\"errors\":{\"vmid\":\"invalid format\"}}";
        routes.put("/api2/json/error", ex -> send(ex, 400, errorBody, true));
        ProxmoxHttpClient client = new ProxmoxHttpClient(baseUrl, "user@pam!t=secret");

        assertThatThrownBy(() -> client.get("error").execute())
                .isInstanceOfSatisfying(ProxmoxAPIError.class, e -> {
                    assertThat(e.getStatusCode()).isEqualTo(400);
                    assertThat(e.getResponseBody()).isEqualTo(errorBody);
                });
    }

    @Test
    @DisplayName("Should handle empty gzip-labelled bodies")
    void shouldHandleEmptyBody() throws Exception {
        routes.put("/api2/json/empty", ex -> {
            ex.getResponseHeaders().set("Content-Encoding", "gzip");
            ex.sendResponseHeaders(200, -1);
            ex.close();
        });

        PveVersion result = new ProxmoxHttpClient(baseUrl, "user@pam!t=secret").get("empty").execute(PveVersion.class);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("Should reuse the login connection for ticket-authenticated requests")
    void shouldReuseLoginConnectionAfterPasswordLogin() throws Exception {
        routes.put("/api2/json/access/ticket", ex -> send(ex, 200,
                "{\"data\":{\"ticket\":\"PVE:root@pam:ABC\",\"CSRFPreventionToken\":\"CSRF1\",\"username\":\"root@pam\"}}", false));
        routes.put("/api2/json/version", ex -> send(ex, 200, VERSION_JSON, true));
        routes.put("/api2/json/nodes/pve/qemu/100/status/start", ex -> send(ex, 200,
                "{\"data\":\"UPID:pve:00001:00002:00003:qmstart:100:root@pam:\"}", false));

        Proxmox proxmox = Proxmox.createWithPassword("http://127.0.0.1", server.getAddress().getPort(),
                "root", "pw", SecurityConfig.secure());
        PveVersion version = proxmox.getVersion().execute();
        PveTask task = proxmox.getNodes().get("pve").getQemu().get(100).start().execute();

        assertThat(version.getVersion()).isEqualTo("9.1.4");
        assertThat(task.getNode()).isEqualTo("pve");
        assertThat(proxmox.getHttpClient().getTicket()).isEqualTo("PVE:root@pam:ABC");
        assertThat(proxmox.getHttpClient().getBaseUrl()).isEqualTo(baseUrl);

        Map<String, String> login = requestHeaders.get(0);
        assertThat(login.get(":method")).isEqualTo("POST");
        assertThat(login).doesNotContainKey("cookie");
        assertThat(login.get(":query")).isEqualTo("null");
        assertThat(login.get(":body")).contains("password=pw").contains("username=root");

        Map<String, String> get = requestHeaders.get(1);
        assertThat(get.get("cookie")).isEqualTo("PVEAuthCookie=PVE:root@pam:ABC");
        assertThat(get).doesNotContainKey("csrfpreventiontoken");

        Map<String, String> post = requestHeaders.get(2);
        assertThat(post.get(":method")).isEqualTo("POST");
        assertThat(post.get("cookie")).isEqualTo("PVEAuthCookie=PVE:root@pam:ABC");
        assertThat(post.get("csrfpreventiontoken")).isEqualTo("CSRF1");

        assertThat(requestHeaders).extracting(h -> h.get(":port")).containsOnly(login.get(":port"));
    }

    @Test
    @DisplayName("Should observe short tasks well before the check interval")
    void shouldObserveShortTaskBeforeInterval() throws Exception {
        String upid = "UPID:pve:00001:00002:00003:qmconfig:100:root@pam:";
        AtomicInteger polls = new AtomicInteger();
        routes.put("/api2/json/nodes/pve/tasks/" + upid + "/status", ex -> {
            String status = polls.incrementAndGet() < 2 ? "running" : "stopped";
            send(ex, 200, "{\"data\":{\"status\":\"" + status + "\",\"exitstatus\":\"OK\",\"upid\":\"" + upid + "\"}}", false);
        });
        Proxmox proxmox = Proxmox.create("http://127.0.0.1", server.getAddress().getPort(), "user@pam!t=secret");

        long start = System.nanoTime();
        ProxmoxRequest.waitForCompletion(proxmox, new PveTask(upid), Duration.ofSeconds(2), Duration.ofSeconds(30));
        Duration elapsed = Duration.ofNanos(System.nanoTime() - start);

        assertThat(polls.get()).isEqualTo(2);
        assertThat(elapsed).isLessThan(Duration.ofMillis(1500));
    }

    @Test
    @DisplayName("Should keep the full interval between retries after transient polling errors")
    void shouldWaitFullIntervalAfterTransientPollingErrors() throws Exception {
        String upid = "UPID:pve:00001:00002:00003:qmstart:100:root@pam:";
        AtomicInteger polls = new AtomicInteger();
        routes.put("/api2/json/nodes/pve/tasks/" + upid + "/status", ex -> {
            int poll = polls.incrementAndGet();
            if (poll <= 2) {
                send(ex, 503, "{\"data\":null}", false);
            } else {
                send(ex, 200, "{\"data\":{\"status\":\"stopped\",\"exitstatus\":\"OK\",\"upid\":\"" + upid + "\"}}", false);
            }
        });
        Proxmox proxmox = Proxmox.create("http://127.0.0.1", server.getAddress().getPort(), "user@pam!t=secret");

        long start = System.nanoTime();
        ProxmoxRequest.waitForCompletion(proxmox, new PveTask(upid), Duration.ofMillis(400), Duration.ofSeconds(30));
        Duration elapsed = Duration.ofNanos(System.nanoTime() - start);

        assertThat(polls.get()).isEqualTo(3);
        assertThat(elapsed).isGreaterThanOrEqualTo(Duration.ofMillis(800));
    }

    @Test
    @DisplayName("Should still report a failed task as a failure")
    void shouldReportFailedTaskAsFailure() {
        String upid = "UPID:pve:00001:00002:00003:qmstart:100:root@pam:";
        routes.put("/api2/json/nodes/pve/tasks/" + upid + "/status", ex -> send(ex, 200,
                "{\"data\":{\"status\":\"stopped\",\"exitstatus\":\"command failed\",\"upid\":\"" + upid + "\"}}", true));
        Proxmox proxmox = Proxmox.create("http://127.0.0.1", server.getAddress().getPort(), "user@pam!t=secret");

        assertThatThrownBy(() -> ProxmoxRequest.waitForCompletion(proxmox, new PveTask(upid)))
                .isInstanceOf(ProxmoxAPIError.class)
                .hasMessageContaining("command failed");
    }

    @Test
    @DisplayName("Should send POST and PUT parameters form-encoded in the body, not in the URL")
    void shouldSendWriteParamsInBody() throws Exception {
        routes.put("/api2/json/access/password", ex -> send(ex, 200, "{\"data\":null}", false));
        ProxmoxHttpClient client = new ProxmoxHttpClient(baseUrl, "user@pam!t=secret");

        client.put("access/password").param("userid", "bob@pve").param("password", "s3cr&t =é").execute();
        client.post("access/password").param("password", "x").execute();

        for (Map<String, String> request : requestHeaders) {
            assertThat(request.get(":query")).isEqualTo("null");
            assertThat(request.get("content-type")).isEqualTo("application/x-www-form-urlencoded");
        }
        Map<String, String> put = requestHeaders.get(0);
        assertThat(put.get(":method")).isEqualTo("PUT");
        assertThat(Map.of(put.get(":body").split("&")[0], 0, put.get(":body").split("&")[1], 0))
                .containsOnlyKeys("userid=bob%40pve", "password=s3cr%26t+%3D%C3%A9");
    }

    @Test
    @DisplayName("Should keep GET and DELETE parameters in the query string, a JSON body for empty writes and no PATCH body")
    void shouldKeepEncodingOfOtherRequests() throws Exception {
        routes.put("/api2/json/things", ex -> send(ex, 200, "{\"data\":null}", false));
        ProxmoxHttpClient client = new ProxmoxHttpClient(baseUrl, "user@pam!t=secret");

        client.get("things").param("type", "vm").execute();
        client.delete("things").param("purge", "1").execute();
        client.post("things").execute();
        client.patch("things").execute();

        assertThat(requestHeaders.get(0).get(":query")).isEqualTo("type=vm");
        assertThat(requestHeaders.get(1).get(":query")).isEqualTo("purge=1");
        assertThat(requestHeaders.get(1).get(":body")).isEmpty();
        assertThat(requestHeaders.get(2).get(":query")).isEqualTo("null");
        assertThat(requestHeaders.get(2).get(":body")).isEqualTo("{}");
        assertThat(requestHeaders.get(2).get("content-type")).isEqualTo("application/json");
        assertThat(requestHeaders.get(3).get(":body")).isEmpty();
    }

    @Test
    @DisplayName("Should keep the HTTP status of a corrupt gzip error body")
    void shouldKeepStatusOfCorruptGzipError() {
        routes.put("/api2/json/broken", ex -> {
            ex.getResponseHeaders().set("Content-Encoding", "gzip");
            byte[] bytes = "not gzip".getBytes(StandardCharsets.UTF_8);
            ex.sendResponseHeaders(503, bytes.length);
            try (OutputStream out = ex.getResponseBody()) {
                out.write(bytes);
            }
        });
        ProxmoxHttpClient client = new ProxmoxHttpClient(baseUrl, "user@pam!t=secret");

        assertThatThrownBy(() -> client.get("broken").execute())
                .isInstanceOfSatisfying(ProxmoxAPIError.class, e -> {
                    assertThat(e.getStatusCode()).isEqualTo(503);
                    assertThat(e.getResponseBody()).isEqualTo("not gzip");
                });
    }

    @Test
    @DisplayName("Should report a corrupt gzip success body as a decompression failure")
    void shouldReportCorruptGzipSuccessAsDecompressionFailure() {
        routes.put("/api2/json/broken", ex -> {
            ex.getResponseHeaders().set("Content-Encoding", "gzip");
            byte[] bytes = "not gzip".getBytes(StandardCharsets.UTF_8);
            ex.sendResponseHeaders(200, bytes.length);
            try (OutputStream out = ex.getResponseBody()) {
                out.write(bytes);
            }
        });
        ProxmoxHttpClient client = new ProxmoxHttpClient(baseUrl, "user@pam!t=secret");

        assertThatThrownBy(() -> client.get("broken").execute())
                .isInstanceOfSatisfying(ProxmoxAPIError.class, e -> {
                    assertThat(e.getStatusCode()).isEqualTo(200);
                    assertThat(e.getMessage()).contains("decompress");
                });
    }

    @Test
    @DisplayName("Should not report a response that does not match the target type as a network error")
    void shouldNotReportMappingErrorAsNetworkError() {
        routes.put("/api2/json/version", ex -> send(ex, 200, "{\"data\":[1,2]}", false));
        ProxmoxHttpClient client = new ProxmoxHttpClient(baseUrl, "user@pam!t=secret");

        assertThatThrownBy(() -> client.get("version").execute(PveVersion.class))
                .isInstanceOfSatisfying(ProxmoxAPIError.class, e -> {
                    assertThat(e.getMessage()).startsWith("Failed to map response to").doesNotContain("Network error");
                    assertThat(e.getStatusCode()).isEqualTo(200);
                    assertThat(e.getResponseBody()).isNull();
                    assertThat(e.getMessage()).doesNotContain("Response:");
                });
    }

    @Test
    @DisplayName("Should throw InterruptedException when interrupted while the body is being received")
    void shouldThrowInterruptedExceptionWhenInterruptedDuringBody() throws Exception {
        CountDownLatch bodyStarted = new CountDownLatch(1);
        routes.put("/api2/json/slow", ex -> {
            ex.sendResponseHeaders(200, 1000);
            OutputStream out = ex.getResponseBody();
            out.write("{\"data\":".getBytes(StandardCharsets.UTF_8));
            out.flush();
            bodyStarted.countDown();
            try {
                releaseHandlers.await(10, TimeUnit.SECONDS);
            } catch (InterruptedException ignored) {
            }
            ex.close();
        });
        ProxmoxHttpClient client = new ProxmoxHttpClient(baseUrl, "user@pam!t=secret");
        AtomicReference<Throwable> thrown = new AtomicReference<>();

        Thread caller = Thread.ofPlatform().start(() -> {
            try {
                client.get("slow").execute();
            } catch (Throwable t) {
                thrown.set(t);
            }
        });
        assertThat(bodyStarted.await(5, TimeUnit.SECONDS)).isTrue();
        Thread.sleep(100);
        caller.interrupt();
        caller.join(3000);

        assertThat(caller.isAlive()).isFalse();
        assertThat(thrown.get()).isInstanceOf(InterruptedException.class);
    }
}
