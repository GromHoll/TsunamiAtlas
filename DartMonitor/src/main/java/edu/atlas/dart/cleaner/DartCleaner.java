package edu.atlas.dart.cleaner;


import edu.atlas.dart.entity.DartState;
import edu.atlas.dart.entity.DartStation;

import java.util.Collection;
import java.util.List;

public interface DartCleaner {

    Collection<DartState> clear(DartStation station, List<DartState> states);
    void dispose();

}
