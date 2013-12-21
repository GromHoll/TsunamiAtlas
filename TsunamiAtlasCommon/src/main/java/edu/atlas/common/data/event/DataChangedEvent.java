package edu.atlas.common.data.event;

import java.util.List;

public class DataChangedEvent<T> {

    private List<T> allData;
    private List<T> changedData;
    private List<T> newData;

    public DataChangedEvent(List<T> allData, List<T> changedData, List<T> newData) {
        this.allData = allData;
        this.changedData = changedData;
        this.newData = newData;
    }

    public List<T> getAllData() {
        return allData;
    }

    public List<T> getChangedData() {
        return changedData;
    }

    public List<T> getNewData() {
        return newData;
    }
}
