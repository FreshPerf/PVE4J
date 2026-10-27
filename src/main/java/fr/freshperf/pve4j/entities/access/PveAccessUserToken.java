package fr.freshperf.pve4j.entities.access;

import com.google.gson.annotations.JsonAdapter;

/**
 * Represents an API token of a Proxmox user, as returned in {@link PveAccessUser#getTokens()}.
 */
public class PveAccessUserToken {

    private String comment;
    private Long expire;
    @JsonAdapter(NumericBooleanAdapter.class)
    private Boolean privsep;

    /**
     * Creates an empty instance; instances are normally read from the API.
     */
    public PveAccessUserToken() {
    }

    /**
     * Gets the token comment.
     *
     * @return the comment, or null if not set
     */
    public String getComment() {
        return comment;
    }

    /**
     * Gets the token expiration date (seconds since epoch, 0 means no expiration).
     *
     * @return the expiration date, or null if not set (same as the user)
     */
    public Long getExpire() {
        return expire;
    }

    /**
     * Whether the token privileges are restricted with separate ACLs.
     *
     * @return true if privileges are separated (the Proxmox default when not reported)
     */
    public boolean isPrivsep() {
        return privsep == null || privsep;
    }

    @Override
    public String toString() {
        return "PveAccessUserToken{" +
                "comment='" + comment + '\'' +
                ", expire=" + expire +
                ", privsep=" + isPrivsep() +
                '}';
    }
}
