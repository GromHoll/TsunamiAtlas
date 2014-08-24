package edu.atlas.common.data.event;

import java.util.Collection;
import java.util.List;

public class DataChangedEvent<T> {

    private Collection<T> allData;
    private Collection<T> changedData;
    private Collection<T> newData;

    public DataChangedEvent(Collection<T> allData, List<T> changedData, List<T> newData) {
        this.allData = allData;
        this.changedData = changedData;
        this.newData = newData;
    }

    public Collection<T> getAllData() {
        return allData;
    }

    public Collection<T> getChangedData() {
        return changedData;
    }

    public Collection<T> getNewData() {
        return newData;
    }
}
