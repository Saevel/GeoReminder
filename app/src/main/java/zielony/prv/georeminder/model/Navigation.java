package zielony.prv.georeminder.model;

import javax.inject.Singleton;

@Singleton
public class Navigation {

    private String alertIdKey = "ALERT_ID";

    private String alertKey = "ALERT";

    private String locationKey = "LOCATION";

    public String getAlertIdKey() {
        return alertIdKey;
    }

    public String getAlertKey() {
        return alertKey;
    }

    public String getLocationKey() {
        return locationKey;
    }
}
