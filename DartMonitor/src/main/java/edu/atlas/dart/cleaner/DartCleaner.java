package edu.atlas.dart.cleaner;


import edu.atlas.dart.entity.DartState;
import edu.atlas.dart.entity.DartStateDelta;
import edu.atlas.dart.entity.DartStation;

import java.util.List;

public interface DartCleaner {

    List<DartStateDelta> clear(DartStation station, List<DartState> states);
    void dispose();

}
