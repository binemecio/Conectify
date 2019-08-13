package app.cloudnicaragua.bluewifi.nicaragua.Service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import app.cloudnicaragua.bluewifi.nicaragua.Helper.ConnectionSSID;
import app.cloudnicaragua.bluewifi.nicaragua.Helper.Helpers;
import app.cloudnicaragua.bluewifi.nicaragua.Singletoon.StorageSingleton;

public class MyService extends Service {
    public MyService() {
    }

    long loopAd = 3000;
    Handler handler = new Handler();

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public ComponentName startForegroundService(Intent service) {

        return super.startForegroundService(service);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        this.loopAd = StorageSingleton.getInstance().getLoopTime();
//
//        handler.postDelayed(() -> {
//            Intent startIntent = new Intent(getApplicationContext(), AdActivity.class);
//            this.startActivity(startIntent);
//        }, this.loopAd);
//

        return super.onStartCommand(intent, flags, startId);
    }

    public void onTaskRemoved(Intent rootIntent) {

        //unregister listeners
        //do any other cleanup if required

        //stop service
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Helpers helper = new Helpers();
        String ssid = helper.getString(StorageSingleton.getInstance().getSsid());
        String ssid2 = helper.getString(StorageSingleton.getInstance().getSsid2());
        ConnectionSSID connectionSSID = new ConnectionSSID(this);
        connectionSSID.setNetworkSSID(ssid);
        connectionSSID.disconnectNetwork(ssid2);
        connectionSSID.tryReconnect();
    }
}
