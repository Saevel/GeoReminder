package zielony.prv.georeminder.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import roboguice.inject.ContentView;
import roboguice.inject.InjectFragment;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import zielony.prv.georeminder.R;
import zielony.prv.georeminder.api.ErrorEvent;
import zielony.prv.georeminder.api.EventHandler;
import zielony.prv.georeminder.api.ModelEvent;
import zielony.prv.georeminder.api.ModelListEvent;
import zielony.prv.georeminder.model.Alert;
import zielony.prv.georeminder.model.Configuration;
import zielony.prv.georeminder.model.Location;
import zielony.prv.georeminder.model.Navigation;
import zielony.prv.georeminder.services.AlertService;
import zielony.prv.georeminder.services.GeolocationService;
import zielony.prv.georeminder.util.LatLngToLocationConverter;
import zielony.prv.georeminder.util.LocationToLatLngConverter;

@ContentView(R.layout.map_alert_activity)
public class MapAlertActivity extends RoboActivity {

    @Singleton
    Navigation navigation;

    @Inject
    AlertService alertService;

    @Inject
    LocationToLatLngConverter locationToLatLngConverter;

    @InjectFragment(R.id.alert_map)
    MapFragment map;

    @InjectView(R.id.map_alert_ok_button)
    Button mapAlertOkButton;

    Alert alert;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Object input = savedInstanceState.getSerializable(navigation.getAlertKey());
        if(input != null) {
            alert = (Alert) input;
        }

        map.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                if (alert != null) {
                    googleMap.addMarker(createMarker(alert));
                } else {
                    //TODO: Log
                    finish();
                }
            }
        });

        mapAlertOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(alert != null) {
                    alertService.deactivateAlert(alert, new EventHandler<ModelListEvent<Alert>>() {
                        @Override
                        public void handle(ModelListEvent<Alert> event) {
                            finish();
                        }
                    }, new EventHandler<ErrorEvent>() {
                        @Override
                        public void handle(ErrorEvent event) {
                            //TODO: Log
                            finish();
                        }
                    });
                }
            }
        });
    }

    public MarkerOptions createMarker(Alert alert){

       MarkerOptions options = new MarkerOptions();
        options.position(locationToLatLngConverter.convert((alert.getLocation())));
        options.draggable(false);
        options.title(alert.getName());

        return options;
    }
}