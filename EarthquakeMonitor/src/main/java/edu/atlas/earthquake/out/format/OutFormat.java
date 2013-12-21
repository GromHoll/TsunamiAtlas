package edu.atlas.earthquake.out.format;

import edu.atlas.common.data.impl.FileReader;
import edu.atlas.earthquake.entity.Earthquake;
import edu.atlas.earthquake.out.format.node.EarthquakeOutNode;
import edu.atlas.earthquake.out.format.node.NewLineOutNode;
import edu.atlas.earthquake.out.format.node.OutNode;
import edu.atlas.earthquake.out.format.node.StringOutNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OutFormat {

    public static final String SPLIT_SYMBOL = "%";
    public static final String COMMENT_SYMBOL = "#";
    private List<OutNode> format;

    public OutFormat(String filename) {
        format = createFormat(filename);
    }

    public String getFormattedText(Earthquake earthquake) {
        StringBuilder stringBuilder = new StringBuilder();
        for (OutNode outNode : format) {
            stringBuilder.append(outNode.getOut(earthquake));
        }
        return stringBuilder.toString();
    }

    private List<OutNode> createFormat(String filename) {
        FileReader reader = new FileReader(filename);
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

    private List<OutNode> getDefaultOutFormat() {
        List<OutNode> format = new ArrayList<>();
        format.add(new EarthquakeOutNode(EarthquakeOutNode.DEFAULT));
        return format;
    }
}
