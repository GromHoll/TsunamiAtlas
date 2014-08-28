package edu.atlas.dart.data;


import edu.atlas.common.data.DataParser;
import edu.atlas.dart.entity.DartStation;
import org.apache.commons.lang3.Range;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class DartStationsParser implements DataParser<DartStation, String[]> {

    private static final int HEADER_SIZE = 2;

    private static final Range<Integer> SITE = Range.between(0, 10);
    private static final Range<Integer> NAME = Range.between(13, 64);
    private static final Range<Integer> LATITUDE  = Range.between(65, 74);
    private static final Range<Integer> LONGITUDE = Range.between(76, 86);

    @Override
    public List<DartStation> parseData(String[] data) {
        Collection<String> dartData = getDartDataWithoutHeader(data);
        return dartData.stream().map(this::parseDart).collect(Collectors.toList());
    }

    private Collection<String> getDartDataWithoutHeader(String[] data) {
        List<String> list = Arrays.asList(data);
        return list.subList(HEADER_SIZE, list.size());
    }

    private DartStation parseDart(String data) {
        String site = getSubString(data, SITE).trim();
        String name = getSubString(data, NAME).trim();
        Double latitude  = Double.valueOf(getSubString(data, LATITUDE));
        Double longitude = Double.valueOf(getSubString(data, LONGITUDE));
        return new DartStation(site, name, latitude, longitude);
    }

    private String getSubString(String string, Range<Integer> range) {
        return string.substring(range.getMinimum(), range.getMaximum());
    }

}
