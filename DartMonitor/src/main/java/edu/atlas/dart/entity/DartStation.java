package edu.atlas.dart.entity;


import lombok.Getter;

public class DartStation {

    @Getter private String siteId;

    public DartStation(String siteId) {
        this.siteId = siteId;
    }

}
