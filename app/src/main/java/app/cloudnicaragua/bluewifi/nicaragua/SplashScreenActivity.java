package app.cloudnicaragua.bluewifi.nicaragua;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import app.cloudnicaragua.bluewifi.nicaragua.Helper.ConnectionSSID;

import app.cloudnicaragua.bluewifi.nicaragua.R;

import app.cloudnicaragua.bluewifi.nicaragua.Service.MyService;
import app.cloudnicaragua.bluewifi.nicaragua.Singletoon.StorageSingleton;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        startService(new Intent(getBaseContext(), MyService.class));
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            SplashScreenActivity.this.startActivity(new Intent(SplashScreenActivity.this, SSIDConnectionActivity.class));
        }, 3000);
    }

    @Override
    protected void onDestroy() {
        String ssid = StorageSingleton.getInstance().getSsid();
        ConnectionSSID connectionSSID = new ConnectionSSID(this, ssid, "");
        connectionSSID.tryReconnect();
        super.onDestroy();
    }
}
