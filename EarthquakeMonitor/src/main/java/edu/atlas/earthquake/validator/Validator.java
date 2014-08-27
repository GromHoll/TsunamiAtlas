package edu.atlas.earthquake.validator;

import edu.atlas.common.config.Configuration;
import edu.atlas.earthquake.entity.Earthquake;
import lombok.Setter;

import static edu.atlas.earthquake.config.ValidatorKeys.*;

public class Validator {

    @Setter private double minLatitude = -90;
    @Setter private double maxLatitude =  90;

    @Setter private double minLongitude = -180;
    @Setter private double maxLongitude =  180;

    @Setter private double minMag = 0;

    public boolean validate(Earthquake earthquake) {
        return (earthquake.getLongitude() >= minLongitude)
            && (earthquake.getLongitude() <= maxLongitude)
            && (earthquake.getLatitude() >= minLatitude)
            && (earthquake.getLatitude() <= maxLatitude)
            && (earthquake.getMag() >= minMag);
    }

    public static Validator createFromConfiguration(Configuration config) {
        Validator newValidator = new Validator();

        if (config.getProperty(MIN_LATITUDE) != null) {
            newValidator.setMinLatitude(config.getProperty(MIN_LATITUDE));
        }
        if (config.getProperty(MAX_LATITUDE) != null) {
            newValidator.setMaxLatitude(config.getProperty(MAX_LATITUDE));
        }
        if (config.getProperty(MIN_LONGITUDE) != null) {
            newValidator.setMinLongitude(config.getProperty(MIN_LONGITUDE));
        }
        if (config.getProperty(MAX_LONGITUDE) != null) {
            newValidator.setMaxLongitude(config.getProperty(MAX_LONGITUDE));
        }
        if (config.getProperty(MIN_MAG) != null) {
            newValidator.setMinMag(config.getProperty(MIN_MAG));
        }

        return newValidator;
    }
}
