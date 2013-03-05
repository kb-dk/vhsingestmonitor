package dk.statsbiblioteket.vhsingestmonitor;

/**
 * Tool that reports state of files in VHS workflow.
 * Queries workflow state monitor for done and failed files, and files that time out.
 * Sends mail about these files.
 * Marks as archived in workflow state monitor.
 */
public class Watchdog {
    /**
     * Run the tools.
     *
     * @param args Ignored.
     *
     * @throws Exception On any trouble.
     */
    public static void main(String[] args) throws Exception {
        Config config = new Config("vhswatchdog.properties");
        ReportGenerator reportGenerator = new ReportGenerator();
        Mailer mailer = new SMTPMailer(config);
        WorkflowStateMonitor workflowStateMonitor = new RestBasedWorkflowStateMonitor(config);
        VHSWorkflowWatchdog vhsWorkflowWatchdog = new VHSWorkflowWatchdog(workflowStateMonitor, reportGenerator, mailer,
                                                                          config);
        vhsWorkflowWatchdog.watch();
    }
}
