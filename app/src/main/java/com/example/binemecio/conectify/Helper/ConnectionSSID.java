package com.example.binemecio.conectify.Helper;

import android.app.Activity;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import java.util.List;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by binemecio on 29/4/2019.
 */

public class ConnectionSSID {
    private String networkSSID = "REDWIFI";
    private String networkPass = "password";
    private WifiConfiguration conf = new WifiConfiguration();
    private Activity context;
    public ConnectionSSID(Activity context)
    {
        this.context = context;
    }

    public ConnectionSSID(Activity context, String networkSSID, String networkPass)
    {
        this.context = context;
        this.networkSSID = networkSSID;
        this.networkPass = networkPass;
    }


    // try connection with the parameter (ssid, password) before sent
    public boolean tryConnection()
    {
        conf.SSID = "\"" + networkSSID + "\"";
        conf.preSharedKey = "\""+ networkPass +"\"";

        WifiManager wifiManager = (WifiManager)this.context.getApplicationContext().getSystemService(WIFI_SERVICE);

        if ((wifiManager.isWifiEnabled() == false)) {
            Toast.makeText(this.context, "Conectando a WIFI...", Toast.LENGTH_LONG).show();

            // is necesary manage the permission for api > 21
            wifiManager.setWifiEnabled(true);
        }

        wifiManager.addNetwork(conf);

        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();// // permission ACCESS_WIFI_STATE
        for( WifiConfiguration i : list ) {
            if(i.SSID != null && i.SSID.equals("\"" + networkSSID + "\"")) {
                wifiManager.disconnect();
                wifiManager.enableNetwork(i.networkId, true);
                wifiManager.reconnect();
                break;
            }
        }

        return this.isConnected();
    }

    // try reconnect in case that this network has been saved
    public boolean tryReconnect()
    {
        WifiManager wifiManager = (WifiManager)this.context.getApplicationContext().getSystemService(WIFI_SERVICE);
        int netId = -1;
        for (WifiConfiguration tmp : wifiManager.getConfiguredNetworks())// // permission ACCESS_WIFI_STATE
            if (tmp.SSID.equals( "\""+this.networkSSID+"\""))
            {
                netId = tmp.networkId;
                wifiManager.enableNetwork(netId, true);
            }

        return this.isConnected();
    }

    // verify if the network configured is connected
    public boolean isConnected()
    {
        return ConnectionSSID.getConnectedSSID(this.context) == this.networkSSID;
    }

    // get the name of the currently network connected (empty if there is no record network)
    public static String getConnectedSSID(Activity context)
    {
        String ssid = "";
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo(); // permission ACCESS_WIFI_STATE
        if (WifiInfo.getDetailedStateOf(wifiInfo.getSupplicantState()) == NetworkInfo.DetailedState.CONNECTED) {
            ssid = wifiInfo.getSSID();
        }

        return ssid;
    }


    /// verify the state of the wifi
    public static boolean isWifiOpen(Activity context)
    {
        WifiManager wifiManager = (WifiManager)context.getApplicationContext().getSystemService(WIFI_SERVICE);
        return  wifiManager.isWifiEnabled();
    }


//    Method Getter and Setter

    public String getNetworkSSID() {
        return networkSSID;
    }

    public void setNetworkSSID(String networkSSID) {
        this.networkSSID = networkSSID;
    }

    public String getNetworkPass() {
        return networkPass;
    }

    public void setNetworkPass(String networkPass) {
        this.networkPass = networkPass;
    }



}
