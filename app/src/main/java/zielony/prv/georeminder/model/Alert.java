package zielony.prv.georeminder.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Proximity alert data.
 */
public class Alert implements Serializable {
    /**
     * Id.
     */
    private Long id;

    /**
     * Alert's location.
     */
    private Location location;

    /**
     * Alert's name.
     */
    private String name;

    /**
     * Alert description.
     */
    private String description;

    /**
     * Alert start date.
     */
    private Date startDate;

    /**
     * Alert end date.
     */
    private Date endDate;

    /**
     * Alert's active state indicator.
     */
    private boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String toString() {
        return "Location[id=" + id + ", name = " + name + ", description = " + description +
                ", active = " + active + ", startDate = " + startDate.toString() + ", endDate = " +
                endDate + (location != null ? ". location = " + location : "") + "]";
    }
}