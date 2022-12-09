package com.example.wearos_app;

import static android.app.PendingIntent.getActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.wear.activity.ConfirmationActivity;

import com.example.wearos_app.databinding.ActivityMainBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static String API_URL = "https://clsw-fe777-default-rtdb.europe-west1.firebasedatabase.app/";
    private static final int REQUEST_AUTHORIZATIONS_RESULT_CODE = 101;
    public static final long LOCATION_UPDATE_INTERVAL = 3000;

    private TextView mTextView;
    private ActivityMainBinding binding;
    private FusedLocationProviderClient fusedLocationClient;

    ArrayList<Location> locations = new ArrayList<>();
    ArrayList<Float> heartRates = new ArrayList<>();
    private static final int MAX_LOCATION_RECORDED = 50;

    // Sensors
    SensorManager mSensorManager;
    Sensor mHeartRateSensor;
    float currHeartRate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mTextView = binding.text;

        /**
         * DataSnap initial = new DataSnap(new Date().toString(), 100f);
         * Log.d(LOG_TAG, "Initial Data : "+ initial.toString());
         * sendDataSnap(initial);
         **/

        if (hasGps()) {
            // Location init
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

            // Check for coarse and fine location permission
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.BODY_SENSORS) == PackageManager.PERMISSION_GRANTED) {
                Log.d(LOG_TAG, "All required location permission are already granted");
                startLocationUpdates();
            } else {
                Log.d(LOG_TAG, "ask for coarse and fine location permission");
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.BODY_SENSORS,
                        },
                        REQUEST_AUTHORIZATIONS_RESULT_CODE);
            }
        } else {
            Log.e(LOG_TAG, "This hardware doesn't have GPS.");
            noGpsExitConfirmation();
        }
    }

    public void sendDataSnap(DataSnap ds) {
        Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

        FirebaseDatabaseApi post = retrofit.create(FirebaseDatabaseApi.class);
        Call<DataSnap> call = post.sendDataSnap(ds);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.d(LOG_TAG, "Data sent successfully");
                Log.d(LOG_TAG, String.valueOf(call.request()));
                Log.d(LOG_TAG, response.toString());
                Toast.makeText(getApplicationContext(), String.format("OK"), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d(LOG_TAG, "Data sending failed");
                Toast.makeText(getApplicationContext(), String.format("KO"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(LOG_TAG, "onRequestPermissionsResult(): " + Arrays.toString(permissions));
        if (requestCode == REQUEST_AUTHORIZATIONS_RESULT_CODE) {
            Log.i(LOG_TAG, "Received response for GPS permission request");
            if (grantResults.length == 3 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                Log.i(LOG_TAG, "All permissions granted.");
                startLocationUpdates();
            } else {
                Log.i(LOG_TAG, "All permissions NOT granted.");
            }
        } else {
            Log.e(LOG_TAG, "Unhandled Request Permissions Result code");
        }
    }

    @SuppressLint("MissingPermission")
    public void startLocationUpdates() {
        Log.d(LOG_TAG, "startLocationUpdates()");

        // create location request
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(LOCATION_UPDATE_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
        );
    }

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
            currHeartRate = event.values[0];
            Log.d(LOG_TAG, "Heart Rate changed : "+currHeartRate);
        }
        else
            Log.d(LOG_TAG, "Unknown sensor type");
    }

    private final LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            Log.d(LOG_TAG, "onLocationResult(" + locationResult.getLocations().size() + ")");
            if (locationResult.getLocations().size() > 0) {
                Location last = null;
                for (Location location : locationResult.getLocations()) {
                    int length = locations.size();
                    if (length >= MAX_LOCATION_RECORDED) {
                        locations.remove(length - 1); // remove last element
                        heartRates.remove(length - 1);
                    }
                    locations.add(0, location); // insert new element
                    last = location;
                    heartRates.add(0, currHeartRate);
                }

                // Build new data snap
                DataSnap currentDS = new DataSnap(new Date().toString(), currHeartRate, last.getLatitude(), last.getLongitude(), last.getAltitude(), last.getSpeed());
                sendDataSnap(currentDS);
            }
        }
    };

    private boolean hasGps() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
    }

    public void noGpsExitConfirmation() {
        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, ConfirmationActivity.FAILURE_ANIMATION);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_DURATION_MILLIS, 3000);
        intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE, "No GPS found");
        startActivity(intent);
        finish();
    }
}