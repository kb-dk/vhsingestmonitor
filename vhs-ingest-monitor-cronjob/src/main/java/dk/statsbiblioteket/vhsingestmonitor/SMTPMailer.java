package dk.statsbiblioteket.vhsingestmonitor;

import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Send email using non-authenticated smtp.
 */
public class SMTPMailer implements Mailer {
    private final String smtpHost;
    private final String smtpPort;

    public SMTPMailer(Config config) {
        smtpHost = config.getProperty(Config.MAIL_SMTP_HOST_KEY);
        smtpPort = config.getProperty(Config.MAIL_SMTP_PORT_KEY);
    }

    @Override
    public void send(String senderAddress, String recipientAddresses, String title, String message) throws AddressException, MessagingException {
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", smtpHost);
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.transport.protocol", "smtp");
        Session session = Session.getInstance(props);

        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(senderAddress));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientAddresses, false));
        msg.setSubject(title);
        msg.setText(message, "utf-8");
        msg.setSentDate(new Date());

        Transport t = session.getTransport();
        t.connect();
        t.sendMessage(msg, msg.getAllRecipients());      
        t.close();
    }
}