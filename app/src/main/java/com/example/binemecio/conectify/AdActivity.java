package com.example.binemecio.conectify;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.binemecio.conectify.Helper.Helpers;
import com.example.binemecio.conectify.Pojo.Ad;
import com.example.binemecio.conectify.Pojo.EnterPrise;
import com.example.binemecio.conectify.Singletoon.StorageSingleton;

import java.util.List;

public class AdActivity extends AppCompatActivity implements View.OnClickListener {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        //StorageSingleton.getInstance().getEnterPrise()
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
        }, time);
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
//
//    @Override
//    public void onAttachedToWindow() {
//        super.onAttachedToWindow();
//        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
//    }

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
        AdActivity.this.setResult(0);
        AdActivity.this.finish();
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
