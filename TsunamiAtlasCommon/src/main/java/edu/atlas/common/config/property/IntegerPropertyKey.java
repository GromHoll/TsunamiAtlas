package edu.atlas.common.config.property;


public class IntegerPropertyKey extends PropertyKey<Integer> {

    public IntegerPropertyKey(String key, int defaultValue) {
        super(key, defaultValue);
    }

    @Override
    public Integer parseValue(String str) {
        return Integer.parseInt(str);
    }
}
