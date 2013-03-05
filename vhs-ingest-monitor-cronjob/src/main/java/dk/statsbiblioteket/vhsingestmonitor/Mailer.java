package dk.statsbiblioteket.vhsingestmonitor;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

/**
 * Created with IntelliJ IDEA.
 * User: kfc
 * Date: 3/5/13
 * Time: 7:49 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Mailer {
    /**
     * Send email to given recipients with given title and message.
     *
     * @param senderAddress Sender address.
     * @param recipientAddresses Comma-separated list of recipient addresses.
     * @param title title of email.
     * @param message text of email.
     * @throws AddressException If recipient email addresses failed to parse.
     * @throws MessagingException If the message could not be sent.
     */
    void send(String senderAddress, String recipientAddresses, String title, String message) throws AddressException, MessagingException;
}
