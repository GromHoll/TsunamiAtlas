package edu.atlas.common.config.property;


public class DoublePropertyKey extends PropertyKey<Double> {

    public DoublePropertyKey(String key, Double defaultValue) {
        super(key, defaultValue);
    }

    @Override
    public Double parseValue(String str) {
        return Double.parseDouble(str);
    }
}
