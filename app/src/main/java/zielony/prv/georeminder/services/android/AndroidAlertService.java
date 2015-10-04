package zielony.prv.georeminder.services.android;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.google.inject.Inject;

import javax.inject.Singleton;

import roboguice.service.RoboService;
import zielony.prv.georeminder.activities.MapPickerActivity;
import zielony.prv.georeminder.model.Configuration;
import zielony.prv.georeminder.model.Navigation;
import zielony.prv.georeminder.api.ErrorEvent;
import zielony.prv.georeminder.api.EventHandler;
import zielony.prv.georeminder.api.ModelEvent;
import zielony.prv.georeminder.model.Alert;
import zielony.prv.georeminder.services.AlertService;

/**
 * Created by Zielony on 2015-07-26.
 */
public class AndroidAlertService extends RoboService {

    private static final String ANDROID_ALERT_SERVICE_TAG =
            Configuration.GEOREMNDER_APP_TAG + "ANR_ALERT_SRVC";

    @Inject
    Context context;

    @Inject
    NotificationManager notificationManager;

    @Inject
    AlarmManager alarmManager;

    @Inject
    AlertService alertService;

    @Singleton
    Navigation navigation;

    @Override
    public IBinder onBind(Intent intent) {

        Long id = intent.getExtras().getLong(navigation.getAlertIdKey());

        if(alertService.shouldTriggerAlert(id)) {

            alertService.getAlert(id, new EventHandler<ModelEvent<Alert>>() {
                @Override
                public void handle(ModelEvent<Alert> event) {

                    Alert alert = event.getAlert();

                    Intent i = new Intent();
                    i.getExtras().putSerializable(navigation.getAlertKey(), alert);
                    i.setClass(context, MapPickerActivity.class);

                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i,
                            PendingIntent.FLAG_ONE_SHOT);

                    Notification.Builder builder = new Notification.Builder(getApplicationContext());
                    builder.setContentTitle(alert.getName());
                    builder.setContentText(alert.getDescription());
                    builder.setContentIntent(pendingIntent);

                    notificationManager.notify(alert.getId().intValue(), builder.getNotification());

                   //TODO: Alarm Manager?
                }
            }, new EventHandler<ErrorEvent>() {
                @Override
                public void handle(ErrorEvent event) {
                    //TODO: Handle, log etc.
                }
            });

        }

        return null;
    }
}
