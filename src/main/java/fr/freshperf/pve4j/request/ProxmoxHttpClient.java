package fr.freshperf.pve4j.request;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import fr.freshperf.pve4j.SecurityConfig;
import fr.freshperf.pve4j.throwable.ProxmoxAPIError;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509TrustManager;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.Socket;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpConnectTimeoutException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * HTTP client for making requests to the Proxmox VE API.
 * Handles authentication, SSL configuration, and JSON parsing.
 */
public class ProxmoxHttpClient {

    private static final Duration DEFAULT_REQUEST_TIMEOUT = Duration.ofSeconds(30);

    private final String apiToken;
    private final String ticket;
    private final String csrfToken;
    private HttpClient client;
    private final String baseUrl;
    private Gson gson;
    private ProxmoxResponseTransformer defaultTransformer;
    private volatile Duration requestTimeout = DEFAULT_REQUEST_TIMEOUT;

    /**
     * Returns the base URL for API requests.
     *
     * @return the base API URL
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * Returns the authentication ticket (if using ticket-based auth).
     *
     * @return the authentication ticket, or null if using API token
     */
    public String getTicket() {
        return ticket;
    }

    /**
     * Returns the per-request response timeout.
     *
     * @return the request timeout (default: 30 seconds)
     */
    public Duration getRequestTimeout() {
        return requestTimeout;
    }

    /**
     * Sets the per-request response timeout. If the server does not answer within
     * this duration, the request fails with a {@link ProxmoxAPIError} instead of
     * blocking indefinitely.
     *
     * @param requestTimeout the maximum time to wait for a response (must be positive)
     * @throws IllegalArgumentException if the timeout is null, zero or negative
     */
    public void setRequestTimeout(Duration requestTimeout) {
        if (requestTimeout == null || requestTimeout.isZero() || requestTimeout.isNegative()) {
            throw new IllegalArgumentException("Request timeout must be positive");
        }
        this.requestTimeout = requestTimeout;
    }

    /**
     * Creates a new HTTP client with API token authentication and default security settings.
     *
     * @param baseUrl  the base API URL
     * @param apiToken the API token for authentication
     */
    public ProxmoxHttpClient(String baseUrl, String apiToken) {
        this(baseUrl, apiToken, SecurityConfig.secure());
    }

    /**
     * Creates a new HTTP client with API token authentication.
     *
     * @param baseUrl        the base API URL
     * @param apiToken       the API token for authentication
     * @param securityConfig SSL/TLS security configuration
     */
    public ProxmoxHttpClient(String baseUrl, String apiToken, SecurityConfig securityConfig) {
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
        this.apiToken = apiToken;
        this.ticket = null;
        this.csrfToken = null;
        initializeClient(securityConfig);
    }

    private ProxmoxHttpClient(String baseUrl, SecurityConfig securityConfig, boolean unauthenticated) {
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
        this.apiToken = null;
        this.ticket = null;
        this.csrfToken = null;
        initializeClient(securityConfig);
    }

    /**
     * Creates an unauthenticated HTTP client (for login requests).
     *
     * @param baseUrl        the base API URL
     * @param securityConfig SSL/TLS security configuration
     * @return a new unauthenticated HTTP client
     */
    public static ProxmoxHttpClient createUnauthenticated(String baseUrl, SecurityConfig securityConfig) {
        return new ProxmoxHttpClient(baseUrl, securityConfig, true);
    }

