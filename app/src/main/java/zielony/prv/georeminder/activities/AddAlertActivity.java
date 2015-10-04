package zielony.prv.georeminder.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.inject.Inject;

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
import zielony.prv.georeminder.services.AlertService;

@ContentView(R.layout.edit_alert)
public class AddAlertActivity extends GenericAlertActivity {

    private static final String ADD_ALERT_ACTIVITY_TAG = Configuration.GEOREMNDER_APP_TAG + "ADD_ALERT";

    @InjectResource(R.string.add_alert)
    String title;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        titleTextView.setText(title);

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MapPickerActivity.class);
                intent.setAction(Intent.ACTION_EDIT);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                startActivityForResult(intent, LOCATION_REQUEST);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Alert alert = collectAlertData();

                alertService.saveAlert(alert, new EventHandler<ModelEvent<Alert>>() {
                    @Override
                    public void handle(ModelEvent<Alert> event) {
                        Log.d(ADD_ALERT_ACTIVITY_TAG, "Saved Alert :" + alert);
                        Intent intent = new Intent(context, AlertListActivity.class);
                        startActivity(intent);
                    }
                }, new EventHandler<ErrorEvent>() {
                    @Override
                    public void handle(ErrorEvent event) {
                        Log.e(ADD_ALERT_ACTIVITY_TAG, "Error saving new Alert: " + alert);
                    }
                });
            }
        });
    }
}