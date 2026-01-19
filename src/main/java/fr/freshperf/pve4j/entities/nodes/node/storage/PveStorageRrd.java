package fr.freshperf.pve4j.entities.nodes.node.storage;

/**
 * Represents RRD (Round Robin Database) statistics for a storage.
 */
public class PveStorageRrd {

    // RRD data is typically returned as a dynamic structure
    // This is a placeholder for RRD graph data
    private Object[] data;

    public Object[] getData() {
        return data;
    }

    @Override
    public String toString() {
        return "PveStorageRrd{" +
                "data=" + (data != null ? data.length + " entries" : "null") +
                '}';
    }
}

