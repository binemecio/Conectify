package com.example.binemecio.conectify.Service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import com.example.binemecio.conectify.AdActivity;
import com.example.binemecio.conectify.R;
import com.example.binemecio.conectify.Singletoon.StorageSingleton;

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
        this.loopAd = StorageSingleton.getInstance().getLoopTime();

        handler.postDelayed(() -> {
            Intent startIntent = new Intent(getApplicationContext(), AdActivity.class);
            this.startActivity(startIntent);
        }, this.loopAd);


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
