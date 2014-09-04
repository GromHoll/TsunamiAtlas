package edu.atlas.common.data;

import java.io.IOException;
import java.util.List;

public interface DataReader {

    public List<String> getData() throws IOException;

    public String getAllData() throws IOException;

}
