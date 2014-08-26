package edu.atlas.common.data.impl;

import edu.atlas.common.data.DataReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractStreamReader implements DataReader {

    @Override
    public String[] getData() throws IOException {
        try (BufferedReader in  = openStream()) {
            return readData(in);
        }
    }

    @Override
    public String getAllData() throws IOException {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(getData()).forEach(line -> sb.append(line).append("\n"));
        return sb.toString();
    }

    private String[] readData(BufferedReader in) throws IOException {
        List<String> lines = new ArrayList<>();
        String tempLine;
        while ((tempLine = in.readLine()) != null) {
            lines.add(tempLine);
        }
        return lines.toArray(new String[lines.size()]);
    }

    abstract public BufferedReader openStream() throws IOException ;
}
