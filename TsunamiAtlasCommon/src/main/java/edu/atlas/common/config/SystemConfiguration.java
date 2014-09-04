package edu.atlas.common.config;


import edu.atlas.common.config.property.PropertyKey;

import static java.lang.System.getProperties;

public class SystemConfiguration implements Configuration {

    @Override
    public <T> T getProperty(PropertyKey<T> propertyKey) {
        String property = getProperties().getProperty(propertyKey.getKey());
        return property != null ? propertyKey.parseValue(property) : propertyKey.getDefaultValue();
    }

}
