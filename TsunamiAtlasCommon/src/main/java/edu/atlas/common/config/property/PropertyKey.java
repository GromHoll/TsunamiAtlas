package edu.atlas.common.config.property;


public abstract class PropertyKey<T> {

    private String key;
    private T defaultValue;
    private boolean isRequired;

    protected PropertyKey(String key) {
        this(key, null, true);
    }

    protected PropertyKey(String key, T defaultValue) {
        this(key, defaultValue, false);
    }

    private PropertyKey(String key, T defaultValue, boolean isRequired) {
        this.key = key;
        this.defaultValue = defaultValue;
        this.isRequired = isRequired;
    }

    public String getKey() {
        return key;
    }

    public T getDefaultValue() {
        if (!isRequired) {
            return defaultValue;
        }
        throw new IllegalArgumentException("Required property <" + key + "> missed.");
    }

    public abstract T parseValue(String str);
}
