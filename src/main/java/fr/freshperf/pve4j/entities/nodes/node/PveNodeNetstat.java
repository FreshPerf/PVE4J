package fr.freshperf.pve4j.entities.nodes.node;

/**
 * Represents node network interface information via netstat.
 */
public class PveNodeNetstat {

    private long vmid,in,out;
    private String dev;

    public long getVmid() {
        return vmid;
    }
    public String getDev() {
        return dev;
    }
    public long getIn() {
        return in;
    }
    public long getOut() {
        return out;
    }

    @Override
    public String toString() {
        return "PveNodeNetstat{" +
                "vmid='" + vmid + '\'' +
                ", dev='" + dev + '\'' +
                ", in='" + in + '\'' +
                ", out='" + out + '\'' +
                '}';
    }
}
