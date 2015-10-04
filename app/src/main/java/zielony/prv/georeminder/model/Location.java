package zielony.prv.georeminder.model;

import java.io.Serializable;

/**
 * Physical location.
 */
public class Location implements Serializable {
    /**
     * Id.
     */
    private Long id;

    /**
     * Longitude.
     */
    private double longitude;

    /**
     * Latitude.
     */
    private double latitude;

    /**
     * Location's name.
     */
    private String name;

    public Location() {
        ;
    }

    public Location(double longitude, double latitude, String name) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "Location[id = " + id + ", longitude = " + longitude + ", latitude = " + latitude +
                ", name = " + name + "]";
    }
}