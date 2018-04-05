package com.cnit355.myles.a425lab10_m1;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnBT, btnBToff, btnScan, btnDiscover;
    BluetoothAdapter mBluetoothAdapter;
    TextView tv;
    boolean isChecked = false;
    boolean isOn = false;
    ArrayList<String> devices = new ArrayList<String>();
    ArrayList<BluetoothDevice> mDeviceList;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Bluetooth");

        btnBT = findViewById(R.id.btnBT);
        btnScan = findViewById(R.id.btnScan);
        btnDiscover = findViewById(R.id.btnDiscover);
        tv = findViewById(R.id.textView);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        final int REQUEST_ENABLE_BT = 1;
        mDeviceList = new ArrayList<>();

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);


        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBluetoothAdapter.isDiscovering()) {
                    mBluetoothAdapter.cancelDiscovery();
                }
                mBluetoothAdapter.startDiscovery();
            }
        });

        //Set starting values for buttons
        if (mBluetoothAdapter.isEnabled()) {
            tv.setText("Bluetooth is ON");
            btnBT.setText("Turn Bluetooth off");
            btnDiscover.setEnabled(true);
            btnScan.setEnabled(true);
        } else {
            tv.setText("Bluetooth is OFF");
            btnBT.setText("Turn Bluetooth on");
            btnDiscover.setEnabled(false);
            btnScan.setEnabled(false);
        }


        //Turn on Bluetooth
        btnBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mBluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                    tv.setText("Bluetooth is ON");
                    btnDiscover.setEnabled(true);
                    btnScan.setEnabled(true);
                    btnBT.setText("Turn Bluetooth off");

                } else {
                    mBluetoothAdapter.disable();
                    tv.setText("Bluetooth is OFF");
                    btnDiscover.setEnabled(false);
                    btnScan.setEnabled(false);
                    btnBT.setText("Turn Bluetooth on");
                }
            }
        });


        //Discover device
        btnDiscover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 10);
                startActivity(discoverableIntent);
            }
        });
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //Get bluetooth device
                Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String dName = device.getName();
                String dHardwareAddress = device.getAddress();
                mDeviceList.add(device);
                Toast.makeText(getApplicationContext(), dName + " - " + dHardwareAddress, Toast.LENGTH_SHORT).show();
                Log.i("bluetooth", dName + " - " + dHardwareAddress);
                Intent newIntent = new Intent(MainActivity.this, DeviceList.class);
                newIntent.putParcelableArrayListExtra("device.list", mDeviceList);
                startActivity(newIntent);
//
//                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//
//                mDeviceList.add(device);
//
//                showToast("Found device " + device.getName());
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
//        unregisterReceiver(mReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        mBluetoothAdapter.cancelDiscovery();
    }

    public void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
