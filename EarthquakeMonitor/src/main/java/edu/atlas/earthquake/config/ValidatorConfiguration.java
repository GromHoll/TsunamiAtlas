package edu.atlas.earthquake.config;

import edu.atlas.common.config.Configuration;
import edu.atlas.earthquake.validator.Validator;


public class ValidatorConfiguration extends Configuration {

    private static final String MIN_LATITUDE = "minLatitude";
    private static final String MAX_LATITUDE = "maxLatitude";
    private static final String MIN_LONGITUDE = "minLongitude";
    private static final String MAX_LONGITUDE = "maxLongitude";
    private static final String MIN_MAG = "minMag";

    public ValidatorConfiguration(String filename) {
        loadFromPropertiesFile(filename);
    }

    public Validator createValidator() {
        Validator newValidator = new Validator();

        if (getMinLatitude() != null) {
            newValidator.setMinLatitude(getMinLatitude());
        }
        if (getMaxLatitude() != null) {
            newValidator.setMaxLatitude(getMaxLatitude());
        }
        if (getMinLongitude() != null) {
            newValidator.setMinLongitude(getMinLongitude());
        }
        if (getMaxLongitude() != null) {
            newValidator.setMaxLongitude(getMaxLongitude());
        }
        if (getMinMag() != null) {
            newValidator.setMinMag(getMinMag());
        }
        return newValidator;
    }

    public Double getMinLatitude() {
        return getDoubleProperty(MIN_LATITUDE);
    }

    public Double getMaxLatitude() {
        return getDoubleProperty(MAX_LATITUDE);
    }

    public Double getMinLongitude() {
        return getDoubleProperty(MIN_LONGITUDE);
    }

    public Double getMaxLongitude() {
        return getDoubleProperty(MAX_LONGITUDE);
    }

    public Double getMinMag() {
        return getDoubleProperty(MIN_MAG);
    }

    private Double getDoubleProperty(String key) {
        try {
            return Double.valueOf(getProperty(key));
        } catch (NumberFormatException exc) {
            System.err.println("Property <" + key + "> not correct.");
            return null;
        } catch (NullPointerException exc) {
            /* It's Ok. Just return null*/
            return null;
        }
    }
}
