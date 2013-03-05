package dk.statsbiblioteket.vhsingestmonitor;

import dk.statsbiblioteket.medieplatform.workflowstatemonitor.State;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Generate a textual report about state of files.
 */
public class ReportGenerator {
    public void generateReport(OutputStream outputStream, List<State> doneStates, List<State> failedStates,
                               List<State> timeoutStates) throws IOException {

        PrintStream printStream = new PrintStream(outputStream);
        try {
            generateHeader(printStream);
            generateSection(printStream, "Completed files", doneStates);
            generateSection(printStream, "Failed files", failedStates);
            generateSection(printStream, "Files that timed out", timeoutStates);
            generateFooter(printStream);
        } finally {
            printStream.flush();
        }
    }

    private void generateHeader(PrintStream printStream) {
        printStream.println("VHS Ingest workflow report");
        printStream.println();
    }

    private void generateSection(PrintStream printStream, String title, List<State> states) {
        if (states.isEmpty()) {
            return;
        }
        printStream.println("=== " + title + " ===");
        for (State state: states) {
            printStream.println(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(state.getDate()) + "\t" + state.getEntity().getName() + "\t" + state.getStateName() + "\t" + state.getComponent() + "\t" + state.getMessage());
        }
        printStream.println();
    }

    private void generateFooter(PrintStream printStream) {
        printStream.println("End of report");
    }

}
