package fr.freshperf.proxmox4j.entities.nodes.node.qemu;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Options for updating a QEMU VM configuration (POST/PUT /nodes/{node}/qemu/{vmid}/config).
 */
public class PveQemuConfigUpdateOptions {

    private static final Set<String> ALLOWED_HOTPLUG = Set.of("0", "1", "disk", "network", "usb", "cpu", "memory", "cloudinit");

    private Boolean acpi;
    private String affinity;
    private String agent;
    private Boolean allowKsm;
    private String amdSev;
    private String arch;
    private String args;
    private String audio0;
    private Boolean autostart;
    private Integer backgroundDelay;
    private Integer balloon;
    private String bios;
    private String boot;
    private String bootdisk;
    private String cdrom;
    private String cicustom;
    private String cipassword;
    private String citype;
    private Boolean ciupgrade;
    private String ciuser;
    private Integer cores;
    private String cpu;
    private Double cpulimit;
    private Integer cpuunits;
    private String delete;
    private String description;
    private String digest;
    private String efidisk0;
    private Boolean force;
    private Boolean freeze;
    private String hookscript;
    private final Map<Integer, String> hostpcis = new HashMap<>();
    private String hotplug;
    private String hugepages;
    private final Map<Integer, String> ides = new HashMap<>();
    private String importWorkingStorage;
    private String intelTdx;
    private final Map<Integer, String> ipconfigs = new HashMap<>();
    private Boolean keephugepages;
    private String keyboard;
    private Boolean kvm;
    private Boolean localtime;
    private String lock;
    private String machine;
    private String memory;
    private Double migrateDowntime;
    private Integer migrateSpeed;
    private String name;
    private String nameserver;
    private final Map<Integer, String> nets = new HashMap<>();
    private Boolean numa;
    private final Map<Integer, String> numas = new HashMap<>();
    private Boolean onboot;
    private String ostype;
    private final Map<Integer, String> parallels = new HashMap<>();
    private Boolean protection;
    private Boolean reboot;
    private Boolean revert;
    private String rng0;
    private final Map<Integer, String> satas = new HashMap<>();
    private final Map<Integer, String> scsis = new HashMap<>();
    private String scsihw;
    private String searchdomain;
    private final Map<Integer, String> serials = new HashMap<>();
    private Integer shares;
    private Boolean skiplock;
    private String smbios1;
    private Integer smp;
    private Integer sockets;
    private String spiceEnhancements;
    private String sshkeys;
    private String startdate;
    private String startup;
    private Boolean tablet;
    private String tags;
    private Boolean tdf;
    private Boolean template;
    private String tpmstate0;
    private final Map<Integer, String> unused = new HashMap<>();
    private final Map<Integer, String> usbs = new HashMap<>();
    private Integer vcpus;
    private String vga;
    private final Map<Integer, String> virtios = new HashMap<>();
    private final Map<Integer, String> virtiofss = new HashMap<>();
    private String vmgenid;
    private String vmstatestorage;
    private String watchdog;

    public static PveQemuConfigUpdateOptions builder() {
        return new PveQemuConfigUpdateOptions();
    }

