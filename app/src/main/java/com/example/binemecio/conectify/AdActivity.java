package com.example.binemecio.conectify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.binemecio.conectify.Helper.ConnectionSSID;
import com.example.binemecio.conectify.Helper.DesignHelper;
import com.example.binemecio.conectify.Helper.HelperAd;
import com.example.binemecio.conectify.Helper.Helpers;
import com.example.binemecio.conectify.Helper.NetworkUtil;
import com.example.binemecio.conectify.Pojo.Ad;
import com.example.binemecio.conectify.Pojo.EnterPrise;
import com.example.binemecio.conectify.Singletoon.StorageSingleton;

import java.util.List;

public class AdActivity extends AppCompatActivity  implements View.OnClickListener {
    private static String defaultUrl = "https://www.google.com";
    private Helpers helper = new Helpers();
    private WebView webView;
    private TextView buttonCloseAd;
    private String url;
    final Handler handler = new Handler();
    // convert to miliseconds: 3 s == 3000
    private long time = 50000;
    private EnterPrise enterPrise;
    private int seconds = 0;
    private boolean isReady = false;
    private boolean hasClosed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_ad);
        this.url = getIntent().getStringExtra("url");
        this.url = helper.isNullOrWhiteSpace(this.url) ? defaultUrl : this.url;
        this.time = getIntent().getLongExtra("time", 15000);
        this.enterPrise = StorageSingleton.getInstance().getEnterPrise();

        List<Ad> ad = this.enterPrise.getAdList();


        if(!ad.isEmpty())
        {
            int index = StorageSingleton.getInstance().getIndexAd();
            if (index >= ad.size())
            {
                index = 0;
                StorageSingleton.getInstance().setIndexAd(index);
            }
            this.url = ad.get(index).getDireccion_url();
            this.time = ad.get(index).getTiempo_ciclo();
            this.seconds = ad.get(index).getTiempo_ciclo().intValue();
            this.seconds = seconds / 1000;

            index++;
            StorageSingleton.getInstance().setIndexAd(index);
        }


        this.initialize();
        this.assigneEventClick();
        // here is the initialize the ad
        this.initSetupAdd();
        if (!StorageSingleton.getInstance().isContinueUse())
        {
            this.verifyConnection();
        }
        //StorageSingleton.getInstance().getEnterPrise()
    }

    private void verifyConnection()
    {
        String privateSSID = StorageSingleton.getInstance().getSsid2();
        if (!ConnectionSSID.isEqualToCurrentNetwork(this, helper.getString(privateSSID)))
        {
            DesignHelper.showSimpleDialog(this,"Atencion","La aplicación no esta conectada a una red o no esta conectada a la red esperada","Salir","",() -> {
                AdActivity.this.setResult(0);
                AdActivity.this.finish();
            }, () -> {
                AdActivity.this.setResult(0);
                AdActivity.this.finish();
            });
        }

    }

    private void initialize()
    {
        this.webView = findViewById(R.id.webview);
        this.buttonCloseAd = findViewById(R.id.btnClose);

    }

    private void assigneEventClick()
    {
        this.buttonCloseAd.setOnClickListener(this);
    }

    private void initSetupAdd()
    {
        this.showAdInWebView();

        handler.postDelayed(() -> {
            isReady = true;
        }, time + 1000);
        this.initCountDown();
    }


    private void initCountDown()
    {
        if (isReady)
        {
            this.buttonCloseAd.setText(" Close ");
            return;
        }

        Handler handlerCount = new Handler();
        handlerCount.postDelayed(() -> {
            this.buttonCloseAd.setText(seconds-- + " s");
            this.initCountDown();
        }, 1000);
    }

    private void showAdInWebView()
    {
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        webView.loadUrl(this.url);
    }

    @Override
    protected void onUserLeaveHint()
    {
        // When user presses home page
        closeActivity();
        super.onUserLeaveHint();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_HOME)
        {
            return  false;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            return false;
        }
        return true;
    }


    private void closeActivity()
    {
        if (!hasClosed)
        {
            hasClosed = true;
            HelperAd helperAd = new HelperAd(this);
            helperAd.startEngine();
        }
        this.minimize();
    }


    public void minimize() {
        this.moveTaskToBack(true);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnClose)
        {
            if (isReady)
                AdActivity.this.closeActivity();
        }
    }



}
