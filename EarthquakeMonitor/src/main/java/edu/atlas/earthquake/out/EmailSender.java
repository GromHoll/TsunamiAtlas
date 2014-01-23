package edu.atlas.earthquake.out;

import edu.atlas.common.data.DataChangedListener;
import edu.atlas.common.data.event.DataChangedEvent;
import edu.atlas.earthquake.config.GlobalConfiguration;
import edu.atlas.earthquake.entity.Earthquake;
import edu.atlas.earthquake.out.format.OutFormat;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

public class EmailSender  implements DataChangedListener<Earthquake> {

    public static String EMAIL_TITLE = "Earthquake Event";

    private final String from;
    private final List<String> receivers;

    private Properties props = new Properties();
    private Session session;
    private OutFormat format;

    public EmailSender(GlobalConfiguration.EmailConfiguration configuration, OutFormat format) {
        this.format = format;

        final String address = configuration.getSmtpAddress();
        final String password = configuration.getSmtpPassword();
        this.from = configuration.getFrom();
        this.receivers = configuration.getReceivers();

        this.props.put("mail.smtp.auth", "true");
        this.props.put("mail.smtp.starttls.enable", "true");
        this.props.put("mail.smtp.host", configuration.getSmtpServer());
        this.props.put("mail.smtp.port", configuration.getSmtpPort());

        this.session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(address, password);
                    }
                });
    }

    @Override
    public void process(DataChangedEvent<Earthquake> event) {
        for (Earthquake earthquake : event.getNewData()) {
            for (String to : receivers) {
                sendTo(to, earthquake);
            }
        }
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
