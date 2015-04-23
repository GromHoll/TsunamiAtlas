package edu.atlas.earthquake.output;

import edu.atlas.common.config.Configuration;
import edu.atlas.common.config.SystemConfiguration;
import edu.atlas.common.data.DataChangedListener;
import edu.atlas.common.data.event.DataChangedEvent;
import edu.atlas.earthquake.entity.Earthquake;
import edu.atlas.earthquake.output.format.OutFormat;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Collection;
import java.util.Properties;

import static edu.atlas.earthquake.config.EmailKeys.*;

public class EmailSender  implements DataChangedListener<Earthquake> {

    public static String EMAIL_TITLE = "Earthquake Event";

    private final String from;
    private final Collection<String> receivers;

    private Session session;
    private OutFormat format;

    public EmailSender(OutFormat format) {
        this.format = format;

        Configuration config = new SystemConfiguration();
        final String address = config.getProperty(SMTP_ADDRESS);
        final String password = config.getProperty(SMTP_PASSWORD);
        this.from = config.getProperty(FROM_KEY);
        this.receivers = config.getProperty(RECEIVERS);

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", config.getProperty(SMTP_SERVER));
        props.put("mail.smtp.port", config.getProperty(SMTP_PORT));

        this.session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(address, password);
                    }
                });
    }

    @Override
    public void process(DataChangedEvent<Earthquake> event) {
        Collection<Earthquake> newEarthquakes = event.getNewData();
        newEarthquakes.forEach(eq -> receivers.forEach(to -> sendTo(to, eq)));
    }

    private void sendTo(String to, Earthquake earthquake) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(EMAIL_TITLE);
            message.setText(format.getFormattedText(earthquake));

            Transport.send(message);

            System.out.println("SENDING!");
        } catch (MessagingException exc) {
            exc.printStackTrace();
        }
    }
}
