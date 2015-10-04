package zielony.prv.georeminder.mappers;

import android.database.Cursor;

import java.util.Date;

import zielony.prv.georeminder.model.Alert;
import zielony.prv.georeminder.model.Location;

public class AlertMapper implements Mapper<Alert> {
    @Override
    public Alert map(Cursor cursor) {

        Alert alert = new Alert();
        Location location = new Location();

        alert.setId(cursor.getLong(cursor.getColumnIndex("rowid")));
        alert.setName(cursor.getString(cursor.getColumnIndex("NAME")));
        alert.setDescription(cursor.getString(cursor.getColumnIndex("DESCRIPTION")));
        alert.setActive((cursor.getInt(cursor.getColumnIndex("ACTIVE")) == 1));
        alert.setStartDate(this.convertDate(cursor.getString(cursor.getColumnIndex("START_DATE"))));
        alert.setEndDate(this.convertDate(cursor.getString(cursor.getColumnIndex("END_DATE"))));

        location.setName(cursor.getString(cursor.getColumnIndex("LOCATION_NAME")));
        location.setLongitude(cursor.getFloat(cursor.getColumnIndex("LOCATION_LONGITUDE")));
        location.setLatitude(cursor.getFloat(cursor.getColumnIndex("LOCATION_LATITUDE")));

        alert.setLocation(location);

        return alert;
    }

    private Date convertDate(String value) {
        return value != null ? new Date(value) : null;
    }
}

