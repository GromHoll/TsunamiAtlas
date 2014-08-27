package edu.atlas.earthquake.out.format.node;

import edu.atlas.earthquake.entity.Earthquake;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EarthquakeOutNode implements OutNode {

    public static final String DATE = "date";
    public static final String PLACE = "place";
    public static final String MAG = "mag";
    public static final String LONGITUDE = "longitude";
    public static final String LATITUDE = "latitude";
    public static final String DEPTH = "depth";

    public static final String DEFAULT = "default";

    private final String outValue;

    @Override
    public String getOut(Earthquake earthquake) {
        switch (outValue) {
            case DATE:
                return earthquake.getDate().toString();
            case PLACE:
                return earthquake.getPlace();
            case MAG:
                return Double.toString(earthquake.getMag());
            case LONGITUDE:
                return Double.toString(earthquake.getLongitude());
            case LATITUDE:
                return Double.toString(earthquake.getLatitude());
            case DEPTH:
                return Double.toString(earthquake.getDepth());
            case DEFAULT:
                return earthquake.toString();
            default:
                return "";
        }
    }
}
