package fr.freshperf.proxmox4j.entities;

/**
 * Represents the status of a Proxmox task.
 */
public class PveTaskStatus {
    private String status;
    private String exitstatus;
    private String node;
    private String pid;
    private String pstart;
    private String starttime;
    private String type;
    private String id;
    private String user;
    private String upid;

    /** @return the current status ("running" or "stopped") */
    public String getStatus() {
        return status;
    }

    /** @param status the status to set */
    public void setStatus(String status) {
        this.status = status;
    }

    /** @return the exit status (e.g., "OK", error message, or null if running) */
    public String getExitstatus() {
        return exitstatus;
    }

    /** @param exitstatus the exit status to set */
    public void setExitstatus(String exitstatus) {
        this.exitstatus = exitstatus;
    }

    /** @return the node name */
    public String getNode() {
        return node;
    }

    /** @param node the node to set */
    public void setNode(String node) {
        this.node = node;
    }

    /** @return the process ID */
    public String getPid() {
        return pid;
    }

    /** @param pid the PID to set */
    public void setPid(String pid) {
        this.pid = pid;
    }

    /** @return the process start time */
    public String getPstart() {
        return pstart;
    }

    /** @param pstart the pstart to set */
    public void setPstart(String pstart) {
        this.pstart = pstart;
    }

    /** @return the task start time */
    public String getStarttime() {
        return starttime;
    }

    /** @param starttime the start time to set */
    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    /** @return the task type */
    public String getType() {
        return type;
    }

    /** @param type the type to set */
    public void setType(String type) {
        this.type = type;
    }

    /** @return the task ID */
    public String getId() {
        return id;
    }

    /** @param id the ID to set */
    public void setId(String id) {
        this.id = id;
    }

    /** @return the user who started the task */
    public String getUser() {
        return user;
    }

    /** @param user the user to set */
    public void setUser(String user) {
        this.user = user;
    }

    /** @return the UPID */
    public String getUpid() {
        return upid;
    }

    /** @param upid the UPID to set */
    public void setUpid(String upid) {
        this.upid = upid;
    }

    /**
     * Checks if the task is currently running.
     *
     * @return true if status is "running"
     */
    public boolean isRunning() {
        return "running".equals(status);
    }

    /**
     * Checks if the task has completed (stopped).
     *
     * @return true if status is "stopped"
     */
    public boolean isCompleted() {
        return "stopped".equals(status);
    }

    /**
     * Checks if the task completed successfully.
     *
     * @return true if completed with OK status or no exit status
     */
    public boolean isSuccessful() {
        return isCompleted() && (exitstatus == null || "OK".equals(exitstatus) || "0".equals(exitstatus));
    }
}

