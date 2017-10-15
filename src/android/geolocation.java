package geolocation;

import org.apache.cordova.*;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// Popup
import android.content.Context;
import android.widget.Toast;

// Location
import android.os.Bundle;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.pm.PackageManager;
import android.Manifest;

public class geolocation extends CordovaPlugin implements LocationListener {

    // Define name of action
    private static final String GET_STATUS = "getstatus";
    private static final String GET_GEOLOCATION = "getgeolocation";

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_UPDATE = 1000 * 60 * 1;

    // The minimum distance to change updates in meters
    private static final float MIN_DISTANCE_UPDATE = 1;

    // Create permission to request
    public static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    String [] permissions = { Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION };

    private LocationManager locationManager;
    private Location location;
    private String provider;

    protected void pluginInitialize() {

      Criteria criteria = new Criteria();

      // Indicates the desired accuracy for latitude and longitude
      criteria.setAccuracy(Criteria.ACCURACY_FINE);

      // Service initialization
      locationManager = (LocationManager) this.webView.getContext().getSystemService(Context.LOCATION_SERVICE);

      // Returns the name of the provider that best meets the given criteria.
      String provider = locationManager.getBestProvider(criteria, false);
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
      if (GET_STATUS.equals(action)) {
        getstatus(args.getString(0), callbackContext);
        return true;
      }else if (GET_GEOLOCATION.equals(action)) {

        // Check permission
        if (checkPermission()) {

          // Check get location by gps is enabled
          boolean check_gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

          // Check get location by network is enabled
          boolean check_network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

          // If have permission access GPS
          if (check_gps) {

            if (location == null) {

              locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_UPDATE, MIN_DISTANCE_UPDATE, this);

              if (locationManager != null) {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                // Have coordinate data
                if (location != null) {

                  JSONArray result_coordinate = getCoordinate(location.getLatitude(), location.getLongitude());

                  // Return success location
                  callbackContext.success(result_coordinate);
                  return true;
                }else {

                  // If can't get location, try update location
                  locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, this, null);

                  showToast("Not found location");

                  callbackContext.error("Not found location");
                  return false;
                }

              }

            }

          }

        }else {
          cordova.requestPermissions(this, 0, permissions);
        }

      }
      return false;
    }

    // Function show status
    private void getstatus(String msg, CallbackContext callbackContext) {
      if (msg == null || msg.length() == 0) {
        callbackContext.error("Don't have message");
      }else {
        callbackContext.success(msg);
      }
    }

    // Function show popup message
    public void showToast(String message) {
      Toast.makeText(
        webView.getContext(),
        message,
        Toast.LENGTH_LONG
      ).show();
    }

    // Function return coordinate result
    public static JSONArray getCoordinate(double latitude, double longitude) {
      try {
        // Create array coordinate of current location
        JSONArray coordinate_arr = new JSONArray();

        // Create object latitude
        JSONObject lat_obj = new JSONObject();
        lat_obj.put("lat", latitude);

        // Create object longitude
        JSONObject lng_obj = new JSONObject();
        lng_obj.put("lng", longitude);

        // Push object latitude & object longitude to array coordinate
        coordinate_arr.put(lat_obj);
        coordinate_arr.put(lng_obj);

        return coordinate_arr;
      }catch (JSONException e) {

      }

      return null;
    }

    private boolean checkPermission() {
      if(cordova.hasPermission(ACCESS_FINE_LOCATION)) {
        return true;
      }
      return false;
    }

    @Override
    public void onLocationChanged(Location location) {
      this.location = location;
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onStart() {
      if (!checkPermission()) {
        cordova.requestPermissions(this, 0, permissions);
      }
      // locationManager.requestLocationUpdates(provider, MIN_TIME_UPDATE, MIN_DISTANCE_UPDATE, this);
      // Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onResume(boolean multitasking) {
      // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_UPDATE, MIN_DISTANCE_UPDATE, this);
    }

    @Override
    public void onPause(boolean multitasking) {
      // locationManager.removeUpdates(this);
      // Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

}
