package edu.atlas.dart.cleaner;


import edu.atlas.dart.entity.DartState;
import edu.atlas.dart.entity.DartStation;
import edu.atlas.dart.entity.DartStations;

import java.util.Collection;
import java.util.List;

public interface DartCleaner {

    public Collection<DartState> clear(DartStation station, List<DartState> states);
    public Collection<DartState> clear(DartStation station, DartStations source);

}
