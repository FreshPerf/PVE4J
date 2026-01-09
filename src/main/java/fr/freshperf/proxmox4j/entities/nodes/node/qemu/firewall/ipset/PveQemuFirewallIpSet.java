package fr.freshperf.proxmox4j.entities.nodes.node.qemu.firewall.ipset;

import com.google.gson.reflect.TypeToken;
import fr.freshperf.proxmox4j.entities.PveTask;
import fr.freshperf.proxmox4j.request.ProxmoxHttpClient;
import fr.freshperf.proxmox4j.request.ProxmoxRequest;
import fr.freshperf.proxmox4j.request.TaskResponseTransformer;

import java.util.List;
import java.util.Map;

/**
 * Facade for managing firewall IP sets for a QEMU VM.
 */
public record PveQemuFirewallIpSet (ProxmoxHttpClient client, String nodeName, int vmid) {

    private String path(String suffix) {
        return "nodes/" + nodeName + "/qemu/" + vmid + "/firewall" + suffix;
    }

    private static void requireNotBlank(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Lists all firewall IP sets for this VM.
     *
     * @return a request returning the list of IP sets
     */
    public ProxmoxRequest<List<PveQemuFirewallIpSetEntry>> list() {
        return new ProxmoxRequest<>(() ->
            client.get(path("/ipset"))
                .executeList(new TypeToken<List<PveQemuFirewallIpSetEntry>>() {})
        );
    }

    /**
     * Creates a firewall IP set.
     *
     * @param name    the IP set name
     * @param options creation options or null
     * @return a request returning the task for tracking
     */
    public ProxmoxRequest<PveTask> create(String name, PveQemuFirewallIpSetCreateOptions options) {
        requireNotBlank(name, "name cannot be null or blank");
        Map<String, Object> params = options != null ? options.toParams(name) : Map.of("name", name);
        return new ProxmoxRequest<>(() ->
            client.post(path("/ipset"))
                .params(params)
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    /**
     * Renames a firewall IP set.
     *
     * @param currentName the current IP set name
     * @param newName     the new name
     * @param options     additional options or null
     * @return a request returning the task for tracking
     */
    public ProxmoxRequest<PveTask> rename(String currentName, String newName, PveQemuFirewallIpSetCreateOptions options) {
        requireNotBlank(currentName, "currentName cannot be null or blank");
        requireNotBlank(newName, "newName cannot be null or blank");
        PveQemuFirewallIpSetCreateOptions effective = options != null ? options : PveQemuFirewallIpSetCreateOptions.builder();
        effective.rename(newName);
        Map<String, Object> params = effective.toParams(currentName);
        return new ProxmoxRequest<>(() ->
            client.post(path("/ipset"))
                .params(params)
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    /**
     * Deletes a firewall IP set.
     *
     * @param name  the IP set name
     * @param force true to force deletion even with members
     * @return a request returning the task for tracking
     */
    public ProxmoxRequest<PveTask> delete(String name, Boolean force) {
        requireNotBlank(name, "name cannot be null or blank");
        Map<String, Object> params = force != null ? Map.of("force", force ? "1" : "0") : Map.of();
        return new ProxmoxRequest<>(() ->
            client.delete(path("/ipset/" + name))
                .params(params)
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    /**
     * Lists members of a firewall IP set.
     *
     * @param name the IP set name
     * @return a request returning the list of members
     */
    public ProxmoxRequest<List<PveQemuFirewallIpSetMember>> listMembers(String name) {
        requireNotBlank(name, "name cannot be null or blank");
        return new ProxmoxRequest<>(() ->
            client.get(path("/ipset/" + name))
                .executeList(new TypeToken<List<PveQemuFirewallIpSetMember>>() {})
        );
    }

    /**
     * Adds a CIDR entry to a firewall IP set.
     *
     * @param name    the IP set name
     * @param cidr    the CIDR to add (e.g., "192.168.1.0/24")
     * @param options additional options or null
     * @return a request returning the task for tracking
     */
    public ProxmoxRequest<PveTask> addMember(String name, String cidr, PveQemuFirewallIpSetMemberCreateOptions options) {
        requireNotBlank(name, "name cannot be null or blank");
        requireNotBlank(cidr, "cidr cannot be null or blank");
        Map<String, Object> params = options != null ? options.toParams(cidr) : Map.of("cidr", cidr);
        return new ProxmoxRequest<>(() ->
            client.post(path("/ipset/" + name))
                .params(params)
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    /**
     * Gets a specific IP set member.
     *
     * @param name the IP set name
     * @param cidr the CIDR to get
     * @return a request returning the member details
     */
    public ProxmoxRequest<PveQemuFirewallIpSetMember> getMember(String name, String cidr) {
        requireNotBlank(name, "name cannot be null or blank");
        requireNotBlank(cidr, "cidr cannot be null or blank");
        return new ProxmoxRequest<>(() ->
            client.get(path("/ipset/" + name + "/" + cidr))
                .execute(PveQemuFirewallIpSetMember.class)
        );
    }

    /**
     * Updates a specific IP set member.
     *
     * @param name    the IP set name
     * @param cidr    the CIDR to update
     * @param options update options
     * @return a request returning the task for tracking
     */
    public ProxmoxRequest<PveTask> updateMember(String name, String cidr, PveQemuFirewallIpSetMemberUpdateOptions options) {
        requireNotBlank(name, "name cannot be null or blank");
        requireNotBlank(cidr, "cidr cannot be null or blank");
        if (options == null) {
            throw new IllegalArgumentException("options cannot be null");
        }
        return new ProxmoxRequest<>(() ->
            client.put(path("/ipset/" + name + "/" + cidr))
                .params(options.toParams(cidr))
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }

    /**
     * Deletes a specific IP set member.
     *
     * @param name   the IP set name
     * @param cidr   the CIDR to delete
     * @param digest optional digest for conflict detection
     * @return a request returning the task for tracking
     */
    public ProxmoxRequest<PveTask> deleteMember(String name, String cidr, String digest) {
        requireNotBlank(name, "name cannot be null or blank");
        requireNotBlank(cidr, "cidr cannot be null or blank");
        Map<String, Object> params = digest != null && !digest.isBlank() ? Map.of("digest", digest) : Map.of();
        return new ProxmoxRequest<>(() ->
            client.delete(path("/ipset/" + name + "/" + cidr))
                .params(params)
                .transformer(new TaskResponseTransformer())
                .execute(PveTask.class)
        );
    }
}
