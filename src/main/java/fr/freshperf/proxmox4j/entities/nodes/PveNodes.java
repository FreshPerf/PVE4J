package fr.freshperf.proxmox4j.entities.nodes;

import com.google.gson.reflect.TypeToken;
import fr.freshperf.proxmox4j.entities.nodes.node.PveNodeIndex;
import fr.freshperf.proxmox4j.request.ProxmoxHttpClient;
import fr.freshperf.proxmox4j.request.ProxmoxRequest;

import java.util.List;

public record PveNodes(ProxmoxHttpClient client) {

    public ProxmoxRequest<List<PveNodesIndex>> getIndex() {
        return new ProxmoxRequest<>(() -> client.get("nodes/").executeList(new TypeToken<List<PveNodesIndex>>(){}));
    }

    public ProxmoxRequest<List<PveNodeIndex>> get(String nodeName) {
        if(nodeName == null || nodeName.isEmpty()) {
            throw new IllegalArgumentException("Node name can't be null or empty.");
        }
        return new ProxmoxRequest<>(() -> client.get("nodes/"+nodeName).executeList(new TypeToken<List<PveNodeIndex>>(){}));
    }
}
