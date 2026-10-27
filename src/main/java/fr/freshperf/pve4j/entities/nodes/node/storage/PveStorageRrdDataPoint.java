package fr.freshperf.pve4j.entities.nodes.node.storage;

/**
 * Represents a storage RRD data point, as returned by
 * {@code GET /nodes/{node}/storage/{storage}/rrddata}.
 *
 * <p>Values are consolidated (averaged or maximum) over the sample interval and may therefore be
 * fractional. They are null when no data was recorded for the interval.</p>
 */
public class PveStorageRrdDataPoint {

    private Long time;
    private Double total;
    private Double used;

    /**
     * Creates an empty instance; instances are normally read from the API.
     */
    public PveStorageRrdDataPoint() {
    }

    /**
     * Returns the sample time (seconds since the UNIX epoch).
     *
     * @return the sample time (seconds since the UNIX epoch)
     */
    public Long getTime() {
        return time;
    }

    /**
     * Returns the total storage space in bytes, or null if unknown.
     *
     * @return the total storage space in bytes, or null if unknown
     */
    public Double getTotal() {
        return total;
    }

    /**
     * Returns the used storage space in bytes, or null if unknown.
     *
     * @return the used storage space in bytes, or null if unknown
     */
    public Double getUsed() {
        return used;
    }

    @Override
    public String toString() {
        return "PveStorageRrdDataPoint{" +
                "time=" + time +
                ", total=" + total +
                ", used=" + used +
                '}';
    }
}
