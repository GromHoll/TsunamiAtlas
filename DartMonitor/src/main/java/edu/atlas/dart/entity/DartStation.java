package edu.atlas.dart.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DartStation {

    @Getter private final String siteId;
    @Getter private final String name;
    @Getter private final double latitude;
    @Getter private final double longitude;

    @Override
    public String toString() {
        return String.format("[% 3.3f, % 3.3f] - %s", latitude, longitude, siteId);
    }
}
