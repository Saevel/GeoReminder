package zielony.prv.georeminder.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import zielony.prv.georeminder.R;

@ContentView(R.layout.menu_activity)
public class MenuActivity extends RoboActivity {

    @InjectView(R.id.add_alert_button)
    Button addAlertButton;

    @InjectView(R.id.activity_list_button)
    Button alertListButton;

    @InjectView(R.id.configuration_button)
    Button configurationButton;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addAlertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddAlertActivity.class);
                startActivity(i);
            }
        });

        alertListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AlertListActivity.class);
                startActivity(i);
            }
        });

        configurationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ConfigurationActivity.class);
                startActivity(i);
            }
        });
    }
}