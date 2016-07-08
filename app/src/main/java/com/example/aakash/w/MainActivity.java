package com.example.aakash.w;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView t1,t2,t3;

    ListView lv;
    WifiManager wifi;
    WifiReceiver receiverWifi;
    String wifis[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv=(ListView)findViewById(R.id.listView);
        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        t1=(TextView)findViewById(R.id.Text1);
        t2=(TextView)findViewById(R.id.Text2);
        t3=(TextView)findViewById(R.id.Text3);

        if (wifi.isWifiEnabled() == false) {
            Toast.makeText(getApplicationContext(), "wifi is disabled..making it enabled",Toast.LENGTH_LONG).show();
            wifi.setWifiEnabled(true);
        }
       // receiverWifi = new WifiReceiver();
        //registerReceiver(receiverWifi, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifi.startScan();
        receiverWifi = new WifiReceiver();
        registerReceiver(receiverWifi, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,0,0,"Refresh");
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        registerReceiver(receiverWifi, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifi.startScan();
        return super.onOptionsItemSelected(item);
    }
    // Broadcast receiver class called its receive method when number of wifi connections change

     class WifiReceiver extends BroadcastReceiver {
         public void onReceive(Context c, Intent intent) {
             List<ScanResult> wifiScanList = wifi.getScanResults();
             wifis = new String[wifiScanList.size()];
             t1.setText("Before loop".toString());

             for(int i = 0; i <wifiScanList.size(); i++){
                 wifis[i] = ((wifiScanList.get(i)).toString());
                 t2.setText("In loop".toString());
             }
             t3.setText("After loop".toString());
             lv.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,wifis));
         }

     }

}
