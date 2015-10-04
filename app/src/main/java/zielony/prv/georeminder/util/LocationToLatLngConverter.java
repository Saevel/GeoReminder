package zielony.prv.georeminder.util;

import com.google.android.gms.maps.model.LatLng;

import zielony.prv.georeminder.model.Location;

public class LocationToLatLngConverter implements Converter<Location, LatLng>{
    @Override
    public LatLng convert(Location source) {
        return new LatLng(source.getLatitude(), source.getLongitude());
    }
}
