package edu.atlas.earthquake.config;

public class GlobalConfigConstants {

    public static final String DATA_URL = "url";
    public static final String UPDATE_PERIOD = "updatePeriod";
    public static final String CONSOLE_AVAILABLE = "consoleAvailable";
    public static final String GUI_AVAILABLE = "guiAvailable";
    public static final String FILE_AVAILABLE = "fileAvailable";
    public static final String FILE_OUT_PATH = "fileOutPath";
    public static final String SMS_AVAILABLE = "smsAvailable";
    public static final String SMS_LOGIN = "smsLogin";
    public static final String SMS_PASSWORD = "smsPassword";
    public static final String SMS_RECEIVERS = "smsReceivers";

    public static final String DEFAULT_DATA_URL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson";
    public static final int DEFAULT_UPDATE_PERIOD = 60000;
    public static final boolean DEFAULT_CONSOLE_AVAILABLE = true;
    public static final boolean DEFAULT_GUI_AVAILABLE = true;
    public static final boolean DEFAULT_FILE_AVAILABLE = true;
    public static final boolean DEFAULT_SMS48_AVAILABLE = false;
    public static final String DEFAULT_FILE_OUT_PATH = "./out/";

    public static final String FRAME_NAME = "Earthquake Monitor";
}