    public Map<String, String> toParams() {
        Map<String, String> params = new HashMap<>();

        putBool(params, "acpi", acpi);
        put(params, "affinity", affinity);
        put(params, "agent", agent);
        putBool(params, "allow-ksm", allowKsm);
        put(params, "amd-sev", amdSev);
        put(params, "arch", arch);
        put(params, "args", args);
        put(params, "audio0", audio0);
        putBool(params, "autostart", autostart);
        putInt(params, "background_delay", backgroundDelay);
        putInt(params, "balloon", balloon);
        put(params, "bios", bios);
        put(params, "boot", boot);
        put(params, "bootdisk", bootdisk);
        put(params, "cdrom", cdrom);
        put(params, "cicustom", cicustom);
        put(params, "cipassword", cipassword);
        put(params, "citype", citype);
        putBool(params, "ciupgrade", ciupgrade);
        put(params, "ciuser", ciuser);
        putInt(params, "cores", cores);
        put(params, "cpu", cpu);
        putDouble(params, "cpulimit", cpulimit);
        putInt(params, "cpuunits", cpuunits);
        put(params, "delete", delete);
        put(params, "description", description);
        put(params, "digest", digest);
        put(params, "efidisk0", efidisk0);
        putBool(params, "force", force);
        putBool(params, "freeze", freeze);
        put(params, "hookscript", hookscript);
        hostpcis.forEach((i, v) -> put(params, "hostpci" + i, v));
        put(params, "hotplug", hotplug);
        put(params, "hugepages", hugepages);
        ides.forEach((i, v) -> put(params, "ide" + i, v));
        put(params, "import-working-storage", importWorkingStorage);
        put(params, "intel-tdx", intelTdx);
        ipconfigs.forEach((i, v) -> put(params, "ipconfig" + i, v));
        putBool(params, "keephugepages", keephugepages);
        put(params, "keyboard", keyboard);
        putBool(params, "kvm", kvm);
        putBool(params, "localtime", localtime);
        put(params, "lock", lock);
        put(params, "machine", machine);
        put(params, "memory", memory);
        putDouble(params, "migrate_downtime", migrateDowntime);
        putInt(params, "migrate_speed", migrateSpeed);
        put(params, "name", name);
        put(params, "nameserver", nameserver);
        nets.forEach((i, v) -> put(params, "net" + i, v));
        putBool(params, "numa", numa);
        numas.forEach((i, v) -> put(params, "numa" + i, v));
        putBool(params, "onboot", onboot);
        put(params, "ostype", ostype);
        parallels.forEach((i, v) -> put(params, "parallel" + i, v));
        putBool(params, "protection", protection);
        putBool(params, "reboot", reboot);
        putBool(params, "revert", revert);
        put(params, "rng0", rng0);
        satas.forEach((i, v) -> put(params, "sata" + i, v));
        scsis.forEach((i, v) -> put(params, "scsi" + i, v));
        put(params, "scsihw", scsihw);
        put(params, "searchdomain", searchdomain);
        serials.forEach((i, v) -> put(params, "serial" + i, v));
        putInt(params, "shares", shares);
        putBool(params, "skiplock", skiplock);
        put(params, "smbios1", smbios1);
        putInt(params, "smp", smp);
        putInt(params, "sockets", sockets);
        put(params, "spice_enhancements", spiceEnhancements);
        put(params, "sshkeys", sshkeys);
        put(params, "startdate", startdate);
        put(params, "startup", startup);
        putBool(params, "tablet", tablet);
        put(params, "tags", tags);
        putBool(params, "tdf", tdf);
        putBool(params, "template", template);
        put(params, "tpmstate0", tpmstate0);
        unused.forEach((i, v) -> put(params, "unused" + i, v));
        usbs.forEach((i, v) -> put(params, "usb" + i, v));
        putInt(params, "vcpus", vcpus);
        put(params, "vga", vga);
        virtios.forEach((i, v) -> put(params, "virtio" + i, v));
        virtiofss.forEach((i, v) -> put(params, "virtiofs" + i, v));
        put(params, "vmgenid", vmgenid);
        put(params, "vmstatestorage", vmstatestorage);
        put(params, "watchdog", watchdog);

        return params;
    }

