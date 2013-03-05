package dk.statsbiblioteket.vhsingestmonitor;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import dk.statsbiblioteket.medieplatform.workflowstatemonitor.State;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kfc
 * Date: 3/5/13
 * Time: 9:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class RestBasedWorkflowStateMonitorTest {

    private Config config;
    private RestBasedWorkflowStateMonitor restBasedWorkflowStateMonitor;

    @Before
    public void setUp() throws Exception {
        config = new Config();
        config.setProperty(Config.WORKFLOW_STATE_MONITOR_BASE_URL_KEY, "http://canopus:9511/workflowstatemonitor");
        config.setProperty(Config.VHSWORKFLOW_TIMEOUT_KEY, "30");
        restBasedWorkflowStateMonitor = new RestBasedWorkflowStateMonitor(config);
    }

    @Ignore
    @Test
    public void onlineTestGetWorkFlowFilesInState() throws Exception {
        System.out.println(restBasedWorkflowStateMonitor.getWorkFlowFilesInState(WorkflowStateMonitor.DONE_STATE));
        System.out.println(restBasedWorkflowStateMonitor.getWorkFlowFilesInState(WorkflowStateMonitor.FAILED_STATE));
    }

    @Ignore
    @Test
    public void onlineTestGetUnfinishedWorkflowFiles() throws Exception {
        System.out.println(restBasedWorkflowStateMonitor.getUnfinishedWorkflowFiles());
    }

    @Ignore
    @Test
    public void onlineTestMarkAsArchived() throws Exception {
        restBasedWorkflowStateMonitor.markAsArchived("testfile");
        List<State> workFlowFilesInStateArchived = restBasedWorkflowStateMonitor
                .getWorkFlowFilesInState(WorkflowStateMonitor.ARCHIVED_STATE);
        Assert.assertThat(workFlowFilesInStateArchived.toString(), Matchers.containsString("testfile"));
    }
}
