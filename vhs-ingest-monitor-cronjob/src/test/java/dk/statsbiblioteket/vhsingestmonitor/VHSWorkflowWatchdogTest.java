package dk.statsbiblioteket.vhsingestmonitor;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;

/**
 * Created with IntelliJ IDEA.
 * User: kfc
 * Date: 3/5/13
 * Time: 9:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class VHSWorkflowWatchdogTest {
    private Config config;

    @Before
    public void setUp() throws Exception {
        config = new Config();
        config.put(Config.VHSINGESTMONITOR_RECIPIENTS_ADDRESSES_KEY, "test@example.com");
        config.put(Config.VHSINGESTMONITOR_SENDER_ADDRESS_KEY, "test2@example.com");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testWatch() throws Exception {
        MockWorkflowStateMonitor wsm = new MockWorkflowStateMonitor();
        MockMailer m = new MockMailer();
        new VHSWorkflowWatchdog(wsm, new ReportGenerator(), m, config).watch();

        Assert.assertThat(wsm.getWorkFlowFilesInStateCount, is(2));
        Assert.assertThat(wsm.getWorkFlowFilesInStateParameters, contains("Done", "Failed"));
        Assert.assertThat(wsm.getUnfinishedWorkflowFilesCount, is(1));

        Assert.assertThat(m.sendCount, is(1));
        Assert.assertThat(m.sendParameters, hasItems("test@example.com", "test2@example.com",
                                                     "VHS Ingest report"));
        Assert.assertThat(m.sendParameters.get(3), stringContainsInOrder(Arrays.asList("Done", "Failed", "timed out")));

        Assert.assertThat(wsm.markAsArchivedCount, is(7));
        Assert.assertThat(wsm.markAsArchivedParameters,
                          contains("entityName1", "entityName2", "entityName3", "entityName4", "entityName5",
                                   "entityName6", "entityName7"));
    }
}
