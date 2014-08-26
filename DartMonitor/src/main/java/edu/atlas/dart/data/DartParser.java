package edu.atlas.dart.data;


import edu.atlas.common.data.DataParser;
import edu.atlas.dart.entity.DartStation;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class DartParser implements DataParser<DartStation, String[]> {

    public static final int HEADER_SIZE = 2;
    public static final String SPLIT_SYMBOL = " ";

    @Override
    public Collection<DartStation> parseData(String[] data) {
        Collection<String> dartData = getDartDataWithoutHeader(data);
        return dartData.stream().map(this::parseDart).collect(Collectors.toList());
    }

    private Collection<String> getDartDataWithoutHeader(String[] data) {
        List<String> list = Arrays.asList(data);
        return list.subList(HEADER_SIZE, list.size());
    }

    private DartStation parseDart(String data) {
        return new DartStation(data.split(SPLIT_SYMBOL)[0]);
    }
}
