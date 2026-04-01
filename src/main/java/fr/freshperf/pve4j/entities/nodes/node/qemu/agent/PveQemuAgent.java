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

    /**
     * Gets guest agent general information and supported commands.
     *
     * @return a request returning guest agent information
     */
    public ProxmoxRequest<PveQemuAgentInfoResult> getInfo() {
        return new ProxmoxRequest<>(() ->
            client.get("nodes/" + nodeName + "/qemu/" + vmid + "/agent/info")
                .execute(PveQemuAgentInfoResult.class)
        );
    }

    /**
     * Gets the guest hostname.
     *
     * @return a request returning the hostname
     */
    public ProxmoxRequest<PveQemuAgentHostNameResult> getHostName() {
        return new ProxmoxRequest<>(() ->
            client.get("nodes/" + nodeName + "/qemu/" + vmid + "/agent/get-host-name")
                .execute(PveQemuAgentHostNameResult.class)
        );
    }

    /**
     * Gets mounted filesystem information reported by the guest agent.
     *
     * @return a request returning filesystem information
     */
    public ProxmoxRequest<PveQemuAgentFsInfoResult> getFsInfo() {
        return new ProxmoxRequest<>(() ->
            client.get("nodes/" + nodeName + "/qemu/" + vmid + "/agent/get-fsinfo")
                .execute(PveQemuAgentFsInfoResult.class)
        );
    }

    /**
     * Gets guest operating system information.
     *
     * @return a request returning OS information
     */
    public ProxmoxRequest<PveQemuAgentOsInfoResult> getOsInfo() {
        return new ProxmoxRequest<>(() ->
            client.get("nodes/" + nodeName + "/qemu/" + vmid + "/agent/get-osinfo")
                .execute(PveQemuAgentOsInfoResult.class)
        );
    }

    /**
     * Gets the guest current time.
     *
     * @return a request returning the guest time
     */
    public ProxmoxRequest<PveQemuAgentTimeResult> getTime() {
        return new ProxmoxRequest<>(() ->
            client.get("nodes/" + nodeName + "/qemu/" + vmid + "/agent/get-time")
                .execute(PveQemuAgentTimeResult.class)
        );
    }

    /**
     * Gets the guest current timezone.
     *
     * @return a request returning the guest timezone
     */
    public ProxmoxRequest<PveQemuAgentTimezoneResult> getTimezone() {
        return new ProxmoxRequest<>(() ->
            client.get("nodes/" + nodeName + "/qemu/" + vmid + "/agent/get-timezone")
                .execute(PveQemuAgentTimezoneResult.class)
        );
    }

    /**
     * Gets the logged-in guest users.
     *
     * @return a request returning guest users
     */
    public ProxmoxRequest<PveQemuAgentUsersResult> getUsers() {
        return new ProxmoxRequest<>(() ->
            client.get("nodes/" + nodeName + "/qemu/" + vmid + "/agent/get-users")
                .execute(PveQemuAgentUsersResult.class)
        );
    }

    /**
     * Gets the guest vCPU status.
     *
     * @return a request returning guest vCPU information
     */
    public ProxmoxRequest<PveQemuAgentVcpusResult> getVcpus() {
        return new ProxmoxRequest<>(() ->
            client.get("nodes/" + nodeName + "/qemu/" + vmid + "/agent/get-vcpus")
                .execute(PveQemuAgentVcpusResult.class)
        );
    }

    /**
     * Gets network interfaces reported by the guest agent.
     *
     * @return a request returning guest network interfaces
     */
    public ProxmoxRequest<PveQemuAgentNetworkInterfacesResult> getNetworkInterfaces() {
        return new ProxmoxRequest<>(() ->
            client.get("nodes/" + nodeName + "/qemu/" + vmid + "/agent/network-get-interfaces")
                .execute(PveQemuAgentNetworkInterfacesResult.class)
        );
    }

    /**
     * Gets the guest filesystem freeze state.
     *
     * @return a request returning the current freeze status
     */
    public ProxmoxRequest<PveQemuAgentFsFreezeStatusResult> getFsFreezeStatus() {
        return new ProxmoxRequest<>(() ->
            client.post("nodes/" + nodeName + "/qemu/" + vmid + "/agent/fsfreeze-status")
                .execute(PveQemuAgentFsFreezeStatusResult.class)
        );
    }

    /**
     * Freezes guest filesystems and returns the number of frozen filesystems.
     *
     * @return a request returning the number of frozen filesystems
     */
    public ProxmoxRequest<PveQemuAgentFsFreezeResult> fsFreeze() {
        return new ProxmoxRequest<>(() ->
            client.post("nodes/" + nodeName + "/qemu/" + vmid + "/agent/fsfreeze-freeze")
                .execute(PveQemuAgentFsFreezeResult.class)
        );
    }

    /**
     * Thaws guest filesystems and returns the number of thawed filesystems.
     *
     * @return a request returning the number of thawed filesystems
     */
    public ProxmoxRequest<PveQemuAgentFsFreezeResult> fsThaw() {
        return new ProxmoxRequest<>(() ->
            client.post("nodes/" + nodeName + "/qemu/" + vmid + "/agent/fsfreeze-thaw")
                .execute(PveQemuAgentFsFreezeResult.class)
        );
    }

    /**
     * Trims guest filesystems and returns trim status per path.
     *
     * @return a request returning trim status per filesystem path
     */
    public ProxmoxRequest<PveQemuAgentFstrimResult> fstrim() {
        return new ProxmoxRequest<>(() ->
            client.post("nodes/" + nodeName + "/qemu/" + vmid + "/agent/fstrim")
                .execute(PveQemuAgentFstrimResult.class)
        );
    }

    /**
     * Pings the guest agent.
     *
     * @return a request returning the guest agent ping result
     */
    public ProxmoxRequest<PveQemuAgentPingResult> ping() {
        return new ProxmoxRequest<>(() ->
            client.post("nodes/" + nodeName + "/qemu/" + vmid + "/agent/ping")
                .execute(PveQemuAgentPingResult.class)
        );
    }

    /**
     * Requests a guest shutdown through the guest agent.
     *
     * @return a request that completes when the shutdown command is accepted
     */
    public ProxmoxRequest<Void> shutdown() {
        return new ProxmoxRequest<>(() -> {
            client.post("nodes/" + nodeName + "/qemu/" + vmid + "/agent/shutdown")
                .execute();
            return null;
        });
    }

}
