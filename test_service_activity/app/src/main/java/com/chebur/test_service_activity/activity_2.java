package com.chebur.test_service_activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by nick on 22.11.2015.
 */
public class activity_2 extends AppCompatActivity implements
        geo_service.geo_serviceListener {
    Button _btn;
    Button _btnStop;
    TextView _tvLat;
    TextView _tvLng;
    geo_service _servInst = null;

    //region activity lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_1);
        _btn = (Button)findViewById(R.id.btnSwitch);
        _btnStop = (Button)findViewById(R.id.btnStop);
        _tvLat = (TextView)findViewById(R.id.tvLat);
        _tvLng = (TextView)findViewById(R.id.tvLng);

        _btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(activity_2.this, activity_1.class);
                newIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(newIntent);
            }
        });

        _btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent srvIntent = new Intent(activity_2.this, geo_service.class);
                stopService(srvIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        Log.d("aaa", "activity 2 resumed");
        super.onResume();

        Intent srvIntent = new Intent(this, geo_service.class);
        startService(srvIntent);
        Log.d("aaa",
                (bindService(srvIntent, conn, 0) ?
                        "bind true" : "bind false"));
    }

    @Override
    protected void onPause() {
        Log.d("aaa", "activity 2 paused");

        if (_servInst != null) {
            _servInst.RemoveServiceListener(activity_2.this);
        }
        unbindService(conn);

        super.onPause();

    }
    //endregion

    //region service connection callback
    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            _servInst = ((geo_service.geo_service_binder)service).GetServiceInst();
            _servInst.AddServiceListener(activity_2.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            _servInst = null;
        }
    };
    //endregion

    @Override
    public void OnLocationChanged(Location l) {
        showData(l);
    }

    @Override
    public void OnServiceReady() {
        showData(_servInst.GetLastKnownLocation());
    }

    private void showData(Location l) {
        _tvLat.setText(String.format("Latitude: %.6f", l.getLatitude()));
        _tvLng.setText(String.format("Longitude: %.6f", l.getLongitude()));
    }
}
