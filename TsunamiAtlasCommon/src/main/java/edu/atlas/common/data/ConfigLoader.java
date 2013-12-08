package edu.atlas.common.data;

import edu.atlas.common.data.impl.FileReader;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ConfigLoader {

    public static final String COMMENT_PREFIX = "#";
    public static final String SPLIT_SYMBOL = "=";
    private FileReader fileReader;

    public ConfigLoader(String filename) {
        fileReader = new FileReader(filename);
    }

    public Map<String, String> getConfig() {
        try {
            return getConfigFromData(fileReader.getData());
        } catch (IOException exc) {
            exc.printStackTrace();
            return Collections.emptyMap();
        }
    }

    private Map<String, String> getConfigFromData(String[] data) {
        Map<String, String> configMap = new HashMap<>();
        for (String s : data) {
            if (!s.startsWith(COMMENT_PREFIX) && !s.trim().isEmpty()) {
                String[] values = s.split(SPLIT_SYMBOL, 2);
                if (values.length == 2) {
                    configMap.put(values[0].trim(), values[1].trim());
                } else {
                    System.err.println("String : \"" + s + "\" invalid");
                }
            }
        }
        return configMap;
    }

}
