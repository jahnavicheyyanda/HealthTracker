package com.example.zece.healthtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.media.audiofx.Visualizer;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    //MyAppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //db = MyAppDatabase.getAppDatabase(getApplicationContext());

        Button next_button = findViewById(R.id.next_button_home);

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recording_wave();
            }
        });
    }

    public void recording_wave() {
        Intent intent = new Intent(this, recording_wave.class);
        startActivity(intent);
    }

    /*public class BluetoothIntentReceiver extends BroadcastReceiver {

        private final String TAG = BluetoothIntentReceiver.class.getSimpleName();

        @Override
        public void onReceive(Context context, Intent intent) {
            int state = intent.getIntExtra("android.bluetooth.a2dp.extra.SINK_STATE", -1);
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            String action = intent.getAction();
            //String name = device != null ? device.getName() : "None";

            //Log.d(TAG, String.format("Sink State: %d; Action: %s; Device: %s", state, action, name));

            boolean actionConnected = false;
            boolean actionDisconnected = false;

            if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
                actionConnected = true;
            } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action) || BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED.equals(action)) {
                actionDisconnected = true;
            }

            boolean connected = state == android.bluetooth.BluetoothA2dp.STATE_CONNECTED || actionConnected;
            boolean disconnected = state == android.bluetooth.BluetoothA2dp.STATE_DISCONNECTED || actionDisconnected;

            if (connected) {
                Log.i(TAG, "Connected to Bluetooth device");
            }

            if (disconnected) {
                Log.i(TAG, "Disconnected from Bluetooth device");
            }
        }
    }

*/

}