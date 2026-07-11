package fr.freshperf.pve4j.request;

import fr.freshperf.pve4j.entities.PveTaskStatus;

/**
 * Callback interface for task completion notifications.
 */
@FunctionalInterface
public interface TaskCompletionCallback {

    /**
     * Called when a task reaches a terminal state, whether it succeeded or failed.
     * Check {@link PveTaskStatus#isSuccessful()} to distinguish the two.
     *
     * @param taskStatus the final status of the completed task
     */
    void onComplete(PveTaskStatus taskStatus);

    /**
     * Called when the task status could not be determined: network error while
     * polling, timeout, or interruption. The task itself may still be running
     * on the Proxmox side.
     *
     * <p>The default implementation does nothing, preserving compatibility with
     * existing lambdas.</p>
     *
     * @param cause the error that aborted status polling
     */
    default void onError(Throwable cause) {
    }
}

