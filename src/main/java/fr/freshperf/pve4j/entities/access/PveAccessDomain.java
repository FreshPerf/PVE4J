package fr.freshperf.pve4j.entities.access;

/**
 * Represents a Proxmox authentication domain/realm.
 */
public class PveAccessDomain {

    private String realm, type, comment;
    private int tfa;
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

    public int getTfa() {
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
                ", default=" + default_ +
                '}';
    }
}

