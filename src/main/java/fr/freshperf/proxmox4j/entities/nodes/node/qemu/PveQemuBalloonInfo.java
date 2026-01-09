package fr.freshperf.proxmox4j.entities.nodes.node.qemu;

/**
 * Represents memory balloon information for a QEMU VM.
 */
public class PveQemuBalloonInfo {

    private long actual, free_mem, last_update, max_mem, mem_swapped_in, mem_swapped_out;
    private long minor_page_faults, major_page_faults, total_mem;

    public long getActual() {
        return actual;
    }

    public long getFree_mem() {
        return free_mem;
    }

    public long getLast_update() {
        return last_update;
    }

    public long getMax_mem() {
        return max_mem;
    }

    public long getMem_swapped_in() {
        return mem_swapped_in;
    }

    public long getMem_swapped_out() {
        return mem_swapped_out;
    }

    public long getMinor_page_faults() {
        return minor_page_faults;
    }

    public long getMajor_page_faults() {
        return major_page_faults;
    }

    public long getTotal_mem() {
        return total_mem;
    }

    @Override
    public String toString() {
        return "PveQemuBalloonInfo{" +
                "actual=" + actual +
                ", free_mem=" + free_mem +
                ", max_mem=" + max_mem +
                ", total_mem=" + total_mem +
                '}';
    }
}

