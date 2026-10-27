package fr.freshperf.pve4j.entities.nodes.node.storage;

import fr.freshperf.pve4j.testsupport.PveJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("PveStorageContent Entity Tests")
class PveStorageContentTest {

    @Test
    @DisplayName("Should deserialize real storage content entries")
    void shouldDeserializeLiveContent() {
        // Real `pvesh get /nodes/{node}/storage/{local,DATA}/content` entries (PVE 9.1);
        // lvmthin storages return ctime as a string.
        String json = "[{\"content\":\"backup\",\"ctime\":1780244461,\"format\":\"vma\",\"size\":938206720,\"subtype\":\"qemu\",\"vmid\":2727,\"volid\":\"local:backup/vzdump-qemu-2727-2026_05_31-18_21_01.vma\"}," +
                "{\"content\":\"images\",\"ctime\":\"1786655375\",\"format\":\"raw\",\"size\":34359738368,\"vmid\":9001,\"volid\":\"DATA:base-9001-disk-0\"}]";

        List<PveStorageContent> content = PveJson.parseList(json, PveStorageContent.class);

        assertThat(content).hasSize(2);
        assertThat(content.get(0).getVmid()).isEqualTo(2727);
        assertThat(content.get(0).getCtime()).isEqualTo(1780244461L);
        assertThat(content.get(0).isProtected()).isFalse();
        assertThat(content.get(0).isEncrypted()).isFalse();
        assertThat(content.get(0).getEncrypted()).isNull();
        assertThat(content.get(1).getCtime()).isEqualTo(1786655375L);
    }

    @Test
    @DisplayName("Should read protection status and encryption fingerprint of PBS backups")
    void shouldReadProtectedAndEncrypted() {
        // Built from PVE::Storage::PBSPlugin::list_volumes: `protected` is 1 when set, and
        // `encrypted` is the key fingerprint, or '1' if the fingerprint is unknown.
        String json = "[{\"content\":\"backup\",\"ctime\":1780244461,\"encrypted\":\"00:11:22:33:44:55:66:77\",\"format\":\"pbs-vm\",\"notes\":\"before upgrade\",\"protected\":1,\"size\":938206720,\"subtype\":\"qemu\",\"verification\":{\"state\":\"ok\",\"upid\":\"UPID:pbs:00000F2A:0001D4C3:00000000:6AE0B00E:verificationjob:x:root@pam:\"},\"vmid\":2727,\"volid\":\"pbs:backup/vm/2727/2026-05-31T18:21:01Z\"}," +
                "{\"content\":\"backup\",\"ctime\":1780244462,\"encrypted\":\"1\",\"format\":\"pbs-ct\",\"size\":1024,\"subtype\":\"lxc\",\"vmid\":200,\"volid\":\"pbs:backup/ct/200/2026-05-31T18:21:02Z\"}]";

        List<PveStorageContent> content = PveJson.parseList(json, PveStorageContent.class);

        assertThat(content.get(0).isProtected()).isTrue();
        assertThat(content.get(0).isEncrypted()).isTrue();
        assertThat(content.get(0).getEncrypted()).isEqualTo("00:11:22:33:44:55:66:77");
        assertThat(content.get(0).getNotes()).isEqualTo("before upgrade");
        assertThat(content.get(1).isProtected()).isFalse();
        assertThat(content.get(1).isEncrypted()).isTrue();
        assertThat(content.get(1).getEncrypted()).isEqualTo("1");
    }
}
