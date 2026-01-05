package fr.freshperf.proxmox4j.request;

import fr.freshperf.proxmox4j.throwable.ProxmoxAPIError;

@FunctionalInterface
public interface ProxmoxRequestExecutor<T> {
    T execute() throws ProxmoxAPIError, InterruptedException;
}

