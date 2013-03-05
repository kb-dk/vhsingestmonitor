package dk.statsbiblioteket.vhsingestmonitor;

import dk.statsbiblioteket.medieplatform.workflowstatemonitor.State;

import java.util.List;

/**
 * Facade for needed operations in workflow state monitor.
 */
public interface WorkflowStateMonitor {
    String DONE_STATE = "Done";
    String FAILED_STATE = "Failed";
    String ARCHIVED_STATE = "Archived";

    /**
     * List last state of all files with a given state.
     * @param stateName The name of states.
     * @return A list of last state of files in state with that name.
     */
    List<State> getWorkFlowFilesInState(String stateName);

    /**
     * List files that have not had their states updated for a long time.
     * @return A list of last state of files with un-updated states.
     */
    List<State> getUnfinishedWorkflowFiles();

    /**
     * Mark file as archived.
     * @param fileName name of file to mark as archived.
     */
    void markAsArchived(String fileName);
}
