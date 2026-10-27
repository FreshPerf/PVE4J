package fr.freshperf.pve4j.entities.access;

import fr.freshperf.pve4j.testsupport.RecordingPveServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

/**
 * Deserialization and request tests for the access endpoints, fed with JSON captured with
 * {@code pvesh get ... --output-format json} on PVE 9.1.4 (or shaped after the Perl handlers in
 * PVE/API2/{User,Group,Domains}.pm when the test server has no such data).
 */
@DisplayName("PveAccess Tests")
class PveAccessTest {

    private RecordingPveServer server;

    @BeforeEach
    void setUp() throws Exception {
        server = new RecordingPveServer();
    }

    @AfterEach
    void tearDown() {
        server.close();
    }

    @Test
    @DisplayName("Should deserialize roles index with comma-separated privileges")
    void shouldDeserializeRolesIndex() throws Exception {
        // pvesh get /access/roles (excerpt)
        server.respondWith("[{\"privs\":\"Pool.Allocate,Pool.Audit\",\"roleid\":\"PVEPoolAdmin\",\"special\":1}," +
                "{\"privs\":\"\",\"roleid\":\"NoAccess\",\"special\":1}," +
                "{\"privs\":\"VM.Console\",\"roleid\":\"ConsoleUser\",\"special\":0}]");

        List<PveAccessRole> roles = new PveAccessRoles(server.client()).getIndex().execute();

        assertThat(roles).hasSize(3);
        assertThat(roles.get(0).getRoleid()).isEqualTo("PVEPoolAdmin");
        assertThat(roles.get(0).isSpecial()).isTrue();
        assertThat(roles.get(0).getPrivs()).containsExactly("Pool.Allocate", "Pool.Audit");
        assertThat(roles.get(1).getPrivs()).isEmpty();
        assertThat(roles.get(2).isSpecial()).isFalse();
        assertThat(roles.get(2).getPrivs()).containsExactly("VM.Console");
    }

    @Test
    @DisplayName("Should read a single role from its privilege map")
    void shouldReadSingleRole() throws Exception {
        // pvesh get /access/roles/PVEPoolAdmin
        server.respondWith("{\"Pool.Audit\":1,\"Pool.Allocate\":1}");

        PveAccessRole role = new PveAccessRoles(server.client()).get("PVEPoolAdmin").execute();

        assertThat(server.last().rawPath()).isEqualTo("/api2/json/access/roles/PVEPoolAdmin");
        assertThat(role.getRoleid()).isEqualTo("PVEPoolAdmin");
        assertThat(role.getPrivs()).containsExactly("Pool.Allocate", "Pool.Audit");
    }

    @Test
    @DisplayName("Should read a single user with API tokens")
    void shouldReadUserWithTokens() throws Exception {
        // pvesh get /access/users/root@pam (excerpt)
        server.respondWith("{\"email\":\"admin@example.com\",\"enable\":1,\"expire\":0,\"groups\":[\"admins\",\"ops\"]," +
                "\"tokens\":{\"automation\":{\"expire\":0,\"privsep\":1},\"readonly\":{\"expire\":0,\"privsep\":0}}}");

        PveAccessUser user = new PveAccessUsers(server.client()).get("root@pam").execute();

        assertThat(user.getUserid()).isEqualTo("root@pam");
        assertThat(user.getRealm()).isEqualTo("pam");
        assertThat(user.isEnable()).isTrue();
        assertThat(user.getGroups()).containsExactly("admins", "ops");
        Map<String, PveAccessUserToken> tokens = user.getTokens();
        assertThat(tokens).containsOnlyKeys("automation", "readonly");
        assertThat(tokens.get("automation").isPrivsep()).isTrue();
        assertThat(tokens.get("automation").getExpire()).isZero();
        assertThat(tokens.get("readonly").isPrivsep()).isFalse();
    }

