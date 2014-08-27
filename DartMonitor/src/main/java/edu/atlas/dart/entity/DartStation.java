package edu.atlas.dart.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public class DartStation {

    @Getter private final String siteId;
    @Getter private final String name;
    @Getter private final double latitude;
    @Getter private final double longitude;

}
