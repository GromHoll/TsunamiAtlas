package edu.atlas.common.config;


import edu.atlas.common.config.property.PropertyKey;

public interface Configuration {

    public <T> T getProperty(PropertyKey<T> property);

}
