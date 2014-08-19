package edu.atlas.earthquake.config;


import edu.atlas.common.config.property.DoublePropertyKey;

public interface ValidatorKeys {

    public static final DoublePropertyKey MIN_LATITUDE = new DoublePropertyKey("minLatitude", null);
    public static final DoublePropertyKey MAX_LATITUDE = new DoublePropertyKey("maxLatitude", null);
    public static final DoublePropertyKey MIN_LONGITUDE = new DoublePropertyKey("minLongitude", null);
    public static final DoublePropertyKey MAX_LONGITUDE = new DoublePropertyKey("maxLongitude", null);
    public static final DoublePropertyKey MIN_MAG = new DoublePropertyKey("minMag", null);

}
