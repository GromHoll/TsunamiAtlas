package edu.atlas.common.config.property;


import java.util.Optional;

public abstract class PropertyKey<T> {

    private String key;
    private Optional<T> defaultValue;

    protected PropertyKey(String key) {
        this(key, null);
    }

    protected PropertyKey(String key, T defaultValue) {
        this.key = key;
        this.defaultValue = Optional.ofNullable(defaultValue);
    }

    public String getKey() {
        return key;
    }

    public T getDefaultValue() {
        return defaultValue.get();
    }

    abstract T parseValue(String str);
}
