package edu.atlas.earthquake.validator;

import edu.atlas.common.config.Configuration;
import edu.atlas.earthquake.entity.Earthquake;

import static edu.atlas.earthquake.config.ValidatorKeys.*;

public class Validator {

    private double minLatitude = -90;
    private double maxLatitude =  90;

    private double minLongitude = -180;
    private double maxLongitude =  180;

    private double minMag = 0;

    public void setMinLongitude(double minLongitude) {
        this.minLongitude = minLongitude;
    }

    public void setMaxLongitude(double maxLongitude) {
        this.maxLongitude = maxLongitude;
    }

    public void setMinLatitude(double minLatitude) {
        this.minLatitude = minLatitude;
    }

    public void setMaxLatitude(double maxLatitude) {
        this.maxLatitude = maxLatitude;
    }

    public void setMinMag(double minMag) {
        this.minMag = minMag;
    }

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
