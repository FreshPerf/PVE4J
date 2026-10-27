package fr.freshperf.pve4j.entities.access;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a Proxmox authentication domain/realm.
 */
public class PveAccessDomain {

    private String realm, type, comment, tfa;
    @SerializedName("default")
    private boolean default_;

    public String getRealm() {
        return realm;
    }

    public String getType() {
        return type;
    }

    public String getComment() {
        return comment;
    }

    /**
     * Gets the two-factor authentication provider enforced for this realm.
     *
     * @return "oath" or "yubico", or null if none is configured
     */
    public String getTfa() {
        return tfa;
    }

    public boolean isDefault() {
        return default_;
    }

    @Override
    public String toString() {
        return "PveAccessDomain{" +
                "realm='" + realm + '\'' +
                ", type='" + type + '\'' +
                ", comment='" + comment + '\'' +
                ", tfa='" + tfa + '\'' +
                ", default=" + default_ +
                '}';
    }
}
