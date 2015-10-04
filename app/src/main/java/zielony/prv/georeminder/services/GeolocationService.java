package zielony.prv.georeminder.services;

import zielony.prv.georeminder.api.ErrorEvent;
import zielony.prv.georeminder.api.EventHandler;
import zielony.prv.georeminder.api.ModelEvent;
import zielony.prv.georeminder.model.Location;

public interface GeolocationService {

    void geocode(Location location, EventHandler<ModelEvent<Location>> successHandler,
                 EventHandler<ErrorEvent> failureHandler);

    void reverseGeocode(Location location, EventHandler<ModelEvent<Location>> successHandler,
                 EventHandler<ErrorEvent> failureHandler);

    void locateUser(EventHandler<ModelEvent<Location>> successHandler,
                    EventHandler<ErrorEvent> failureHandler);

    double getDistance(Location first, Location second);
}
