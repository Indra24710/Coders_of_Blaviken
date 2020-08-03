package com.codersofblvkn.criminaltagging.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.codersofblvkn.criminaltagging.R;

import java.util.Locale;

public class CrewActivity extends AppCompatActivity {
    WebView webView;

    String language;
    private  Locale locale;
    private ProgressDialog progDailog;
    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sp=getSharedPreferences("mycredentials", Context.MODE_PRIVATE);
        language=sp.getString("language","en");
        locale = new Locale(language);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crew);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        progDailog = ProgressDialog.show(CrewActivity.this, getString(R.string.showDevs),getString(R.string.loading_text), true);
        progDailog.setCancelable(false);
        webView=findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                progDailog.show();
                view.loadUrl("https://jatayu.org/about/");
                return true;
            }
            @Override
            public void onPageFinished(WebView view, final String url) {
                progDailog.dismiss();
            }
        });

        webView.loadUrl("https://jatayu.org/about/");
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        Configuration config = getBaseContext().getResources().getConfiguration();
        // refresh your views here
        Locale.setDefault(locale);
        config.locale = locale;
        super.onConfigurationChanged(newConfig);

    }
}