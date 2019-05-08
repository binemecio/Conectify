package com.example.binemecio.conectify.Helper;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
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
    private String networkSSID = "";
    private String networkPass = "";
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

    public boolean tryHiddenConnection()
    {
        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = "\"" + networkSSID + "\"";   // Please note the quotes. String should contain ssid in quotes

        conf.preSharedKey = "\"" + networkPass + "\"";

        conf.hiddenSSID = true;
        conf.status = WifiConfiguration.Status.ENABLED;
        conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        conf.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        conf.allowedProtocols.set(WifiConfiguration.Protocol.WPA);

        WifiManager wifiManager = (WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        wifiManager.addNetwork(conf);

        boolean isConected = false;

        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        for (WifiConfiguration i : list) {
            if (i.SSID != null && i.SSID.equals("\"" + networkSSID + "\"")) {

                wifiManager.disconnect();

                wifiManager.enableNetwork(i.networkId, true);

                wifiManager.reconnect();

                wifiManager.saveConfiguration();
                isConected = true;
                break;
            }
        }

        return isConected;
    }



    // try connection with the parameter (ssid, password) before sent
    public boolean tryConnection()
    {
        conf.SSID = "\"" + networkSSID + "\"";
        conf.preSharedKey = "\""+ networkPass +"\"";

        WifiManager wifiManager = (WifiManager)this.context.getApplicationContext().getSystemService(WIFI_SERVICE);

        if ((wifiManager.isWifiEnabled() == false)) {
            Toast.makeText(this.context, "Encendiendo WIFI...", Toast.LENGTH_LONG).show();

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

    public static boolean setEnabledWifi(Activity activity)
    {
        WifiManager wifiManager = (WifiManager)activity.getApplicationContext().getSystemService(WIFI_SERVICE);
        return wifiManager.setWifiEnabled(true);
    }

    // try reconnect in case that this network has been saved
    public boolean tryReconnect(String ssid)
    {
        WifiManager wifiManager = (WifiManager)this.context.getApplicationContext().getSystemService(WIFI_SERVICE);

        if ((wifiManager.isWifiEnabled() == false)) {
            Toast.makeText(this.context, "Encendiendo WIFI...", Toast.LENGTH_LONG).show();

            // is necesary manage the permission for api > 21
            wifiManager.setWifiEnabled(true);
        }


        int netId = -1;
        for (WifiConfiguration tmp : wifiManager.getConfiguredNetworks())// // permission ACCESS_WIFI_STATE
            if (tmp.SSID.equals( "\""+ssid+"\""))
            {
                wifiManager.disconnect();
                netId = tmp.networkId;
                wifiManager.enableNetwork(netId, true);
                wifiManager.reconnect();
            }

        return this.isConnected();
    }

    public boolean tryReconnect()
    {
        WifiManager wifiManager = (WifiManager)this.context.getApplicationContext().getSystemService(WIFI_SERVICE);

        if ((wifiManager.isWifiEnabled() == false)) {
            Toast.makeText(this.context, "Encendiendo WIFI...", Toast.LENGTH_LONG).show();

            // is necesary manage the permission for api > 21
            wifiManager.setWifiEnabled(true);
        }

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
//    public static String getConnectedSSID(Activity context)
//    {
//        String ssid = "";
//        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
//        WifiInfo wifiInfo = wifiManager.getConnectionInfo(); // permission ACCESS_WIFI_STATE
//        if (WifiInfo.getDetailedStateOf(wifiInfo.getSupplicantState()) == NetworkInfo.DetailedState.CONNECTED) {
//            ssid = wifiInfo.getSSID();
//        }
//
//        return ssid;
//    }

    public static String getConnectedSSID(Context context) {
        String ssid = null;
        Helpers helper = new Helpers();
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo.isConnected()) {
            final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo != null && !helper.isNullOrWhiteSpace(connectionInfo.getSSID())) {
                ssid = connectionInfo.getSSID();
            }
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
