package fr.freshperf.pve4j.entities.nodes.node;

/**
 * Represents node network interface information via netstat.
 */
public class PveNodeNetstat {

    private String vmid,dev,in,out;

    public String getVmid() {
        return vmid;
    }
    public String getDev() {
        return dev;
    }
    public String getIn() {
        return in;
    }
    public String getOut() {
        return out;
    }

    public String toString() {
        return "PveNodeNetstat{" +
                "vmid='" + vmid + '\'' +
                ", dev='" + dev + '\'' +
                ", in='" + in + '\'' +
                ", out='" + out + '\'' +
                '}';
    }
}
