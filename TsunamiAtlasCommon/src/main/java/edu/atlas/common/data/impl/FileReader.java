package edu.atlas.common.data.impl;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileReader extends AbstractStreamReader {

    private final String filename;

    public FileReader(final String filename) {
        this.filename = filename;
    }

    @Override
    public BufferedReader openStream() throws IOException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
    }
}
