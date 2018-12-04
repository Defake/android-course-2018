package com.defake.opener;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button openBatteryBtn = findViewById(R.id.openBatteryScreenButton);
        final Intent batteryIntent = new Intent(this, BatteryActivity.class);
        openBatteryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(batteryIntent);
            }
        });

        Button openLocationBtn = findViewById(R.id.openLocationActivity);
        final Intent locationIntent = new Intent(this, LocationActivity.class);
        openLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(locationIntent);
            }
        });

        Button openApiBtn = findViewById(R.id.openApiScreenButton);
        final Intent apiIntent = new Intent(this, ApiActivity.class);
        openApiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(apiIntent);
            }
        });
    }
}
