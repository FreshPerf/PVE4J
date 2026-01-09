package fr.freshperf.proxmox4j.entities.nodes.node.qemu;

import com.google.gson.annotations.SerializedName;

/**
 * Represents the configuration of a QEMU VM.
 */
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

    // Memory
    private int memory, balloon;
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
    private String[] args;

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

    public int getMemory() {
        return memory;
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

    public String[] getArgs() {
        return args;
    }

    @Override
    public String toString() {
        return "PveQemuConfig{" +
                "name='" + name + '\'' +
                ", ostype='" + ostype + '\'' +
                ", sockets=" + sockets +
                ", cores=" + cores +
                ", memory=" + memory +
                ", boot='" + boot + '\'' +
                ", machine='" + machine + '\'' +
                '}';
    }
}

