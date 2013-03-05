package dk.statsbiblioteket.vhsingestmonitor;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Properties;

/**
 * Test mailer.
 */
public class SMTPMailerTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Ignore
    @Test
    public void onlineTestSend() throws Exception {
        Config config = new Config();
        config.put(Config.MAIL_SMTP_HOST_KEY, "post.statsbiblioteket.dk");
        config.put(Config.MAIL_SMTP_PORT_KEY, "25");
        new SMTPMailer(config)
                .send("kfc@statsbiblioteket.dk", "kfc@statsbiblioteket.dk, mail@kaarefc.dk", "Subject", "Hello\nWorld");
    }
}
