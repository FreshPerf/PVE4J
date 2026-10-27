package fr.freshperf.pve4j.entities.nodes.node.qemu;

import com.google.gson.JsonObject;
import fr.freshperf.pve4j.testsupport.PveJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("PveQemuConfig Entity Tests")
class PveQemuConfigTest {

    @Test
    @DisplayName("Should deserialize a real template config")
    void shouldDeserializeTemplateConfig() {
        PveQemuConfig config = PveJson.parse(PveJson.fixture("qemu/config-template-9001.json"), PveQemuConfig.class);

        assertThat(config.getName()).isEqualTo("win2025-cloudinit");
        assertThat(config.getCores()).isEqualTo(6);
        assertThat(config.getSockets()).isEqualTo(1);
        assertThat(config.getMemory()).isEqualTo(4096);
        assertThat(config.getTemplate()).isEqualTo(1);
        assertThat(config.getIde1()).isEqualTo("local:iso/win25.iso,media=cdrom,size=7961286K");
    }

    @Test
    @DisplayName("Should deserialize 'args', which the API returns as a plain string")
    void shouldDeserializeArgsAsString() {
        JsonObject json = PveJson.fixtureObject("qemu/config-template-9001.json");
        json.addProperty("args", "-cpu host,+kvm_pv_unhalt -device intel-iommu");

        PveQemuConfig config = PveJson.parse(json, PveQemuConfig.class);

        assertThat(config.getArgs()).isEqualTo("-cpu host,+kvm_pv_unhalt -device intel-iommu");
    }

    @Test
    @DisplayName("Should read memory written as a property string (current=...)")
    void shouldReadMemoryPropertyString() {
        JsonObject json = PveJson.fixtureObject("qemu/config-template-9001.json");
        json.addProperty("memory", "current=8192");

        PveQemuConfig config = PveJson.parse(json, PveQemuConfig.class);

        assertThat(config.getMemory()).isEqualTo(8192);
    }

    @Test
    @DisplayName("Should keep keys that have no dedicated getter")
    void shouldKeepUnmodelledKeys() {
        JsonObject json = PveJson.fixtureObject("qemu/config-template-9001.json");
        json.addProperty("serial0", "socket");
        json.addProperty("unused0", "local-lvm:vm-9001-disk-3");
        json.addProperty("unused5", "DATA:vm-9001-disk-8");
        json.addProperty("scsi12", "DATA:vm-9001-disk-9,size=8G");
        json.addProperty("virtiofs0", "dirid=share");

        PveQemuConfig config = PveJson.parse(json, PveQemuConfig.class);

        assertThat(config.getProperties())
                .containsEntry("serial0", "socket")
                .containsEntry("cores", "6")
                .containsEntry("template", "1")
                .containsEntry("ostype", "win11");
        assertThat(config.getIndexed("unused"))
                .containsExactly(entry(0, "local-lvm:vm-9001-disk-3"), entry(5, "DATA:vm-9001-disk-8"));
        assertThat(config.getIndexed("scsi"))
                .containsOnlyKeys(0, 12);
        assertThat(config.getIndexed("ide"))
                .containsOnlyKeys(1, 2);
        assertThat(config.getIndexed("virtio")).isEmpty();
        assertThat(config.getIndexed("virtiofs")).containsOnlyKeys(0);
        assertThat(config.getUnused0()).isEqualTo("local-lvm:vm-9001-disk-3");
    }

    @Test
    @DisplayName("Should expose an empty property map when built without JSON")
    void shouldExposeEmptyPropertiesByDefault() {
        PveQemuConfig config = new PveQemuConfig();

        assertThat(config.getProperties()).isEmpty();
        assertThat(config.getIndexed("net")).isEmpty();
        assertThat(config.getMemory()).isZero();
    }

    @Test
    @DisplayName("Should read memory returned as a JSON number (older PVE versions)")
    void shouldReadMemoryAsNumber() {
        JsonObject json = PveJson.fixtureObject("qemu/config-template-9001.json");
        json.addProperty("memory", 2048);

        PveQemuConfig config = PveJson.parse(json, PveQemuConfig.class);

        assertThat(config.getMemory()).isEqualTo(2048);
    }
}
