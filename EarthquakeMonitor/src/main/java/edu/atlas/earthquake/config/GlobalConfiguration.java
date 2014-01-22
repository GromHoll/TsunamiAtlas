package edu.atlas.earthquake.config;

import edu.atlas.common.config.Configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class GlobalConfiguration extends Configuration {
    private static final String DATA_URL = "url";
    private static final String UPDATE_PERIOD = "updatePeriod";
    private static final String CONSOLE_AVAILABLE = "consoleAvailable";
    private static final String GUI_AVAILABLE = "guiAvailable";
    private static final String FILE_AVAILABLE = "fileAvailable";
    private static final String FILE_OUT_PATH = "fileOutPath";
    private static final String SMS_AVAILABLE = "smsAvailable";
    private static final String SMS_LOGIN = "smsLogin";
    private static final String SMS_PASSWORD = "smsPassword";
    private static final String SMS_RECEIVERS = "smsReceivers";

    private static final String SMS_RECEIVERS_SEPARATOR = ",";

    private static final String DEFAULT_DATA_URL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson";
    private static final int DEFAULT_UPDATE_PERIOD = 60000;
    private static final boolean DEFAULT_CONSOLE_AVAILABLE = true;
    private static final boolean DEFAULT_GUI_AVAILABLE = true;
    private static final boolean DEFAULT_FILE_AVAILABLE = true;
    private static final boolean DEFAULT_SMS_AVAILABLE = false;
    private static final String DEFAULT_FILE_OUT_PATH = "./out/";

    public GlobalConfiguration(String filename) {
        loadFromPropertiesFile(filename);
    }

    public String getDataUrl() {
        return getPropertyWithDefault(DATA_URL, DEFAULT_DATA_URL);
    }

    public String getSmsLogin() {
        return getProperty(SMS_LOGIN);
    }

    public String getSmsPassword() {
        return getProperty(SMS_PASSWORD);
    }

    public List<String> getSmsReceivers() {
        String receivers = getProperty(SMS_RECEIVERS);
        return (receivers != null) ? Arrays.asList(receivers.split(SMS_RECEIVERS_SEPARATOR))
                                   : Collections.<String>emptyList();
    }

    public String getFileOutPath() {
        return getPropertyWithDefault(FILE_OUT_PATH, DEFAULT_FILE_OUT_PATH);
    }

    public int getUpdatePeriod() {
        return getPropertyWithDefault(UPDATE_PERIOD, DEFAULT_UPDATE_PERIOD);
    }

    public boolean isConsoleAvailable() {
        return getPropertyWithDefault(CONSOLE_AVAILABLE, DEFAULT_CONSOLE_AVAILABLE);
    }

    public boolean isGuiAvailable() {
        return getPropertyWithDefault(GUI_AVAILABLE, DEFAULT_GUI_AVAILABLE);
    }

    public boolean isFileAvailable() {
        return getPropertyWithDefault(FILE_AVAILABLE, DEFAULT_FILE_AVAILABLE);
    }

    public boolean isSmsAvailable() {
        return getPropertyWithDefault(SMS_AVAILABLE, DEFAULT_SMS_AVAILABLE);
    }

}
