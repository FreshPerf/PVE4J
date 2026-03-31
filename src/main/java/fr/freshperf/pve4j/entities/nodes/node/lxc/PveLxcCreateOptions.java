package fr.freshperf.pve4j.entities.nodes.node.lxc;

import fr.freshperf.pve4j.entities.options.ParameterizedParamsConvertible;
import fr.freshperf.pve4j.util.ParamsHelpers;

import java.util.HashMap;
import java.util.Map;

/**
 * Options for creating an LXC container.
 * Use the builder pattern to configure container settings.
 */
public class PveLxcCreateOptions implements ParameterizedParamsConvertible<Integer> {

    private String ostemplate;
    private String hostname;
    private String description;
    private Integer memory;
    private Integer swap;
    private Integer cores;
    private String rootfs;
    private String password;
    private String sshPublicKeys;
    private Boolean unprivileged;
    private Boolean onboot;
    private Boolean start;
    private String pool;
    private String nameserver;
    private String searchdomain;
    private String arch;
    private Boolean template;
    
    // Mount point configurations (mp0, mp1, etc.)
    private final Map<String, String> mountpoints = new HashMap<>();
    
    // Network configurations (net0, net1, etc.)
    private final Map<String, String> networks = new HashMap<>();

    /**
     * Creates a new builder for container create options.
     *
     * @return a new PveLxcCreateOptions instance
     */
    public static PveLxcCreateOptions builder() {
        return new PveLxcCreateOptions();
    }

    @Override
    public void addRequiredParam(Map<String, Object> params, Integer vmid) {
        params.put("vmid", String.valueOf(vmid));
    }

    @Override
    public void populateParams(Map<String, Object> params) {
        if (ostemplate == null || ostemplate.isBlank()) {
            throw new IllegalArgumentException("ostemplate cannot be null or empty");
        }
        ParamsHelpers.put(params, "ostemplate", ostemplate);
        ParamsHelpers.put(params, "hostname", hostname);
        ParamsHelpers.put(params, "description", description);
        ParamsHelpers.putInt(params, "memory", memory);
        ParamsHelpers.putInt(params, "swap", swap);
        ParamsHelpers.putInt(params, "cores", cores);
        ParamsHelpers.put(params, "rootfs", rootfs);
        ParamsHelpers.put(params, "password", password);
        ParamsHelpers.put(params, "ssh-public-keys", sshPublicKeys);
        ParamsHelpers.putBool(params, "unprivileged", unprivileged);
        ParamsHelpers.putBool(params, "onboot", onboot);
        ParamsHelpers.putBool(params, "start", start);
        ParamsHelpers.put(params, "pool", pool);
        ParamsHelpers.put(params, "nameserver", nameserver);
        ParamsHelpers.put(params, "searchdomain", searchdomain);
        ParamsHelpers.put(params, "arch", arch);
        ParamsHelpers.putBool(params, "template", template);
        
        // Add mount point configurations
        for (Map.Entry<String, String> entry : mountpoints.entrySet()) {
            params.put(entry.getKey(), entry.getValue());
        }
        
        // Add network configurations
        for (Map.Entry<String, String> entry : networks.entrySet()) {
            params.put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Sets the OS template (REQUIRED).
     *
     * @param ostemplate the template location (e.g., "local:vztmpl/ubuntu-22.04-standard_22.04-1_amd64.tar.zst")
     * @return this instance for method chaining
     */
    public PveLxcCreateOptions ostemplate(String ostemplate) {
        this.ostemplate = ostemplate;
        return this;
    }

    /** Indicates whether the required ostemplate was configured. */
    public boolean hasOstemplate() {
        return ostemplate != null && !ostemplate.isBlank();
    }

    /** Sets the container hostname. */
    public PveLxcCreateOptions hostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    /** Sets the container description. */
    public PveLxcCreateOptions description(String description) {
        this.description = description;
        return this;
    }

    /** Sets the memory in MB. */
    public PveLxcCreateOptions memory(Integer memory) {
        this.memory = memory;
        return this;
    }

    /** Sets the swap memory in MB. */
    public PveLxcCreateOptions swap(Integer swap) {
        this.swap = swap;
        return this;
    }

    /** Sets the number of CPU cores. */
    public PveLxcCreateOptions cores(Integer cores) {
        this.cores = cores;
        return this;
    }

    /**
     * Sets the root filesystem configuration.
     *
     * @param rootfs the rootfs config (e.g., "local-lvm:8" for 8GB on local-lvm)
     * @return this instance for method chaining
     */
    public PveLxcCreateOptions rootfs(String rootfs) {
        this.rootfs = rootfs;
        return this;
    }

    /** Sets the root password. */
    public PveLxcCreateOptions password(String password) {
        this.password = password;
        return this;
    }

    /** Sets the SSH public keys (one per line). */
    public PveLxcCreateOptions sshPublicKeys(String sshPublicKeys) {
        this.sshPublicKeys = sshPublicKeys;
        return this;
    }

    /** Sets whether to create an unprivileged container (recommended). */
    public PveLxcCreateOptions unprivileged(Boolean unprivileged) {
        this.unprivileged = unprivileged;
        return this;
    }

    /** Sets whether to start container on boot. */
    public PveLxcCreateOptions onboot(Boolean onboot) {
        this.onboot = onboot;
        return this;
    }

    /** Sets whether to start container after creation. */
    public PveLxcCreateOptions start(Boolean start) {
        this.start = start;
        return this;
    }

    /** Sets the resource pool to add this container to. */
    public PveLxcCreateOptions pool(String pool) {
        this.pool = pool;
        return this;
    }

    /** Sets the DNS server. */
    public PveLxcCreateOptions nameserver(String nameserver) {
        this.nameserver = nameserver;
        return this;
    }

    /** Sets the DNS search domain. */
    public PveLxcCreateOptions searchdomain(String searchdomain) {
        this.searchdomain = searchdomain;
        return this;
    }

    /** Sets the architecture (amd64, i386, arm64, armhf). */
    public PveLxcCreateOptions arch(String arch) {
        this.arch = arch;
        return this;
    }

    /** Sets whether to create as template. */
    public PveLxcCreateOptions template(Boolean template) {
        this.template = template;
        return this;
    }

    /**
     * Adds a mount point configuration.
     *
     * @param index  the mount point index (0, 1, 2, etc.)
     * @param config the mount point configuration (e.g., "local-lvm:10,mp=/data")
     * @return this instance for method chaining
     */
    public PveLxcCreateOptions mp(int index, String config) {
        if (index < 0) {
            throw new IllegalArgumentException("mount point index must be >= 0");
        }
        mountpoints.put("mp" + index, config);
        return this;
    }

    /**
     * Adds a network interface configuration.
     *
     * @param index  the network index (0, 1, 2, etc.)
     * @param config the network configuration (e.g., "name=eth0,bridge=vmbr0,ip=dhcp")
     * @return this instance for method chaining
     */
    public PveLxcCreateOptions net(int index, String config) {
        if (index < 0) {
            throw new IllegalArgumentException("network index must be >= 0");
        }
        networks.put("net" + index, config);
        return this;
    }

    /**
     * Builds and returns the options instance.
     *
     * @return this instance
     */
    public PveLxcCreateOptions build() {
        return this;
    }
}

