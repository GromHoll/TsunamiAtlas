package edu.atlas.earthquake.config;


import edu.atlas.common.config.property.BooleanPropertyKey;
import edu.atlas.common.config.property.CollectionPropertyKey;
import edu.atlas.common.config.property.IntegerPropertyKey;
import edu.atlas.common.config.property.StringPropertyKey;

public interface GlobalKeys {

    static final String DEFAULT_DATA_URL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson";


    public static final StringPropertyKey DATA_URL = new StringPropertyKey("url", DEFAULT_DATA_URL);
    public static final StringPropertyKey FILE_OUT_PATH = new StringPropertyKey("fileOutPath", "./out/");
    public static final StringPropertyKey SMS_LOGIN = new StringPropertyKey("smsLogin");
    public static final StringPropertyKey SMS_PASSWORD = new StringPropertyKey("smsPassword");

    public static final IntegerPropertyKey UPDATE_PERIOD = new IntegerPropertyKey("updatePeriod", 60000);

    public static final BooleanPropertyKey CONSOLE_AVAILABLE = new BooleanPropertyKey("consoleAvailable", true);
    public static final BooleanPropertyKey GUI_AVAILABLE = new BooleanPropertyKey("guiAvailable", true);
    public static final BooleanPropertyKey FILE_AVAILABLE = new BooleanPropertyKey("fileAvailable", true);
    public static final BooleanPropertyKey EMAIL_AVAILABLE = new BooleanPropertyKey("emailAvailable", false);
    public static final BooleanPropertyKey SMS_AVAILABLE = new BooleanPropertyKey("smsAvailable", false);

    static final StringPropertyKey SMS_RECEIVER = new StringPropertyKey("smsReceivers");
    public static final CollectionPropertyKey<String> SMS_RECEIVERS = new CollectionPropertyKey<>(SMS_RECEIVER, ",");

}
