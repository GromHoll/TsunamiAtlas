package edu.atlas.dart.entity;


import edu.atlas.common.data.DataParser;
import edu.atlas.common.data.DataReader;
import edu.atlas.common.data.impl.UrlDataReader;
import edu.atlas.dart.data.DartParser;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

public class DartStations {

    private DataReader dataReader;

    private DataParser<DartStation, String[]> dataParser;

    public DartStations() {
        // TODO use config for getting url
        dataReader = new UrlDataReader("http://www.ndbc.noaa.gov/dart_stations.php");
        dataParser = new DartParser();
    }

    public Collection<DartStation> getDartStations() {
        try {
            String[] data = dataReader.getData();
            return dataParser.parseData(data);
        } catch (IOException exc) {
            return Collections.emptyList();
        }
    }

}
