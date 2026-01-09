package fr.freshperf.proxmox4j.request;

import fr.freshperf.proxmox4j.throwable.ProxmoxAPIError;

/**
 * Functional interface for executing Proxmox API requests.
 *
 * @param <T> the return type of the request
 */
@FunctionalInterface
public interface ProxmoxRequestExecutor<T> {

    /**
     * Executes the request and returns the result.
     *
     * @return the request result
     * @throws ProxmoxAPIError    if the API returns an error
     * @throws InterruptedException if the request is interrupted
     */
    T execute() throws ProxmoxAPIError, InterruptedException;
}

