package zielony.prv.georeminder.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.inject.Inject;

import java.util.List;

import zielony.prv.georeminder.model.Alert;

public class AlertArrayAdapter extends ArrayAdapter<Alert> {

    public AlertArrayAdapter(Context context, int resource, List<Alert> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Alert alert = this.getItem(position);
        TextView textView = new TextView(this.getContext());
        textView.setText(alert.getName());

        return textView;
    }
}
