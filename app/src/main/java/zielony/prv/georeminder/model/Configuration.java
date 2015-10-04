package zielony.prv.georeminder.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.inject.Singleton;

import javax.inject.Inject;

import roboguice.inject.SharedPreferencesProvider;

/**
 * Serves as a store for all configuration parameters inside the app.
 */
public class Configuration {

    public static final String PREFERENCES_FILE_NAME = "prv.zielony.georeminder.default";

    /**
     * General app tag for Android logging.
     */
    public static final String GEOREMNDER_APP_TAG = "GEOREMINDER:";

    private static final String PROXIMITY_DISTANCE_KEY = "distance.proximity";

    private static final String DISTINGUISHABLE_DISTANCE_KEY = "distance.distinguishable";

    private static final String LOCATION_UPDATE_INTERVAL_KEY = "location.update.interval";

    private static final String DATABASE_VERSION_KEY = "database.version";

    /**
     * Application context
     */
    private Context context;

    /**
     * The distance within which locations are recognized as 'close' by the application.
     */
    private float proximityDistance = 300;

    /**
     * Minimal distance distunguished by the application.
     */
    private double minimalDistinguishibleDistance = 1;

    /**
     * Database version
     */
    private int databaseVersion = 1;

    /**
     * Minimal interval within which the locations are updated
     */
    private long minimumUpdateInterval = 3600;

    private SharedPreferences preferences;

    @Inject
    public Configuration(Context context, SharedPreferences preferences) {
        this.context = context;

        this.preferences = preferences;

        this.proximityDistance = preferences.getFloat(PROXIMITY_DISTANCE_KEY, proximityDistance);
        this.minimalDistinguishibleDistance = preferences.getFloat(DISTINGUISHABLE_DISTANCE_KEY,
                Float.parseFloat(Double.toString(minimalDistinguishibleDistance)));
        this.databaseVersion = preferences.getInt(DATABASE_VERSION_KEY, this.databaseVersion);
        this.minimumUpdateInterval = preferences.getLong(LOCATION_UPDATE_INTERVAL_KEY,
                minimumUpdateInterval);
    }

    public void save() {
        preferences.edit().putFloat(PROXIMITY_DISTANCE_KEY, proximityDistance);
        preferences.edit().putFloat(DISTINGUISHABLE_DISTANCE_KEY, minimumUpdateInterval);
        preferences.edit().putInt(DATABASE_VERSION_KEY, databaseVersion);
        preferences.edit().putLong(LOCATION_UPDATE_INTERVAL_KEY, minimumUpdateInterval);
    }

    public float getProximityDistance() {
        return proximityDistance;
    }

    public void setProximityDistance(float proximityDistance) {
        this.proximityDistance = proximityDistance;
    }

    public int getDatabaseVersion() {
        return databaseVersion;
    }

    public void setDatabaseVersion(int databaseVersion) {
        this.databaseVersion = databaseVersion;
    }

    public long getMinimumUpdateInterval() {
        return minimumUpdateInterval;
    }

    public void setMinimumUpdateInterval(long minimumUpdateInterval) {
        this.minimumUpdateInterval = minimumUpdateInterval;
    }

    public double getMinimalDistinguishibleDistance() {
        return minimalDistinguishibleDistance;
    }

    public void setMinimalDistinguishibleDistance(double minimalDistinguishibleDistance) {
        this.minimalDistinguishibleDistance = minimalDistinguishibleDistance;
    }
}