package zielony.prv.georeminder.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.inject.Inject;
import javax.inject.Singleton;

import roboguice.activity.RoboActivity;
import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectFragment;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import zielony.prv.georeminder.R;
import zielony.prv.georeminder.api.ErrorEvent;
import zielony.prv.georeminder.api.EventHandler;
import zielony.prv.georeminder.api.ModelEvent;
import zielony.prv.georeminder.model.Configuration;
import zielony.prv.georeminder.model.Location;
import zielony.prv.georeminder.model.Navigation;
import zielony.prv.georeminder.services.GeolocationService;
import zielony.prv.georeminder.util.LatLngToLocationConverter;
import zielony.prv.georeminder.util.LocationToLatLngConverter;

@ContentView(R.layout.map_picker_activity)
public class MapPickerActivity extends RoboActivity {

    @Singleton
    Navigation navigation;

    @Inject
    Context context;

    @Inject
    GeolocationService geolocationService;

    @Inject
    LatLngToLocationConverter latLngToLocationConverter;

    @Inject
    LocationToLatLngConverter locationToLatLngConverter;

    @InjectFragment(R.id.map)
    MapFragment map;

    @InjectView(R.id.map_location_input)
    TextView mapLocationInput;

    @InjectView(R.id.map_location_input_button)
    Button mapLocationInputButton;

    @InjectView(R.id.map_location_return_button)
    Button mapLocationReturnButton;

    @InjectResource(R.string.geolocaton_error)
    String geolocationErrorMessage;

    private Location targetLocation;

    @Override
    protected void onStart() {
        super.onStart();

        map.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {

                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {

                        googleMap.clear();
                        geolocationService.geocode(latLngToLocationConverter.convert(latLng),
                                new EventHandler<ModelEvent<Location>>() {
                                    @Override
                                    public void handle(ModelEvent<Location> event) {
                                        Location location = event.getAlert();
                                        googleMap.clear();
                                        googleMap.addMarker(createMarker(location));
                                        targetLocation = location;
                                    }
                                }, new EventHandler<ErrorEvent>() {
                                    @Override
                                    public void handle(ErrorEvent event) {
                                        showGeolocationErrorMessage();
                                    }
                                });
                    }
                });

                mapLocationInputButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Location location = new Location(0.0, 0.0, mapLocationInput.getText().toString());
                        geolocationService.reverseGeocode(location, new EventHandler<ModelEvent<Location>>() {
                            @Override
                            public void handle(ModelEvent<Location> event) {
                                Location location = event.getAlert();
                                googleMap.clear();
                                googleMap.addMarker(createMarker(location));
                                targetLocation = event.getAlert();
                            }
                        }, new EventHandler<ErrorEvent>() {
                            @Override
                            public void handle(ErrorEvent event) {
                                showGeolocationErrorMessage();
                            }
                        });
                    }
                });

                mapLocationReturnButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        extractResult();
                    }
                });
            }
        });
    }

    private void extractResult() {
        Intent resultIntent = new Intent();
        resultIntent.getExtras().putSerializable(navigation.getLocationKey(),
                targetLocation);
        setResult(Activity.RESULT_OK, resultIntent);
    }

    private void showGeolocationErrorMessage() {
        Toast
          .makeText(context, geolocationErrorMessage, Toast.LENGTH_LONG)
          .show();
    }

    private MarkerOptions createMarker(Location location) {
        MarkerOptions options = new MarkerOptions();
        options.draggable(true);
        options.position(locationToLatLngConverter.convert(location));
        mapLocationInput.setText(location.getName());
        return options;
    }
}