package dk.statsbiblioteket.vhsingestmonitor;

import dk.statsbiblioteket.medieplatform.workflowstatemonitor.Entity;
import dk.statsbiblioteket.medieplatform.workflowstatemonitor.State;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kfc
 * Date: 3/5/13
 * Time: 9:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class MockWorkflowStateMonitor implements WorkflowStateMonitor {
    public List<String> getWorkFlowFilesInStateParameters = new ArrayList<String>();
    public List<String> markAsArchivedParameters = new ArrayList<String>();
    public int getWorkFlowFilesInStateCount = 0;
    public int getUnfinishedWorkflowFilesCount = 0;
    public int markAsArchivedCount = 0;

    @Override
    public List<State> getWorkFlowFilesInState(String stateName) {
        getWorkFlowFilesInStateParameters.add(stateName);
        getWorkFlowFilesInStateCount++;


        if (stateName.equals("Done")) {
            List<State> doneStates = new ArrayList<State>();
            doneStates.add(generateTestState("entityName1", new Date(), "Done", "endComponent", "message1"));
            doneStates.add(generateTestState("entityName2", new Date(), "Done", "endComponent", "message2"));
            return doneStates;
        }
        List<State> failedStates = new ArrayList<State>();
        failedStates.add(generateTestState("entityName3", new Date(), "Failed", "component1", "message3"));
        failedStates.add(generateTestState("entityName4", new Date(), "Failed", "component2", "message4"));
        return failedStates;

    }

    @Override
    public List<State> getUnfinishedWorkflowFiles() {
        getUnfinishedWorkflowFilesCount++;

        List<State> timeoutStates = new ArrayList<State>();
        timeoutStates.add(generateTestState("entityName5", new Date(), "In progress", "component3", "message5"));
        timeoutStates.add(generateTestState("entityName6", new Date(), "Running", "component4", "message6"));
        timeoutStates.add(generateTestState("entityName7", new Date(), "Running", "component5", "message7"));
        return timeoutStates;
    }

    @Override
    public void markAsArchived(String fileName) {
        markAsArchivedParameters.add(fileName);
        markAsArchivedCount++;
    }

    public static State generateTestState(String entityName, Date date, String stateName, String component, String message) {
        State state = new State();
        state.setComponent(component);
        state.setDate(date);
        state.setMessage(message);
        state.setStateName(stateName);
        Entity entity = new Entity();
        entity.setName(entityName);
        state.setEntity(entity);
        return state;
    }
}
