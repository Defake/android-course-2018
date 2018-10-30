package com.defake.opener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class BatteryActivity extends AppCompatActivity {

    private BroadcastReceiver batteryStatusReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean isCharging =
                    status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL;

            final TextView chargeStatusTextView = findViewById(R.id.chargeStatus);
            chargeStatusTextView.setText(getString(R.string.chargeStatus, isCharging ? "Charging" : "Unplugged"));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);

        BatteryManager bm = (BatteryManager)getSystemService(BATTERY_SERVICE);
        int batteryLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

        final TextView batteryPercentTextView = findViewById(R.id.batteryPercentTextView);
        batteryPercentTextView.setText(getString(R.string.batteryPercentAmount, batteryLevel));
        this.registerReceiver(this.batteryStatusReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

}
