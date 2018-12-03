package com.defake.opener;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationActivity extends AppCompatActivity {

    private static final String TAG = "LocationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        showLoading();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            new LoadLocationTask().execute();
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
        }
    }

    private class LoadLocationTask extends AsyncTask<Void, Integer, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            synchronized (this) {
                showLocation();
            }
            return null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)){
            if (grantResults[0] == 0) {
                showLocation();
            } else {
                showLocationText("unavailable");
            }
        }
    }

    private void showLocation() throws SecurityException {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            String address = addresses.get(0).getAddressLine(0);
            String city = addresses.get(0).getLocality();
            String fullAddress = city + ", " + address;

            showLocationText(fullAddress);
        } catch (IOException e) {
            Log.e(TAG, "Can't read location. " + e.getMessage());
            showLocationText("unavailable");
        }
    }

    private void showLocationText(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final TextView locationText = findViewById(R.id.locationTextView);
                final ProgressBar loadingBar = findViewById(R.id.locationProgressBar);

                loadingBar.setVisibility(View.INVISIBLE);
                locationText.setVisibility(View.VISIBLE);
                locationText.setText(getString(R.string.locationText, text));
            }
        });

    }

    private void showLoading() {
        final TextView locationText = findViewById(R.id.locationTextView);
        final ProgressBar loadingBar = findViewById(R.id.locationProgressBar);

        locationText.setVisibility(View.INVISIBLE);
        loadingBar.setVisibility(View.VISIBLE);
    }

}
