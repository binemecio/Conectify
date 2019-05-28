package com.example.binemecio.conectify;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.binemecio.conectify.Helper.ConnectionSSID;
import com.example.binemecio.conectify.Service.MyService;
import com.example.binemecio.conectify.Singletoon.StorageSingleton;
import com.example.binemecio.conectify.Test.TestActivity;

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
//        handler.postDelayed(() -> {
//            SplashScreenActivity.this.startActivity(new Intent(SplashScreenActivity.this, TestActivity.class));
//        }, 3000);
    }

    @Override
    protected void onDestroy() {
        String ssid = StorageSingleton.getInstance().getSsid();
        ConnectionSSID connectionSSID = new ConnectionSSID(this, ssid, "");
        connectionSSID.tryReconnect();
        super.onDestroy();
    }
}
