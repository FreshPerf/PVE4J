package fr.freshperf.pve4j.entities.cluster.firewall;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a datacenter-level firewall security group
 * returned by {@code GET /cluster/firewall/groups}.
 */
public class PveFirewallSecurityGroup {

    @SerializedName("group")
    private String group;

    @SerializedName("comment")
    private String comment;

    @SerializedName("digest")
    private String digest;

    /**
     * Gets the security group name.
     *
     * @return the group name
     */
    public String getGroup() {
        return group;
    }

    /**
     * Gets the descriptive comment.
     *
     * @return the comment, or null
     */
    public String getComment() {
        return comment;
    }

    /**
     * Gets the groups configuration digest, for concurrency guards on group create/rename.
     *
     * @return the digest
     */
    public String getDigest() {
        return digest;
    }

    @Override
    public String toString() {
        return "PveFirewallSecurityGroup{" +
                "group='" + group + '\'' +
                ", comment='" + comment + '\'' +
                ", digest='" + digest + '\'' +
                '}';
    }
}
