package fr.freshperf.pve4j.entities.nodes.node.qemu.agent.fsinfo;

/**
 * Represents a PCI disk controller address reported by the guest agent.
 */
public class PveQemuAgentFsInfoPciAddress {
    private Integer bus;
    private Integer domain;
    private Integer function;
    private Integer slot;

    public Integer getBus() {
        return bus;
    }

    public Integer getDomain() {
        return domain;
    }

    public Integer getFunction() {
        return function;
    }

    public Integer getSlot() {
        return slot;
    }

    @Override
    public String toString() {
        return "PveQemuAgentFsInfoPciAddress{" +
                "bus=" + bus +
                ", domain=" + domain +
                ", function=" + function +
                ", slot=" + slot +
                '}';
    }
}
