package edu.atlas.dart.gui;

import edu.atlas.dart.entity.DartState;
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

    public void setDartStates(@NonNull List<DartState> states) {
        this.states = states;
        fireDatasetChanged();
    }

    @Override
    public int getSeriesCount() {
        return 2;
    }

    @Override
    public Comparable getSeriesKey(int series) {
        return SERIES_KEYS[series];
    }

    @Override
    public int getItemCount(int series) {
        return states != null ? states.size() : 0;
    }

    @Override
    public Number getX(int series, int item) {
        return states != null ? states.get(item).getDate().getTimeInMillis() : 0;
    }

    @Override
    public Number getY(int series, int item) {
        if (states == null) { return 0; }
        switch (series) {
            case 0:     return states.get(item).getHeight();
            case 1:     return states.get(item).getClearedHeight();
            default:    return 0;
        }
    }
}
