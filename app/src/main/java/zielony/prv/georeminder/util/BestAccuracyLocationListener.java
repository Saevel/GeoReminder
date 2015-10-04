package zielony.prv.georeminder.util;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import javax.inject.Inject;

import zielony.prv.georeminder.api.ErrorEvent;
import zielony.prv.georeminder.api.ErrorType;
import zielony.prv.georeminder.api.EventHandler;
import zielony.prv.georeminder.api.ModelEvent;
import zielony.prv.georeminder.model.Configuration;

public class BestAccuracyLocationListener implements LocationListener {

    private EventHandler<ModelEvent<zielony.prv.georeminder.model.Location>> successHandler;

    private EventHandler<ErrorEvent> failureHandler;

    private LocationManager locationManager;

    private Configuration configuration;

    public BestAccuracyLocationListener(LocationManager locationManager,
                                        Configuration configuration,
                                        EventHandler<ModelEvent<zielony.prv.georeminder.model.Location>> successHandler,
                                        EventHandler<ErrorEvent> failureHandler) {
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.locationManager = locationManager;
        this.configuration = configuration;
    }

    @Override
    public void onLocationChanged(Location location) {
        successHandler.handle(new ModelEvent<zielony.prv.georeminder.model.Location>(
                new zielony.prv.georeminder.model.Location(
                        location.getLongitude(), location.getLatitude(), null)));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        ;
    }

    @Override
    public void onProviderEnabled(String provider) {
        String bestProvider = this.chooseBestProvider();
        locationManager.removeUpdates(this);
        if(bestProvider != null) {
            this.registerLocationListener(bestProvider, this.configuration, this);
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        locationManager.removeUpdates(this);
        String bestProvider = this.chooseBestProvider();
        if(bestProvider != null) {
            this.registerLocationListener(bestProvider, this.configuration, this);
        }
    }

    public String chooseBestProvider() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_HIGH);
        String provider = locationManager.getBestProvider(criteria, true);
        if(provider != null) {
            return provider;
        }

        criteria.setAccuracy(Criteria.ACCURACY_MEDIUM);
        provider = locationManager.getBestProvider(criteria, true);
        if(provider != null) {
            return provider;
        }

        criteria.setAccuracy(Criteria.ACCURACY_LOW);
        provider = locationManager.getBestProvider(criteria, true);

        if(provider != null) {
            return provider;
        }
        else {
            failureHandler.handle(new ErrorEvent(ErrorType.NO_LOCATION_PROVIDER_AVAILABLE));
            return null;
        }
    }

    public LocationListener registerLocationListener(String provider, Configuration configuration,
                                                     LocationListener listener) {
        locationManager.requestLocationUpdates(provider, configuration.getMinimumUpdateInterval(),
                (float)configuration.getMinimalDistinguishibleDistance(), listener);

        return listener;
    }
}