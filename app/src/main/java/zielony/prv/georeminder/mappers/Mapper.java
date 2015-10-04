package zielony.prv.georeminder.mappers;

import android.database.Cursor;

public interface Mapper<Entity> {

    Entity map(Cursor cursor);
}
