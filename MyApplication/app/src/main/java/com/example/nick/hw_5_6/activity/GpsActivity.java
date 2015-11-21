package com.example.nick.hw_5_6.activity;

import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.nick.hw_5_6.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by nick on 21.11.2015.
 */
public class GpsActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private TextView _lat;
    private TextView _lng;
    private GoogleApiClient _googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("aaa", "onCreate");

        setContentView(R.layout.activity_gps);

        _lat = (TextView)findViewById(R.id.tvLat);
        _lng = (TextView)findViewById(R.id.tvLng);

        _googleApiClient = new GoogleApiClient.Builder(this).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).
                addApi(LocationServices.API).build();
        _googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        StopLocationUpdates();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("aaa", "onStart");
        if (_googleApiClient != null && _googleApiClient.isConnected()) {
            StartLocationUpdates();
        }
    }

    private void ShowLocation(Location l) {
        _lat.setText(String.format("Latitude: %.6f", l.getLatitude()));
        _lng.setText(String.format("Longitude: %.6f", l.getLongitude()));
    }

    //region google location updates
    @Override
    public void onConnected(Bundle bundle) {
        StartLocationUpdates();
        Location l = LocationServices.FusedLocationApi.getLastLocation(_googleApiClient);
        ShowLocation(l);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    protected LocationRequest createLocationRequest() {
        LocationRequest lr = new LocationRequest();
        lr.setInterval(500);
        lr.setFastestInterval(500);
        lr.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        return  lr;
    }

    private void StartLocationUpdates() {
        LocationServices.FusedLocationApi.
                requestLocationUpdates(_googleApiClient, createLocationRequest(), this);
    }

    private void StopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(_googleApiClient, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        ShowLocation(location);
    }
    //endregion
}
