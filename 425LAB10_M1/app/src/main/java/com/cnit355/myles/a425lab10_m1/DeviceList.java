package com.cnit355.myles.a425lab10_m1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DeviceList extends AppCompatActivity {

    ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        setTitle("Device List");

        listView = findViewById(R.id.listView);

     //   ArrayAdapter<String> devices = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, );
    }
}
