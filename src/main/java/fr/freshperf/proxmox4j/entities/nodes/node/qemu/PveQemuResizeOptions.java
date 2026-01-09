package fr.freshperf.proxmox4j.entities.nodes.node.qemu;

import fr.freshperf.proxmox4j.entities.options.TwoParamsConvertible;
import fr.freshperf.proxmox4j.util.ParamsHelpers;

import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Options for resizing a QEMU VM disk.
 * Use the builder pattern to configure resize settings.
 */
public class PveQemuResizeOptions implements TwoParamsConvertible<String, String> {

    private static final Set<String> ALLOWED_DISKS = Set.of(
            "ide0", "ide1", "ide2", "ide3",
            "scsi0", "scsi1", "scsi2", "scsi3", "scsi4", "scsi5", "scsi6", "scsi7", "scsi8", "scsi9",
            "scsi10", "scsi11", "scsi12", "scsi13", "scsi14", "scsi15", "scsi16", "scsi17", "scsi18",
            "scsi19", "scsi20", "scsi21", "scsi22", "scsi23", "scsi24", "scsi25", "scsi26", "scsi27",
            "scsi28", "scsi29", "scsi30",
            "virtio0", "virtio1", "virtio2", "virtio3", "virtio4", "virtio5", "virtio6", "virtio7",
            "virtio8", "virtio9", "virtio10", "virtio11", "virtio12", "virtio13", "virtio14", "virtio15",
            "sata0", "sata1", "sata2", "sata3", "sata4", "sata5",
            "efidisk0", "tpmstate0"
    );

    private static final Pattern SIZE_PATTERN = Pattern.compile("\\+?\\d+(\\.\\d+)?[KMGT]?");

    private String digest;
    private Boolean skiplock;

    /**
     * Creates a new builder for resize options.
     *
     * @return a new PveQemuResizeOptions instance
     */
    public static PveQemuResizeOptions builder() {
        return new PveQemuResizeOptions();
    }

    @Override
    public void addRequiredParams(Map<String, Object> params, String disk, String size) {
        validateDisk(disk);
        validateSize(size);
        params.put("disk", disk);
        params.put("size", size);
    }

    @Override
    public void populateParams(Map<String, Object> params) {
        ParamsHelpers.put(params, "digest", digest);
        ParamsHelpers.putBool(params, "skiplock", skiplock);
    }

    /** Sets the configuration digest for conflict detection. */
    public PveQemuResizeOptions digest(String digest) {
        this.digest = digest;
        return this;
    }

    /** Sets whether to skip the lock check. */
    public PveQemuResizeOptions skiplock(Boolean skiplock) {
        this.skiplock = skiplock;
        return this;
    }

    private static void validateDisk(String disk) {
        if (disk == null || disk.isBlank()) {
            throw new IllegalArgumentException("disk is required");
        }
        if (!ALLOWED_DISKS.contains(disk)) {
            throw new IllegalArgumentException("disk must be one of: " + String.join(", ", ALLOWED_DISKS));
        }
    }

    private static void validateSize(String size) {
        if (size == null || size.isBlank()) {
            throw new IllegalArgumentException("size is required");
        }
        if (!SIZE_PATTERN.matcher(size).matches()) {
            throw new IllegalArgumentException("size must match +?number(.number)?[K|M|G|T]");
        }
    }
}


