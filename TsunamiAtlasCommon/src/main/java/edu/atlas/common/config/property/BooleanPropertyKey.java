package edu.atlas.common.config.property;


public class BooleanPropertyKey extends PropertyKey<Boolean> {

    public BooleanPropertyKey(String key, boolean defaultValue) {
        super(key, defaultValue);
    }

    @Override
    public Boolean parseValue(String str) {
        return Boolean.parseBoolean(str);
    }
}
