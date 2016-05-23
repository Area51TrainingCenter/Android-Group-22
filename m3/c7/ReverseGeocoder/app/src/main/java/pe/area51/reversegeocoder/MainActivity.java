package pe.area51.reversegeocoder;

import android.app.ProgressDialog;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private LocationManager locationManager;
    private Location currentLocation;
    private TextView locationInfoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationInfoTextView = (TextView) findViewById(R.id.textview_info);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationRetrieving();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationRetrieving();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_do_reverse_geocoding:
                queryCurrentLocationAddress();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void showMessage(final String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void queryCurrentLocationAddress() {
        if (currentLocation != null) {
            new ReverseGeocodingRequest(currentLocation.getLatitude(), currentLocation.getLongitude()).execute();
        } else {
            showMessage(getString(R.string.no_location));
        }
    }

    private void showLocation(final pe.area51.reversegeocoder.Location location) {
        final String locationInfo = new StringBuilder()
                .append(getString(R.string.location_latitude, String.valueOf(location.getLatitude())))
                .append("\n")
                .append(getString(R.string.location_longitude, String.valueOf(location.getLongitude())))
                .append("\n")
                .append(getString(R.string.location_country, location.getCountry()))
                .append("\n")
                .append(getString(R.string.location_address, location.getAddress()))
                .toString();
        locationInfoTextView.setText(locationInfo);
    }

    private void startLocationRetrieving() {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
    }

    private void stopLocationRetrieving() {
        locationManager.removeUpdates(this);
    }

    private class ReverseGeocodingRequest extends AsyncTask<Void, Void, Bundle> {

        ProgressDialog progressDialog;
        final double latitude;
        final double longitude;

        private static final String ERROR_MESSAGE = "error_message";

        private pe.area51.reversegeocoder.Location location;

        public ReverseGeocodingRequest(final double latitude, final double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        @Override
        protected void onPreExecute() {
            this.progressDialog = ProgressDialog.show(MainActivity.this, "", getString(R.string.getting_address), true, false);
        }

        @Override
        protected Bundle doInBackground(Void... params) {
            final Bundle responseBundle = new Bundle();
            try {
                this.location = Request.
                        doReverseGeocodingRequest(
                                currentLocation.getLatitude(),
                                currentLocation.getLongitude());
            } catch (IOException e) {
                e.printStackTrace();
                final String errorMessage = getString(R.string.connection_error);
                responseBundle.putString(ERROR_MESSAGE, errorMessage);
            } catch (JSONException e) {
                e.printStackTrace();
                final String errorMessage = getString(R.string.parse_error);
                responseBundle.putString(ERROR_MESSAGE, errorMessage);
            }
            return responseBundle;
        }

        @Override
        protected void onPostExecute(Bundle responseBundle) {
            this.progressDialog.dismiss();
            if (responseBundle.getString(ERROR_MESSAGE, null) == null) {
                showLocation(this.location);
            } else {
                Toast.makeText(MainActivity.this, responseBundle.getString(ERROR_MESSAGE), Toast.LENGTH_LONG).show();
            }
        }
    }

}
