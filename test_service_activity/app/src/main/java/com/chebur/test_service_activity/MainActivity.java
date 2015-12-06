package com.chebur.test_service_activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent srvIntent = new Intent(this, geo_service.class);
        startService(srvIntent);
    }

    public void onFirts(View v) {
        Intent srvIntent = new Intent(this, activity_1.class);
        startActivity(srvIntent);
    }

    public void onSec(View v) {
        Intent srvIntent = new Intent(this, activity_2.class);
        startActivity(srvIntent);
    }
}