    // Builder methods
    public PveQemuConfigUpdateOptions acpi(Boolean acpi) { this.acpi = acpi; return this; }
    public PveQemuConfigUpdateOptions affinity(String affinity) { this.affinity = affinity; return this; }
    public PveQemuConfigUpdateOptions agent(String agent) { this.agent = agent; return this; }
    public PveQemuConfigUpdateOptions allowKsm(Boolean allowKsm) { this.allowKsm = allowKsm; return this; }
    public PveQemuConfigUpdateOptions amdSev(String amdSev) { this.amdSev = amdSev; return this; }
    public PveQemuConfigUpdateOptions arch(String arch) { this.arch = arch; return this; }
    public PveQemuConfigUpdateOptions args(String args) { this.args = args; return this; }
    public PveQemuConfigUpdateOptions audio0(String audio0) { this.audio0 = audio0; return this; }
    public PveQemuConfigUpdateOptions autostart(Boolean autostart) { this.autostart = autostart; return this; }
    public PveQemuConfigUpdateOptions backgroundDelay(Integer backgroundDelay) { this.backgroundDelay = backgroundDelay; return this; }
    public PveQemuConfigUpdateOptions balloon(Integer balloon) { this.balloon = balloon; return this; }
    public PveQemuConfigUpdateOptions bios(String bios) { this.bios = bios; return this; }
    public PveQemuConfigUpdateOptions boot(String boot) { this.boot = boot; return this; }
    public PveQemuConfigUpdateOptions bootdisk(String bootdisk) { this.bootdisk = bootdisk; return this; }
    public PveQemuConfigUpdateOptions cdrom(String cdrom) { this.cdrom = cdrom; return this; }
    public PveQemuConfigUpdateOptions cicustom(String cicustom) { this.cicustom = cicustom; return this; }
    public PveQemuConfigUpdateOptions cipassword(String cipassword) { this.cipassword = cipassword; return this; }
    public PveQemuConfigUpdateOptions citype(String citype) { this.citype = citype; return this; }
    public PveQemuConfigUpdateOptions ciupgrade(Boolean ciupgrade) { this.ciupgrade = ciupgrade; return this; }
    public PveQemuConfigUpdateOptions ciuser(String ciuser) { this.ciuser = ciuser; return this; }
    public PveQemuConfigUpdateOptions cores(Integer cores) { this.cores = cores; return this; }
    public PveQemuConfigUpdateOptions cpu(String cpu) { this.cpu = cpu; return this; }
    public PveQemuConfigUpdateOptions cpulimit(Double cpulimit) { this.cpulimit = cpulimit; return this; }
    public PveQemuConfigUpdateOptions cpuunits(Integer cpuunits) { this.cpuunits = cpuunits; return this; }
    public PveQemuConfigUpdateOptions delete(String delete) { this.delete = delete; return this; }
    public PveQemuConfigUpdateOptions description(String description) { this.description = description; return this; }
    public PveQemuConfigUpdateOptions digest(String digest) { this.digest = digest; return this; }
    public PveQemuConfigUpdateOptions efidisk0(String efidisk0) { this.efidisk0 = efidisk0; return this; }
    public PveQemuConfigUpdateOptions force(Boolean force) { this.force = force; return this; }
    public PveQemuConfigUpdateOptions freeze(Boolean freeze) { this.freeze = freeze; return this; }
    public PveQemuConfigUpdateOptions hookscript(String hookscript) { this.hookscript = hookscript; return this; }
    public PveQemuConfigUpdateOptions hostpci(int index, String hostpci) { putIndexed(hostpcis, index, hostpci); return this; }
    public PveQemuConfigUpdateOptions hotplug(String hotplug) { this.hotplug = validateHotplug(hotplug); return this; }
    public PveQemuConfigUpdateOptions hugepages(String hugepages) { this.hugepages = hugepages; return this; }
    public PveQemuConfigUpdateOptions ide(int index, String ide) { putIndexed(ides, index, ide); return this; }
    public PveQemuConfigUpdateOptions importWorkingStorage(String importWorkingStorage) { this.importWorkingStorage = importWorkingStorage; return this; }
    public PveQemuConfigUpdateOptions intelTdx(String intelTdx) { this.intelTdx = intelTdx; return this; }
    public PveQemuConfigUpdateOptions ipconfig(int index, String ipconfig) { putIndexed(ipconfigs, index, ipconfig); return this; }
    public PveQemuConfigUpdateOptions keephugepages(Boolean keephugepages) { this.keephugepages = keephugepages; return this; }
    public PveQemuConfigUpdateOptions keyboard(String keyboard) { this.keyboard = keyboard; return this; }
    public PveQemuConfigUpdateOptions kvm(Boolean kvm) { this.kvm = kvm; return this; }
    public PveQemuConfigUpdateOptions localtime(Boolean localtime) { this.localtime = localtime; return this; }
    public PveQemuConfigUpdateOptions lock(String lock) { this.lock = lock; return this; }
    public PveQemuConfigUpdateOptions machine(String machine) { this.machine = machine; return this; }
    public PveQemuConfigUpdateOptions memory(String memory) { this.memory = memory; return this; }
    public PveQemuConfigUpdateOptions migrateDowntime(Double migrateDowntime) { this.migrateDowntime = migrateDowntime; return this; }
    public PveQemuConfigUpdateOptions migrateSpeed(Integer migrateSpeed) { this.migrateSpeed = migrateSpeed; return this; }
    public PveQemuConfigUpdateOptions name(String name) { this.name = name; return this; }
    public PveQemuConfigUpdateOptions nameserver(String nameserver) { this.nameserver = nameserver; return this; }
    public PveQemuConfigUpdateOptions net(int index, String net) { putIndexed(nets, index, net); return this; }
    public PveQemuConfigUpdateOptions numa(Boolean numa) { this.numa = numa; return this; }
    public PveQemuConfigUpdateOptions numaConfig(int index, String numaConfig) { putIndexed(numas, index, numaConfig); return this; }
    public PveQemuConfigUpdateOptions onboot(Boolean onboot) { this.onboot = onboot; return this; }
    public PveQemuConfigUpdateOptions ostype(String ostype) { this.ostype = ostype; return this; }
    public PveQemuConfigUpdateOptions parallel(int index, String parallel) { putIndexed(parallels, index, parallel); return this; }
    public PveQemuConfigUpdateOptions protection(Boolean protection) { this.protection = protection; return this; }
    public PveQemuConfigUpdateOptions reboot(Boolean reboot) { this.reboot = reboot; return this; }
    public PveQemuConfigUpdateOptions revert(Boolean revert) { this.revert = revert; return this; }
    public PveQemuConfigUpdateOptions rng0(String rng0) { this.rng0 = rng0; return this; }
    public PveQemuConfigUpdateOptions sata(int index, String sata) { putIndexed(satas, index, sata); return this; }
    public PveQemuConfigUpdateOptions scsi(int index, String scsi) { putIndexed(scsis, index, scsi); return this; }
    public PveQemuConfigUpdateOptions scsihw(String scsihw) { this.scsihw = scsihw; return this; }
    public PveQemuConfigUpdateOptions searchdomain(String searchdomain) { this.searchdomain = searchdomain; return this; }
    public PveQemuConfigUpdateOptions serial(int index, String serial) { putIndexed(serials, index, serial); return this; }
    public PveQemuConfigUpdateOptions shares(Integer shares) { this.shares = shares; return this; }
    public PveQemuConfigUpdateOptions skiplock(Boolean skiplock) { this.skiplock = skiplock; return this; }
    public PveQemuConfigUpdateOptions smbios1(String smbios1) { this.smbios1 = smbios1; return this; }
    public PveQemuConfigUpdateOptions smp(Integer smp) { this.smp = smp; return this; }
    public PveQemuConfigUpdateOptions sockets(Integer sockets) { this.sockets = sockets; return this; }
    public PveQemuConfigUpdateOptions spiceEnhancements(String spiceEnhancements) { this.spiceEnhancements = spiceEnhancements; return this; }
    public PveQemuConfigUpdateOptions sshkeys(String sshkeys) { this.sshkeys = sshkeys; return this; }
    public PveQemuConfigUpdateOptions startdate(String startdate) { this.startdate = startdate; return this; }
    public PveQemuConfigUpdateOptions startup(String startup) { this.startup = startup; return this; }
    public PveQemuConfigUpdateOptions tablet(Boolean tablet) { this.tablet = tablet; return this; }
    public PveQemuConfigUpdateOptions tags(String tags) { this.tags = tags; return this; }
    public PveQemuConfigUpdateOptions tdf(Boolean tdf) { this.tdf = tdf; return this; }
    public PveQemuConfigUpdateOptions template(Boolean template) { this.template = template; return this; }
    public PveQemuConfigUpdateOptions tpmstate0(String tpmstate0) { this.tpmstate0 = tpmstate0; return this; }
    public PveQemuConfigUpdateOptions unused(int index, String unused) { putIndexed(this.unused, index, unused); return this; }
    public PveQemuConfigUpdateOptions usb(int index, String usb) { putIndexed(usbs, index, usb); return this; }
    public PveQemuConfigUpdateOptions vcpus(Integer vcpus) { this.vcpus = vcpus; return this; }
    public PveQemuConfigUpdateOptions vga(String vga) { this.vga = vga; return this; }
    public PveQemuConfigUpdateOptions virtio(int index, String virtio) { putIndexed(virtios, index, virtio); return this; }
    public PveQemuConfigUpdateOptions virtiofs(int index, String virtiofs) { putIndexed(virtiofss, index, virtiofs); return this; }
    public PveQemuConfigUpdateOptions vmgenid(String vmgenid) { this.vmgenid = vmgenid; return this; }
    public PveQemuConfigUpdateOptions vmstatestorage(String vmstatestorage) { this.vmstatestorage = vmstatestorage; return this; }
    public PveQemuConfigUpdateOptions watchdog(String watchdog) { this.watchdog = watchdog; return this; }

