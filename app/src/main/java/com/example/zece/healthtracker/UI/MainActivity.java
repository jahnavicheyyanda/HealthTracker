package com.example.zece.healthtracker.UI;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.UUID;

import com.example.zece.healthtracker.R;
import com.example.zece.healthtracker.View.DeviceListAdapter;

import static android.bluetooth.BluetoothAdapter.STATE_CONNECTED;
import static android.bluetooth.BluetoothProfile.STATE_CONNECTING;



public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final String TAG = "Main Activity";

    BluetoothAdapter mBluetoothAdapter;
    Button btnEnableDisable_Discoverable;
    ArrayList<BluetoothDevice> mBTDevices = new ArrayList<>();
    DeviceListAdapter mDeviceListAdapter;
    ListView lvNewDevices;
    public BluetoothDevice device;
    private Handler handler;
    //private ConnectThread mConnectThread;
    //private AcceptThread mAcceptThread;
    private ConnectedThread mConnectedThread;
    private BluetoothSocket mmSocket ;
    UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");


    // Create a BroadcastReceiver for ACTION_FOUND.
    private final BroadcastReceiver mBroadcastReceiver1 = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, mBluetoothAdapter.ERROR);

                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        Log.d(TAG, "onReceive: STATE OFF");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d(TAG, "mBroadcastReceiver1: STATE TURNING OFF");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.d(TAG, "mBroadcastReceiver1: STATE ON");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d(TAG, "mBroadcastReceiver1: STATE TURNING ON");
                        break;
                }
            }
        }
    };

    private final BroadcastReceiver mBroadcastReceiver2 = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)) {
                final int mode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, mBluetoothAdapter.ERROR);

                switch (mode) {
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Enabled");
                        break;
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability disabled. Able to receive connections");
                        break;
                    case BluetoothAdapter.SCAN_MODE_NONE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability disabled. Not able to receive connections");
                        break;
                    case BluetoothAdapter.STATE_CONNECTING:
                        Log.d(TAG, "mBroadcastReceiver2: connecting");
                        break;
                    case STATE_CONNECTED:
                        Log.d(TAG, "mBroadcastReceiver2: connected");
                        break;
                }
            }
        }
    };
    private BroadcastReceiver mBroadcastReceiver3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.d(TAG, "onReceive: ACTION_FOUND");

            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mBTDevices.add(device);

                Log.d(TAG, "onReceive: " + device.getName() + ": " + device.getAddress());

                mDeviceListAdapter = new DeviceListAdapter(context, R.layout.device_list_item, mBTDevices);
                lvNewDevices.setAdapter(mDeviceListAdapter);
            }
        }
    };

    //Broadcast receiver that detects bond state changes (Pairing status changes)

    private final BroadcastReceiver mBroadcastReceiver4 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
                BluetoothDevice mDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                //3 cases:
                //case1: bonded already
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDED) {
                    Log.d(TAG, "BroadcastReceiver: BOND_BONDED.");
                }

                //case2: creating bond
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDING) {
                    Log.d(TAG, "BroadcastReceiver: BOND_BONDING.");
                }

                //case3: breaking a bond
                if (mDevice.getBondState() == BluetoothDevice.BOND_NONE) {
                    Log.d(TAG, "BroadcastReceiver: BOND_NONE.");
                }
            }
        }
    };

    private void OperateFolderActions() {

        String state = Environment.getExternalStorageState();
        Log.d("Media State", state);

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File appDirectoryTransfer = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Health_tracker_transfer/");

            File appDirectory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Health_tracker/");

            Log.d("appDirectroyTransExist", appDirectoryTransfer.exists() + "");
            Log.d("appDirectroyExist", appDirectory.exists() + "");
            if (!appDirectoryTransfer.exists())
                Log.d("appDirTr created: ", appDirectoryTransfer.mkdir() + "");

            if (!appDirectory.exists()) Log.d("appDir created: ", appDirectory.mkdir() + "");
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: called.");
        //unregisterReceiver(mBroadcastReceiver1);
        //unregisterReceiver(mBroadcastReceiver2);
        //unregisterReceiver(mBroadcastReceiver3);
        //unregisterReceiver(mBroadcastReceiver4);
        //mBluetoothAdapter.cancelDiscovery();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OperateFolderActions();

        TextView text_status = findViewById(R.id.text_bluetooth_connecton_status);
        text_status.setText("Please turn the bluetooth on.");

        Button record_start = findViewById(R.id.record_start);

        Button btnONOFF = findViewById(R.id.btnONOFF);
        btnEnableDisable_Discoverable = findViewById(R.id.btnDiscoverable_on_off);
        lvNewDevices = (ListView) findViewById(R.id.lvNewDevices);
        mBTDevices = new ArrayList<>();

        //Broadcasts when bond state changes (ie: pairing)
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(mBroadcastReceiver4, filter);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        lvNewDevices.setOnItemClickListener(MainActivity.this);

        btnONOFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "enabling / disabling bluetooth.");
                enableDisableBT();
                TextView text_status = findViewById(R.id.text_bluetooth_connecton_status);
                text_status.setText("Please discover devices to connect.");
            }

        });

        Button next_button = findViewById(R.id.next_button_home);

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uriBluetooth = Uri.parse(Environment.getExternalStorageDirectory() + "/bluetooth/Test.wav");
                File fileBluetooth = new File(uriBluetooth.toString());

                Uri uriDownload = Uri.parse(Environment.getExternalStorageDirectory() + "/Download/Test.wav");
                File fileDownload = new File(uriDownload.toString());

                if(fileBluetooth.exists()){
                    File to = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                            + "/Health_tracker_transfer/Test.wav");
                    fileBluetooth.renameTo(to);
                } else if (fileDownload.exists()) {
                    File to = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                            + "/Health_tracker_transfer/Test.wav");
                    fileDownload.renameTo(to);
                }

                Uri uri = Uri.parse(Environment.getExternalStorageDirectory() + "/Health_tracker_transfer/Test.wav");
                File file = new File(uri.toString());
                if (file.exists()) {
                    recording_wave();
                } else {

                    Toast.makeText(getApplicationContext(), "There is no file to stream", Toast.LENGTH_LONG).show();
                }

            }
        });



        record_start.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Uri uriBluetooth = Uri.parse(Environment.getExternalStorageDirectory() + "/bluetooth/Test.wav");
                File fileBluetooth = new File(uriBluetooth.toString());
                if(fileBluetooth.exists()){
                    fileBluetooth.delete();
                }

                Uri uri = Uri.parse(Environment.getExternalStorageDirectory() + "/Health_tracker_transfer/Test.wav");
                File file = new File(uri.toString());
                if(file.exists()){
                    file.delete();
                }
                TextView text_status = findViewById(R.id.text_bluetooth_connecton_status);
                if (mmSocket.isConnected()) {
                    byte[] num = "1".getBytes();
                    mConnectedThread.write(num);
                }
                else {
                    text_status.setText("Please select the correct bluetooth device");
                }
                try {
                    mmSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //mConnectedThread.run();


            }
        });

    }


    public void enableDisableBT() {

        if (mBluetoothAdapter == null) {
            Log.d(TAG, "enableDisableBT: Does not have Bluetooth capabilities.");
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBTIntent);

            //instead of activityForResult, broadcastReceivers are used.
            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadcastReceiver1, BTIntent);
        }

        if (mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.disable();

            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadcastReceiver1, BTIntent);
        }
    }

    public void btnEnableDisable_Discoverable(View view) {
        Log.d(TAG, "btnEnableDisable_Discoverable: Making device discoverable for 3000 secs");
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 3000);
        startActivity(discoverableIntent);

        IntentFilter intentFilter = new IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        registerReceiver(mBroadcastReceiver2, intentFilter);
    }

    public void btnDiscover(View view) {
        Log.d(TAG, "btnDiscover: Looking for unpaired devices.");
        TextView text_status = findViewById(R.id.text_bluetooth_connecton_status);
        text_status.setText("Please select a device to connect.");

        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
            Log.d(TAG, "cancel discovery.");

            //Check BT permissions in manifest
            checkBTPermissions();

            mBluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
        }

        if (!mBluetoothAdapter.isDiscovering()) {

            //Check BT permissions in manifest
            checkBTPermissions();

            mBluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
        }
    }

    private void checkBTPermissions() {

        //if it is greater that LOLLIPOP version, it will check the manifest for bluetooth permissions
        //do not care about errors below. the reason is they can be used only after API 23. with if clauses we give it so no problem.
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
            permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
            if (permissionCheck != 0) {

                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
            }
        } else {
            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        //cancel discovery because it is memory intensive
        mBluetoothAdapter.cancelDiscovery();

        Log.d(TAG, "onItemClick: you clicked on a device.");
        String deviceName = mBTDevices.get(i).getName();
        final String deviceAddress = mBTDevices.get(i).getAddress();

        Log.d(TAG, "onItemClick: deviceName = " + deviceName);
        Log.d(TAG, "onItemClick: deviceAddress = " + deviceAddress);

        //create the bond
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Log.d(TAG, "Trying to pair with " + deviceName);

            mBTDevices.get(i).createBond();
            device = mBTDevices.get(i);
        }

        new Thread() {

            public void run() {
                boolean fail = false;

                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(deviceAddress);

                try {
                    mmSocket = createBluetoothSocket(device);
                } catch (IOException e) {
                    fail = true;
                    Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_SHORT).show();
                }
                // Establish the Bluetooth socket connection.
                try {
                    mmSocket.connect();

                } catch (IOException e) {
                    // Unable to connect; close the socket and return.
                    try {
                        fail = true;
                        mmSocket.close();
                    } catch (IOException e2) {
                        //insert code to deal with this
                        Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_SHORT).show();
                    }
                }

                if (fail == false) {

                    mConnectedThread = new ConnectedThread(mmSocket);
                    mConnectedThread.start();

                }
            }
        }.start();

        Button record_start = findViewById(R.id.record_start);
        record_start.setVisibility(View.VISIBLE);

        TextView text_status = findViewById(R.id.text_bluetooth_connecton_status);
        text_status.setText("Connected.");

    }


    //creates insecure outgoing connection with BT device using UUID
    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        try {
            final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", UUID.class);

            return (BluetoothSocket) m.invoke(device, uuid);
        } catch (Exception e) {
            Log.e(TAG, "Could not create Insecure RFComm Connection", e);
        }
        return device.createRfcommSocketToServiceRecord(uuid);
    }


    //creates secure outgoing connection with BT device using UUID
   /* private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        mmSocket = device.createRfcommSocketToServiceRecord(uuid);
        return mmSocket;

    }*/

    private interface MessageConstants {
        public static final int MESSAGE_READ = 0;
        public static final int MESSAGE_WRITE = 1;
        public static final int MESSAGE_TOAST = 2;

        // ... (Add other message types here as needed.)
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        private byte[] mmBuffer; // mmBuffer store for the stream

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "Error occurred when creating output stream", e);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }


        //Call this from the main activity to send data to the remote device.
        public void write(byte[] bytes) {
            try {
                mmOutStream.write(bytes);

            } catch (IOException e) {
                Log.e(TAG, "Error occurred when sending data", e);

                // Send a failure message back to the activity.
                Message writeErrorMsg =
                        handler.obtainMessage(MessageConstants.MESSAGE_TOAST);
                Bundle bundle = new Bundle();
                bundle.putString("toast",
                        "Couldn't send data to the other device");
                writeErrorMsg.setData(bundle);
                handler.sendMessage(writeErrorMsg);
            }
        }




        // Call this method from the main activity to shut down the connection.
    /*    public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Could not close the connect socket", e);
            }
        }*/


    }



    public void recording_wave() {
        Intent intent = new Intent(this, RecordWave.class);
        startActivity(intent);
    }





}









