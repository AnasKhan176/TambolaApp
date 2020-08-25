package com.uae.tambolaapp;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.barteksc.pdfviewer.PDFView;

import androidx.annotation.RequiresApi;

public class PrivacyTermActivity extends BaseActivity {

    private WebView webView;
    private String base_url = "https://avsstech.com/RejectionTask";
    private String rules_url = "home.php", winning_url = "home.php", privacy_url = "home.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_web);

        if (getIntent() != null) {
            int val = getIntent().getIntExtra("url_type", 0);
            if (val == 0) {
                base_url = base_url + "/" + rules_url;
                setActivityTitle("Game Rules");
            } else if (val == 1) {
                base_url = base_url + "/" + winning_url;
                setActivityTitle("Winning Patterns");
            } else if (val == 2) {
                setActivityTitle("Privacy Policy");
                base_url = base_url + "/" + privacy_url;
            }
        }
        webView = (WebView) findViewById(R.id.webView);

        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.setWebViewClient(new PrivacyTermActivity.WebViewClient());
                webView.getSettings().setSupportZoom(true);
//                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl(base_url);
            }
        });
    }

    private class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}