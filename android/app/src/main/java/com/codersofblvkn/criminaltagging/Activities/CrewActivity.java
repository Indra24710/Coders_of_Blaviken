package com.codersofblvkn.criminaltagging.Activities;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.codersofblvkn.criminaltagging.R;

public class CrewActivity extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crew);

//        webView=findViewById(R.id.webview);
//        webView.loadUrl("https://jatayu.org/about/");
    }
}