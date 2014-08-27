package edu.atlas.common.data.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;

@AllArgsConstructor
public class DataChangedEvent<T> {

    @Getter private Collection<T> allData;
    @Getter private Collection<T> changedData;
    @Getter private Collection<T> newData;

}
