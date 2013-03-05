package dk.statsbiblioteket.vhsingestmonitor;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test loading of config.
 */
public class ConfigTest {

    @Test
    public void testLoad() throws Exception {
        Config config = new Config("vhswatchdog.properties");
        Assert.assertThat(config.getProperty(Config.VHSWORKFLOW_TIMEOUT_KEY), Matchers.is("1800"));
    }
}
