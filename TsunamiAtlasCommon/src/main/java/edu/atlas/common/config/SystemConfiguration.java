package edu.atlas.common.config;


import edu.atlas.common.config.property.PropertyKey;

import java.util.Properties;

public class SystemConfiguration implements Configuration {

    @Override
    public <T> T getProperty(PropertyKey<T> propertyKey) {
        Properties properties = System.getProperties();
        String property = properties.getProperty(propertyKey.getKey());
        return property != null ? propertyKey.parseValue(property) : propertyKey.getDefaultValue();
    }

}
