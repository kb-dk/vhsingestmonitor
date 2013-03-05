package dk.statsbiblioteket.vhsingestmonitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dk.statsbiblioteket.medieplatform.workflowstatemonitor.State;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * The VHS workflow monitor workflow watchdog.
 * Will look for completed, failed, and timed-out files, send a mail about them, and archive them.
 */
public class VHSWorkflowWatchdog {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final WorkflowStateMonitor wsm;
    private final Mailer m;
    private final String smtpRecipientAdresses;
    private final String smtpSenderAddress;
    private final ReportGenerator reportGenerator;

    public VHSWorkflowWatchdog(WorkflowStateMonitor wsm, ReportGenerator reportGenerator, Mailer m,
                               Config config) {
        this.wsm = wsm;
        this.m = m;
        this.smtpRecipientAdresses = config.getProperty(Config.VHSINGESTMONITOR_RECIPIENTS_ADDRESSES_KEY);
        this.smtpSenderAddress = config.getProperty(Config.VHSINGESTMONITOR_SENDER_ADDRESS_KEY);
        this.reportGenerator = reportGenerator;
    }

    public void watch() {
        ByteArrayOutputStream report = new ByteArrayOutputStream();
        List<State> statesToArchive = new ArrayList<State>();
        List<State> doneStates;
        List<State> failedStates;
        List<State> timeoutStates;
        try {
            doneStates = wsm.getWorkFlowFilesInState(WorkflowStateMonitor.DONE_STATE);
            failedStates = wsm.getWorkFlowFilesInState(WorkflowStateMonitor.FAILED_STATE);
            timeoutStates = wsm.getUnfinishedWorkflowFiles();
            if (doneStates.isEmpty() && failedStates.isEmpty() && timeoutStates.isEmpty()) {
                return;
            }
            statesToArchive.addAll(doneStates);
            statesToArchive.addAll(failedStates);
            statesToArchive.addAll(timeoutStates);
            reportGenerator.generateReport(report, doneStates, failedStates, timeoutStates);
        } catch (Exception e) {
            log.error("Error generating report", e);
            try {
                report.write(("ERROR DURING REPORT GENERATION! PLEASE REPORT THIS ERROR TO IT OPERATIONS: "
                        + e.toString() + "\n").getBytes());
            } catch (IOException e2) {
                log.error("Error adding exception details to report", e2);
            }
        }
        log.debug("Generated report\n{}", report);
        try {
            m.send(smtpSenderAddress, smtpRecipientAdresses, "VHS Ingest report", report.toString());
        } catch (Exception e) {
            log.error("Error sending report email\n{}", report, e);
        }
        for (State state : statesToArchive) {
            wsm.markAsArchived(state.getEntity().getName());
        }
    }
}
