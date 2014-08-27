package edu.atlas.earthquake.entity;

import lombok.Getter;

import java.util.Date;

public class Earthquake {
    
    public static final String UNKNOWN_ID = "unknown id";
    public static final String UNKNOWN_PLACE = "unknown place";

    @Getter private String id;

    @Getter private Date date;
    @Getter private String place;
    @Getter private double mag;

    @Getter private double longitude;
    @Getter private double latitude;
    @Getter private double depth;

    public Earthquake(String id, Date date, String place, double mag, double longitude, double latitude, double depth) {
        this.id = (id != null) ? id : UNKNOWN_ID;
        this.date = (date != null) ? date : new Date();
        this.place = (place != null) ? place : UNKNOWN_PLACE;
        this.mag = mag;
        this.longitude = longitude;
        this.latitude = latitude;
        this.depth = depth;
    }

    @Override
    public String toString() {
        return id + " : " + place;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj == this) return true;
        if(!(obj instanceof Earthquake)) return false;
        if(this.id.equals(UNKNOWN_ID)) return false;

        Earthquake eq = (Earthquake) obj;
        return this.id.equals(eq.id);
    }

}
