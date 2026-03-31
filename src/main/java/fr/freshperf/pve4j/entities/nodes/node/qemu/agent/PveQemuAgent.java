package fr.freshperf.pve4j.entities.nodes.node.qemu.agent;

import fr.freshperf.pve4j.entities.nodes.node.qemu.agent.hostname.PveQemuAgentHostNameResult;
import fr.freshperf.pve4j.entities.nodes.node.qemu.agent.fsfreeze.PveQemuAgentFsFreezeResult;
import fr.freshperf.pve4j.entities.nodes.node.qemu.agent.fsfreeze.PveQemuAgentFsFreezeStatusResult;
import fr.freshperf.pve4j.entities.nodes.node.qemu.agent.fsinfo.PveQemuAgentFsInfoResult;
import fr.freshperf.pve4j.entities.nodes.node.qemu.agent.fstrim.PveQemuAgentFstrimResult;
import fr.freshperf.pve4j.entities.nodes.node.qemu.agent.info.PveQemuAgentInfoResult;
import fr.freshperf.pve4j.entities.nodes.node.qemu.agent.networkinterfaces.PveQemuAgentNetworkInterfacesResult;
import fr.freshperf.pve4j.entities.nodes.node.qemu.agent.osinfo.PveQemuAgentOsInfoResult;
import fr.freshperf.pve4j.entities.nodes.node.qemu.agent.ping.PveQemuAgentPingResult;
import fr.freshperf.pve4j.entities.nodes.node.qemu.agent.time.PveQemuAgentTimeResult;
import fr.freshperf.pve4j.entities.nodes.node.qemu.agent.timezone.PveQemuAgentTimezoneResult;
import fr.freshperf.pve4j.entities.nodes.node.qemu.agent.users.PveQemuAgentUsersResult;
import fr.freshperf.pve4j.entities.nodes.node.qemu.agent.vcpus.PveQemuAgentVcpusResult;
import fr.freshperf.pve4j.request.ProxmoxHttpClient;
import fr.freshperf.pve4j.request.ProxmoxRequest;

/**
 * Facade for accessing all guest agent commands of a QEMU VM.
 */
public record PveQemuAgent (ProxmoxHttpClient client, String nodeName, int vmid) {

    private String path(String suffix) {
        return "nodes/" + nodeName + "/qemu/" + vmid + "/agent/" + suffix;
    }

    private <T> ProxmoxRequest<T> get(String suffix, Class<T> clazz) {
        return new ProxmoxRequest<>(() -> client.get(path(suffix)).execute(clazz));
    }

    private <T> ProxmoxRequest<T> post(String suffix, Class<T> clazz) {
        return new ProxmoxRequest<>(() -> client.post(path(suffix)).execute(clazz));
    }

    private ProxmoxRequest<Void> postWithoutResult(String suffix) {
        return new ProxmoxRequest<>(() -> {
            client.post(path(suffix)).execute();
            return null;
        });
    }

    /**
     * Gets guest agent general information and supported commands.
     *
     * @return a request returning guest agent information
     */
    public ProxmoxRequest<PveQemuAgentInfoResult> getInfo() {
        return get("info", PveQemuAgentInfoResult.class);
    }

    /**
     * Gets the guest hostname.
     *
     * @return a request returning the hostname
     */
    public ProxmoxRequest<PveQemuAgentHostNameResult> getHostName() {
        return get("get-host-name", PveQemuAgentHostNameResult.class);
    }

    /**
     * Gets mounted filesystem information reported by the guest agent.
     *
     * @return a request returning filesystem information
     */
    public ProxmoxRequest<PveQemuAgentFsInfoResult> getFsInfo() {
        return get("get-fsinfo", PveQemuAgentFsInfoResult.class);
    }

    /**
     * Gets guest operating system information.
     *
     * @return a request returning OS information
     */
    public ProxmoxRequest<PveQemuAgentOsInfoResult> getOsInfo() {
        return get("get-osinfo", PveQemuAgentOsInfoResult.class);
    }

    /**
     * Gets the guest current time.
     *
     * @return a request returning the guest time
     */
    public ProxmoxRequest<PveQemuAgentTimeResult> getTime() {
        return get("get-time", PveQemuAgentTimeResult.class);
    }

    /**
     * Gets the guest current timezone.
     *
     * @return a request returning the guest timezone
     */
    public ProxmoxRequest<PveQemuAgentTimezoneResult> getTimezone() {
        return get("get-timezone", PveQemuAgentTimezoneResult.class);
    }

    /**
     * Gets the logged-in guest users.
     *
     * @return a request returning guest users
     */
    public ProxmoxRequest<PveQemuAgentUsersResult> getUsers() {
        return get("get-users", PveQemuAgentUsersResult.class);
    }

    /**
     * Gets the guest vCPU status.
     *
     * @return a request returning guest vCPU information
     */
    public ProxmoxRequest<PveQemuAgentVcpusResult> getVcpus() {
        return get("get-vcpus", PveQemuAgentVcpusResult.class);
    }

    /**
     * Gets network interfaces reported by the guest agent.
     *
     * @return a request returning guest network interfaces
     */
    public ProxmoxRequest<PveQemuAgentNetworkInterfacesResult> getNetworkInterfaces() {
        return get("network-get-interfaces", PveQemuAgentNetworkInterfacesResult.class);
    }

    /**
     * Gets the guest filesystem freeze state.
     *
     * @return a request returning the current freeze status
     */
    public ProxmoxRequest<PveQemuAgentFsFreezeStatusResult> getFsFreezeStatus() {
        return post("fsfreeze-status", PveQemuAgentFsFreezeStatusResult.class);
    }

    /**
     * Freezes guest filesystems and returns the number of frozen filesystems.
     *
     * @return a request returning the number of frozen filesystems
     */
    public ProxmoxRequest<PveQemuAgentFsFreezeResult> fsFreeze() {
        return post("fsfreeze-freeze", PveQemuAgentFsFreezeResult.class);
    }

    /**
     * Thaws guest filesystems and returns the number of thawed filesystems.
     *
     * @return a request returning the number of thawed filesystems
     */
    public ProxmoxRequest<PveQemuAgentFsFreezeResult> fsThaw() {
        return post("fsfreeze-thaw", PveQemuAgentFsFreezeResult.class);
    }

    /**
     * Trims guest filesystems and returns trim status per path.
     *
     * @return a request returning trim status per filesystem path
     */
    public ProxmoxRequest<PveQemuAgentFstrimResult> fstrim() {
        return post("fstrim", PveQemuAgentFstrimResult.class);
    }

    /**
     * Pings the guest agent.
     *
     * @return a request returning the guest agent ping result
     */
    public ProxmoxRequest<PveQemuAgentPingResult> ping() {
        return post("ping", PveQemuAgentPingResult.class);
    }

    /**
     * Requests a guest shutdown through the guest agent.
     *
     * @return a request that completes when the shutdown command is accepted
     */
    public ProxmoxRequest<Void> shutdown() {
        return postWithoutResult("shutdown");
    }

}
