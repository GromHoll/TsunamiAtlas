package edu.atlas.common.config;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public abstract class Configuration {

    private Properties properties = new Properties();

    public void loadFromPropertiesFile(String filename) {
        try {
            FileInputStream inputStream = new FileInputStream(filename);
            properties.load(inputStream);
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Properties getProperties() {
        return properties;
    }

    public String getPropertyWithDefault(String key, String defaultValue) {
        String property = getProperty(key);
        return (property != null) ? property : defaultValue;
    }

    public int getPropertyWithDefault(String key, Integer defaultValue) {
        try {
            return Integer.valueOf(getProperty(key));
        } catch (NumberFormatException exc) {
            printPropertyNotFoundError(key, defaultValue);
            return defaultValue;
        }
    }

    public boolean getPropertyWithDefault(String key, Boolean defaultValue) {
        try {
            return parseBoolean(getProperty(key));
        } catch (NumberFormatException exc) {
            printPropertyNotFoundError(key, defaultValue);
            return defaultValue;
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    private void printPropertyNotFoundError(String key, Object defaultValue) {
        System.err.println("Property with name <" + key + "> not found. Used default value " + defaultValue);
    }

    private boolean parseBoolean(String string) {
        if (string == null) {
            throw new NumberFormatException();
        }
        return Boolean.parseBoolean(string);
    }

}
