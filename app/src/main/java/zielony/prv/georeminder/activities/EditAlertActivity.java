package zielony.prv.georeminder.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.inject.Inject;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Singleton;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import zielony.prv.georeminder.R;
import zielony.prv.georeminder.api.ErrorEvent;
import zielony.prv.georeminder.api.EventHandler;
import zielony.prv.georeminder.api.ModelEvent;
import zielony.prv.georeminder.model.Alert;
import zielony.prv.georeminder.model.Configuration;
import zielony.prv.georeminder.model.Location;
import zielony.prv.georeminder.model.Navigation;
import zielony.prv.georeminder.services.AlertService;

@ContentView(R.layout.edit_alert)
public class EditAlertActivity extends GenericAlertActivity {

    static final String EDIT_ALERT_ACTIVITY_TAG = Configuration.GEOREMNDER_APP_TAG + "EDIT_ALERT";

    @InjectResource(R.string.edit_alert)
    String title;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        titleTextView.setText(title);

        Intent intent = getIntent();

        final long id = intent.getLongExtra(navigation.getAlertIdKey(), -1);

        alertService.getAlert(id, new EventHandler<ModelEvent<Alert>>() {
            @Override
            public void handle(ModelEvent<Alert> event) {

                updateFormData(event.getAlert());

                locationButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_EDIT);
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        startActivityForResult(intent, LOCATION_REQUEST);
                    }
                });

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Alert editedAlert = collectAlertData();

                        alertService.updateAlert(editedAlert, new EventHandler<ModelEvent<Alert>>() {
                                    @Override
                                    public void handle(ModelEvent<Alert> event) {
                                        Intent i = new Intent(context, AlertListActivity.class);
                                        startActivity(i);
                                    }
                                },
                                new EventHandler<ErrorEvent>() {
                                    @Override
                                    public void handle(ErrorEvent event) {
                                        Log.e(EDIT_ALERT_ACTIVITY_TAG, "Could not update alert");
                                    }
                                });
                    }
                });
            }
        }, new EventHandler<ErrorEvent>() {
            @Override
            public void handle(ErrorEvent event) {
                Log.e(EDIT_ALERT_ACTIVITY_TAG, "Could not retrieve Alert by id: " + id);
            }
        });
    }

    void updateFormData(Alert alert) {

        Log.d(EDIT_ALERT_ACTIVITY_TAG, "Alert for form update: " + alert);

        alertNameInput.setText(alert.getName());
        descriptionInput.setText(alert.getDescription());
        activeCheckbox.setChecked(alert.isActive());
        startDateInput.setText(
                alert.getStartDate() != null ? alert.getStartDate().toString() : new Date().toString());
        endDateInput.setText(
                alert.getEndDate() != null ? alert.getEndDate().toString() : new Date().toString());
        locationInput.setText(alert.getLocation() != null ? alert.getLocation().getName() : "");
    }

}