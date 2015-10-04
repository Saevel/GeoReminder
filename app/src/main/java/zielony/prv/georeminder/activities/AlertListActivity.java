package zielony.prv.georeminder.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import javax.inject.Inject;
import javax.inject.Singleton;

import roboguice.activity.RoboListActivity;
import roboguice.inject.ContentView;
import zielony.prv.georeminder.R;
import zielony.prv.georeminder.api.ErrorEvent;
import zielony.prv.georeminder.api.EventHandler;
import zielony.prv.georeminder.api.ModelListEvent;
import zielony.prv.georeminder.model.Alert;
import zielony.prv.georeminder.model.Configuration;
import zielony.prv.georeminder.model.Navigation;
import zielony.prv.georeminder.services.AlertService;
import zielony.prv.georeminder.util.AlertArrayAdapter;

public class AlertListActivity extends RoboListActivity {

    private static final String ALERT_LIST_ACTIVITY_TAG = Configuration.GEOREMNDER_APP_TAG +
            "ALERT_LIST";

    @Inject
    Context context;

    @Inject
    AlertService alertService;

    @Inject
    Configuration configuration;

    @Inject
    Navigation navigation;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: add a menu!

        alertService.getAllAlerts(new EventHandler<ModelListEvent<Alert>>() {
            @Override
            public void handle(ModelListEvent<Alert> event) {
                if (event.getList() != null && !event.getList().isEmpty()) {

                    Log.d(ALERT_LIST_ACTIVITY_TAG, "Retrieved Alert List: " + event.getList());

                    setListAdapter(new AlertArrayAdapter(context,
                            android.R.layout.simple_list_item_1, event.getList()));
                }
                else {
                    Log.e(ALERT_LIST_ACTIVITY_TAG, "Alert List null or empty");
                }
            }
        }, new EventHandler<ErrorEvent>() {
            @Override
            public void handle(ErrorEvent event) {
                Log.e(ALERT_LIST_ACTIVITY_TAG, "Could not retrieve alert list");
            }
        });
    }

    @Override
    public void onListItemClick(ListView thisView, View itemView, int position, long id) {
        Alert alert = (Alert) getListAdapter().getItem(position);

        Log.d(ALERT_LIST_ACTIVITY_TAG, "Selected alert: " + alert.toString());
        Log.d(ALERT_LIST_ACTIVITY_TAG, "Alert id key: " + navigation.getAlertIdKey());

        Intent intent = new Intent(this, EditAlertActivity.class);
        intent.putExtra(navigation.getAlertIdKey(), alert.getId());

        startActivity(intent);
    }
}