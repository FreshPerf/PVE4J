package fr.freshperf.pve4j.entities.nodes.node.qemu;

import com.google.gson.reflect.TypeToken;
import fr.freshperf.pve4j.request.ProxmoxHttpClient;
import fr.freshperf.pve4j.request.ProxmoxRequest;

import java.util.List;
import java.util.Set;

/**
 * Facade for managing cloud-init operations on a QEMU VM.
 */
public class PveQemuCloudInit {

    private static final Set<String> ALLOWED_DUMP_TYPES = Set.of("user", "network", "meta");

    private final ProxmoxHttpClient client;
    private final String nodeName;
    private final int vmid;

    /**
     * Creates a new cloud-init facade.
     *
     * @param client   the HTTP client
     * @param nodeName the node name
     * @param vmid     the VM ID
     */
    public PveQemuCloudInit(ProxmoxHttpClient client, String nodeName, int vmid) {
        this.client = client;
        this.nodeName = nodeName;
        this.vmid = vmid;
    }

    /**
     * Gets the cloud-init configuration with current and pending values.
     *
     * @return a request returning the cloud-init entries
     */
    public ProxmoxRequest<List<PveQemuPendingChange>> getConfig() {
        return new ProxmoxRequest<>(() ->
            client.get("nodes/" + nodeName + "/qemu/" + vmid + "/cloudinit")
                .executeList(new TypeToken<List<PveQemuPendingChange>>() {})
        );
    }

    /**
     * Dumps the generated cloud-init content.
     *
     * @param type the dump type (user, network, meta)
     * @return a request returning the generated content
     */
    public ProxmoxRequest<String> dump(String type) {
        validateDumpType(type);

        return new ProxmoxRequest<>(() ->
            client.get("nodes/" + nodeName + "/qemu/" + vmid + "/cloudinit/dump")
                .param("type", type)
                .execute(String.class)
        );
    }

    /**
     * Regenerates the cloud-init config drive.
     *
     * @return a request that completes when regeneration is done
     */
    public ProxmoxRequest<Void> update() {
        return new ProxmoxRequest<>(() -> {
            client.put("nodes/" + nodeName + "/qemu/" + vmid + "/cloudinit")
                .execute();
            return null;
        });
    }

    private static void validateDumpType(String type) {
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("type cannot be null or empty");
        }
        if (!ALLOWED_DUMP_TYPES.contains(type)) {
            throw new IllegalArgumentException("type must be one of: " + String.join(", ", ALLOWED_DUMP_TYPES));
        }
    }
}
