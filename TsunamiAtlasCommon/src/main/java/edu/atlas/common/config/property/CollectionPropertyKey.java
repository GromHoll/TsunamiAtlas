package edu.atlas.common.config.property;


import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionPropertyKey<T> extends PropertyKey<Collection<T>> {

    PropertyKey<? extends T> propertyKey;
    String separator;

    public CollectionPropertyKey(PropertyKey<? extends T> propertyKey, String separator) {
        super(propertyKey.getKey(), Collections.emptyList());
        this.propertyKey = propertyKey;
        this.separator = separator;
    }

    @Override
    public Collection<T> parseValue(String str) {
        String[] split = str.split(separator);
        return Stream.of(split).map(propertyKey::parseValue).collect(Collectors.<T>toList());
    }
}
