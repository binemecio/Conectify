package com.example.binemecio.conectify;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.binemecio.conectify.Helper.ConnectionSSID;
import com.example.binemecio.conectify.Helper.HelperAd;
import com.example.binemecio.conectify.Singletoon.StorageSingleton;

public class DashBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        this.runOnUiThread(() -> {

            this.minimize();
            HelperAd helperAd = new HelperAd(DashBoardActivity.this);
            helperAd.startEngine();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 0)
        {
            this.finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(1);
    }

    //    @Override
//    protected void onPause() {
//        super.onPause();
//        boolean isFinishActivity = StorageSingleton.getInstance().isFinish();
//
//        if (isFinishing() ) {
//            String ssid = StorageSingleton.getInstance().getSsid();
//            ConnectionSSID connectionSSID = new ConnectionSSID(this);
//            connectionSSID.setNetworkSSID(ssid);
//            connectionSSID.disconnectCurrentNetwork();
//            connectionSSID.tryReconnect();
//        }
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (isFinishing()) {
//            StorageSingleton.getInstance().setFinish(true);
//            String ssid = StorageSingleton.getInstance().getSsid();
//            ConnectionSSID connectionSSID = new ConnectionSSID(this);
//            connectionSSID.setNetworkSSID(ssid);
//            connectionSSID.disconnectCurrentNetwork();
//            connectionSSID.tryReconnect();
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        String ssid = StorageSingleton.getInstance().getSsid();
//        ConnectionSSID connectionSSID = new ConnectionSSID(this);
//        connectionSSID.setNetworkSSID(ssid);
//        connectionSSID.disconnectCurrentNetwork();
//        connectionSSID.tryReconnect();
//    }
//
//    @Override
//    public void finish() {
//        super.finish();
//        String ssid = StorageSingleton.getInstance().getSsid();
//        ConnectionSSID connectionSSID = new ConnectionSSID(this);
//        connectionSSID.setNetworkSSID(ssid);
//        connectionSSID.disconnectCurrentNetwork();
//        connectionSSID.tryReconnect();
//    }

    public void minimize() {
        this.moveTaskToBack(true);
    }
}
