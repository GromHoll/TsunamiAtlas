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

    private List<DartState> states;

    public void setDartStates(@NonNull List<DartState> states) {
        this.states = states;
        fireDatasetChanged();
    }

    @Override
    public int getSeriesCount() {
        return 1;
    }

    @Override
    public Comparable getSeriesKey(int series) {
        return 0;
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
        return states != null ? states.get(item).getHeight() : 0;
    }
}
