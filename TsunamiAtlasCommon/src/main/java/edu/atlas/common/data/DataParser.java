package edu.atlas.common.data;

import java.util.List;

public interface DataParser<T> {
    public List<T> parseData(String data);
}
