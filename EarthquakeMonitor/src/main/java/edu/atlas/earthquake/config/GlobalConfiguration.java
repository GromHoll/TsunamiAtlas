package edu.atlas.earthquake.config;

import edu.atlas.common.config.Configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class GlobalConfiguration extends Configuration {

    public class EmailConfiguration extends Configuration {
        private static final String EMAIL_SMTP_SERVER_KEY   = "emailSmtpServer";
        private static final String EMAIL_SMTP_ADDRESS_KEY  = "emailSmtpAddress";
        private static final String EMAIL_SMTP_PASSWORD_KEY = "emailSmtpPassword";
        private static final String EMAIL_SMTP_PORT_KEY     = "emailSmtpPort";
        private static final String EMAIL_FROM_KEY          = "emailFrom";
        private static final String EMAIL_RECEIVERS_KEY     = "emailReceivers";

        private static final String EMAIL_RECEIVERS_SEPARATOR = ",";

        public String getSmtpServer() {
            return getProperty(EMAIL_SMTP_SERVER_KEY);
        }

        public String getSmtpAddress() {
            return getProperty(EMAIL_SMTP_ADDRESS_KEY);
        }

        public String getSmtpPassword() {
            return getProperty(EMAIL_SMTP_PASSWORD_KEY);
        }

        public String getSmtpPort() {
            return getProperty(EMAIL_SMTP_PORT_KEY);
        }

        public String getFrom() {
            return getProperty(EMAIL_FROM_KEY);
        }

        public List<String> getReceivers() {
            String receivers = getProperty(EMAIL_RECEIVERS_KEY);
            return (receivers != null) ? Arrays.asList(receivers.split(EMAIL_RECEIVERS_SEPARATOR))
                    : Collections.<String>emptyList();
        }
    }

    private static final String DATA_URL_KEY          = "url";
    private static final String UPDATE_PERIOD_KEY     = "updatePeriod";
    private static final String CONSOLE_AVAILABLE_KEY = "consoleAvailable";
    private static final String GUI_AVAILABLE_KEY     = "guiAvailable";
    private static final String FILE_AVAILABLE_KEY    = "fileAvailable";
    private static final String FILE_OUT_PATH_KEY     = "fileOutPath";
    private static final String EMAIL_AVAILABLE_KEY   = "emailAvailable";
    private static final String SMS_AVAILABLE_KEY     = "smsAvailable";
    private static final String SMS_LOGIN_KEY         = "smsLogin";
    private static final String SMS_PASSWORD_KEY      = "smsPassword";
    private static final String SMS_RECEIVERS_KEY     = "smsReceivers";

    private static final String SMS_RECEIVERS_SEPARATOR = ",";

    private static final String DEFAULT_DATA_URL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson";
    private static final int DEFAULT_UPDATE_PERIOD          = 60000;
    private static final boolean DEFAULT_CONSOLE_AVAILABLE  = true;
    private static final boolean DEFAULT_GUI_AVAILABLE      = true;
    private static final boolean DEFAULT_FILE_AVAILABLE     = true;
    private static final boolean DEFAULT_SMS_AVAILABLE      = false;
    private static final boolean DEFAULT_EMAIL_AVAILABLE    = false;
    private static final String DEFAULT_FILE_OUT_PATH       = "./out/";

    private EmailConfiguration emailConfiguration = new EmailConfiguration();

    public GlobalConfiguration(String filename) {
        loadFromPropertiesFile(filename);
        emailConfiguration.setProperties(getProperties());
    }

    public String getDataUrl() {
        return getPropertyWithDefault(DATA_URL_KEY, DEFAULT_DATA_URL);
    }

    public String getSmsLogin() {
        return getProperty(SMS_LOGIN_KEY);
    }

    public String getSmsPassword() {
        return getProperty(SMS_PASSWORD_KEY);
    }

    public List<String> getSmsReceivers() {
        String receivers = getProperty(SMS_RECEIVERS_KEY);
        return (receivers != null) ? Arrays.asList(receivers.split(SMS_RECEIVERS_SEPARATOR))
                                   : Collections.<String>emptyList();
    }

    public String getFileOutPath() {
        return getPropertyWithDefault(FILE_OUT_PATH_KEY, DEFAULT_FILE_OUT_PATH);
    }

    public int getUpdatePeriod() {
        return getPropertyWithDefault(UPDATE_PERIOD_KEY, DEFAULT_UPDATE_PERIOD);
    }

    public boolean isConsoleAvailable() {
        return getPropertyWithDefault(CONSOLE_AVAILABLE_KEY, DEFAULT_CONSOLE_AVAILABLE);
    }

    public boolean isGuiAvailable() {
        return getPropertyWithDefault(GUI_AVAILABLE_KEY, DEFAULT_GUI_AVAILABLE);
    }

    public boolean isFileAvailable() {
        return getPropertyWithDefault(FILE_AVAILABLE_KEY, DEFAULT_FILE_AVAILABLE);
    }

    public boolean isEmailAvailable() {
        return getPropertyWithDefault(EMAIL_AVAILABLE_KEY, DEFAULT_EMAIL_AVAILABLE);
    }

    public boolean isSmsAvailable() {
        return getPropertyWithDefault(SMS_AVAILABLE_KEY, DEFAULT_SMS_AVAILABLE);
    }

    public EmailConfiguration getEmailConfiguration() {
        return emailConfiguration;
    }

}