    /**
     * Initializes the HTTP client with the given security configuration.
     *
     * @param securityConfig SSL/TLS security configuration
     */
    private void initializeClient(SecurityConfig securityConfig) {
        HttpClient.Builder clientBuilder = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10));

        boolean verifyCertificate = securityConfig.shouldVerifySslCertificate();
        boolean verifyHostname = securityConfig.shouldVerifyHostname();

        if (!verifyCertificate || !verifyHostname) {
            try {
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, selectTrustManagers(verifyCertificate, verifyHostname), new SecureRandom());
                clientBuilder.sslContext(sslContext);
            } catch (GeneralSecurityException e) {
                throw new RuntimeException("Failed to create SSL context", e);
            }
        }

        this.client = clientBuilder.build();
        this.gson = new Gson();
        this.defaultTransformer = new ProxmoxResponseTransformer();
    }

    /*
     * java.net.http.HttpClient always enables endpoint identification ("HTTPS") on its
     * connections and overrides any SSLParameters that try to unset it; the only other
     * escape hatch, the jdk.internal.httpclient.disableHostnameVerification system
     * property, is read once per JVM and affects every HttpClient in the process.
     * The identity check itself is performed by the trust manager, which is per-client
     * state, so hostname verification is controlled here:
     *  - a plain X509TrustManager is wrapped by JSSE in a wrapper that enforces the
     *    hostname check on top of whatever the delegate accepts;
     *  - an X509ExtendedTrustManager is used as-is and is solely responsible for both
     *    chain validation and endpoint identification;
     *  - the default trust manager's channel-less checkServerTrusted(chain, authType)
     *    validates the chain but never checks endpoint identity.
     */
    private static TrustManager[] selectTrustManagers(boolean verifyCertificate, boolean verifyHostname)
            throws GeneralSecurityException {
        if (!verifyCertificate && verifyHostname) {
            // Plain (non-extended) trust-all manager: JSSE's wrapper still performs the
            // hostname check against the presented certificate.
            return new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }
            };
        }

        if (!verifyCertificate) {
            // Extended trust-all manager: neither the chain nor the hostname is verified,
            // for this client only.
            return new TrustManager[]{
                new X509ExtendedTrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] certs, String authType, Socket socket) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String authType, Socket socket) {
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] certs, String authType, SSLEngine engine) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String authType, SSLEngine engine) {
                    }
                }
            };
        }

        // Certificate verified, hostname not: delegate chain validation to the platform
        // default trust manager through its channel-less methods.
        X509TrustManager defaultTm = defaultTrustManager();
        return new TrustManager[]{
            new X509ExtendedTrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return defaultTm.getAcceptedIssuers();
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
                    defaultTm.checkClientTrusted(certs, authType);
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
                    defaultTm.checkServerTrusted(certs, authType);
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType, Socket socket) throws CertificateException {
                    defaultTm.checkClientTrusted(certs, authType);
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType, Socket socket) throws CertificateException {
                    defaultTm.checkServerTrusted(certs, authType);
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType, SSLEngine engine) throws CertificateException {
                    defaultTm.checkClientTrusted(certs, authType);
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType, SSLEngine engine) throws CertificateException {
                    defaultTm.checkServerTrusted(certs, authType);
                }
            }
        };
    }

    private static X509TrustManager defaultTrustManager() throws GeneralSecurityException {
        TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        factory.init((KeyStore) null);
        for (TrustManager trustManager : factory.getTrustManagers()) {
            if (trustManager instanceof X509TrustManager x509TrustManager) {
                return x509TrustManager;
            }
        }
        throw new GeneralSecurityException("No X509TrustManager available from the default TrustManagerFactory");
    }

    /**
     * Creates a new HTTP client with ticket-based authentication.
     *
     * @param baseUrl        the base API URL
     * @param ticket         the authentication ticket
     * @param csrfToken      the CSRF prevention token
     * @param securityConfig SSL/TLS security configuration
     */
    public ProxmoxHttpClient(String baseUrl, String ticket, String csrfToken, SecurityConfig securityConfig) {
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
        this.apiToken = null;
        this.ticket = ticket;
        this.csrfToken = csrfToken;
        initializeClient(securityConfig);
    }

    /**
     * Creates a GET request builder.
     *
     * @param path the API path (relative to base URL)
     * @return a new request builder
     */
    public RequestBuilder get(String path) {
        return new RequestBuilder(this, "GET", path);
    }

    /**
     * Creates a POST request builder.
     *
     * @param path the API path (relative to base URL)
     * @return a new request builder
     */
    public RequestBuilder post(String path) {
        return new RequestBuilder(this, "POST", path);
    }

    /**
     * Creates a PUT request builder.
     *
     * @param path the API path (relative to base URL)
     * @return a new request builder
     */
    public RequestBuilder put(String path) {
        return new RequestBuilder(this, "PUT", path);
    }

    /**
     * Creates a PATCH request builder.
     *
     * @param path the API path (relative to base URL)
     * @return a new request builder
     */
    public RequestBuilder patch(String path) {
        return new RequestBuilder(this, "PATCH", path);
    }

    /**
     * Creates a DELETE request builder.
     *
     * @param path the API path (relative to base URL)
     * @return a new request builder
     */
    public RequestBuilder delete(String path) {
        return new RequestBuilder(this, "DELETE", path);
    }

    /**
     * Executes a request and deserializes the response to the given class.
     */
    <T> T execute(RequestBuilder builder, Class<T> clazz) throws ProxmoxAPIError, InterruptedException {
        return executeRequest(builder, clazz);
    }

    /**
     * Executes a request and deserializes the response to a parameterized type.
     */
    <T> T executeList(RequestBuilder builder, TypeToken<T> typeToken) throws ProxmoxAPIError, InterruptedException {
        return executeRequestWithType(builder, typeToken);
    }
    
    private <T> T executeRequest(RequestBuilder builder, Class<T> clazz) throws ProxmoxAPIError, InterruptedException {
        String url = buildUrl(builder.path, builder.params);
        Duration timeout = requestTimeout;

        try {
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(timeout)
                    .header("Content-Type", "application/json");
            
            if (apiToken != null) {
                requestBuilder.header("Authorization", "PVEAPIToken=" + apiToken);
            } else if (ticket != null) {
                requestBuilder.header("Cookie", "PVEAuthCookie=" + ticket);
                if (csrfToken != null && (builder.method.equals("POST") || builder.method.equals("PUT") || 
                    builder.method.equals("PATCH") || builder.method.equals("DELETE"))) {
                    requestBuilder.header("CSRFPreventionToken", csrfToken);
                }
            }

            switch (builder.method) {
                case "GET" -> requestBuilder.GET();
                case "POST" -> requestBuilder.POST(HttpRequest.BodyPublishers.ofString(builder.body != null ? builder.body : "{}"));
                case "PUT" -> requestBuilder.PUT(HttpRequest.BodyPublishers.ofString(builder.body != null ? builder.body : "{}"));
                case "PATCH" -> requestBuilder.method("PATCH", HttpRequest.BodyPublishers.ofString(builder.body != null ? builder.body : "{}"));
                case "DELETE" -> requestBuilder.DELETE();
            }

            HttpResponse<String> response = client.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() >= 400) {
                throw new ProxmoxAPIError(
                    "HTTP request failed",
                    response.statusCode(),
                    response.body(),
                    url
                );
            }
            
            JsonElement parsedResponse;
            try {
                parsedResponse = JsonParser.parseString(response.body());
            } catch (JsonSyntaxException e) {
                throw new ProxmoxAPIError(
                    "Failed to parse JSON response: " + e.getMessage(),
                    response.statusCode(),
                    response.body(),
                    url
                );
            }
            JsonElement dataElement = extractDataFromResponse(parsedResponse);
            
            ResponseTransformer transformer = builder.transformer != null ? builder.transformer : defaultTransformer;
            JsonElement transformed = transformer.transform(dataElement, clazz);
            
            return gson.fromJson(transformed, clazz);

        } catch (HttpConnectTimeoutException e) {
            throw new ProxmoxAPIError("HTTP connection timed out: " + url, e);
        } catch (HttpTimeoutException e) {
            throw new ProxmoxAPIError("HTTP request timed out after " + timeout + ": " + url, e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw e;
        } catch (Exception e) {
            if (e instanceof ProxmoxAPIError) {
                throw (ProxmoxAPIError) e;
            }
            throw new ProxmoxAPIError("Network error: " + e.getMessage(), e);
        }
    }
    
    private <T> T executeRequestWithType(RequestBuilder builder, TypeToken<T> typeToken) throws ProxmoxAPIError, InterruptedException {
        String url = buildUrl(builder.path, builder.params);
        Duration timeout = requestTimeout;

        try {
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(timeout)
                    .header("Content-Type", "application/json");
            
            if (apiToken != null) {
                requestBuilder.header("Authorization", "PVEAPIToken=" + apiToken);
            } else if (ticket != null) {
                requestBuilder.header("Cookie", "PVEAuthCookie=" + ticket);
                if (csrfToken != null && (builder.method.equals("POST") || builder.method.equals("PUT") || 
                    builder.method.equals("PATCH") || builder.method.equals("DELETE"))) {
                    requestBuilder.header("CSRFPreventionToken", csrfToken);
                }
            }

            switch (builder.method) {
                case "GET" -> requestBuilder.GET();
                case "POST" -> requestBuilder.POST(HttpRequest.BodyPublishers.ofString(builder.body != null ? builder.body : "{}"));
                case "PUT" -> requestBuilder.PUT(HttpRequest.BodyPublishers.ofString(builder.body != null ? builder.body : "{}"));
                case "PATCH" -> requestBuilder.method("PATCH", HttpRequest.BodyPublishers.ofString(builder.body != null ? builder.body : "{}"));
                case "DELETE" -> requestBuilder.DELETE();
            }

            HttpResponse<String> response = client.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() >= 400) {
                throw new ProxmoxAPIError(
                    "HTTP request failed",
                    response.statusCode(),
                    response.body(),
                    url
                );
            }
            
            JsonElement parsedResponse;
            try {
                parsedResponse = JsonParser.parseString(response.body());
            } catch (JsonSyntaxException e) {
                throw new ProxmoxAPIError(
                    "Failed to parse JSON response: " + e.getMessage(),
                    response.statusCode(),
                    response.body(),
                    url
                );
            }
            JsonElement dataElement = extractDataFromResponse(parsedResponse);
            
            Class<?> elementClass = extractElementClass(typeToken);
            
            ResponseTransformer transformer = builder.transformer != null ? builder.transformer : defaultTransformer;
            JsonElement transformed = transformer.transform(dataElement, elementClass);
            
            return gson.fromJson(transformed, typeToken.getType());

        } catch (HttpConnectTimeoutException e) {
            throw new ProxmoxAPIError("HTTP connection timed out: " + url, e);
        } catch (HttpTimeoutException e) {
            throw new ProxmoxAPIError("HTTP request timed out after " + timeout + ": " + url, e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw e;
        } catch (Exception e) {
            if (e instanceof ProxmoxAPIError) {
                throw (ProxmoxAPIError) e;
            }
            throw new ProxmoxAPIError("Network error: " + e.getMessage(), e);
        }
    }
    
    private Class<?> extractElementClass(TypeToken<?> typeToken) {
        Type type = typeToken.getType();
        
        if (type instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) type;
            Type[] typeArgs = paramType.getActualTypeArguments();
            
            if (typeArgs.length > 0 && typeArgs[0] instanceof Class) {
                return (Class<?>) typeArgs[0];
            }
        }
        
        return typeToken.getRawType();
    }

    private JsonElement extractDataFromResponse(JsonElement jsonElement) {
        if (jsonElement.isJsonObject()) {
            JsonObject obj = jsonElement.getAsJsonObject();
            if (obj.has("data")) {
                JsonElement data = obj.get("data");
                if (!data.isJsonNull()) {
                    return data;
                }
            }
        }
        return jsonElement;
    }

    private String buildUrl(String path, Map<String, Object> params) {
        StringBuilder url = new StringBuilder(baseUrl + path);
        if (!params.isEmpty()) {
            String query = params.entrySet().stream()
                    .map(e -> URLEncoder.encode(e.getKey(), StandardCharsets.UTF_8)
                            + "=" + URLEncoder.encode(e.getValue().toString(), StandardCharsets.UTF_8))
                    .collect(Collectors.joining("&"));
            url.append("?").append(query);
        }
        return url.toString();
    }

    /**
     * Builder for constructing and executing HTTP requests.
     */
    public static class RequestBuilder {
        private final ProxmoxHttpClient client;
        private final String method;
        private final String path;
        private final Map<String, Object> params;
        private String body;
        private ResponseTransformer transformer;

        RequestBuilder(ProxmoxHttpClient client, String method, String path) {
            this.client = client;
            this.method = method;
            this.path = path;
            this.params = new HashMap<>();
        }

        /**
         * Adds a query parameter to the request.
         *
         * @param key   the parameter name
         * @param value the parameter value
         * @return this builder for chaining
         */
        public RequestBuilder param(String key, Object value) {
            this.params.put(key, value);
            return this;
        }

        /**
         * Adds multiple query parameters to the request.
         *
         * @param params map of parameter names to values
         * @return this builder for chaining
         */
        public RequestBuilder params(Map<String, Object> params) {
            this.params.putAll(params);
            return this;
        }

        /**
         * Sets the JSON body for the request.
         *
         * @param json the JSON string body
         * @return this builder for chaining
         */
        public RequestBuilder body(String json) {
            this.body = json;
            return this;
        }

        /**
         * Sets a custom response transformer.
         *
         * @param transformer the transformer to use
         * @return this builder for chaining
         */
        public RequestBuilder transformer(ResponseTransformer transformer) {
            this.transformer = transformer;
            return this;
        }

        /**
         * Executes the request and returns the raw JSON response.
         *
         * @return the JSON response object
         * @throws ProxmoxAPIError    if the API returns an error
         * @throws InterruptedException if the request is interrupted
         */
        public JsonObject execute() throws ProxmoxAPIError, InterruptedException {
            return client.execute(this, JsonObject.class);
        }

        /**
         * Executes the request and deserializes the response.
         *
         * @param clazz the class to deserialize to
         * @param <T>   the response type
         * @return the deserialized response
         * @throws ProxmoxAPIError    if the API returns an error
         * @throws InterruptedException if the request is interrupted
         */
        public <T> T execute(Class<T> clazz) throws ProxmoxAPIError, InterruptedException {
            return client.execute(this, clazz);
        }

        /**
         * Executes the request and deserializes to a parameterized type (e.g., List).
         *
         * @param typeToken the type token for deserialization
         * @param <T>       the response type
         * @return the deserialized response
         * @throws ProxmoxAPIError    if the API returns an error
         * @throws InterruptedException if the request is interrupted
         */
        public <T> T executeList(TypeToken<T> typeToken) throws ProxmoxAPIError, InterruptedException {
            return client.executeList(this, typeToken);
        }
    }
}
