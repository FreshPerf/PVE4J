package fr.freshperf.proxmox4j.entities.nodes.node.qemu;

import fr.freshperf.proxmox4j.Proxmox;
import fr.freshperf.proxmox4j.request.ProxmoxRequest;
import fr.freshperf.proxmox4j.util.ProxmoxApiBaseUrlBuilder;

/**
 * Represents VNC proxy connection details.
 */
public class PveQemuVmVncProxy {

    private String cert, ticket, upid, user, password;
    private int port;

    public String getCert() {
        return cert;
    }

    public void setCert(String cert) {
        this.cert = cert;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getUpid() {
        return upid;
    }

    public void setUpid(String upid) {
        this.upid = upid;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "PveQemuVmVncProxy{" +
                "cert='" + cert + '\'' +
                ", ticket='" + ticket + '\'' +
                ", upid='" + upid + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", port=" + port +
                '}';
    }
}
