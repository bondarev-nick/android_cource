package com.chebur.test_service_activity;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nick on 22.11.2015.
 */
public class geo_service extends Service implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener{

    private GoogleApiClient _gAPI;
    private int _startCount;
    private List<geo_serviceListener> _serviceLsnr;
    private geo_service_binder _binder;
    private LocationRequest _lr;

    //region service override
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("aaa", "service binded");
        return _binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("aaa", "service unbinded");
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("aaa", "service created");
        _startCount = 0;
        _serviceLsnr = new ArrayList<geo_serviceListener>();
        _binder = new geo_service_binder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        _startCount++;
        if (_startCount == 1) {
            Log.d("aaa", "service started");
            GoogleAPIConnect();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        _startCount = 0;
        LocationServices.FusedLocationApi.removeLocationUpdates(_gAPI, this);
        Log.d("aaa", "service stopped");
        super.onDestroy();
    }
    //endregion

    //region service connection
    public void AddServiceListener(geo_serviceListener lsnr) {
        _serviceLsnr.add(lsnr);
        Log.d("aaa", "listener added");
        Log.d("aaa", _serviceLsnr.size() + " listeners remain");
        if(_gAPI.isConnected()) {
            lsnr.OnServiceReady();
        }
    }

    public void RemoveServiceListener(geo_serviceListener lsnr) {
        if (_serviceLsnr.remove(lsnr)) {
            Log.d("aaa", "listener removed");
            Log.d("aaa", _serviceLsnr.size() + " listeners remain");
        }
    }

    public Location GetLastKnownLocation() {
        return LocationServices.FusedLocationApi.getLastLocation(_gAPI);
    }
    //endregion

    @Override
    public void onLocationChanged(Location location) {
        for(geo_serviceListener inst : _serviceLsnr) {
            inst.OnLocationChanged(location);
        }
    }

    //region google location api override
    @Override
    public void onConnected(Bundle bundle) {
        _lr = CreateLocationRequest();
        LocationServices.FusedLocationApi.
                requestLocationUpdates(_gAPI, _lr, this);

        for(geo_serviceListener sl : _serviceLsnr) {
            sl.OnServiceReady();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
    //endregion

    //region google location API init
    private void GoogleAPIConnect() {
        _gAPI = new GoogleApiClient.Builder(this).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).
                addApi(LocationServices.API).build();
        _gAPI.connect();
    }

    private LocationRequest CreateLocationRequest() {
        LocationRequest lr = new LocationRequest();
        lr.setInterval(500L);
        lr.setFastestInterval(500L);
        lr.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        lr.setSmallestDisplacement(1);

        return lr;
    }
    //endregion

    public interface geo_serviceListener {
        void OnLocationChanged(Location l);
        void OnServiceReady();
    }

    public class geo_service_binder extends Binder {
        public geo_service GetServiceInst() {
            return geo_service.this;
        }
    }
}
