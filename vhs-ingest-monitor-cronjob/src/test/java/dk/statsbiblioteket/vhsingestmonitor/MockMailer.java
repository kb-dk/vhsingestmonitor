package dk.statsbiblioteket.vhsingestmonitor;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kfc
 * Date: 3/5/13
 * Time: 9:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class MockMailer implements Mailer {
    public List<String> sendParameters = new ArrayList<String>();
    public int sendCount = 0;

    @Override
    public void send(String senderAddress, String recipientAddresses, String title, String message)
            throws AddressException, MessagingException {
        sendParameters.addAll(Arrays.asList(senderAddress, recipientAddresses, title, message));
        sendCount++;
    }
}
