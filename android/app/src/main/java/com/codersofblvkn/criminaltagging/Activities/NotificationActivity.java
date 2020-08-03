package com.codersofblvkn.criminaltagging.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.codersofblvkn.criminaltagging.R;
import com.codersofblvkn.criminaltagging.Utils.Detection;
import com.codersofblvkn.criminaltagging.Utils.InternetConnection;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NotificationActivity extends AppCompatActivity implements Serializable {

    CircleImageView imgView;
    TextView cid,time,coordinates,did,name,gender;
    MapView mapView;
    ProgressBar seekBar;
    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    final List<Detection> detections=new ArrayList<Detection>();
    String name_,gender_,picturePath;
    int severity;
    int cidCheck;
    String language;
    private Locale locale;
    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
        SharedPreferences sp=getSharedPreferences("mycredentials", Context.MODE_PRIVATE);
        language=sp.getString("language","en");
        locale = new Locale(language);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imgView=findViewById(R.id.profile_image);
        cid=findViewById(R.id.cid);
        time=findViewById(R.id.time);
        coordinates=findViewById(R.id.coordinates);
        mapView=findViewById(R.id.mapView);
        name=findViewById(R.id.name);
        gender=findViewById(R.id.gender);
        did=findViewById(R.id.did);
        did.setVisibility(View.GONE);
        seekBar=findViewById(R.id.severity);
        mapView.onCreate(savedInstanceState);

        Intent notIntent=getIntent();
        cidCheck=Integer.parseInt(notIntent.getStringExtra("cid").split(":")[1]);
        networkcallnotification(cidCheck);
    }


    @SuppressLint("CheckResult")
    public void networkcallnotification(int cidCheck)
    {
        if(InternetConnection.checkConnection(getApplicationContext()))
        {
            Observable.fromCallable(() -> {

                Map<Integer,String> map=new HashMap<Integer,String>();
                @SuppressLint("CheckResult") Request request1 = new Request.Builder()
                        .url("http://coders-of-blaviken-api.herokuapp.com/api/detections")
                        .build();
                @SuppressLint("CheckResult") Request request2 = new Request.Builder()
                        .url("http://coders-of-blaviken-api.herokuapp.com/api/criminals/"+cidCheck)
                        .build();
                try {
                    OkHttpClient sHttpClient=new OkHttpClient();
                    Response response = sHttpClient.newCall(request1).execute();
                    if(response.isSuccessful())
                    {
                        map.put(1,response.body().string());
                    }
                    else
                    {
                        Log.e("FailureCheck", "Failure");
                        map.put(1,null);
                    }
                } catch (IOException e) {
                    map.put(1,null);
                    Log.e("Network request", "Failure", e);
                }

                try {
                    OkHttpClient sHttpClient=new OkHttpClient();
                    Response response = sHttpClient.newCall(request2).execute();
                    if(response.isSuccessful())
                    {
                        map.put(2,response.body().string());

                    }
                    else
                    {
                        Log.e("FailureCheck", "Failure");
                        map.put(2,null);
                    }
                } catch (IOException e) {
                    map.put(2,null);
                    Log.e("Network request", "Failure", e);
                }
                return map;
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((result) -> {

                        if(result.get(1)!=null&&result.get(2)!=null)
                        {

                            String result1=result.get(1);
                            String result2=result.get(2);
                            JSONObject jsonObject=new JSONObject(result1);
                            JSONArray jsonArray=jsonObject.getJSONArray("detections");
                            JSONObject result2Object=new JSONObject(result2).getJSONArray("criminals").getJSONObject(0);
                            name_=getString(R.string.name)+" "+result2Object.getString("name");
                            gender_=getString(R.string.gender)+" "+result2Object.getString("gender");
                            severity=result2Object.getInt("severity");
                            picturePath=result2Object.getString("picture");
                            seekBar.setMax(100);
                            seekBar.setProgress(severity);
                            name.setText(name_);
                            gender.setText(gender_);
                            seekBar.setEnabled(false);
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                double lat=0,lon=0;
                                JSONObject tDetect=jsonArray.getJSONObject(i);
                                int id=tDetect.getInt("id");
                                int cid_=tDetect.getInt("cid");
                                if(cid_==cidCheck)
                                {
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
                                    String img=tDetect.getString("rsrc");
                                    String myDate = tDetect.getString("time_stamp");
                                    Date date = sdf.parse(myDate);
                                    long time=date.getTime();
                                    Detection detection_=new Detection(lat,lon,time,img,id,cid_);
                                    detections.add(detection_);
                                }

                            }
                            Collections.sort(detections, new Comparator<Detection>() {
                                @Override
                                public int compare(Detection t1, Detection t2) {
                                    return (int)(t2.getTimestamp()-t1.getTimestamp());
                                }
                            });
                            if(detections.size()>=1)
                            {
                                CircularProgressDrawable circularProgressDrawable=new CircularProgressDrawable(NotificationActivity.this);
                                circularProgressDrawable.setStrokeWidth(5);
                                circularProgressDrawable.setCenterRadius(30);
                                circularProgressDrawable.start();
                                RequestOptions options = new RequestOptions()
                                        .centerCrop()
                                        .placeholder(circularProgressDrawable)
                                        .error(R.mipmap.ic_launcher_round);
                                Glide.with(NotificationActivity.this).load(picturePath).apply(options).into(imgView);
                                String tempCid=getString(R.string.criminalid)+" "+detections.get(0).getCid();
                                cid.setText(tempCid);
                                String tempLatestLoc=getString(R.string.latestloc)+detections.get(0).getLatitude()+ (char)0x00B0 + "N ,"+detections.get(0).getLongitude()+ (char)0x00B0 +"E";
                                coordinates.setText(tempLatestLoc);

                                Date ld=new Date(detections.get(0).getTimestamp());
                                String ldText=getString(R.string.lastDetected)+sdf.format(ld);
                                time.setText(ldText);

                                mapView.getMapAsync(new OnMapReadyCallback() {
                                    @Override
                                    public void onMapReady(GoogleMap googleMap) {

                                        for(Detection i:detections)
                                        {
                                            Date date=new Date(i.getTimestamp());
                                            String dateText=getString(R.string.timestamp)+sdf.format(date);
                                            googleMap.addMarker(new MarkerOptions()
                                                    .position(new LatLng(i.getLatitude(),i.getLongitude()))
                                                    .title(dateText));
                                        }

                                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(detections.get(0).getLatitude(),detections.get(0).getLongitude()), 5.0f));

                                        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                            @Override
                                            public boolean onMarkerClick(Marker marker) {
                                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15.0f));
                                                marker.showInfoWindow();
                                                return true;
                                            }
                                        });

                                        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                            @Override
                                            public void onInfoWindowClick(Marker marker) {
                                                double lat=marker.getPosition().latitude;
                                                double lon=marker.getPosition().longitude;
                                                String toParse="geo:"+lat+","+lon;
                                                Uri gmmIntentUri = Uri.parse(toParse);
                                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                                mapIntent.setPackage("com.google.android.apps.maps");
                                                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                                                    startActivity(mapIntent);
                                                }
                                                else
                                                {
                                                    Toast.makeText(getApplicationContext(),getString(R.string.mapnotinstalled),Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                    }
                                });
                            }
                            else
                            {
                                Intent redirectIntent=new Intent(NotificationActivity.this,MainActivity.class);
                                redirectIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(redirectIntent);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.ic_home)
        {
            Intent intent=new Intent(NotificationActivity.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.ic_reload)
        {
            networkcallnotification(cidCheck);
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
