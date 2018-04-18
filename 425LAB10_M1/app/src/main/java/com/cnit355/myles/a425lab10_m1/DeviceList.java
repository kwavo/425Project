package com.cnit355.myles.a425lab10_m1;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class DeviceList extends AppCompatActivity {

    ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        setTitle("Device List");

        listView = findViewById(R.id.listView);
        TextView tvBTName = findViewById(R.id.tvDeviceName);
        TextView tvBTAddress = findViewById(R.id.tvAddress);
     //   ArrayAdapter<String> devices = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, );

    }

//    public class DeviceListAdapter extends BaseAdapter {
//        LayoutInflater mInflator;
//        List<BluetoothDevice> mData;
//
//        public DeviceListAdapter(Context c) {
//            mInflator = LayoutInflater.from(c);
//        }
//
//        @Override
//        public View getView(int i, View view, ViewGroup viewGroup) {
//            TextView tvName = findViewById(R.id.tvDeviceName);
//            TextView tvAddress = findViewById(R.id.tvAddress);
//
//            BluetoothDevice device = mData.get(i);
//            tvName = device.getName();
//        }
//    }
}
