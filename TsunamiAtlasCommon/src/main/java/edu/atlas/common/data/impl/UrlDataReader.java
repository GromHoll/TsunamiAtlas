package edu.atlas.common.data.impl;

import edu.atlas.common.data.DataReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class UrlDataReader extends AbstractStreamReader {

    public static final int CONNECT_TIMEOUT = 5000;
    public static final int READ_TIMEOUT    = 1000;

    private final String urlAddress;

    public UrlDataReader(String urlAddress) {
        this.urlAddress = urlAddress;
    }

    @Override
    public BufferedReader openStream() throws IOException {
        URL url = new URL(urlAddress);
        URLConnection connection = url.openConnection();
        connection.setConnectTimeout(CONNECT_TIMEOUT);
        connection.setReadTimeout(READ_TIMEOUT);
        return new BufferedReader(new InputStreamReader(url.openStream()));
    }
}
