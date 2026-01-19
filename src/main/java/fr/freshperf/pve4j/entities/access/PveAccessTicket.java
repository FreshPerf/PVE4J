package fr.freshperf.pve4j.entities.access;

/**
 * Represents an authentication ticket returned after login.
 */
public class PveAccessTicket {

    private String username, ticket, CSRFPreventionToken;
    private String clustername;

    public String getUsername() {
        return username;
    }

    public String getTicket() {
        return ticket;
    }

    public String getCSRFPreventionToken() {
        return CSRFPreventionToken;
    }

    public String getClustername() {
        return clustername;
    }

    @Override
    public String toString() {
        return "PveAccessTicket{" +
                "username='" + username + '\'' +
                ", clustername='" + clustername + '\'' +
                ", ticket='" + (ticket != null ? "***" : "null") + '\'' +
                ", csrfPreventionToken='" + (CSRFPreventionToken != null ? "***" : "null") + '\'' +
                '}';
    }
}

