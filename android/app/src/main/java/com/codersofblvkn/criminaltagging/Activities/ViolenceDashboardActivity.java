package com.codersofblvkn.criminaltagging.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.codersofblvkn.criminaltagging.Adapters.OnViolenceClickListener;
import com.codersofblvkn.criminaltagging.Adapters.ViolenceAdapter;
import com.codersofblvkn.criminaltagging.R;
import com.codersofblvkn.criminaltagging.Utils.InternetConnection;
import com.codersofblvkn.criminaltagging.Utils.Violence;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class ViolenceDashboardActivity extends AppCompatActivity {


    SwipeRefreshLayout swipeviolence;
    EditText violenceid;
    TextView notext;
    RecyclerView recyclerView;
    ViolenceAdapter violenceAdapter,editViolenceAdapter;

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
        setContentView(R.layout.activity_violence_dashboard2);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        swipeviolence=findViewById(R.id.swipeviolence);
        violenceid=findViewById(R.id.vid_ed);

        swipeviolence.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                networkCall();
            }
        });
        networkCall();
    }


    @SuppressLint("CheckResult")
    public void networkCall()
    {
        if(InternetConnection.checkConnection(Objects.requireNonNull(getApplicationContext())))
        {
            recyclerView=findViewById(R.id.recyclerView);
            notext=findViewById(R.id.nodata);
            swipeviolence.setRefreshing(true);
            Observable.fromCallable(() -> {
                Request request = new Request.Builder()
                        .url("https://coders-of-blaviken-api.herokuapp.com/api/violence")
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
                        return null;
                    }
                } catch (IOException e) {
                    Log.e("Network request", "Failure", e);
                }

                return null;
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((result) -> {

                        if(result!=null)
                        {
                            recyclerView.setVisibility(View.VISIBLE);
                            notext.setVisibility(View.GONE);
                            List<Violence> violences=new ArrayList<Violence>();
                            JSONObject jsonObject=new JSONObject(result);
                            JSONArray jsonArray=jsonObject.getJSONArray("detections");
//                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                double lat=0,lon=0;
                                JSONObject tDetect=jsonArray.getJSONObject(i);
                                int vid=tDetect.getInt("id");
                                String[] location =tDetect.getString("location").split("Lats");
                                if(location.length!=2)
                                {
                                    lat=0;
                                    lon=0;
                                }
                                else {
                                    lat=Double.parseDouble(location[0]);
                                    lon=Double.parseDouble(location[1]);
                                }
                                Log.d("Detections",lat+" "+lon+" "+i);
                                String video=tDetect.getString("rsrc");
                                Boolean valid=tDetect.getBoolean("valid");
                                String myDate = tDetect.getString("time_stamp");
                                Violence violence=new Violence(lat,lon,vid,myDate,valid,video);
                                violences.add(violence);
                            }
                            Collections.sort(violences, new Comparator<Violence>() {
                                @Override
                                public int compare(Violence t1, Violence t2) {
                                    return t1.getId()-t2.getId();
                                }
                            });
                            violenceAdapter =new ViolenceAdapter(violences, new OnViolenceClickListener() {
                                @Override
                                public void onItemClick(Violence item) {
                                    Intent intent=new Intent(ViolenceDashboardActivity.this, ViewViolenceActivity.class);
                                    intent.putExtra("violence",item);
                                    startActivity(intent);
                                }
                            });
                            recyclerView.setAdapter(violenceAdapter);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setItemAnimator(new SlideInUpAnimator());
                            Log.d("Detections",jsonArray.length()+" ");

                            violenceid.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                    if(!charSequence.toString().equals(""))
                                    {
                                        List<Violence> detections_new=new ArrayList<Violence>();
                                        for (Violence det:violences)
                                        {
                                            if(det.getId()==Integer.parseInt(charSequence.toString()))
                                            {
                                                detections_new.add(det);
                                            }
                                        }
                                        if(detections_new.size()>0)
                                        {
                                            editViolenceAdapter=new ViolenceAdapter(detections_new, new OnViolenceClickListener() {
                                                @Override
                                                public void onItemClick(Violence item) {
                                                    Intent intent=new Intent(ViolenceDashboardActivity.this, ViewViolenceActivity.class);
                                                    intent.putExtra("violence",item);
                                                    startActivity(intent);
                                                }
                                            });
                                            recyclerView.setVisibility(View.VISIBLE);
                                            notext.setVisibility(View.GONE);
                                            editViolenceAdapter.notifyDataSetChanged();
                                            recyclerView.setAdapter(editViolenceAdapter);
                                        }
                                        else
                                        {
                                            recyclerView.setAdapter(null);
                                            recyclerView.setVisibility(View.GONE);
                                            notext.setVisibility(View.VISIBLE);
                                        }

                                    }
                                    else
                                    {
                                        recyclerView.setVisibility(View.VISIBLE);
                                        notext.setVisibility(View.GONE);
                                        violenceAdapter.notifyDataSetChanged();
                                        recyclerView.setAdapter(violenceAdapter);
                                    }



                                }

                                @Override
                                public void afterTextChanged(Editable editable) {

                                }
                            });
                        }
                        swipeviolence.setRefreshing(false);
//                    DetectionsAdapter detectionsAdapter=new DetectionsAdapter();

                    });
        }
        else {
            Toast.makeText(getApplicationContext(),getString(R.string.no_internet),Toast.LENGTH_SHORT).show();
            swipeviolence.setRefreshing(false);
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
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