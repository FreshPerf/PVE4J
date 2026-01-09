package fr.freshperf.proxmox4j.entities.nodes.node.storage;

/**
 * Represents content stored on a storage (disk images, ISOs, etc.).
 */
public class PveStorageContent {

    private String volid, format, content;
    private long size, used;
    private int vmid;
    private String parent, notes;
    private boolean encrypted, isProtected;
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

    public boolean isEncrypted() {
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

