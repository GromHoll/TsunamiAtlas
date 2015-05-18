package edu.atlas.dart.cleaner;


import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import edu.atlas.dart.entity.DartState;
import edu.atlas.dart.entity.DartStateDelta;
import edu.atlas.dart.entity.DartStation;
import edu.t_tide.TTide;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TTideCleaner implements DartCleaner {

    // TODO Move to properties
    private static final double DEFAULT_INTERVAL = 0.25;

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
    public List<DartStateDelta> clear(@NonNull DartStation station, @NonNull List<DartState> states) {
        try {
            return startClearing(station, states);
        } catch (MWException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<DartStateDelta> startClearing(DartStation station, List<DartState> states) throws MWException {

        List<DartState> filteredStates = filterDartStates(states);

        /* Input values*/
        double interval = DEFAULT_INTERVAL;
        double[] heights = getHeights(filteredStates);
        double[] date = getStartDate(filteredStates.get(0));

        Object[] res = tTide.t_tide(2, heights, INTERVAL_VAR, interval, START_TIME_VAR, date, LATITUDE_VAR, station.getLatitude());
        double[] heightDeltas = ((MWNumericArray) res[1]).getDoubleData();

        List<DartStateDelta> result = new ArrayList<>();
        for (int i = 0; i < filteredStates.size(); i++) {
            DartState state = filteredStates.get(i);
            if (state != null) {
                result.add(new DartStateDelta(state, heightDeltas[i]));
            }
        }

        return result;
    }

    private List<DartState> filterDartStates(List<DartState> states) {
        /* By default interval between dart states - 15 minutes*/
        List<DartState> filtered = states.stream().filter(state -> state.getDate().get(Calendar.MINUTE)%15 == 0
                                                                && state.getDate().get(Calendar.SECOND) == 0
                                                                && state.getDate().get(Calendar.MILLISECOND) == 0)
                .collect(Collectors.toList());

        if (!filtered.isEmpty()) {
            List<DartState> result = new ArrayList<>(filtered.size());

            result.add(filtered.get(0));
            for (int i = 1; i < filtered.size(); i++) {
                long previousDate = filtered.get(i - 1).getDate().getTimeInMillis();
                long currentDate = filtered.get(i).getDate().getTimeInMillis();

                long shift = 15*1000*60;
                while (previousDate + shift < currentDate) {
                    result.add(null);
                    shift += 15*1000*60;
                }
                if (previousDate < currentDate) {
                    result.add(filtered.get(i));
                }
            }

            return result;
        }

        return filtered;
    }

    private double[] getHeights(Collection<DartState> states) {
        return states.stream().mapToDouble(ds -> ds == null ? Double.NaN : ds.getHeight()).toArray();
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
