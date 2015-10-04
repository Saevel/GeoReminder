package zielony.prv.georeminder.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import zielony.prv.georeminder.api.ErrorEvent;
import zielony.prv.georeminder.api.EventHandler;
import zielony.prv.georeminder.api.ModelListEvent;
import zielony.prv.georeminder.dao.api.AlertDao;
import zielony.prv.georeminder.mappers.AlertMapper;
import zielony.prv.georeminder.mappers.Mapper;
import zielony.prv.georeminder.model.Alert;
import zielony.prv.georeminder.model.Configuration;
import zielony.prv.georeminder.model.Location;

public class DefaultAlertDao extends SQLiteOpenHelper implements AlertDao {

    private static final String DATABASE_NAME = "Alerts";

    private static final String CREATE_STATEMENT = "CREATE TABLE Alerts(NAME TEXT, DESCRIPTION TEXT," +
            " ACTIVE BOOLEAN, LOCATION_NAME TEXT, LOCATION_LATITUDE REAL, LOCATION_LONGITUDE REAL, " +
            "START_DATE DATE, END_DATE DATE)";

    private static final String DROP_STATEMENT = "DROP TABLE Alerts;";

    private static final String INSERT_ALERT_STATEMENT = "INSERT INTO Alerts(NAME, DESCRIPTION, " +
            "ACTIVE, LOCATION_NAME, LOCATION_LONGITUDE, LOCATION_LATITUDE, START_DATE, END_DATE)" +
            " VALUES( ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String FULL_UPDATE_STATEMENT = "UPDATE Alerts SET NAME = ?, DESCRIPTION = ?," +
            " ACTIVE = ?, LOCATION_NAME = ?, LOCATION_LONGITUDE = ?, LOCATION_LATITUDE = ?," +
            " START_DATE = ?, END_DATE = ? WHERE " +
            "ROWID = ?";

    private static final String DELETE_BY_ID_STATEMENT = "DELETE FROM Alerts WHERE ROWID = ?";

    private static final String FIND_ALL_STATEMENT = "SELECT ROWID, * FROM Alerts";

    private static final String FIND_BY_ID = "SELECT ROWID, * FROM Alerts WHERE ROWID = ?";

    private Configuration configuration;

    private AlertMapper alertMapper;

    @Inject
    public DefaultAlertDao(Context context, Configuration configuration, AlertMapper alertMapper) {
        super(context, DATABASE_NAME, null, configuration.getDatabaseVersion());
        this.configuration = configuration;
        this.alertMapper = alertMapper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_STATEMENT);
        onCreate(db);
    }

    @Override
    public Alert save(Alert entity) {

        Object[] params = new Object[]{entity.getName(), entity.getDescription(),
                (entity.getLocation() == null ? null : entity.getLocation().getName()),
                (entity.getLocation() == null ? null : entity.getLocation().getLongitude()),
                (entity.getLocation() == null ? null : entity.getLocation().getLatitude()),
                entity.getStartDate(), entity.getEndDate()};

        this.getWritableDatabase().execSQL(INSERT_ALERT_STATEMENT, params);

        return entity;
    }

    @Override
    public Alert update(Alert entity) {
        Object[] params = new Object[]{entity.getName(), entity.getDescription(),
                (entity.getLocation() == null ? null : entity.getLocation().getName()),
                (entity.getLocation() == null ? null : entity.getLocation().getLongitude()),
                (entity.getLocation() == null ? null : entity.getLocation().getLatitude()),
                entity.getStartDate(), entity.getEndDate()};

        this.getWritableDatabase().execSQL(FULL_UPDATE_STATEMENT, params);

        return entity;
    }

    @Override
    public void delete(Alert entity) {
        Object[] params = new Object[]{entity.getId()};
        this.getWritableDatabase().execSQL(DELETE_BY_ID_STATEMENT, params);
    }

    @Override
    public List<Alert> findAll() {
        Cursor cursor = this.getReadableDatabase().rawQuery(FIND_ALL_STATEMENT, new String[0]);

        List<Alert> alerts = new LinkedList<Alert>();
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++) {
            alerts.add(alertMapper.map(cursor));
            cursor.moveToNext();
        }

        return alerts;
    }

    @Override
    public Alert findById(Long id) {
        Alert alert = new Alert();
        Cursor cursor = this.getReadableDatabase().rawQuery(FIND_BY_ID, new String[]{id.toString()});
        if(cursor.getCount() == 1) {
            cursor.moveToFirst();
            return alertMapper.map(cursor);
        }
        else {
            return null;
        }
    }
}