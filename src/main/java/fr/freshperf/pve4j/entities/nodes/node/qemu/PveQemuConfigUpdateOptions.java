package fr.freshperf.pve4j.entities.nodes.node.qemu;

import fr.freshperf.pve4j.entities.options.ParamsConvertible;
import fr.freshperf.pve4j.util.ParamsHelpers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Options for updating a QEMU VM configuration.
 * Use the builder pattern to set configuration options.
 */
public class PveQemuConfigUpdateOptions implements ParamsConvertible {

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

    /**
     * Creates a new builder for config update options.
     *
     * @return a new PveQemuConfigUpdateOptions instance
     */
    public static PveQemuConfigUpdateOptions builder() {
        return new PveQemuConfigUpdateOptions();
    }

    @Override
    public void populateParams(Map<String, Object> params) {
        ParamsHelpers.putBool(params, "acpi", acpi);
        ParamsHelpers.put(params, "affinity", affinity);
        ParamsHelpers.put(params, "agent", agent);
        ParamsHelpers.putBool(params, "allow-ksm", allowKsm);
        ParamsHelpers.put(params, "amd-sev", amdSev);
        ParamsHelpers.put(params, "arch", arch);
        ParamsHelpers.put(params, "args", args);
        ParamsHelpers.put(params, "audio0", audio0);
        ParamsHelpers.putBool(params, "autostart", autostart);
        ParamsHelpers.putInt(params, "background_delay", backgroundDelay);
        ParamsHelpers.putInt(params, "balloon", balloon);
        ParamsHelpers.put(params, "bios", bios);
        ParamsHelpers.put(params, "boot", boot);
        ParamsHelpers.put(params, "bootdisk", bootdisk);
        ParamsHelpers.put(params, "cdrom", cdrom);
        ParamsHelpers.put(params, "cicustom", cicustom);
        ParamsHelpers.put(params, "cipassword", cipassword);
        ParamsHelpers.put(params, "citype", citype);
        ParamsHelpers.putBool(params, "ciupgrade", ciupgrade);
        ParamsHelpers.put(params, "ciuser", ciuser);
        ParamsHelpers.putInt(params, "cores", cores);
        ParamsHelpers.put(params, "cpu", cpu);
        ParamsHelpers.putDouble(params, "cpulimit", cpulimit);
        ParamsHelpers.putInt(params, "cpuunits", cpuunits);
        ParamsHelpers.put(params, "delete", delete);
        ParamsHelpers.put(params, "description", description);
        ParamsHelpers.put(params, "digest", digest);
        ParamsHelpers.put(params, "efidisk0", efidisk0);
        ParamsHelpers.putBool(params, "force", force);
        ParamsHelpers.putBool(params, "freeze", freeze);
        ParamsHelpers.put(params, "hookscript", hookscript);
        hostpcis.forEach((i, v) -> ParamsHelpers.put(params, "hostpci" + i, v));
        ParamsHelpers.put(params, "hotplug", hotplug);
        ParamsHelpers.put(params, "hugepages", hugepages);
        ides.forEach((i, v) -> ParamsHelpers.put(params, "ide" + i, v));
        ParamsHelpers.put(params, "import-working-storage", importWorkingStorage);
        ParamsHelpers.put(params, "intel-tdx", intelTdx);
        ipconfigs.forEach((i, v) -> ParamsHelpers.put(params, "ipconfig" + i, v));
        ParamsHelpers.putBool(params, "keephugepages", keephugepages);
        ParamsHelpers.put(params, "keyboard", keyboard);
        ParamsHelpers.putBool(params, "kvm", kvm);
        ParamsHelpers.putBool(params, "localtime", localtime);
        ParamsHelpers.put(params, "lock", lock);
        ParamsHelpers.put(params, "machine", machine);
        ParamsHelpers.put(params, "memory", memory);
        ParamsHelpers.putDouble(params, "migrate_downtime", migrateDowntime);
        ParamsHelpers.putInt(params, "migrate_speed", migrateSpeed);
        ParamsHelpers.put(params, "name", name);
        ParamsHelpers.put(params, "nameserver", nameserver);
        nets.forEach((i, v) -> ParamsHelpers.put(params, "net" + i, v));
        ParamsHelpers.putBool(params, "numa", numa);
        numas.forEach((i, v) -> ParamsHelpers.put(params, "numa" + i, v));
        ParamsHelpers.putBool(params, "onboot", onboot);
        ParamsHelpers.put(params, "ostype", ostype);
        parallels.forEach((i, v) -> ParamsHelpers.put(params, "parallel" + i, v));
        ParamsHelpers.putBool(params, "protection", protection);
        ParamsHelpers.putBool(params, "reboot", reboot);
        ParamsHelpers.putBool(params, "revert", revert);
        ParamsHelpers.put(params, "rng0", rng0);
        satas.forEach((i, v) -> ParamsHelpers.put(params, "sata" + i, v));
        scsis.forEach((i, v) -> ParamsHelpers.put(params, "scsi" + i, v));
        ParamsHelpers.put(params, "scsihw", scsihw);
        ParamsHelpers.put(params, "searchdomain", searchdomain);
        serials.forEach((i, v) -> ParamsHelpers.put(params, "serial" + i, v));
        ParamsHelpers.putInt(params, "shares", shares);
        ParamsHelpers.putBool(params, "skiplock", skiplock);
        ParamsHelpers.put(params, "smbios1", smbios1);
        ParamsHelpers.putInt(params, "smp", smp);
        ParamsHelpers.putInt(params, "sockets", sockets);
        ParamsHelpers.put(params, "spice_enhancements", spiceEnhancements);
        ParamsHelpers.put(params, "sshkeys", sshkeys);
        ParamsHelpers.put(params, "startdate", startdate);
        ParamsHelpers.put(params, "startup", startup);
        ParamsHelpers.putBool(params, "tablet", tablet);
        ParamsHelpers.put(params, "tags", tags);
        ParamsHelpers.putBool(params, "tdf", tdf);
        ParamsHelpers.putBool(params, "template", template);
        ParamsHelpers.put(params, "tpmstate0", tpmstate0);
        unused.forEach((i, v) -> ParamsHelpers.put(params, "unused" + i, v));
        usbs.forEach((i, v) -> ParamsHelpers.put(params, "usb" + i, v));
        ParamsHelpers.putInt(params, "vcpus", vcpus);
        ParamsHelpers.put(params, "vga", vga);
        virtios.forEach((i, v) -> ParamsHelpers.put(params, "virtio" + i, v));
        virtiofss.forEach((i, v) -> ParamsHelpers.put(params, "virtiofs" + i, v));
        ParamsHelpers.put(params, "vmgenid", vmgenid);
        ParamsHelpers.put(params, "vmstatestorage", vmstatestorage);
        ParamsHelpers.put(params, "watchdog", watchdog);
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
    public PveQemuConfigUpdateOptions hostpci(int index, String hostpci) { ParamsHelpers.putIndexed(hostpcis, index, hostpci); return this; }
    public PveQemuConfigUpdateOptions hotplug(String hotplug) { this.hotplug = validateHotplug(hotplug); return this; }
    public PveQemuConfigUpdateOptions hugepages(String hugepages) { this.hugepages = hugepages; return this; }
    public PveQemuConfigUpdateOptions ide(int index, String ide) { ParamsHelpers.putIndexed(ides, index, ide); return this; }
    public PveQemuConfigUpdateOptions importWorkingStorage(String importWorkingStorage) { this.importWorkingStorage = importWorkingStorage; return this; }
    public PveQemuConfigUpdateOptions intelTdx(String intelTdx) { this.intelTdx = intelTdx; return this; }
    public PveQemuConfigUpdateOptions ipconfig(int index, String ipconfig) { ParamsHelpers.putIndexed(ipconfigs, index, ipconfig); return this; }
    public PveQemuConfigUpdateOptions keephugepages(Boolean keephugepages) { this.keephugepages = keephugepages; return this; }
    public PveQemuConfigUpdateOptions keyboard(String keyboard) { this.keyboard = keyboard; return this; }
    public PveQemuConfigUpdateOptions kvm(Boolean kvm) { this.kvm = kvm; return this; }
    public PveQemuConfigUpdateOptions localtime(Boolean localtime) { this.localtime = localtime; return this; }
    public PveQemuConfigUpdateOptions lock(String lock) { this.lock = lock; return this; }
    public PveQemuConfigUpdateOptions machine(String machine) { this.machine = machine; return this; }

    @Deprecated(since = "0.1.2")
    /*
    *   Deprecated: Please use #memory(int); instead of the confusing String.
    *
    * */
    public PveQemuConfigUpdateOptions memory(String memory) { this.memory = memory; return this; }

    public PveQemuConfigUpdateOptions memory(int memory) { this.memory = String.valueOf(memory); return this; }
    public PveQemuConfigUpdateOptions migrateDowntime(Double migrateDowntime) { this.migrateDowntime = migrateDowntime; return this; }
    public PveQemuConfigUpdateOptions migrateSpeed(Integer migrateSpeed) { this.migrateSpeed = migrateSpeed; return this; }
    public PveQemuConfigUpdateOptions name(String name) { this.name = name; return this; }
    public PveQemuConfigUpdateOptions nameserver(String nameserver) { this.nameserver = nameserver; return this; }
    public PveQemuConfigUpdateOptions net(int index, String net) { ParamsHelpers.putIndexed(nets, index, net); return this; }
    public PveQemuConfigUpdateOptions numa(Boolean numa) { this.numa = numa; return this; }
    public PveQemuConfigUpdateOptions numaConfig(int index, String numaConfig) { ParamsHelpers.putIndexed(numas, index, numaConfig); return this; }
    public PveQemuConfigUpdateOptions onboot(Boolean onboot) { this.onboot = onboot; return this; }
    public PveQemuConfigUpdateOptions ostype(String ostype) { this.ostype = ostype; return this; }
    public PveQemuConfigUpdateOptions parallel(int index, String parallel) { ParamsHelpers.putIndexed(parallels, index, parallel); return this; }
    public PveQemuConfigUpdateOptions protection(Boolean protection) { this.protection = protection; return this; }
    public PveQemuConfigUpdateOptions reboot(Boolean reboot) { this.reboot = reboot; return this; }
    public PveQemuConfigUpdateOptions revert(Boolean revert) { this.revert = revert; return this; }
    public PveQemuConfigUpdateOptions rng0(String rng0) { this.rng0 = rng0; return this; }
    public PveQemuConfigUpdateOptions sata(int index, String sata) { ParamsHelpers.putIndexed(satas, index, sata); return this; }
    public PveQemuConfigUpdateOptions scsi(int index, String scsi) { ParamsHelpers.putIndexed(scsis, index, scsi); return this; }
    public PveQemuConfigUpdateOptions scsihw(String scsihw) { this.scsihw = scsihw; return this; }
    public PveQemuConfigUpdateOptions searchdomain(String searchdomain) { this.searchdomain = searchdomain; return this; }
    public PveQemuConfigUpdateOptions serial(int index, String serial) { ParamsHelpers.putIndexed(serials, index, serial); return this; }
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
    public PveQemuConfigUpdateOptions unused(int index, String unused) { ParamsHelpers.putIndexed(this.unused, index, unused); return this; }
    public PveQemuConfigUpdateOptions usb(int index, String usb) { ParamsHelpers.putIndexed(usbs, index, usb); return this; }
    public PveQemuConfigUpdateOptions vcpus(Integer vcpus) { this.vcpus = vcpus; return this; }
    public PveQemuConfigUpdateOptions vga(String vga) { this.vga = vga; return this; }
    public PveQemuConfigUpdateOptions virtio(int index, String virtio) { ParamsHelpers.putIndexed(virtios, index, virtio); return this; }
    public PveQemuConfigUpdateOptions virtiofs(int index, String virtiofs) { ParamsHelpers.putIndexed(virtiofss, index, virtiofs); return this; }
    public PveQemuConfigUpdateOptions vmgenid(String vmgenid) { this.vmgenid = vmgenid; return this; }
    public PveQemuConfigUpdateOptions vmstatestorage(String vmstatestorage) { this.vmstatestorage = vmstatestorage; return this; }
    public PveQemuConfigUpdateOptions watchdog(String watchdog) { this.watchdog = watchdog; return this; }

    // Helpers
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
}

