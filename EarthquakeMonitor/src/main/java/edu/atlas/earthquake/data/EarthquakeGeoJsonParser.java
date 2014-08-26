package edu.atlas.earthquake.data;

import edu.atlas.common.data.DataParser;
import edu.atlas.earthquake.entity.Earthquake;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class EarthquakeGeoJsonParser implements DataParser<Earthquake, String> {

    private static final String ID_KEY          = "id";
    private static final String MAG_KEY         = "mag";
    private static final String TIME_KEY        = "time";
    private static final String PLACE_KEY       = "place";
    private static final String FEATURES_KEY    = "features";
    private static final String GEOMETRY_KEY    = "geometry";
    private static final String PROPERTIES_KEY  = "properties";
    private static final String COORDINATES_KEY = "coordinates";

    private final JSONParser jsonParser = new JSONParser();

    @Override
    @SuppressWarnings("unchecked")
    public List<Earthquake> parseData(String data) {
        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        try {
            JSONObject jsonObj = (JSONObject) jsonParser.parse(data);
            JSONArray featuresArray = (JSONArray) jsonObj.get(FEATURES_KEY);

            for (JSONObject jsonEarthquake : (Iterable<JSONObject>) featuresArray) {
                Earthquake earthquake = parseEarthquake(jsonEarthquake);
                if (earthquake != null) {
                    earthquakes.add(earthquake);
                }
            }
        } catch(ParseException | NullPointerException exc) {
            exc.printStackTrace();
        }

        return earthquakes;
    }

    private Earthquake parseEarthquake(JSONObject feature) {
        Earthquake earthquake = null;

        try {
            JSONObject properties = (JSONObject) feature.get(PROPERTIES_KEY);
            JSONObject geometry = (JSONObject) feature.get(GEOMETRY_KEY);
            JSONArray  coordinates = (JSONArray) geometry.get(COORDINATES_KEY);

            String id = (String) feature.get(ID_KEY);
            String place = (String) properties.get(PLACE_KEY);
            Number mag = (Number) properties.get(MAG_KEY);
            Number time = (Number) properties.get(TIME_KEY);
            Date date = new Date(time.longValue());
            Number longitude = (Number) coordinates.get(0);
            Number latitude  = (Number) coordinates.get(1);
            Number depth = (Number) coordinates.get(2);

            earthquake = new Earthquake(id, date, place, mag.doubleValue(),
                                        longitude.doubleValue(), latitude.doubleValue(), depth.doubleValue());
        } catch(ClassCastException | NullPointerException exc) {
            exc.printStackTrace();
        }

        return earthquake;
    }


}
