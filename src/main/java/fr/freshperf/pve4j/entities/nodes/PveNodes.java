package fr.freshperf.pve4j.entities.nodes;

import com.google.gson.reflect.TypeToken;
import fr.freshperf.pve4j.entities.nodes.node.PveNode;
import fr.freshperf.pve4j.request.ProxmoxHttpClient;
import fr.freshperf.pve4j.request.ProxmoxRequest;

import java.util.List;

/**
 * Facade for Proxmox nodes management endpoints.
 */
public record PveNodes(ProxmoxHttpClient client) {

    /**
     * Lists all nodes in the cluster.
     *
     * @return a request returning the list of nodes
     */
    public ProxmoxRequest<List<PveNodesIndex>> getIndex() {
        return new ProxmoxRequest<>(() -> client.get("nodes/").executeList(new TypeToken<List<PveNodesIndex>>(){}));
    }

    /**
     * Gets a specific node by name.
     *
     * @param nodeName the node name
     * @return the node API facade
     * @throws IllegalArgumentException if nodeName is null or empty
     */
    public PveNode get(String nodeName) {
        if(nodeName == null || nodeName.isEmpty()) {
            throw new IllegalArgumentException("Node name can't be null or empty.");
        }
        return new PveNode(client, nodeName);
    }
}
