package edu.atlas.dart.data;


import com.google.common.collect.Lists;
import edu.atlas.common.data.DataParser;
import edu.atlas.dart.entity.DartState;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class DartStatesParser implements DataParser<DartState, List<String>> {

    private static final int HEADER_SIZE = 2;
    private static final String SPLIT_SYMBOL = " ";

    private static final int YEAR = 0;
    private static final int YEARS_SHIFT = 1900;
    private static final int MONTH = 1;
    private static final int DAY = 2;
    private static final int HOURS = 3;
    private static final int MINUTES = 4;
    private static final int SECONDS = 5;

    @Override
    public List<DartState> parseData(List<String> data) {
        Collection<String> dartData = getStatesDataWithoutHeader(data);
        List<DartState> originalOrder = dartData.stream().map(this::parseState).collect(Collectors.toList());
        return Lists.reverse(originalOrder);
    }

    private Collection<String> getStatesDataWithoutHeader(List<String> data) {
        return data.subList(HEADER_SIZE, data.size());
    }

    private DartState parseState(String data) {
        String tokens[] = data.split(SPLIT_SYMBOL);
        Calendar date = parseDate(tokens);
        double height = parseDouble(tokens[7]);
        return new DartState(date, height);
    }
    
    private Calendar parseDate(String tokens[]) {
       return new GregorianCalendar(parseInt(tokens[YEAR]),
                                    parseInt(tokens[MONTH]), parseInt(tokens[DAY]),
                                    parseInt(tokens[HOURS]), parseInt(tokens[MINUTES]), parseInt(tokens[SECONDS]));
    }


}
