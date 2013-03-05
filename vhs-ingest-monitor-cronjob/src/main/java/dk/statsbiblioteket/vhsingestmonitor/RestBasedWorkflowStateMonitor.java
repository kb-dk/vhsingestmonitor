package dk.statsbiblioteket.vhsingestmonitor;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dk.statsbiblioteket.medieplatform.workflowstatemonitor.State;

import javax.ws.rs.core.MediaType;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

/** Get states from workflow state monitor using REST. */
public class RestBasedWorkflowStateMonitor implements WorkflowStateMonitor {
    private static final String ISO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final GenericType<List<State>> GENERIC_TYPE_STATE_LIST = new GenericType<List<State>>() {
    };

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final String workFlowStateMonitorBaseUrl;
    private final int timeout;

    public RestBasedWorkflowStateMonitor(Config config) {
        workFlowStateMonitorBaseUrl = config.getProperty(Config.WORKFLOW_STATE_MONITOR_BASE_URL_KEY);
        timeout = Integer.parseInt(config.getProperty(Config.VHSWORKFLOW_TIMEOUT_KEY));
    }

    @Override
    public List<State> getWorkFlowFilesInState(String stateName) {
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource webResource = client.resource(workFlowStateMonitorBaseUrl).path("states")
                .queryParam("includes", stateName).queryParam("onlyLast", "true");
        List<State> states = webResource.get(GENERIC_TYPE_STATE_LIST);
        log.debug("Found states: " + states);
        return states;
    }

    @Override
    public List<State> getUnfinishedWorkflowFiles() {
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.add(Calendar.SECOND, -timeout);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ISO_DATE_FORMAT);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String endDate = simpleDateFormat.format(calendar.getTime());
        WebResource webResource = client.resource(workFlowStateMonitorBaseUrl).path("states")
                .queryParam("excludes", DONE_STATE).queryParam("excludes", FAILED_STATE)
                .queryParam("excludes", ARCHIVED_STATE).queryParam("endDate", endDate).queryParam("onlyLast", "true");
        List<State> states = webResource.get(GENERIC_TYPE_STATE_LIST);
        log.debug("Found states: " + states);
        return states;
    }

    @Override
    public void markAsArchived(String fileName) {
        State state = new State();
        state.setComponent("VHS Ingest Monitor");
        state.setStateName(ARCHIVED_STATE);
        state.setMessage("File state reported and archived");

        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource webResource = client.resource(workFlowStateMonitorBaseUrl).path("states")
                .path(fileName);
        webResource.type(MediaType.TEXT_XML_TYPE).post(state);
        log.debug("Added state: " + state);
    }
}
