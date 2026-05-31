package fr.freshperf.pve4j.entities.nodes.node.storage;

import com.google.gson.annotations.SerializedName;

/**
 * Represents the attributes of a single storage volume, as returned by
 * {@code GET /nodes/{node}/storage/{storage}/content/{volume}}.
 *
 * <p>For backup volumes, {@link #getNotes()} and {@link #isProtected()} are the
 * editable attributes (see
 * {@code PveStorageItem.updateVolumeAttributes(String, String, Boolean)}).</p>
 */
public class PveStorageVolume {

    private String path;
    private String format;
    private long size;
    private long used;
    private String notes;
    @SerializedName("protected")
    private boolean isProtected;

    /** @return the filesystem path of the volume. */
    public String getPath() {
        return path;
    }

    /** @return the format identifier ("raw", "qcow2", "subvol", "iso", "tgz", ...). */
    public String getFormat() {
        return format;
    }

    /** @return the volume size in bytes. */
    public long getSize() {
        return size;
    }

    /** @return the used space in bytes (most storage plugins do not report anything useful here). */
    public long getUsed() {
        return used;
    }

    /** @return the optional notes attached to the volume, or null. */
    public String getNotes() {
        return notes;
    }

    /** @return the protection status (currently only supported for backups). */
    public boolean isProtected() {
        return isProtected;
    }

    @Override
    public String toString() {
        return "PveStorageVolume{" +
                "path='" + path + '\'' +
                ", format='" + format + '\'' +
                ", size=" + size +
                ", used=" + used +
                ", isProtected=" + isProtected +
                ", notes='" + notes + '\'' +
                '}';
    }
}
