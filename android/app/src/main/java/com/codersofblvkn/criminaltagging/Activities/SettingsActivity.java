package com.codersofblvkn.criminaltagging.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.codersofblvkn.criminaltagging.R;
import com.codersofblvkn.criminaltagging.Utils.InternetConnection;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SettingsActivity extends AppCompatActivity {

    Button update,developers,change;
    ProgressBar progressBar;
    String currversion="1.0";

    String language;
    private  Locale locale;
    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sp=getSharedPreferences("mycredentials", Context.MODE_PRIVATE);
        language=sp.getString("language","en");
        locale = new Locale(language);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        update=findViewById(R.id.checkUpdate);
        developers=findViewById(R.id.showDevelopers);
        change=findViewById(R.id.changeLang);
        progressBar=findViewById(R.id.updateProgress);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        developers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SettingsActivity.this,CrewActivity.class);
                startActivity(intent);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                    currversion= pInfo.versionName;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
//                Toast.makeText(getApplicationContext(), currversion,Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.VISIBLE);
                networkCallProfile();


            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sp=getSharedPreferences("mycredentials", Context.MODE_PRIVATE);
                language=sp.getString("language","en");

                if(language.substring(0,2).equals("en"))
                {
                    locale = new Locale("hi");
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("language","hi");
                    edit.commit();
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                    refresh();
                }
                else
                {
                    locale = new Locale("en");
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("language","en");
                    edit.commit();
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                    refresh();
                }



            }
        });

    }

    private void refresh() {
        finish();
        Intent intent=new Intent(SettingsActivity.this,LaunchActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressLint("CheckResult")
    public void networkCallProfile()
    {
        if(InternetConnection.checkConnection(getApplicationContext()))
        {
            Observable.fromCallable(() -> {

                Map<Integer,String> map=new HashMap<Integer,String>();
                @SuppressLint("CheckResult") Request request = new Request.Builder()
                        .url("https://sih-version-control.herokuapp.com/")
                        .build();
                try {
                    OkHttpClient sHttpClient=new OkHttpClient();
                    Response response = sHttpClient.newCall(request).execute();
                    if(response.isSuccessful())
                    {
                        return response.body().string();
                    }
                    else
                    {
                        Log.e("FailureCheck", "Failure");
                        return "Error";
                    }
                } catch (IOException e) {
                    return "Error";
                }
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((result) -> {
                        progressBar.setVisibility(View.INVISIBLE);
                        if(result!=null)
                        {
                            if(result.equals("Error"))
                            {
                                Toast.makeText(getApplicationContext(), getString(R.string.error),Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                if(result.equals(currversion))
                                {
                                    Toast.makeText(getApplicationContext(), getString(R.string.noupdates),Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    AlertDialog.Builder builder=new AlertDialog.Builder(SettingsActivity.this);
                                    builder.setTitle(getString(R.string.update));
                                    builder.setMessage(getString(R.string.updateavailable));
                                    builder.setPositiveButton(getString(R.string.updateCAPS), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            String url = "https://drive.google.com/file/d/1jf2gF54voIMy7eVFV71OjMqzVqdjYPup/view?usp=sharing";
                                            Intent intent = new Intent(Intent.ACTION_VIEW);
                                            intent.setData(Uri.parse(url));
                                            startActivity(intent);
                                        }
                                    });
                                    builder.setNegativeButton(getString(R.string.nottoday), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                        }
                                    });
                                    AlertDialog dialog=builder.create();
                                    dialog.show();
                                }
                            }
                        }
                    });
        }
        else
        {
            Toast.makeText(getApplicationContext(),getString(R.string.no_internet),Toast.LENGTH_SHORT).show();
        }

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