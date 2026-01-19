package fr.freshperf.pve4j.request;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Thread manager for Proxmox4J using Virtual Threads for better scalability.
 * This class manages the lifecycle of threads used for asynchronous tasks.
 */
public class ProxmoxThreadManager {

    private static final AtomicInteger INSTANCE_COUNTER = new AtomicInteger(0);
    private static final Duration DEFAULT_SHUTDOWN_TIMEOUT = Duration.ofSeconds(30);

    private final ExecutorService virtualThreadExecutor;
    private final AtomicBoolean isShutdown;
    private final String instanceName;

    /**
     * Creates a new thread manager with a default name.
     */
    public ProxmoxThreadManager() {
        this("proxmox4j-" + INSTANCE_COUNTER.incrementAndGet());
    }

    /**
     * Creates a new thread manager with a custom name.
     *
     * @param name the name to use for threads (helps with debugging)
     */
    public ProxmoxThreadManager(String name) {
        this.instanceName = name;
        this.isShutdown = new AtomicBoolean(false);

        // Virtual threads for I/O-bound tasks (HTTP requests, status polling, etc.)
        this.virtualThreadExecutor = Executors.newVirtualThreadPerTaskExecutor();

        // Register shutdown hook for clean shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (!isShutdown.get()) {
                shutdownGracefully(DEFAULT_SHUTDOWN_TIMEOUT);
            }
        }, instanceName + "-shutdown-hook"));
    }

    /**
     * Returns the executor with virtual threads for I/O-bound tasks.
     * Virtual threads are ideal for all Proxmox operations (HTTP requests, task polling).
     *
     * @return the ExecutorService with virtual threads
     * @throws IllegalStateException if the manager is already shutdown
     */
    public ExecutorService getVirtualThreadExecutor() {
        ensureNotShutdown();
        return virtualThreadExecutor;
    }

    /**
     * Checks if the manager is shutdown (internal use).
     *
     * @return true if shutdown, false otherwise
     */
    private boolean isShutdown() {
        return isShutdown.get();
    }

    /**
     * Shuts down the executor immediately (internal use).
     * Tasks in progress are interrupted.
     */
    private void shutdownNow() {
        if (isShutdown.compareAndSet(false, true)) {
            virtualThreadExecutor.shutdownNow();
        }
    }

    /**
     * Shuts down the executor gracefully with the default timeout.
     * Tasks in progress have time to complete.
     */
    public void shutdown() {
        shutdownGracefully(DEFAULT_SHUTDOWN_TIMEOUT);
    }

    /**
     * Shuts down the executor gracefully with a custom timeout.
     *
     * @param timeout the maximum duration to wait for shutdown
     */
    public void shutdownGracefully(Duration timeout) {
        if (isShutdown.compareAndSet(false, true)) {
            virtualThreadExecutor.shutdown();

            try {
                if (!virtualThreadExecutor.awaitTermination(timeout.toMillis(), TimeUnit.MILLISECONDS)) {
                    virtualThreadExecutor.shutdownNow();
                }
            } catch (InterruptedException e) {
                virtualThreadExecutor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Ensures the manager is not shutdown.
     *
     * @throws IllegalStateException if the manager is shutdown
     */
    private void ensureNotShutdown() {
        if (isShutdown.get()) {
            throw new IllegalStateException("ThreadManager is already shutdown");
        }
    }

    /**
     * Returns the name of this instance.
     *
     * @return the instance name
     */
    public String getInstanceName() {
        return instanceName;
    }
}

