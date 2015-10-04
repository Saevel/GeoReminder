package zielony.prv.georeminder.services;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.ConnectivityManager;

import com.google.inject.Inject;

import java.io.IOException;
import java.util.List;

import javax.inject.Singleton;

import zielony.prv.georeminder.api.ErrorEvent;
import zielony.prv.georeminder.api.ErrorType;
import zielony.prv.georeminder.api.EventHandler;
import zielony.prv.georeminder.api.ModelEvent;
import zielony.prv.georeminder.model.Configuration;
import zielony.prv.georeminder.model.Location;
import zielony.prv.georeminder.util.BestAccuracyLocationListener;

public class DefaultGeolocationService implements GeolocationService {

    private static final int SINGLE_RESULT = 1;

    @Singleton
    Configuration configuration;

    @Inject
    ConnectivityManager connectivityManager;

    @Inject
    LocationManager locationManager;

    @Inject
    Context context;

    private Geocoder geocoder;

    @Override
    public void geocode(Location location, EventHandler<ModelEvent<Location>> successHandler, EventHandler<ErrorEvent> failureHandler) {
        if(isConnected()){
            try {
                Geocoder geocoder = this.getGeocoder();
                List<Address> addressList =
                        geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), SINGLE_RESULT);

                if(addressList == null || addressList.isEmpty()) {
                    failureHandler.handle(new ErrorEvent(ErrorType.GEOCODING_ERROR));
                }

                String addressAsText = parseAddresses(addressList);

                Location geocodedLocation = new Location(location.getLongitude(), location.getLatitude(),
                        addressAsText);
                successHandler.handle(new ModelEvent<Location>(geocodedLocation));

            } catch (IOException e) {
                failureHandler.handle(new ErrorEvent(ErrorType.NO_INTERNET_CONNECTION));
            }
        }
        else {
            failureHandler.handle(new ErrorEvent(ErrorType.NO_INTERNET_CONNECTION));
        }
    }

    @Override
    public void reverseGeocode(Location location, EventHandler<ModelEvent<Location>> successHandler, EventHandler<ErrorEvent> failureHandler) {
        if(isConnected()){
            try {
                geocoder = this.getGeocoder();

                List<Address> addressList = geocoder.getFromLocationName(location.getName(), SINGLE_RESULT);

                if(addressList == null || addressList.isEmpty()) {
                    failureHandler.handle(new ErrorEvent(ErrorType.GEOCODING_ERROR));
                }

                Address address = addressList.iterator().next();

                String addressAsText = parseAddresses(addressList);

                Location result = new Location(address.getLongitude(), address.getLatitude(), addressAsText);

                successHandler.handle(new ModelEvent<Location>(result));

            } catch (IOException e) {
                failureHandler.handle(new ErrorEvent(ErrorType.NO_INTERNET_CONNECTION));
            }
        }
        else{
            failureHandler.handle(new ErrorEvent(ErrorType.NO_INTERNET_CONNECTION));
        }
    }

    private String parseAddresses(List<Address> addressList) {
        Address address = addressList.iterator().next();
        String addressAsText = "";
        for(int i = 0; i < address.getMaxAddressLineIndex(); i++) {
            addressAsText += (address.getAddressLine(i) + " ");
        }
        addressAsText += address.getAddressLine(address.getMaxAddressLineIndex());
        return addressAsText;
    }

    @Override
    public void locateUser(EventHandler<ModelEvent<Location>> successHandler, EventHandler<ErrorEvent> failureHandler) {

        BestAccuracyLocationListener listener = new BestAccuracyLocationListener(locationManager, configuration,
                successHandler, failureHandler);

        String bestProvider = listener.chooseBestProvider();
        if(bestProvider == null) {
            listener.registerLocationListener(bestProvider, configuration, listener);
        }
    }

    @Override
    public double getDistance(Location first, Location second) {
        //TODO: Haversine Formula
        return 0;
    }

    private boolean isConnected() {
        return connectivityManager.getActiveNetworkInfo() == null ? false : connectivityManager.
                getActiveNetworkInfo().isConnected();
    }

    private Geocoder getGeocoder() throws IOException {
        if(geocoder == null){
            geocoder = new Geocoder(context);
        }

        return geocoder;
    }
}