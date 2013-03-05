package dk.statsbiblioteket.vhsingestmonitor;

import java.io.IOException;
import java.util.Properties;

/**
 * Wrapper for properties.
 */
public class Config extends Properties {
    public static final String WORKFLOW_STATE_MONITOR_BASE_URL_KEY = "workflow.state.monitor.base.url";
    public static final String VHSWORKFLOW_TIMEOUT_KEY = "workflow.vhs.timeout";
    public static final String MAIL_SMTP_HOST_KEY = "mail.smtp.host";
    public static final String MAIL_SMTP_PORT_KEY = "mail.smtp.port";
    public static final String VHSINGESTMONITOR_RECIPIENTS_ADDRESSES_KEY = "vhsingestmonitor.recipients.addresses";
    public static final String VHSINGESTMONITOR_SENDER_ADDRESS_KEY = "vhsingestmonitor.sender.address";

    /**
     * Initialise properties based solely on system properties.
     */
    public Config() {
        super(System.getProperties());
    }

    /**
     * Initialise properties based on file loaded from classpath and system properties.
     *
     * @param filename The name of file from classpath.
     *
     * @throws IOException If properties cannot be loaded.
     */
    public Config(String filename) throws IOException {
        super(System.getProperties());
        load(Thread.currentThread().getContextClassLoader().getResourceAsStream(filename));

    }
}
