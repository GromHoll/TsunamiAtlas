package edu.atlas.earthquake.out;

import edu.atlas.common.data.impl.FileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class OutFormat {

    public static final String SPLIT_SYMBOL = "%";
    public static final String COMMENT_SYMBOL = "#";
    private FileReader reader;

    public OutFormat(String filename) {
        reader = new FileReader(filename);
    }

    public List<OutNode> createFormat() {
        try {
            return parseLines(reader.getData());
        } catch (IOException exc) {
            exc.printStackTrace();
            return getDefaultOutFormat();
        }
    }

    private List<OutNode> parseLines(String[] lines) {
        List<OutNode> format = new ArrayList<>();
        for (String line : lines) {
            format.addAll(parseSingleLine(line));
        }
        format.remove(format.size() - 1);
        return format;
    }

    private List<OutNode> parseSingleLine(String line) {
        if (line.startsWith(COMMENT_SYMBOL)) {
            return Collections.EMPTY_LIST;
        }

        List<OutNode> lineFormat = new ArrayList<>();
        String[] values = line.split(SPLIT_SYMBOL);
        for (String v : values) {
            lineFormat.add(createOutNode(v));
        }
        lineFormat.add(new NewLineOutNode());
        return lineFormat;
    }

    private OutNode createOutNode(String value) {
        switch (value) {
            case EarthquakeOutNode.DATE:
            case EarthquakeOutNode.PLACE:
            case EarthquakeOutNode.MAG:
            case EarthquakeOutNode.LONGITUDE:
            case EarthquakeOutNode.LATITUDE:
            case EarthquakeOutNode.DEPTH:
                return new EarthquakeOutNode(value);
            default:
                return new StringOutNode(value);
        }
    }

    public List<OutNode> getDefaultOutFormat() {
        List<OutNode> format = new ArrayList<>();
        format.add(new EarthquakeOutNode(EarthquakeOutNode.DEFAULT));
        return format;
    }
}
