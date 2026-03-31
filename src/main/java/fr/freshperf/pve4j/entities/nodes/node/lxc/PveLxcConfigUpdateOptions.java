package fr.freshperf.pve4j.entities.nodes.node.lxc;

import fr.freshperf.pve4j.entities.options.ParamsConvertible;
import fr.freshperf.pve4j.util.ParamsHelpers;

import java.util.HashMap;
import java.util.Map;

/**
 * Options for updating an LXC container configuration.
 */
public class PveLxcConfigUpdateOptions implements ParamsConvertible {

    private String arch;
    private String cmode;
    private Boolean console;
    private Integer cores;
    private String delete;
    private String description;
    private String digest;
    private String features;
    private String hostname;
    private Integer memory;
    private String nameserver;
    private Boolean onboot;
    private String ostype;
    private String password;
    private String pool;
    private Boolean protection;
    private String revert;
    private String rootfs;
    private String searchdomain;
    private String sshPublicKeys;
    private String startup;
    private Integer swap;
    private String tags;
    private Boolean template;
    private String timezone;
    private Integer tty;
    private Boolean unprivileged;
    private final Map<String, String> mountpoints = new HashMap<>();
    private final Map<String, String> networks = new HashMap<>();
    private final Map<String, String> unusedVolumes = new HashMap<>();

    public static PveLxcConfigUpdateOptions builder() {
        return new PveLxcConfigUpdateOptions();
    }

    @Override
    public void populateParams(Map<String, Object> params) {
        ParamsHelpers.put(params, "arch", arch);
        ParamsHelpers.put(params, "cmode", cmode);
        ParamsHelpers.putBool(params, "console", console);
        ParamsHelpers.putInt(params, "cores", cores);
        ParamsHelpers.put(params, "delete", delete);
        ParamsHelpers.put(params, "description", description);
        ParamsHelpers.put(params, "digest", digest);
        ParamsHelpers.put(params, "features", features);
        ParamsHelpers.put(params, "hostname", hostname);
        ParamsHelpers.putInt(params, "memory", memory);
        ParamsHelpers.put(params, "nameserver", nameserver);
        ParamsHelpers.putBool(params, "onboot", onboot);
        ParamsHelpers.put(params, "ostype", ostype);
        ParamsHelpers.put(params, "password", password);
        ParamsHelpers.put(params, "pool", pool);
        ParamsHelpers.putBool(params, "protection", protection);
        ParamsHelpers.put(params, "revert", revert);
        ParamsHelpers.put(params, "rootfs", rootfs);
        ParamsHelpers.put(params, "searchdomain", searchdomain);
        ParamsHelpers.put(params, "ssh-public-keys", sshPublicKeys);
        ParamsHelpers.put(params, "startup", startup);
        ParamsHelpers.putInt(params, "swap", swap);
        ParamsHelpers.put(params, "tags", tags);
        ParamsHelpers.putBool(params, "template", template);
        ParamsHelpers.put(params, "timezone", timezone);
        ParamsHelpers.putInt(params, "tty", tty);
        ParamsHelpers.putBool(params, "unprivileged", unprivileged);

        for (Map.Entry<String, String> entry : mountpoints.entrySet()) {
            params.put(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, String> entry : networks.entrySet()) {
            params.put(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, String> entry : unusedVolumes.entrySet()) {
            params.put(entry.getKey(), entry.getValue());
        }
    }

    public PveLxcConfigUpdateOptions arch(String arch) { this.arch = arch; return this; }
    public PveLxcConfigUpdateOptions cmode(String cmode) { this.cmode = cmode; return this; }
    public PveLxcConfigUpdateOptions console(Boolean console) { this.console = console; return this; }
    public PveLxcConfigUpdateOptions cores(Integer cores) { this.cores = cores; return this; }
    public PveLxcConfigUpdateOptions delete(String delete) { this.delete = delete; return this; }
    public PveLxcConfigUpdateOptions description(String description) { this.description = description; return this; }
    public PveLxcConfigUpdateOptions digest(String digest) { this.digest = digest; return this; }
    public PveLxcConfigUpdateOptions features(String features) { this.features = features; return this; }
    public PveLxcConfigUpdateOptions hostname(String hostname) { this.hostname = hostname; return this; }
    public PveLxcConfigUpdateOptions memory(Integer memory) { this.memory = memory; return this; }
    public PveLxcConfigUpdateOptions nameserver(String nameserver) { this.nameserver = nameserver; return this; }
    public PveLxcConfigUpdateOptions onboot(Boolean onboot) { this.onboot = onboot; return this; }
    public PveLxcConfigUpdateOptions ostype(String ostype) { this.ostype = ostype; return this; }
    public PveLxcConfigUpdateOptions password(String password) { this.password = password; return this; }
    public PveLxcConfigUpdateOptions pool(String pool) { this.pool = pool; return this; }
    public PveLxcConfigUpdateOptions protection(Boolean protection) { this.protection = protection; return this; }
    public PveLxcConfigUpdateOptions revert(String revert) { this.revert = revert; return this; }
    public PveLxcConfigUpdateOptions rootfs(String rootfs) { this.rootfs = rootfs; return this; }
    public PveLxcConfigUpdateOptions searchdomain(String searchdomain) { this.searchdomain = searchdomain; return this; }
    public PveLxcConfigUpdateOptions sshPublicKeys(String sshPublicKeys) { this.sshPublicKeys = sshPublicKeys; return this; }
    public PveLxcConfigUpdateOptions startup(String startup) { this.startup = startup; return this; }
    public PveLxcConfigUpdateOptions swap(Integer swap) { this.swap = swap; return this; }
    public PveLxcConfigUpdateOptions tags(String tags) { this.tags = tags; return this; }
    public PveLxcConfigUpdateOptions template(Boolean template) { this.template = template; return this; }
    public PveLxcConfigUpdateOptions timezone(String timezone) { this.timezone = timezone; return this; }
    public PveLxcConfigUpdateOptions tty(Integer tty) { this.tty = tty; return this; }
    public PveLxcConfigUpdateOptions unprivileged(Boolean unprivileged) { this.unprivileged = unprivileged; return this; }

    public PveLxcConfigUpdateOptions mp(int index, String config) {
        if (index < 0) {
            throw new IllegalArgumentException("mount point index must be >= 0");
        }
        mountpoints.put("mp" + index, config);
        return this;
    }

    public PveLxcConfigUpdateOptions net(int index, String config) {
        if (index < 0) {
            throw new IllegalArgumentException("network index must be >= 0");
        }
        networks.put("net" + index, config);
        return this;
    }

    public PveLxcConfigUpdateOptions unused(int index, String volume) {
        if (index < 0) {
            throw new IllegalArgumentException("unused volume index must be >= 0");
        }
        unusedVolumes.put("unused" + index, volume);
        return this;
    }
}
