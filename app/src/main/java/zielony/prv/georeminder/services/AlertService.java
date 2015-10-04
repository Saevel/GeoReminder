package zielony.prv.georeminder.services;

import java.util.List;

import zielony.prv.georeminder.api.ErrorEvent;
import zielony.prv.georeminder.api.EventHandler;
import zielony.prv.georeminder.api.ModelEvent;
import zielony.prv.georeminder.api.ModelListEvent;
import zielony.prv.georeminder.model.Alert;

public interface AlertService {

    void saveAlert(Alert alert, EventHandler<ModelEvent<Alert>> successHandler,
                   EventHandler<ErrorEvent> failureHandler);

    void updateAlert(Alert alert, EventHandler<ModelEvent<Alert>> successHandler,
                     EventHandler<ErrorEvent> failureHandler);

    void getAlert(Long id, EventHandler<ModelEvent<Alert>> successHandler,
                  EventHandler<ErrorEvent> failureHandler);

    void cancelAlert(Alert alert, EventHandler<Void> successHandler,
                     EventHandler<ErrorEvent> failureHandler);

    void getAllAlerts(EventHandler<ModelListEvent<Alert>> successHandler,
                      EventHandler<ErrorEvent> failureHandler);

    public void activateAlert(Alert alert, EventHandler<ModelListEvent<Alert>> successHandler,
                              EventHandler<ErrorEvent> failureHandler);

    public void deactivateAlert(Alert alert, EventHandler<ModelListEvent<Alert>> successHandler,
                                EventHandler<ErrorEvent> failureHandler);

    public boolean shouldTriggerAlert(Long id);
}
