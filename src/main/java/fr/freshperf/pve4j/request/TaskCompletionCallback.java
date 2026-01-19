package fr.freshperf.pve4j.request;

import fr.freshperf.pve4j.entities.PveTaskStatus;

/**
 * Callback interface for task completion notifications.
 */
@FunctionalInterface
public interface TaskCompletionCallback {

    /**
     * Called when a task completes.
     *
     * @param taskStatus the final status of the completed task
     */
    void onComplete(PveTaskStatus taskStatus);
}

