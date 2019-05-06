package com.example.binemecio.conectify;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.binemecio.conectify.Helper.Helpers;
import com.example.binemecio.conectify.Singletoon.StorageSingleton;

public class AdActivity extends AppCompatActivity {
    private static String defaultUrl = "https://www.google.com";
    private Helpers helper = new Helpers();
    private WebView webView;
    private String url;
    final Handler handler = new Handler();
    // convert to miliseconds: 3 s == 3000
    private long time = 150000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        this.url = getIntent().getStringExtra("url");
        this.url = helper.isNullOrWhiteSpace(this.url) ? defaultUrl : this.url;
        this.time = getIntent().getLongExtra("time", 15000);

        this.initialize();
        // here is the initialize the ad
        this.initSetupAdd();
        //StorageSingleton.getInstance().getEnterPrise()
    }

    private void initialize()
    {
        this.webView = findViewById(R.id.webview);
    }

    private void initSetupAdd()
    {
        this.showAdInWebView();
        handler.postDelayed(() -> {
            AdActivity.this.closeActivity();
        }, time);
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
}
