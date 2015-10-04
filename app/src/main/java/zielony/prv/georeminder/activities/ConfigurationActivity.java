package zielony.prv.georeminder.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import javax.inject.Inject;
import javax.inject.Singleton;

import roboguice.activity.RoboActivity;
import roboguice.activity.RoboFragmentActivity;

import roboguice.inject.ContentView;

import roboguice.inject.InjectView;
import zielony.prv.georeminder.R;
import zielony.prv.georeminder.model.Configuration;

@ContentView(R.layout.configuration)
public class ConfigurationActivity extends RoboActivity {

    private static final String CONFIGURATION_ACTIVITY_TAG = Configuration.GEOREMNDER_APP_TAG + "CONFIG";

    @Inject
    Configuration configuration;

    @InjectView(R.id.save_configuration_button)
    Button saveButton;

    @InjectView(R.id.distinguishable_distance_input)
    EditText distinguishableDistanceInput;

    @InjectView(R.id.proximity_distance_input)
    EditText proximityDistanceInput;

    @InjectView(R.id.location_update_interval_input)
    EditText locationUpdateIntervalInput;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        updateForm(configuration);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateConfigurationFromForm(configuration);
                configuration.save();
            }
        });
    }

    private void updateForm(Configuration configuration) {
        proximityDistanceInput.setText(Float.toString(configuration.getProximityDistance()));
        distinguishableDistanceInput.setText(Double.toString(configuration.getMinimalDistinguishibleDistance()));
        locationUpdateIntervalInput.setText(Long.toString(configuration.getMinimumUpdateInterval()));
    }

    private void updateConfigurationFromForm(Configuration configuration) {
        configuration.setProximityDistance(Float.parseFloat(proximityDistanceInput.getText().toString()));
        configuration.setMinimalDistinguishibleDistance(Double.parseDouble(distinguishableDistanceInput.getText().toString()));
        configuration.setMinimumUpdateInterval(Long.parseLong(locationUpdateIntervalInput.getText().toString()));
    }
}