package com.example.binemecio.conectify.Helper;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;

import com.example.binemecio.conectify.AdActivity;
import com.example.binemecio.conectify.Singletoon.StorageSingleton;

/**
 * Created by binemecio on 4/5/2019.
 */

public class HelperAd{

    private Activity activity;
    final Handler handler = new Handler();
    private long loopAd = 3000;


    public HelperAd(Activity activity)
    {
        this.activity = activity;
    }


    public void startEngine()
    {
        this.loopAd = StorageSingleton.getInstance().getLoopTime();

        handler.postDelayed(() -> {
            this.activity.runOnUiThread(() -> {
                this.activity.startActivityForResult(new Intent(this.activity, AdActivity.class), 0);
            });
        }, this.loopAd);
    }
}
