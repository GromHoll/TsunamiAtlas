package edu.atlas.common.data;

import java.util.List;

public interface DataWriter<T> {
    public void output(List<T> list);
}
