package fr.freshperf.pve4j.entities;

/**
 * Represents a Proxmox task identifier (UPID).
 * Format: UPID:node:pid:pstart:starttime:type:id:user:
 */
public class PveTask {
    private String upid;
    private String node;
    private String pid;
    private String pstart;
    private String starttime;
    private String type;
    private String id;
    private String user;

    /**
     * Default constructor.
     */
    public PveTask() {
    }

    /**
     * Creates a task from a UPID string.
     *
     * @param upid the UPID string to parse
     */
    public PveTask(String upid) {
        this.upid = upid;
        parseUpid(upid);
    }

    /**
     * Parses the UPID string into its components.
     */
    private void parseUpid(String upid) {
        if (upid != null && upid.startsWith("UPID:")) {
            String[] parts = upid.split(":", -1);
            if (parts.length >= 8) {
                this.node = parts[1];
                this.pid = parts[2];
                this.pstart = parts[3];
                this.starttime = parts[4];
                this.type = parts[5];
                this.id = parts[6];
                this.user = parts[7];
            }
        }
    }

    /**
     * Returns the full UPID string.
     *
     * @return the UPID
     */
    public String getUpid() {
        return upid;
    }

    /**
     * Sets the UPID and parses its components.
     *
     * @param upid the UPID string
     */
    public void setUpid(String upid) {
        this.upid = upid;
        parseUpid(upid);
    }

    /**
     * Returns the node name where the task is running.
     *
     * @return the node name
     */
    public String getNode() {
        return node;
    }

    /**
     * Returns the process ID.
     *
     * @return the PID
     */
    public String getPid() {
        return pid;
    }

    /**
     * Returns the process start time (hex).
     *
     * @return the pstart value
     */
    public String getPstart() {
        return pstart;
    }

    /**
     * Returns the task start time (hex).
     *
     * @return the start time
     */
    public String getStarttime() {
        return starttime;
    }

    /**
     * Returns the task type (e.g., "qmstart", "vzdump").
     *
     * @return the task type
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the task ID (usually VMID or resource name).
     *
     * @return the ID
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the user who started the task.
     *
     * @return the username
     */
    public String getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "PveTask{" +
                "upid='" + upid + '\'' +
                ", node='" + node + '\'' +
                ", pid='" + pid + '\'' +
                ", pstart='" + pstart + '\'' +
                ", starttime='" + starttime + '\'' +
                ", type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", user='" + user + '\'' +
                '}';
    }
}