    @Test
    @DisplayName("Should read users index with realm type, two-factor keys, joined groups and token list")
    void shouldDeserializeUsersIndex() throws Exception {
        // pvesh get /access/users, plus a user with yubico/oath keys (a plain string in user.cfg);
        // with full=1 the list joins the groups into one string and lists tokens as an array (PVE/API2/User.pm)
        server.respondWith("[{\"email\":\"admin@example.com\",\"enable\":1,\"expire\":0,\"groups\":\"admins,ops\",\"realm-type\":\"pam\",\"tokens\":[{\"tokenid\":\"automation\",\"expire\":0,\"privsep\":0}],\"userid\":\"root@pam\"}," +
                "{\"enable\":1,\"expire\":0,\"keys\":\"x!oath\",\"realm-type\":\"pve\",\"userid\":\"vmconsole@pve\"}]");

        List<PveAccessUser> users = new PveAccessUsers(server.client()).getIndex().execute();

        assertThat(users).hasSize(2);
        assertThat(users.get(0).getRealm()).isEqualTo("pam");
        assertThat(users.get(0).getRealmType()).isEqualTo("pam");
        assertThat(users.get(0).getGroups()).containsExactly("admins", "ops");
        assertThat(users.get(0).getTokens()).containsOnlyKeys("automation");
        assertThat(users.get(0).getTokens().get("automation").isPrivsep()).isFalse();
        assertThat(users.get(1).getUserid()).isEqualTo("vmconsole@pve");
        assertThat(users.get(1).getRealm()).isEqualTo("pve");
        assertThat(users.get(1).getKeys()).isEqualTo("x!oath");
    }

    @Test
    @DisplayName("Should escape special characters of the user ID in the path")
    void shouldEscapeUserIdInPath() throws Exception {
        server.respondWith("{\"enable\":1,\"expire\":0,\"groups\":[]}");

        PveAccessUser user = new PveAccessUsers(server.client()).get("a#b@pve").execute();

        assertThat(server.last().rawPath()).isEqualTo("/api2/json/access/users/a%23b%40pve");
        assertThat(user.getUserid()).isEqualTo("a#b@pve");
    }

    @Test
    @DisplayName("Should read group members from the index string and the single group array")
    void shouldDeserializeGroups() throws Exception {
        server.respondWith("[{\"groupid\":\"admins\",\"comment\":\"Admins\",\"users\":\"a@pve,b@pam\"},{\"groupid\":\"empty\",\"users\":\"\"}]");

        List<PveAccessGroup> groups = new PveAccessGroups(server.client()).getIndex().execute();

        assertThat(groups.get(0).getUsers()).containsExactly("a@pve", "b@pam");
        assertThat(groups.get(1).getUsers()).isEmpty();

        server.respondWith("{\"comment\":\"Admins\",\"members\":[\"a@pve\",\"b@pam\"]}");

        PveAccessGroup group = new PveAccessGroups(server.client()).get("admins").execute();

        assertThat(group.getGroupid()).isEqualTo("admins");
        assertThat(group.getComment()).isEqualTo("Admins");
        assertThat(group.getUsers()).containsExactly("a@pve", "b@pam");
    }

    @Test
    @DisplayName("Should deserialize domains with tfa provider and default flag")
    void shouldDeserializeDomains() throws Exception {
        // pvesh get /access/domains, plus a realm with tfa and the default flag (PVE/API2/Domains.pm)
        server.respondWith("[{\"comment\":\"Linux PAM standard authentication\",\"realm\":\"pam\",\"type\":\"pam\"}," +
                "{\"comment\":\"Proxmox VE authentication server\",\"default\":1,\"realm\":\"pve\",\"tfa\":\"oath\",\"type\":\"pve\"}]");

        List<PveAccessDomain> domains = new PveAccess(server.client()).getDomains().execute();

        assertThat(domains.get(0).getTfa()).isNull();
        assertThat(domains.get(0).isDefault()).isFalse();
        assertThat(domains.get(1).getTfa()).isEqualTo("oath");
        assertThat(domains.get(1).isDefault()).isTrue();
    }

    @Test
    @DisplayName("Should not append the realm twice when logging in with a full user ID")
    void shouldNotDuplicateRealmOnLogin() throws Exception {
        server.respondWith("{\"username\":\"root@pam\",\"ticket\":\"t\",\"CSRFPreventionToken\":\"c\"}");
        PveAccess access = new PveAccess(server.client());

        access.getTicket("root@pam", "secret").execute();
        assertThat(server.last().params()).containsEntry("username", "root@pam").doesNotContainKey("realm");

        access.getTicket("john@pve", "secret").execute();
        assertThat(server.last().params()).containsEntry("username", "john@pve").doesNotContainKey("realm");

        access.getTicket("root@pam", "secret", "pam").execute();
        assertThat(server.last().params()).containsEntry("username", "root@pam").doesNotContainKey("realm");

        access.getTicket("root", "secret").execute();
        assertThat(server.last().params()).containsEntry("username", "root").containsEntry("realm", "pam");

        access.getTicket("john", "secret", "pve").execute();
        assertThat(server.last().params()).containsEntry("username", "john").containsEntry("realm", "pve");
    }
}
