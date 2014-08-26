package edu.atlas.common.data;

import java.util.Collection;

public interface DataParser<R, S> {
    public Collection<R> parseData(S data);
}
