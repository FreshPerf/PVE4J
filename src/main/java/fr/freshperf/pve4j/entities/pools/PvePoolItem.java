package fr.freshperf.pve4j.entities.pools;

import com.google.gson.reflect.TypeToken;
import fr.freshperf.pve4j.request.ProxmoxHttpClient;
import fr.freshperf.pve4j.request.ProxmoxRequest;

import java.util.List;

/**
 * Facade for managing a specific resource pool.
 */
public class PvePoolItem {

    private final ProxmoxHttpClient client;
    private final String poolid;

    /**
     * Creates a new pool item facade.
     *
     * @param client the HTTP client
     * @param poolid the pool ID
     */
    public PvePoolItem(ProxmoxHttpClient client, String poolid) {
        this.client = client;
        this.poolid = poolid;
    }

    /*
     * Nested pool IDs ("parent/child") cannot be used as a path segment: pveproxy unescapes the
     * path before routing, so they only work with the /pools endpoints taking a poolid parameter
     * (PVE 8.1+). Flat IDs keep using the /pools/{poolid} endpoints, which older versions require.
     */
    private boolean isNested() {
        return poolid.contains("/");
    }

    /**
     * Gets detailed pool information including members.
     *
     * @return a request returning the pool details
     */
    public ProxmoxRequest<PvePoolDetails> getDetails() {
        return getDetails(null);
    }

    /**
     * Gets detailed pool information with specific member type.
     *
     * @param type the member type to filter ("qemu", "lxc", "storage"), or null for all
     * @return a request returning the pool details
     */
    public ProxmoxRequest<PvePoolDetails> getDetails(String type) {
        return new ProxmoxRequest<>(() -> {
            if (isNested()) {
                var builder = client.get("pools").param("poolid", poolid);
                if (type != null) {
                    builder.param("type", type);
                }
                List<PvePoolDetails> pools = builder.executeList(new TypeToken<List<PvePoolDetails>>() {});
                return pools == null || pools.isEmpty() ? null : pools.get(0);
            }
            var builder = client.get("pools/" + poolid);
            if (type != null) {
                builder.param("type", type);
            }
            return builder.execute(PvePoolDetails.class);
        });
    }

    /**
     * Updates the pool configuration.
     *
     * @param options the update options
     * @return a request that completes when the update is done
     * @throws IllegalArgumentException if options is null
     */
    public ProxmoxRequest<Void> update(PvePoolUpdateOptions options) {
        if (options == null) {
            throw new IllegalArgumentException("options cannot be null");
        }
        return new ProxmoxRequest<>(() -> {
            var builder = isNested()
                ? client.put("pools").param("poolid", poolid)
                : client.put("pools/" + poolid);
            builder.params(options.toParams())
                .execute();
            return null;
        });
    }

    /**
     * Adds VMs to this pool.
     *
     * @param vmids the VMIDs to add
     * @return a request that completes when the update is done
     */
    public ProxmoxRequest<Void> addVms(List<Integer> vmids) {
        return update(PvePoolUpdateOptions.builder().vms(vmids).delete(false));
    }

    /**
     * Removes VMs from this pool.
     *
     * @param vmids the VMIDs to remove
     * @return a request that completes when the update is done
     */
    public ProxmoxRequest<Void> removeVms(List<Integer> vmids) {
        return update(PvePoolUpdateOptions.builder().vms(vmids).delete(true));
    }

    /**
     * Adds storage to this pool.
     *
     * @param storageNames the storage names to add
     * @return a request that completes when the update is done
     */
    public ProxmoxRequest<Void> addStorage(List<String> storageNames) {
        return update(PvePoolUpdateOptions.builder().storage(storageNames).delete(false));
    }

    /**
     * Removes storage from this pool.
     *
     * @param storageNames the storage names to remove
     * @return a request that completes when the update is done
     */
    public ProxmoxRequest<Void> removeStorage(List<String> storageNames) {
        return update(PvePoolUpdateOptions.builder().storage(storageNames).delete(true));
    }

    /**
     * Deletes this pool.
     *
     * @return a request that completes when the deletion is done
     */
    public ProxmoxRequest<Void> delete() {
        return new ProxmoxRequest<>(() -> {
            var builder = isNested()
                ? client.delete("pools").param("poolid", poolid)
                : client.delete("pools/" + poolid);
            builder.execute();
            return null;
        });
    }
}

