package zielony.prv.georeminder.services;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.location.LocationManager;
import android.util.Log;

import com.google.inject.Inject;

import java.util.Date;
import java.util.List;

import zielony.prv.georeminder.model.Navigation;
import zielony.prv.georeminder.api.ErrorEvent;
import zielony.prv.georeminder.api.ErrorType;
import zielony.prv.georeminder.api.EventHandler;
import zielony.prv.georeminder.api.ModelEvent;
import zielony.prv.georeminder.api.ModelListEvent;
import zielony.prv.georeminder.dao.api.AlertDao;
import zielony.prv.georeminder.model.Alert;
import zielony.prv.georeminder.model.Configuration;
import zielony.prv.georeminder.services.android.AndroidAlertService;

public class DefaultAlertService implements AlertService {

      static final String LOG_TAG = Configuration.GEOREMNDER_APP_TAG +
            "ALERT_SERV";

      static final String SQL_EXCEPTION_LOG = "SQLException Occured";

    @Inject
      AlertDao alertDao;

    @Inject
      LocationManager locationManager;

    @Inject
      Context context;

    @Inject
      Navigation navigation;

    @Inject
      Configuration configuration;

    @Override
    public void saveAlert(Alert alert, EventHandler<ModelEvent<Alert>> successHandler, EventHandler<ErrorEvent> failureHandler) {
        try {
            Alert result = alertDao.save(alert);
            successHandler.handle(new ModelEvent<Alert>(result));
        } catch(SQLException e) {
            Log.e(LOG_TAG, SQL_EXCEPTION_LOG, e);
            failureHandler.handle(new ErrorEvent(ErrorType.SQL_EXCEPTION));
        }
    }

    @Override
    public void updateAlert(Alert alert, EventHandler<ModelEvent<Alert>> successHandler, EventHandler<ErrorEvent> failureHandler) {
        try {
            alert = alertDao.update(alert);
            successHandler.handle(new ModelEvent<Alert>(alert));
        }
        catch(Exception e) {
            //TODO: Handle
        }
    }

    @Override
    public void getAlert(Long id, EventHandler<ModelEvent<Alert>> successHandler, EventHandler<ErrorEvent> failureHandler) {

        try {
            Alert alert = alertDao.findById(id);
            successHandler.handle(new ModelEvent<Alert>(alert));
        }
        catch(Exception e) {
            Log.e(LOG_TAG, SQL_EXCEPTION_LOG, e);
            failureHandler.handle(new ErrorEvent(ErrorType.SQL_EXCEPTION));
        }
    }

    @Override
    public void cancelAlert(Alert alert, EventHandler<Void> successHandler, EventHandler<ErrorEvent> failureHandler) {
        try {
            alertDao.delete(alert);
            successHandler.handle(null);
        } catch(SQLException e) {
            Log.e(LOG_TAG, SQL_EXCEPTION_LOG, e);
            failureHandler.handle(new ErrorEvent(ErrorType.SQL_EXCEPTION));
        }
    }

    @Override
    public void getAllAlerts(EventHandler<ModelListEvent<Alert>> successHandler, EventHandler<ErrorEvent> failureHandler) {
        try {
            List<Alert> allAlerts = alertDao.findAll();
            successHandler.handle(new ModelListEvent<Alert>(allAlerts));
        } catch(SQLException e) {
            Log.e(LOG_TAG, SQL_EXCEPTION_LOG, e);
            failureHandler.handle(new ErrorEvent(ErrorType.SQL_EXCEPTION));
        }
    }

    @Override
    public void activateAlert(Alert alert, EventHandler<ModelListEvent<Alert>> successHandler, EventHandler<ErrorEvent> failureHandler) {

        long expirationTime = (alert.getEndDate().getTime() - new Date().getTime());

        Intent intent = new Intent();
        intent.getExtras().putSerializable(navigation.getAlertIdKey(), alert);
        intent.setClass(context, AndroidAlertService.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        locationManager.addProximityAlert(alert.getLocation().getLatitude(),
                alert.getLocation().getLongitude(), configuration.getProximityDistance(),
                expirationTime, pendingIntent);
    }

    @Override
    public void deactivateAlert(Alert alert, EventHandler<ModelListEvent<Alert>> successHandler,
                                EventHandler<ErrorEvent> failureHandler) {
        //TODO: Implement!
    }

    @Override
    public boolean shouldTriggerAlert(Long id) {

        Alert alert = alertDao.findById(id);

        Date now = new Date();
        if(alert != null && alert.isActive() &&
                (alert.getStartDate() == null || alert.getStartDate().before(now)) &&
                (alert.getEndDate() == null || alert.getEndDate().after(now) ) ) {
            return true;
        }

        return false;
    }
}