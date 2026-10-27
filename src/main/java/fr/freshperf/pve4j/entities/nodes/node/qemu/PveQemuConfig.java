package fr.freshperf.pve4j.entities.nodes.node.qemu;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Represents the configuration of a QEMU VM.
 * <p>Only the most common keys have dedicated getters; every key returned by the API
 * (including indexed devices beyond the modelled ones) is available through
 * {@link #getProperties()} and {@link #getIndexed(String)}.
 */
@JsonAdapter(PveQemuConfig.PropertiesCapturingAdapterFactory.class)
public class PveQemuConfig {

    // Identification / metadata
    private String name, description, tags, digest, lock, parent;

    // OS / firmware / boot
    private String ostype, arch, machine, bios, smbios1, vmgenid;
    private String bootdisk, boot, startdate;

    // CPU
    private String cpu, affinity;
    private int sockets, cores, vcpus, numa;
    private double cpulimit;
    private int cpuunits, shares;

    // Memory (property string, e.g. "4096" or "current=4096")
    private String memory;
    private int balloon;
    private String hugepages;
    private int keephugepages;

    // Timing / platform flags
    private int acpi, kvm, localtime, tdf, freeze, reboot;

    // Lifecycle
    private int onboot, autostart, template, protection;
    private String startup;

    // Graphics / input / misc devices
    private String vga, audio0, ivshmem, keyboard;
    private int tablet;
    @SerializedName("spice_enhancements")
    private String spiceEnhancements;

    // Guest agent / cloud-init
    private String agent, cicustom, cipassword, citype, ciuser, nameserver, sshkeys;
    private int ciupgrade;
    private String ipconfig0, ipconfig1;

    // Disks / storage
    private String scsihw;
    private String scsi0, scsi1, scsi2, scsi3, scsi4, scsi5, scsi6, scsi7;
    private String virtio0, virtio1, virtio2, virtio3, virtio4, virtio5, virtiofs0;
    private String ide0, ide1, ide2, ide3;
    private String sata0, sata1, sata2, sata3, sata4, sata5;
    private String efidisk0, tpmstate0, cdrom, unused0, unused1, vmstate, vmstatestorage;

    // Network
    private String net0, net1, net2, net3, net4, net5;

    // PCI / USB / RNG
    private String hostpci0, hostpci1, hostpci2, hostpci3;
    private String usb0, usb1;
    private String rng0;

    // Migration
    @SerializedName("migrate_downtime")
    private double migrateDowntime;
    @SerializedName("migrate_speed")
    private int migrateSpeed;

    // Other options
    private String hotplug, hookscript, meta, watchdog;
    private String args;

    // All raw keys as returned by the API (filled by PropertiesCapturingAdapterFactory)
    private transient Map<String, String> properties = Collections.emptyMap();

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getTags() {
        return tags;
    }

    public String getDigest() {
        return digest;
    }

    public String getLock() {
        return lock;
    }

    public String getParent() {
        return parent;
    }

    public String getOstype() {
        return ostype;
    }

    public String getArch() {
        return arch;
    }

    public String getMachine() {
        return machine;
    }

    public String getBios() {
        return bios;
    }

    public String getSmbios1() {
        return smbios1;
    }

    public String getVmgenid() {
        return vmgenid;
    }

    public String getBootdisk() {
        return bootdisk;
    }

    public String getBoot() {
        return boot;
    }

    public String getStartdate() {
        return startdate;
    }

    public String getCpu() {
        return cpu;
    }

    public String getAffinity() {
        return affinity;
    }

    public int getSockets() {
        return sockets;
    }

    public int getCores() {
        return cores;
    }

    public int getVcpus() {
        return vcpus;
    }

    public int getNuma() {
        return numa;
    }

    public double getCpulimit() {
        return cpulimit;
    }

    public int getCpuunits() {
        return cpuunits;
    }

    public int getShares() {
        return shares;
    }

    /**
     * Returns the amount of RAM in MiB.
     * <p>The API returns {@code memory} as a property string whose default key is
     * {@code current}, so both {@code "4096"} and {@code "current=4096"} are valid.
     *
     * @return the {@code current} memory in MiB, or 0 if not set
     */
    public int getMemory() {
        if (memory == null || memory.isBlank()) {
            return 0;
        }
        for (String part : memory.split(",")) {
            String token = part.trim();
            if (token.startsWith("current=")) {
                return Integer.parseInt(token.substring("current=".length()).trim());
            }
            if (!token.contains("=")) {
                return Integer.parseInt(token);
            }
        }
        return 0;
    }

    public int getBalloon() {
        return balloon;
    }

    public String getHugepages() {
        return hugepages;
    }

    public int getKeephugepages() {
        return keephugepages;
    }

    public int getAcpi() {
        return acpi;
    }

    public int getKvm() {
        return kvm;
    }

    public int getLocaltime() {
        return localtime;
    }

    public int getTdf() {
        return tdf;
    }

    public int getFreeze() {
        return freeze;
    }

    public int getReboot() {
        return reboot;
    }

    public int getOnboot() {
        return onboot;
    }

    public int getAutostart() {
        return autostart;
    }

    public int getTemplate() {
        return template;
    }

    public int getProtection() {
        return protection;
    }

    public String getStartup() {
        return startup;
    }

    public String getVga() {
        return vga;
    }

    public String getAudio0() {
        return audio0;
    }

    public String getIvshmem() {
        return ivshmem;
    }

    public String getKeyboard() {
        return keyboard;
    }

    public int getTablet() {
        return tablet;
    }

    public String getSpiceEnhancements() {
        return spiceEnhancements;
    }

    public String getAgent() {
        return agent;
    }

    public String getCicustom() {
        return cicustom;
    }

    public String getCipassword() {
        return cipassword;
    }

    public String getCitype() {
        return citype;
    }

    public String getCiuser() {
        return ciuser;
    }

    public int getCiupgrade() {
        return ciupgrade;
    }

    public String getIpconfig0() {
        return ipconfig0;
    }

    public String getIpconfig1() {
        return ipconfig1;
    }

    public String getNameserver() {
        return nameserver;
    }

    public String getSshkeys() {
        return sshkeys;
    }

    public String getScsihw() {
        return scsihw;
    }

    public String getScsi0() {
        return scsi0;
    }

    public String getScsi1() {
        return scsi1;
    }

    public String getScsi2() {
        return scsi2;
    }

    public String getScsi3() {
        return scsi3;
    }

    public String getScsi4() {
        return scsi4;
    }

    public String getScsi5() {
        return scsi5;
    }

    public String getScsi6() {
        return scsi6;
    }

    public String getScsi7() {
        return scsi7;
    }

    public String getVirtio0() {
        return virtio0;
    }

    public String getVirtio1() {
        return virtio1;
    }

    public String getVirtio2() {
        return virtio2;
    }

    public String getVirtio3() {
        return virtio3;
    }

    public String getVirtio4() {
        return virtio4;
    }

    public String getVirtio5() {
        return virtio5;
    }

    public String getVirtiofs0() {
        return virtiofs0;
    }

    public String getIde0() {
        return ide0;
    }

    public String getIde1() {
        return ide1;
    }

    public String getIde2() {
        return ide2;
    }

    public String getIde3() {
        return ide3;
    }

    public String getSata0() {
        return sata0;
    }

    public String getSata1() {
        return sata1;
    }

    public String getSata2() {
        return sata2;
    }

    public String getSata3() {
        return sata3;
    }

    public String getSata4() {
        return sata4;
    }

    public String getSata5() {
        return sata5;
    }

    public String getEfidisk0() {
        return efidisk0;
    }

    public String getTpmstate0() {
        return tpmstate0;
    }

    public String getCdrom() {
        return cdrom;
    }

    public String getUnused0() {
        return unused0;
    }

    public String getUnused1() {
        return unused1;
    }

    public String getVmstate() {
        return vmstate;
    }

    public String getVmstatestorage() {
        return vmstatestorage;
    }

    public String getNet0() {
        return net0;
    }

    public String getNet1() {
        return net1;
    }

    public String getNet2() {
        return net2;
    }

    public String getNet3() {
        return net3;
    }

    public String getNet4() {
        return net4;
    }

    public String getNet5() {
        return net5;
    }

    public String getHostpci0() {
        return hostpci0;
    }

    public String getHostpci1() {
        return hostpci1;
    }

    public String getHostpci2() {
        return hostpci2;
    }

    public String getHostpci3() {
        return hostpci3;
    }

    public String getUsb0() {
        return usb0;
    }

    public String getUsb1() {
        return usb1;
    }

    public String getRng0() {
        return rng0;
    }

    public double getMigrateDowntime() {
        return migrateDowntime;
    }

    public int getMigrateSpeed() {
        return migrateSpeed;
    }

    public String getHotplug() {
        return hotplug;
    }

    public String getHookscript() {
        return hookscript;
    }

    public String getMeta() {
        return meta;
    }

    public String getWatchdog() {
        return watchdog;
    }

    /**
     * Returns the arbitrary arguments passed to kvm.
     * <p>The API returns {@code args} as a single string (e.g. {@code "-cpu host,+kvm_pv_unhalt"}).
     *
     * @return the raw kvm arguments, or null if not set
     */
    public String getArgs() {
        return args;
    }

    /**
     * Returns every configuration key exactly as returned by the API, values as strings.
     * <p>Use it for keys without a dedicated getter, such as {@code serial0},
     * {@code unused5}, {@code scsi12}, {@code numa0} or {@code ipconfig3}.
     *
     * @return an unmodifiable map of config key to raw value (empty if not deserialized from JSON)
     */
    public Map<String, String> getProperties() {
        return properties;
    }

    /**
     * Returns all indexed keys of a device family, keyed by index.
     * <p>For example {@code getIndexed("unused")} returns {@code {0=..., 5=...}} for
     * {@code unused0} and {@code unused5}. The prefix must match exactly, so {@code "virtio"}
     * does not include {@code virtiofs0}.
     *
     * @param prefix the key prefix (e.g. "scsi", "virtio", "net", "unused", "serial", "ipconfig")
     * @return a sorted, unmodifiable map of index to raw value
     */
    public SortedMap<Integer, String> getIndexed(String prefix) {
        SortedMap<Integer, String> result = new TreeMap<>();
        if (prefix == null || prefix.isEmpty()) {
            return Collections.unmodifiableSortedMap(result);
        }
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            String key = entry.getKey();
            if (key.length() > prefix.length() && key.startsWith(prefix)) {
                String index = key.substring(prefix.length());
                if (index.chars().allMatch(Character::isDigit) && index.length() <= 3) {
                    result.put(Integer.parseInt(index), entry.getValue());
                }
            }
        }
        return Collections.unmodifiableSortedMap(result);
    }

    /**
     * Keeps every raw key of the JSON object in {@link #properties}, so that keys
     * without a dedicated field are not silently dropped.
     */
    static final class PropertiesCapturingAdapterFactory implements TypeAdapterFactory {
        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            if (type.getRawType() != PveQemuConfig.class) {
                return null;
            }
            TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
            TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
            return new TypeAdapter<T>() {
                @Override
                public void write(JsonWriter out, T value) throws IOException {
                    delegate.write(out, value);
                }

                @Override
                public T read(JsonReader in) throws IOException {
                    JsonElement tree = elementAdapter.read(in);
                    T value = delegate.fromJsonTree(tree);
                    if (value != null && tree.isJsonObject()) {
                        Map<String, String> raw = new LinkedHashMap<>();
                        for (Map.Entry<String, JsonElement> entry : tree.getAsJsonObject().entrySet()) {
                            JsonElement element = entry.getValue();
                            if (element.isJsonPrimitive()) {
                                raw.put(entry.getKey(), element.getAsString());
                            } else if (!element.isJsonNull()) {
                                raw.put(entry.getKey(), element.toString());
                            }
                        }
                        ((PveQemuConfig) value).properties = Collections.unmodifiableMap(raw);
                    }
                    return value;
                }
            };
        }
    }

    @Override
    public String toString() {
        return "PveQemuConfig{" +
                "name='" + name + '\'' +
                ", ostype='" + ostype + '\'' +
                ", sockets=" + sockets +
                ", cores=" + cores +
                ", memory='" + memory + '\'' +
                ", boot='" + boot + '\'' +
                ", machine='" + machine + '\'' +
                '}';
    }
}

