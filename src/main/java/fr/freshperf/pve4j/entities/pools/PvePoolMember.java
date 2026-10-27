package fr.freshperf.pve4j.entities.pools;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a member of a Proxmox resource pool.
 */
public class PvePoolMember {

    @SerializedName("id")
    private String id;

    @SerializedName("type")
    private String type;

    @SerializedName("vmid")
    private Integer vmid;

    @SerializedName("node")
    private String node;

    @SerializedName("storage")
    private String storage;

    @SerializedName("name")
    private String name;

    @SerializedName("status")
    private String status;

    @SerializedName("uptime")
    private Long uptime;

    // A number, not an integer: containers report their cpulimit, which can be fractional.
    @SerializedName("maxcpu")
    private Double maxcpu;

    @SerializedName("maxmem")
    private Long maxmem;

    @SerializedName("maxdisk")
    private Long maxdisk;

    @SerializedName("mem")
    private Long mem;

    @SerializedName("disk")
    private Long disk;

    @SerializedName("cpu")
    private Double cpu;

    /**
     * Gets the resource ID (e.g., "qemu/100", "storage/local").
     *
     * @return the resource ID
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the resource type ("qemu", "lxc", "storage").
     *
     * @return the resource type
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the VMID (for VMs and containers).
     *
     * @return the VMID, or null for storage
     */
    public Integer getVmid() {
        return vmid;
    }

    /**
     * Gets the node name.
     *
     * @return the node name
     */
    public String getNode() {
        return node;
    }

    /**
     * Gets the storage name (for storage resources).
     *
     * @return the storage name, or null for VMs
     */
    public String getStorage() {
        return storage;
    }

    /**
     * Gets the resource name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the status (e.g., "running", "stopped").
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Gets the uptime in seconds.
     *
     * @return the uptime
     */
    public Long getUptime() {
        return uptime;
    }

    /**
     * Gets the maximum number of CPUs; fractional for containers limited by a fractional
     * {@code cpulimit}.
     *
     * @return the max CPU
     */
    public Double getMaxcpu() {
        return maxcpu;
    }

    /**
     * Gets the maximum memory in bytes.
     *
     * @return the max memory
     */
    public Long getMaxmem() {
        return maxmem;
    }

    /**
     * Gets the maximum disk size in bytes.
     *
     * @return the max disk
     */
    public Long getMaxdisk() {
        return maxdisk;
    }

    /**
     * Gets the current memory usage in bytes.
     *
     * @return the memory usage
     */
    public Long getMem() {
        return mem;
    }

    /**
     * Gets the current disk usage in bytes.
     *
     * @return the disk usage
     */
    public Long getDisk() {
        return disk;
    }

    /**
     * Gets the CPU usage (0.0 - 1.0).
     *
     * @return the CPU usage
     */
    public Double getCpu() {
        return cpu;
    }

    @Override
    public String toString() {
        return "PvePoolMember{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", vmid=" + vmid +
                ", node='" + node + '\'' +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

