package zielony.prv.georeminder.util;

import com.google.android.gms.maps.model.LatLng;

import zielony.prv.georeminder.model.Location;


public class LatLngToLocationConverter implements Converter<LatLng, Location> {
    @Override
    public Location convert(LatLng source) {
        return new Location(source.longitude, source.latitude, null);
    }
}
