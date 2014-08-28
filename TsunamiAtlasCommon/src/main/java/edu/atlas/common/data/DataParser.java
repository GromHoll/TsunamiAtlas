package edu.atlas.common.data;

import java.util.List;

public interface DataParser<R, S> {
    public List<R> parseData(S data);
}
