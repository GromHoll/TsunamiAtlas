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
public class HeightDeltaXYDataSet extends AbstractXYDataset {

    private static final String[] SERIES_KEYS = { "Cleared data delta" };

    private List<DartStateDelta> delta;

    public void setHeightDelta(@NonNull List<DartStateDelta> delta) {
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
        return delta != null ? delta.size() : 0;
    }

    @Override
    public Number getX(int series, int item) {
        return delta != null ? delta.get(item).getState().getDate().getTimeInMillis() : 0;
    }

    @Override
    public Number getY(int series, int item) {
        return delta != null ? delta.get(item).getState().getDelta() : 0;
    }
}
