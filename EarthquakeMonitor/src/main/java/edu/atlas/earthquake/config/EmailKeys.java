package edu.atlas.earthquake.config;


import edu.atlas.common.config.property.CollectionPropertyKey;
import edu.atlas.common.config.property.StringPropertyKey;

public interface EmailKeys {

    public static final StringPropertyKey SMTP_SERVER = new StringPropertyKey("emailSmtpServer");
    public static final StringPropertyKey SMTP_ADDRESS = new StringPropertyKey("emailSmtpAddress");
    public static final StringPropertyKey SMTP_PASSWORD = new StringPropertyKey("emailSmtpPassword");
    public static final StringPropertyKey SMTP_PORT = new StringPropertyKey("emailSmtpPort");
    public static final StringPropertyKey FROM_KEY = new StringPropertyKey("emailFrom");

    static final StringPropertyKey RECEIVER = new StringPropertyKey("emailReceivers");
    public static final CollectionPropertyKey<String> RECEIVERS = new CollectionPropertyKey<>(RECEIVER, ",");

}
