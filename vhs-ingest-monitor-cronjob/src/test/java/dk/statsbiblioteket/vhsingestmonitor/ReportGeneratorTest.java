package dk.statsbiblioteket.vhsingestmonitor;

import org.junit.Assert;

import dk.statsbiblioteket.medieplatform.workflowstatemonitor.State;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

/**
 * Test reports
 */
public class ReportGeneratorTest {
    @org.junit.Before
    public void setUp() throws Exception {

    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @org.junit.Test
    public void testGenerateReport() throws Exception {
        // Generate some test lists of states
        OutputStream baos = new ByteArrayOutputStream();
        List<State> doneStates = new ArrayList<State>();
        doneStates.add(MockWorkflowStateMonitor
                               .generateTestState("entityName1", new Date(), "Done", "endComponent", "message1"));
        doneStates.add(MockWorkflowStateMonitor
                               .generateTestState("entityName2", new Date(), "Done", "endComponent", "message2"));
        List<State> failedStates = new ArrayList<State>();
        failedStates.add(MockWorkflowStateMonitor
                                 .generateTestState("entityName3", new Date(), "Failed", "component1", "message3"));
        failedStates.add(MockWorkflowStateMonitor
                                 .generateTestState("entityName4", new Date(), "Failed", "component2", "message4"));
        List<State> timeoutStates = new ArrayList<State>();
        timeoutStates.add(MockWorkflowStateMonitor
                                  .generateTestState("entityName5", new Date(), "In progress", "component3", "message5"));
        timeoutStates.add(MockWorkflowStateMonitor
                                  .generateTestState("entityName6", new Date(), "Running", "component4", "message6"));
        timeoutStates.add(MockWorkflowStateMonitor
                                  .generateTestState("entityName7", new Date(), "Running", "component5", "message7"));

        // Make a report with all lists containing info
        new ReportGenerator().generateReport(baos, doneStates, failedStates, timeoutStates);
        Assert.assertThat(baos.toString(), containsString("Completed files"));
        Assert.assertThat(baos.toString(), containsString("Failed files"));
        Assert.assertThat(baos.toString(), containsString("Files that timed out"));
        Assert.assertThat(baos.toString(), containsString("entityName1"));
        Assert.assertThat(baos.toString(), containsString("entityName2"));
        Assert.assertThat(baos.toString(), containsString("entityName3"));
        Assert.assertThat(baos.toString(), containsString("entityName4"));
        Assert.assertThat(baos.toString(), containsString("entityName5"));
        Assert.assertThat(baos.toString(), containsString("entityName6"));
        Assert.assertThat(baos.toString(), containsString("entityName7"));
        Assert.assertThat(baos.toString(), containsString("Done"));
        Assert.assertThat(baos.toString(), containsString("Failed"));
        Assert.assertThat(baos.toString(), containsString("In progress"));
        Assert.assertThat(baos.toString(), containsString("Running"));
        Assert.assertThat(baos.toString(), containsString("endComponent"));
        Assert.assertThat(baos.toString(), containsString("component1"));
        Assert.assertThat(baos.toString(), containsString("component2"));
        Assert.assertThat(baos.toString(), containsString("component3"));
        Assert.assertThat(baos.toString(), containsString("component4"));
        Assert.assertThat(baos.toString(), containsString("component5"));
        Assert.assertThat(baos.toString(), containsString("message1"));
        Assert.assertThat(baos.toString(), containsString("message2"));
        Assert.assertThat(baos.toString(), containsString("message3"));
        Assert.assertThat(baos.toString(), containsString("message4"));
        Assert.assertThat(baos.toString(), containsString("message5"));
        Assert.assertThat(baos.toString(), containsString("message6"));
        Assert.assertThat(baos.toString(), containsString("message7"));
        //System.out.println(baos.toString());

        // Make a report with only one list containing info
        baos = new ByteArrayOutputStream();
        new ReportGenerator().generateReport(baos, Collections.<State>emptyList(), Collections.<State>emptyList(),
                                             timeoutStates);
        Assert.assertThat(baos.toString(), not(containsString("Completed files")));
        Assert.assertThat(baos.toString(), not(containsString("Failed files")));
        Assert.assertThat(baos.toString(), containsString("Files that timed out"));
        Assert.assertThat(baos.toString(), not(containsString("entityName1")));
        Assert.assertThat(baos.toString(), not(containsString("entityName2")));
        Assert.assertThat(baos.toString(), not(containsString("entityName3")));
        Assert.assertThat(baos.toString(), not(containsString("entityName4")));
        Assert.assertThat(baos.toString(), containsString("entityName5"));
        Assert.assertThat(baos.toString(), containsString("entityName6"));
        Assert.assertThat(baos.toString(), containsString("entityName7"));
        Assert.assertThat(baos.toString(), not(containsString("Done")));
        Assert.assertThat(baos.toString(), not(containsString("Failed")));
        Assert.assertThat(baos.toString(), containsString("In progress"));
        Assert.assertThat(baos.toString(), containsString("Running"));
        Assert.assertThat(baos.toString(), not(containsString("endComponent")));
        Assert.assertThat(baos.toString(), not(containsString("component1")));
        Assert.assertThat(baos.toString(), not(containsString("component2")));
        Assert.assertThat(baos.toString(), containsString("component3"));
        Assert.assertThat(baos.toString(), containsString("component4"));
        Assert.assertThat(baos.toString(), containsString("component5"));
        Assert.assertThat(baos.toString(), not(containsString("message1")));
        Assert.assertThat(baos.toString(), not(containsString("message2")));
        Assert.assertThat(baos.toString(), not(containsString("message3")));
        Assert.assertThat(baos.toString(), not(containsString("message4")));
        Assert.assertThat(baos.toString(), containsString("message5"));
        Assert.assertThat(baos.toString(), containsString("message6"));
        Assert.assertThat(baos.toString(), containsString("message7"));
        //System.out.println(baos.toString());
    }

}
