package edu.atlas.dart.cleaner;


import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import edu.atlas.dart.entity.DartState;
import edu.atlas.dart.entity.DartStation;
import edu.t_tide.TTide;
import lombok.NonNull;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TTideCleaner implements DartCleaner {

    // TODO Move to properties
    private static final double DEFAULT_INTERVAL = 0.25;

    private static final String IN_ARRAY_VAR = "in";
    private static final String INTERVAL_VAR = "interval";
    private static final String START_TIME_VAR = "start";
    private static final String LATITUDE_VAR = "latitude";

    private TTide tTide;

    public TTideCleaner() {
        try {
            tTide = new TTide();
        } catch (MWException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dispose() {
        tTide.dispose();
    }

    @Override
    public Collection<DartState> clear(@NonNull DartStation station, @NonNull List<DartState> states) {
        try {
            return startClearing(station, states);
        } catch (MWException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Collection<DartState> startClearing(DartStation station, List<DartState> states) throws MWException {

        List<DartState> filteredStates = filterDartStates(states);

        /* Input values*/
        double interval = DEFAULT_INTERVAL;
        double[] heights = getHeights(filteredStates);
        double[] date = getStartDate(filteredStates.get(0));

        Object[] res = tTide.t_tide(2, heights, INTERVAL_VAR, interval, START_TIME_VAR, date, LATITUDE_VAR, station.getLatitude());
        double[] heightDeltas = ((MWNumericArray) res[1]).getDoubleData();

        for (int i = 0; i < filteredStates.size(); i++) {
            DartState state = filteredStates.get(i);
            state.setClearedHeightDelta(heightDeltas[i]);
        }

        return states;
    }

    private List<DartState> filterDartStates(List<DartState> states) {
        /* By default interval between dart states - 15 minutes*/
        List<DartState> filtered = states.stream().filter(state -> state.getDate().get(Calendar.MINUTE) % 15 == 0)
                .collect(Collectors.toList());



        return filtered.subList(filtered.size() - 500, filtered.size() - 1);
    }

    private double[] getHeights(Collection<DartState> states) {
        return states.stream().mapToDouble(DartState::getHeight).toArray();
    }

    /*
     * Transform date from DartState to array
     * [year, month, day, hour, min, sec]
     */
    private double[] getStartDate(DartState dartState) {
        Calendar date = dartState.getDate();
        return new double[] {date.get(Calendar.YEAR) - 1900,
                             date.get(Calendar.MONTH),
                             date.get(Calendar.DAY_OF_MONTH),
                             date.get(Calendar.HOUR_OF_DAY),
                             date.get(Calendar.MINUTE),
                             date.get(Calendar.SECOND)};
    }

}
