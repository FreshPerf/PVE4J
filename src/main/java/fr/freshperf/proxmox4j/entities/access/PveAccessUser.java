package fr.freshperf.proxmox4j.entities.access;

/**
 * Represents a Proxmox user account.
 */
public class PveAccessUser {

    private String userid, comment, email, firstname, lastname, realm;
    private String[] groups, tokens, keys;
    private boolean enable;
    private long expire;

    public String getUserid() {
        return userid;
    }

    public String getComment() {
        return comment;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getRealm() {
        return realm;
    }

    public String[] getGroups() {
        return groups;
    }

    public String[] getTokens() {
        return tokens;
    }

    public String[] getKeys() {
        return keys;
    }

    public boolean isEnable() {
        return enable;
    }

    public long getExpire() {
        return expire;
    }

    @Override
    public String toString() {
        return "PveAccessUser{" +
                "userid='" + userid + '\'' +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", enable=" + enable +
                '}';
    }
}

