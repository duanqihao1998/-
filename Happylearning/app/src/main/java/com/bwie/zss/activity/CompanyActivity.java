package com.bwie.zss.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.bwie.zss.R;

public class CompanyActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_company);
        webView = (WebView) findViewById(R.id.webview_web);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        selectData(url);

    }

    private void selectData(String url) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setJavaScriptEnabled(true);//支持js
        webView.loadUrl(url);
    }
}
