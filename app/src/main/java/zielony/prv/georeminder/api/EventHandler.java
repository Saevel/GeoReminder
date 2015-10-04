package zielony.prv.georeminder.api;

public interface EventHandler<EventType> {

    void handle(EventType event);
}
