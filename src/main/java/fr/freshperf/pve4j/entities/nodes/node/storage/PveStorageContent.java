package fr.freshperf.pve4j.entities.nodes.node.storage;

import com.google.gson.annotations.SerializedName;

/**
 * Represents content stored on a storage (disk images, ISOs, etc.).
 */
public class PveStorageContent {

    private String volid, format, content;
    private long size, used;
    private int vmid;
    private String parent, notes;
    // The API returns the key fingerprint, or '1' if encrypted without a known fingerprint.
    private String encrypted;
    @SerializedName("protected")
    private boolean isProtected;
    private long ctime;

    public String getVolid() {
        return volid;
    }

    public String getFormat() {
        return format;
    }

    public String getContent() {
        return content;
    }

    public long getSize() {
        return size;
    }

    public long getUsed() {
        return used;
    }

    public int getVmid() {
        return vmid;
    }

    public String getParent() {
        return parent;
    }

    public String getNotes() {
        return notes;
    }

    /**
     * Checks whether the backup is encrypted.
     *
     * @return whether the backup is encrypted (only reported by Proxmox Backup Server storages)
     */
    public boolean isEncrypted() {
        return encrypted != null && !encrypted.isEmpty() && !"0".equals(encrypted);
    }

    /**
     * Gets the encryption state of the backup.
     *
     * @return the encryption key fingerprint, "1" if the backup is encrypted but the fingerprint
     *         is unknown, or null if the backup is not encrypted (only reported by Proxmox
     *         Backup Server storages)
     */
    public String getEncrypted() {
        return encrypted;
    }

    public boolean isProtected() {
        return isProtected;
    }

    public long getCtime() {
        return ctime;
    }

    @Override
    public String toString() {
        return "PveStorageContent{" +
                "volid='" + volid + '\'' +
                ", format='" + format + '\'' +
                ", content='" + content + '\'' +
                ", size=" + size +
                ", vmid=" + vmid +
                '}';
    }
}

