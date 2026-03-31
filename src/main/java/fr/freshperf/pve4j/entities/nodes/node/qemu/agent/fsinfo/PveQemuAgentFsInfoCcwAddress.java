package fr.freshperf.pve4j.entities.nodes.node.qemu.agent.fsinfo;

/**
 * Represents a CCW disk address reported by the guest agent.
 */
public class PveQemuAgentFsInfoCcwAddress {
    private Integer cssid;
    private Integer devno;
    private Integer ssid;
    private Integer subchno;

    public Integer getCssid() {
        return cssid;
    }

    public Integer getDevno() {
        return devno;
    }

    public Integer getSsid() {
        return ssid;
    }

    public Integer getSubchno() {
        return subchno;
    }

    @Override
    public String toString() {
        return "PveQemuAgentFsInfoCcwAddress{" +
                "cssid=" + cssid +
                ", devno=" + devno +
                ", ssid=" + ssid +
                ", subchno=" + subchno +
                '}';
    }
}
