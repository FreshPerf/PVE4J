package fr.freshperf.pve4j.entities.access;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Represents a Proxmox user account.
 */
public class PveAccessUser {

    private String userid, comment, email, firstname, lastname, realm, keys;
    @SerializedName("realm-type")
    private String realmType;
    // An array for GET /access/users/{userid}, a comma-separated string in the users list (full=1)
    @JsonAdapter(StringListAdapter.class)
    private String[] groups;
    // An object keyed by token ID for GET /access/users/{userid}, an array in the users list (full=1)
    @JsonAdapter(UserTokensDeserializer.class)
    private Map<String, PveAccessUserToken> tokens;
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

    /**
     * Gets the realm of the user (e.g. "pam", "pve"), taken from the user ID.
     *
     * @return the realm, or null if unknown
     */
    public String getRealm() {
        if (realm == null && userid != null && userid.lastIndexOf('@') >= 0) {
            return userid.substring(userid.lastIndexOf('@') + 1);
        }
        return realm;
    }

    /**
     * Gets the type of the user's realm (e.g. "pam", "pve", "ldap").
     * Only reported by the users index ({@link PveAccessUsers#getIndex()}).
     *
     * @return the realm type, or null if not reported
     */
    public String getRealmType() {
        return realmType;
    }

    /**
     * Gets the groups the user belongs to.
     *
     * @return the group IDs, or null if not reported
     */
    public String[] getGroups() {
        return groups;
    }

    /**
     * Gets the API tokens of the user, keyed by token ID (the part after '!').
     * Only reported by {@link PveAccessUsers#get(String)}.
     *
     * @return the tokens, or null if not reported
     */
    public Map<String, PveAccessUserToken> getTokens() {
        return tokens;
    }

    /**
     * Gets the two-factor authentication keys (yubico key IDs or OATH secrets).
     *
     * @return the keys, or null if not set
     */
    public String getKeys() {
        return keys;
    }

    public boolean isEnable() {
        return enable;
    }

    public long getExpire() {
        return expire;
    }

    void setUserid(String userid) {
        this.userid = userid;
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
