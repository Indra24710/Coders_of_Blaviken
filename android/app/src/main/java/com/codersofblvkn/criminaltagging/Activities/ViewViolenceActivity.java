package com.codersofblvkn.criminaltagging.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.codersofblvkn.criminaltagging.R;
import com.codersofblvkn.criminaltagging.Utils.Violence;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

public class ViewViolenceActivity extends AppCompatActivity {
    TextView textView,textView4;
    MapView mapView;
    Button button;

    String language;
    private  Locale locale;

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sp=getSharedPreferences("mycredentials", Context.MODE_PRIVATE);
        language=sp.getString("language","en");
        locale = new Locale(language);
        mapView.onStart();
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
        setContentView(R.layout.activity_view_violence);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        textView=findViewById(R.id.textView);
        textView4=findViewById(R.id.textView4);
        Intent intent=getIntent();
        Violence violence= (Violence) intent.getSerializableExtra("violence");
        String url=violence.getVideoPath();
        mapView=findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        String ids=getString(R.string.violenceid)+violence.getId()+" ";
        textView.setText(ids);
        String temptime=getString(R.string.timestamp)+violence.getTimestamp().substring(0,20);
        textView4.setText(temptime);
        button=findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ViewViolenceActivity.this,VideoActivity.class);
                intent.putExtra("video",violence.getVideoPath());
                startActivity(intent);
            }
        });

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                LatLng coord=new LatLng(violence.getLatitude(),violence.getLongitude());
                googleMap.addMarker(new MarkerOptions().position(coord).title("Violence Detected Area"));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coord, 5.0f));

                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15.0f));
                        marker.showInfoWindow();
                        return true;
                    }
                });
            }
        });




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