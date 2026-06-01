package fr.freshperf.pve4j.entities.nodes.node.qemu.firewall.aliases;

import com.google.gson.reflect.TypeToken;
import fr.freshperf.pve4j.request.ProxmoxHttpClient;
import fr.freshperf.pve4j.request.ProxmoxRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Facade for managing firewall IP/Network aliases of a QEMU VM.
 */
public record PveQemuFirewallAliases(ProxmoxHttpClient client, String nodeName, int vmid) {

    private String path(String suffix) {
        return "nodes/" + nodeName + "/qemu/" + vmid + "/firewall" + suffix;
    }

    private static void requireNotBlank(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Lists all firewall aliases for this VM.
     *
     * @return a request returning the list of aliases
     */
    public ProxmoxRequest<List<PveQemuFirewallAlias>> list() {
        return new ProxmoxRequest<>(() ->
            client.get(path("/aliases"))
                .executeList(new TypeToken<List<PveQemuFirewallAlias>>() {})
        );
    }

    /**
     * Reads a specific firewall alias by name.
     *
     * @param name the alias name
     * @return a request returning the alias
     * @throws IllegalArgumentException if name is null or blank
     */
    public ProxmoxRequest<PveQemuFirewallAlias> get(String name) {
        requireNotBlank(name, "name cannot be null or blank");
        return new ProxmoxRequest<>(() ->
            client.get(path("/aliases/" + name))
                .execute(PveQemuFirewallAlias.class)
        );
    }

    /**
     * Creates a new firewall alias.
     *
     * @param name    the alias name (required)
     * @param cidr    the network/IP specification in CIDR format (required)
     * @param comment an optional descriptive comment, or null
     * @return a request that completes when the alias is created
     * @throws IllegalArgumentException if name or cidr is null or blank
     */
    public ProxmoxRequest<Void> create(String name, String cidr, String comment) {
        requireNotBlank(name, "name cannot be null or blank");
        requireNotBlank(cidr, "cidr cannot be null or blank");
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("cidr", cidr);
        if (comment != null && !comment.isBlank()) {
            params.put("comment", comment);
        }
        return new ProxmoxRequest<>(() -> {
            client.post(path("/aliases"))
                .params(params)
                .execute();
            return null;
        });
    }

    /**
     * Updates an existing firewall alias.
     *
     * @param name    the alias name
     * @param cidr    the network/IP specification in CIDR format (required by the API)
     * @param options additional options (comment, rename, digest), or null
     * @return a request that completes when the alias is updated
     * @throws IllegalArgumentException if name or cidr is null or blank
     */
    public ProxmoxRequest<Void> update(String name, String cidr, PveQemuFirewallAliasUpdateOptions options) {
        requireNotBlank(name, "name cannot be null or blank");
        requireNotBlank(cidr, "cidr cannot be null or blank");
        PveQemuFirewallAliasUpdateOptions effective = options != null ? options : PveQemuFirewallAliasUpdateOptions.builder();
        Map<String, Object> params = effective.toParams(cidr);
        return new ProxmoxRequest<>(() -> {
            client.put(path("/aliases/" + name))
                .params(params)
                .execute();
            return null;
        });
    }

    /**
     * Deletes a firewall alias.
     *
     * @param name the alias name
     * @return a request that completes when the alias is deleted
     * @throws IllegalArgumentException if name is null or blank
     */
    public ProxmoxRequest<Void> delete(String name) {
        return delete(name, null);
    }

    /**
     * Deletes a firewall alias, optionally guarding against concurrent modifications.
     *
     * @param name   the alias name
     * @param digest optional configuration digest for conflict detection, or null
     * @return a request that completes when the alias is deleted
     * @throws IllegalArgumentException if name is null or blank
     */
    public ProxmoxRequest<Void> delete(String name, String digest) {
        requireNotBlank(name, "name cannot be null or blank");
        Map<String, Object> params = new HashMap<>();
        if (digest != null && !digest.isBlank()) {
            params.put("digest", digest);
        }
        return new ProxmoxRequest<>(() -> {
            client.delete(path("/aliases/" + name))
                .params(params)
                .execute();
            return null;
        });
    }
}
