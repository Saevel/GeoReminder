package zielony.prv.georeminder.api;

public class ModelEvent<TYPE> {

    private final TYPE model;

    public ModelEvent(TYPE model) {
        this.model = model;
    }

    public TYPE getAlert() {
        return model;
    }
}
