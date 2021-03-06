package zielony.prv.georeminder.model;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import roboguice.activity.RoboActivity;
import zielony.prv.georeminder.R;
import zielony.prv.georeminder.services.AlertService;
import zielony.prv.georeminder.services.GeolocationService;


public class MainActivity extends RoboActivity {

    private AlertService alertService;

    private GeolocationService geolocationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}