package fr.freshperf.proxmox4j.request;

import fr.freshperf.proxmox4j.entities.PveTaskStatus;

@FunctionalInterface
public interface TaskCompletionCallback {
    void onComplete(PveTaskStatus taskStatus);
}

