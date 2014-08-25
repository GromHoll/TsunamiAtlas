package edu.atlas.common.data;

import java.util.Collection;

public interface DataParser<T> {
    public Collection<T> parseData(String data);
}
