package zielony.prv.georeminder.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.inject.AbstractModule;

import roboguice.inject.SharedPreferencesProvider;
import zielony.prv.georeminder.dao.DefaultAlertDao;
import zielony.prv.georeminder.dao.api.AlertDao;
import zielony.prv.georeminder.mappers.AlertMapper;
import zielony.prv.georeminder.mappers.Mapper;
import zielony.prv.georeminder.model.Alert;
import zielony.prv.georeminder.model.Configuration;
import zielony.prv.georeminder.model.Navigation;
import zielony.prv.georeminder.services.AlertService;
import zielony.prv.georeminder.services.DefaultAlertService;
import zielony.prv.georeminder.services.DefaultGeolocationService;
import zielony.prv.georeminder.services.GeolocationService;
import zielony.prv.georeminder.util.LatLngToLocationConverter;

/**
 * Created by Zielony on 2015-07-27.
 */
public class GeoReminderModule extends AbstractModule {

    private Context context;

    public GeoReminderModule(Application application) {
        this.context = application.getApplicationContext();
    }

    @Override
    protected void configure() {

        bind(Configuration.class).toInstance(new Configuration(context,
                context.getSharedPreferences(Configuration.PREFERENCES_FILE_NAME, 0)));

        bind(Navigation.class).toInstance(new Navigation());

        bind(AlertDao.class).to(DefaultAlertDao.class);

        bind(AlertService.class).to(DefaultAlertService.class);

        bind(GeolocationService.class).to(DefaultGeolocationService.class);

        this.bind(LatLngToLocationConverter.class);

        this.bind(LatLngToLocationConverter.class);
    }
}
