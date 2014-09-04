package edu.atlas.common.data.impl;

import edu.atlas.common.data.DataReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractStreamReader implements DataReader {

    @Override
    public List<String> getData() throws IOException {
        try (BufferedReader in  = openStream()) {
            return readData(in);
        }
    }

    @Override
    public String getAllData() throws IOException {
        return getData().stream().collect(Collectors.joining("\n"));
    }

    private List<String> readData(BufferedReader in) throws IOException {
        return in.lines().collect(Collectors.toList());

//        List<String> lines = new ArrayList<>();
//        String tempLine;
//        while ((tempLine = in.readLine()) != null) {
//            lines.add(tempLine);
//        }
//        return lines.toArray(new String[lines.size()]);
    }

    abstract public BufferedReader openStream() throws IOException ;
}
