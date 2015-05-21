package edu.atlas.dart.gui;

import edu.atlas.dart.entity.DartState;
import edu.atlas.dart.entity.DartStateDelta;
import lombok.NonNull;
import org.jfree.data.xy.AbstractXYDataset;

import java.util.List;

/**
 * @author GromHoll
 * @since 27.02.2015
 */
public class DartStatesXYDataSet extends AbstractXYDataset {

    private static final String[] SERIES_KEYS = { "DART Data"," Cleared data" };

    private List<DartState> states;
    private List<DartStateDelta> delta;

    public void setDartStates(@NonNull List<DartState> states,@NonNull  List<DartStateDelta> delta) {
        this.states = states;
        this.delta = delta;
        fireDatasetChanged();
    }

    @Override
    public int getSeriesCount() {
        return SERIES_KEYS.length;
    }

    @Override
    public Comparable getSeriesKey(int series) {
        return SERIES_KEYS[series];
    }

    @Override
    public int getItemCount(int series) {
        if (states == null) { return 0; }
        switch (series) {
            case 0:     return states.size();
            case 1:     return delta.size();
            default:    return 0;
        }
    }

    @Override
    public Number getX(int series, int item) {
        if (states == null) { return 0; }
        switch (series) {
            case 0:     return states.get(item).getDate().getTimeInMillis();
            case 1:     return delta.get(item).getState().getDate().getTimeInMillis();
            default:    return 0;
        }
    }

    @Override
    public Number getY(int series, int item) {
        if (states == null) { return 0; }
        switch (series) {
            case 0:     return states.get(item).getHeight();
            case 1:     return delta.get(item).getState().getClearedHeight();
            default:    return 0;
        }
    }
}
