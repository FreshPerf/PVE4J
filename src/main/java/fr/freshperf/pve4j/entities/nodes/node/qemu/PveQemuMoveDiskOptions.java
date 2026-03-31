package fr.freshperf.pve4j.entities.nodes.node.qemu;

import fr.freshperf.pve4j.entities.options.ParameterizedParamsConvertible;
import fr.freshperf.pve4j.util.ParamsHelpers;

import java.util.Map;
import java.util.Set;

/**
 * Options for moving a QEMU VM disk.
 */
public class PveQemuMoveDiskOptions implements ParameterizedParamsConvertible<String> {

    private static final Set<String> ALLOWED_FORMATS = Set.of("raw", "qcow2", "vmdk");

    private Integer bwlimit;
    private Boolean delete;
    private String digest;
    private String format;
    private String storage;
    private String targetDigest;
    private String targetDisk;
    private Integer targetVmid;

    /**
     * Creates a new builder for move disk options.
     *
     * @return a new PveQemuMoveDiskOptions instance
     */
    public static PveQemuMoveDiskOptions builder() {
        return new PveQemuMoveDiskOptions();
    }

    @Override
    public void addRequiredParam(Map<String, Object> params, String disk) {
        if (disk == null || disk.isBlank()) {
            throw new IllegalArgumentException("disk cannot be null or empty");
        }
        params.put("disk", disk);
    }

    @Override
    public void populateParams(Map<String, Object> params) {
        validateDestination();
        ParamsHelpers.putInt(params, "bwlimit", bwlimit);
        ParamsHelpers.putBool(params, "delete", delete);
        ParamsHelpers.put(params, "digest", digest);
        ParamsHelpers.put(params, "format", format);
        ParamsHelpers.put(params, "storage", storage);
        ParamsHelpers.put(params, "target-digest", targetDigest);
        ParamsHelpers.put(params, "target-disk", targetDisk);
        ParamsHelpers.putInt(params, "target-vmid", targetVmid);
    }

    /** Sets the bandwidth limit in KiB/s. */
    public PveQemuMoveDiskOptions bwlimit(Integer bwlimit) {
        if (bwlimit != null && bwlimit < 0) {
            throw new IllegalArgumentException("bwlimit must be >= 0");
        }
        this.bwlimit = bwlimit;
        return this;
    }

    /** Sets whether to delete the source disk after a successful move. */
    public PveQemuMoveDiskOptions delete(Boolean delete) {
        this.delete = delete;
        return this;
    }

    /** Sets the source VM config digest. */
    public PveQemuMoveDiskOptions digest(String digest) {
        this.digest = digest;
        return this;
    }

    /** Sets the target disk format. */
    public PveQemuMoveDiskOptions format(String format) {
        if (format != null && !format.isBlank() && !ALLOWED_FORMATS.contains(format)) {
            throw new IllegalArgumentException("format must be one of: " + String.join(", ", ALLOWED_FORMATS));
        }
        this.format = format;
        return this;
    }

    /** Sets the target storage. */
    public PveQemuMoveDiskOptions storage(String storage) {
        this.storage = storage;
        return this;
    }

    /** Sets the target VM config digest. */
    public PveQemuMoveDiskOptions targetDigest(String targetDigest) {
        this.targetDigest = targetDigest;
        return this;
    }

    /** Sets the target disk key on the target VM. */
    public PveQemuMoveDiskOptions targetDisk(String targetDisk) {
        this.targetDisk = targetDisk;
        return this;
    }

    /** Sets the target VM ID. */
    public PveQemuMoveDiskOptions targetVmid(Integer targetVmid) {
        if (targetVmid != null && targetVmid < 100) {
            throw new IllegalArgumentException("targetVmid must be >= 100");
        }
        this.targetVmid = targetVmid;
        return this;
    }

    private void validateDestination() {
        if ((storage == null || storage.isBlank()) && targetVmid == null) {
            throw new IllegalArgumentException("either storage or targetVmid must be provided");
        }
    }
}
