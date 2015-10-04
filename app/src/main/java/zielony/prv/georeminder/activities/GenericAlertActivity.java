package zielony.prv.georeminder.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.inject.Inject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import zielony.prv.georeminder.R;
import zielony.prv.georeminder.model.Alert;
import zielony.prv.georeminder.model.Configuration;
import zielony.prv.georeminder.model.Location;
import zielony.prv.georeminder.model.Navigation;
import zielony.prv.georeminder.services.AlertService;

/**
 * Created by Zielony on 2015-10-04.
 */
public class GenericAlertActivity extends RoboActivity {

    public static final int LOCATION_REQUEST = 500;

    @Inject
    Context context;

    @Inject
    Configuration configuration;

    @Inject
    Navigation navigation;

    @Inject
    AlertService alertService;

    @InjectView(R.id.edit_alert_title)
    TextView titleTextView;

    @InjectView(R.id.save_button)
    Button saveButton;

    @InjectView(R.id.alert_name_input)
    EditText alertNameInput;

    @InjectView(R.id.description_input)
      EditText descriptionInput;

    @InjectView(R.id.start_date_input)
      EditText startDateInput;

    @InjectView(R.id.end_date_input)
      EditText endDateInput;

    @InjectView(R.id.active_checkbox)
    CheckBox activeCheckbox;

    @InjectView(R.id.location_input)
      EditText locationInput;

    @InjectView(R.id.location_button)
      Button locationButton;
    Location targetLocation;

    Alert collectAlertData() {

      Alert alert = new Alert();
      alert.setActive(activeCheckbox.isChecked());
      alert.setDescription(descriptionInput.getText().toString());
      alert.setName(alertNameInput.getText().toString());
      alert.setStartDate(new Date(startDateInput.getText().toString()));
      alert.setEndDate(new Date(endDateInput.getText().toString()));
      alert.setLocation(targetLocation);

      return alert;
  }

    private Date parseDate(String input) throws ParseException {
      DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
      return format.parse(input);
  }

    //TODO: Refaktor superklasy
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if(LOCATION_REQUEST == requestCode) {
            if(resultCode == Activity.RESULT_OK){
                Object data = intent.getExtras().getSerializable(navigation.getLocationKey());

                if(data != null) {
                    Location location = (Location) data;
                    locationInput.setText(location.getName());
                    targetLocation = location;
                }
            }
            else {
                //TODO:Log!
                Toast.makeText(context, "Error while retriving location", Toast.LENGTH_LONG).show();
            }
        }
    }
}
