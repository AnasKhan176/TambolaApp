package com.uae.tambolaapp;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.barteksc.pdfviewer.PDFView;

public class GameRuleActivity extends BaseActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_rule);
        setActivityTitle("Game rules");
/*
        webView = (WebView) findViewById(R.id.nyc_poi_webview);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptEnabled(true);
        String url = "https://github.github.com/training-kit/downloads/github-git-cheat-sheet.pdf";
        webView.loadUrl(url);*/
        PDFView pdfView = (PDFView) findViewById(R.id.pdfView);
        pdfView.fromAsset("rules.pdf").load();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}