package edu.atlas.dart.cleaner;


import edu.atlas.dart.entity.DartState;
import edu.atlas.dart.entity.DartStation;
import edu.atlas.dart.entity.DartStations;
import lombok.NonNull;
import matlabcontrol.*;

import java.io.File;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TTideCleaner implements DartCleaner {

    private static final String TIDE_INFO_VAR = "tide";
    private static final String OUT_ARRAY_VAR = "out";

    private static final String IN_ARRAY_VAR = "in";
    private static final String INTERVAL_VAR = "interval";
    private static final String START_TIME_VAR = "start";
    private static final String LATITUDE_VAR = "latitude";

    private static final String COMMAND
            = String.format("[%s, %s] = t_tide(%s, 'interval', %s, 'start', %s, 'latitude', %s)",
            TIDE_INFO_VAR, OUT_ARRAY_VAR,
            IN_ARRAY_VAR, INTERVAL_VAR, START_TIME_VAR, LATITUDE_VAR);

    private MatlabProxy proxy = null;

    public TTideCleaner() {
        connectToMatlab();
    }

    private void connectToMatlab() {
        try {
            if (proxy != null) {
                proxy.disconnect();
            }

            MatlabProxyFactoryOptions options =
                    new MatlabProxyFactoryOptions.Builder().setMatlabStartingDirectory(new File("G:\\Projects\\MATLAB\\t_tide3")).build();
            MatlabProxyFactory factory = new MatlabProxyFactory(options);
            proxy = factory.getProxy();
        } catch (MatlabConnectionException exc) {
            throw new RuntimeException("MATLAB cannot be started. Check it and try again", exc);
        }
    }

    public Collection<DartState> clear(@NonNull DartStation station, @NonNull DartStations source) {
        return clear(station, source.getDartStates(station));
    }

    public Collection<DartState> clear(@NonNull DartStation station, @NonNull List<DartState> states) {
        try {
            return startClearing(station, states);
        } catch (MatlabInvocationException exc) {
            throw new RuntimeException("Error in MATLAB", exc);
        }
    }

    private Collection<DartState> startClearing(DartStation station, List<DartState> states)
            throws MatlabInvocationException {

        /* Output values */
        proxy.setVariable(TIDE_INFO_VAR, 0);
        proxy.setVariable(OUT_ARRAY_VAR, new double[100]);

        /* Input values*/
        List<DartState> filteredStates = filterDartStates(states);
        double[] heights = getHeights(filteredStates);
        double interval = getInterval(filteredStates);
        double[] date = getStartDate(filteredStates.get(0));

        proxy.setVariable(IN_ARRAY_VAR, heights);
        proxy.setVariable(INTERVAL_VAR, interval);
        proxy.setVariable(START_TIME_VAR, date);
        proxy.setVariable(LATITUDE_VAR, station.getLatitude());

        /* Run matlab code*/
        proxy.eval(COMMAND);


        return states;
    }

    private List<DartState> filterDartStates(List<DartState> states) {
        /* By default interval between dart states - 15 minutes*/
        return states.subList(states.size() - 100, states.size()).stream().filter(state -> state.getDate().get(Calendar.MINUTE) % 15 == 0)
                .collect(Collectors.toList());
    }

    private double[] getHeights(Collection<DartState> states) {
        return states.stream().mapToDouble(DartState::getHeight).toArray();
    }

    private double getInterval(List<DartState> states) {
        Calendar first = states.get(0).getDate();
        Calendar last = states.get(states.size() - 1).getDate();
        return (first.getTimeInMillis() - last.getTimeInMillis())/3600000.0;
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
