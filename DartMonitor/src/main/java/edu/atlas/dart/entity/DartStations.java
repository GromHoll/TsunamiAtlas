package edu.atlas.dart.entity;


import edu.atlas.common.data.DataParser;
import edu.atlas.common.data.DataReader;
import edu.atlas.common.data.impl.UrlDataReader;
import edu.atlas.dart.data.DartStatesParser;
import edu.atlas.dart.data.DartStationsParser;
import lombok.NonNull;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DartStations {

    // TODO use config for getting url
    public static final String DARTS_FOLDER = "http://www.ndbc.noaa.gov/data/realtime2/";
    public static final String DART_EXTENTION = ".dart";

    private DataReader dartsDataReader;
    private DataParser<DartStation, String[]> dartsDataParser;
    private DataParser<DartState, String[]> dartStatesDataParser;

    public DartStations() {
        // TODO use config for getting url
        dartsDataReader = new UrlDataReader("http://www.ndbc.noaa.gov/dart_stations.php");
        dartsDataParser = new DartStationsParser();
        dartStatesDataParser = new DartStatesParser();
    }

    public Collection<DartStation> getDartStations() {
        try {
            String[] data = dartsDataReader.getData();
            return dartsDataParser.parseData(data);
        } catch (IOException exc) {
            return Collections.emptyList();
        }
    }

    public List<DartState> getDartStates(@NonNull DartStation station) {
        try {
            DataReader dartStatesDataReader = new UrlDataReader(getDartStatesLink(station));
            String[] data = dartStatesDataReader.getData();
            return dartStatesDataParser.parseData(data);
        } catch (IOException exc) {
            return Collections.emptyList();
        }
    }

    private String getDartStatesLink(@NonNull DartStation station) {
        return DARTS_FOLDER + station.getSiteId() + DART_EXTENTION;
    }

}
