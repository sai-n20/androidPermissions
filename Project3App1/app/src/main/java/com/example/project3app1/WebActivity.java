package com.example.project3app1;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webactivity);

        // TV show wikipedia link fetched from intent sent by app 3
        String url = getIntent().getStringExtra("tvURL");
        if (url == null) {
            finish();
        }

        // Webview will open in chrome by default, should remove defaults set from AVD for chrome for app to open in webview
        WebView tvWebView = findViewById(R.id.webview);
        tvWebView.loadUrl(url);
        tvWebView.getSettings().setJavaScriptEnabled(true);
        tvWebView.getSettings().setLoadsImagesAutomatically(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
