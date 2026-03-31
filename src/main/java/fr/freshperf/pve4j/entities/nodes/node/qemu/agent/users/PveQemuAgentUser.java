package fr.freshperf.pve4j.entities.nodes.node.qemu.agent.users;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a user session reported by the guest agent.
 */
public class PveQemuAgentUser {
    private String domain;
    @SerializedName("login-time")
    private Long loginTime;
    private String user;

    public String getDomain() {
        return domain;
    }

    public Long getLoginTime() {
        return loginTime;
    }

    public String getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "PveQemuAgentUser{" +
                "domain='" + domain + '\'' +
                ", loginTime=" + loginTime +
                ", user='" + user + '\'' +
                '}';
    }
}
