package fr.freshperf.proxmox4j.entities;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExitstatus() {
        return exitstatus;
    }

    public void setExitstatus(String exitstatus) {
        this.exitstatus = exitstatus;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPstart() {
        return pstart;
    }

    public void setPstart(String pstart) {
        this.pstart = pstart;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUpid() {
        return upid;
    }

    public void setUpid(String upid) {
        this.upid = upid;
    }

    public boolean isRunning() {
        return "running".equals(status);
    }

    public boolean isCompleted() {
        return "stopped".equals(status);
    }

    public boolean isSuccessful() {
        return isCompleted() && (exitstatus == null || "OK".equals(exitstatus) || "0".equals(exitstatus));
    }
}

