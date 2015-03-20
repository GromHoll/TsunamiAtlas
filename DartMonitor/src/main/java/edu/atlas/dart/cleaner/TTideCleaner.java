package edu.atlas.dart.cleaner;


import edu.atlas.dart.entity.DartState;
import edu.atlas.dart.entity.DartStation;
import lombok.NonNull;
import matlabcontrol.*;
import matlabcontrol.MatlabProxyFactoryOptions.Builder;

import java.io.File;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TTideCleaner implements DartCleaner {

    private static final String MATLAB_EXECUTE_PATH = "G:\\Projects\\MATLAB\\t_tide3";
    private static final double DEFAULT_INTERVAL = 0.25;

    private static final String TIDE_INFO_VAR = "tide";
    private static final String OUT_ARRAY_VAR = "out";

    private static final String IN_ARRAY_VAR = "in";
    private static final String INTERVAL_VAR = "interval";
    private static final String START_TIME_VAR = "start";
    private static final String LATITUDE_VAR = "latitude";

    private static final String COMMAND

            = String.format("t_tide(%s, 'interval', %s, 'start', %s, 'latitude', %s)",
            IN_ARRAY_VAR, INTERVAL_VAR, START_TIME_VAR, LATITUDE_VAR);

//            = String.format("[%s, %s] = t_tide(%s, 'interval', %s, 'start', %s, 'latitude', %s)",
//            TIDE_INFO_VAR, OUT_ARRAY_VAR,
//            IN_ARRAY_VAR, INTERVAL_VAR, START_TIME_VAR, LATITUDE_VAR);

    private MatlabProxy proxy = null;

    public TTideCleaner() {
        connectToMatlab();
    }

    private void connectToMatlab() {
        try {
            MatlabProxyFactoryOptions options = new Builder().setMatlabStartingDirectory(new File(MATLAB_EXECUTE_PATH))
                                                             .build();
            MatlabProxyFactory factory = new MatlabProxyFactory(options);
            proxy = factory.getProxy();
        } catch (MatlabConnectionException exc) {
            throw new RuntimeException("MATLAB cannot be started. Check it and try again", exc);
        }
    }

    @Override
    public Collection<DartState> clear(@NonNull DartStation station, @NonNull List<DartState> states) {
        try {
            return startClearing(station, states);
        } catch (MatlabInvocationException exc) {
            throw new RuntimeException("Error in MATLAB", exc);
        }
    }

    private Collection<DartState> startClearing(DartStation station, List<DartState> states)
            throws MatlabInvocationException {

        List<DartState> filteredStates = filterDartStates(states);

        /* Input values*/
        double interval = DEFAULT_INTERVAL;
        double[] heights = getHeights(filteredStates);
        double[] date = getStartDate(filteredStates.get(0));

        proxy.setVariable(IN_ARRAY_VAR, heights);
        proxy.setVariable(INTERVAL_VAR, interval);
        proxy.setVariable(START_TIME_VAR, date);
        proxy.setVariable(LATITUDE_VAR, station.getLatitude());

        /* Run matlab code*/
        Object[] returned = proxy.returningEval(COMMAND, 2);
        double[] heightDeltas = (double[]) returned[1];

        for (int i = 0; i < filteredStates.size(); i++) {
            DartState state = filteredStates.get(i);
            state.setClearedHeightDelta(heightDeltas[i]);
        }

        return states;
    }

    private List<DartState> filterDartStates(List<DartState> states) {
        // TODO cut subset of all data for performance
        /* By default interval between dart states - 15 minutes*/
        return states.stream().filter(state -> state.getDate().get(Calendar.MINUTE) % 15 == 0)
                .collect(Collectors.toList());
    }

    private double[] getHeights(Collection<DartState> states) {
        return states.stream().mapToDouble(DartState::getHeight).toArray();
    }

    /* [year,month,day,hour,min,sec] */
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