    // Helpers
    private static void put(Map<String, String> params, String key, String value) {
        if (value != null && !value.isBlank()) {
            params.put(key, value);
        }
    }

    private static void putBool(Map<String, String> params, String key, Boolean value) {
        if (value != null) {
            params.put(key, value ? "1" : "0");
        }
    }

    private static void putInt(Map<String, String> params, String key, Integer value) {
        if (value != null) {
            params.put(key, String.valueOf(value));
        }
    }

    private static void putDouble(Map<String, String> params, String key, Double value) {
        if (value != null) {
            params.put(key, String.valueOf(value));
        }
    }

    private static String validateHotplug(String value) {
        if (value == null || value.isBlank()) {
            return value;
        }
        if ("1".equals(value) || "0".equals(value)) {
            return value;
        }
        String[] parts = value.split(",");
        for (String p : parts) {
            if (!ALLOWED_HOTPLUG.contains(p.trim())) {
                throw new IllegalArgumentException("hotplug must contain only: " + String.join(", ", ALLOWED_HOTPLUG));
            }
        }
        return String.join(",", parts);
    }

    private static void putIndexed(Map<Integer, String> target, int index, String value) {
        if (index < 0) {
            throw new IllegalArgumentException("index must be >= 0");
        }
        if (value != null && !value.isBlank()) {
            target.put(index, value);
        }
    }
}

