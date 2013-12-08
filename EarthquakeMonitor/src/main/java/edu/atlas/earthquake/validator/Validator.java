package edu.atlas.earthquake.validator;

import edu.atlas.earthquake.entity.Earthquake;

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
}
