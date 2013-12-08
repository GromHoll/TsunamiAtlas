package edu.atlas.earthquake.entity;

import java.util.Date;

public class Earthquake {
    
    public static final String UNKNOWN_ID = "unknown id";
    public static final String UNKNOWN_PLACE = "unknown place";

    private String id;

    private Date date;
    private String place;
    private double mag;

    private double longitude;
    private double latitude;
    private double depth;

    public Earthquake(String id, Date date, String place, double mag, double longitude, double latitude, double depth) {
        this.id = (id != null) ? id : UNKNOWN_ID;
        this.date = (date != null) ? date : new Date();
        this.place = (place != null) ? place : UNKNOWN_PLACE;
        this.mag = mag;
        this.longitude = longitude;
        this.latitude = latitude;
        this.depth = depth;
    }

    public Date getDate() {
        return date;
    }

    public String getPlace() {
        return place;
    }

    public double getMag() {
        return mag;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getDepth() {
        return depth;
    }

    public String getId() {
        return id;
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
