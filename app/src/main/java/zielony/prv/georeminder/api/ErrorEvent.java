package zielony.prv.georeminder.api;

public class ErrorEvent {

    private final ErrorType error;

    public ErrorEvent(ErrorType error) {
        this.error = error;
    }

    public ErrorType getError() {
        return error;
    }
}
