package edu.atlas.common.data;

import edu.atlas.common.data.event.DataChangedEvent;

public interface DataChangedListener<T> {
    public void process(DataChangedEvent<T> event);
}
