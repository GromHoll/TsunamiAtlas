package edu.atlas.common.config.property;


public class StringPropertyKey extends PropertyKey<String> {

    public StringPropertyKey(String key) {
        super(key);
    }

    public StringPropertyKey(String key, String defaultValue) {
        super(key, defaultValue);
    }

    @Override
    public String parseValue(String str) {
        return str;
    }
}
