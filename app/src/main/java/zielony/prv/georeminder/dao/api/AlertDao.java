package zielony.prv.georeminder.dao.api;

import java.util.Date;
import java.util.List;

import zielony.prv.georeminder.api.ErrorEvent;
import zielony.prv.georeminder.api.EventHandler;
import zielony.prv.georeminder.api.ModelListEvent;
import zielony.prv.georeminder.model.Alert;
import zielony.prv.georeminder.model.Location;

public interface AlertDao extends Dao<Alert, Long> {
}
