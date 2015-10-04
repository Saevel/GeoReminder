package zielony.prv.georeminder.api;

import java.util.LinkedList;
import java.util.List;

public class ModelListEvent<TYPE> {

    private final List<TYPE> list;

    public ModelListEvent(List<TYPE> list) {
        this.list = list;
    }

    public ModelListEvent() {
        this.list = new LinkedList<TYPE>();
    }

    public List<TYPE> getList() {
        return list;
    }
}
