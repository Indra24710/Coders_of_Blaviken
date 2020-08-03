package com.codersofblvkn.criminaltagging.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.codersofblvkn.criminaltagging.R;
import com.codersofblvkn.criminaltagging.Utils.InternetConnection;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MapsFragment extends Fragment {

    private final OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @SuppressLint("CheckResult")
        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng cam = new LatLng(20.5937, 78.9629);
            if(InternetConnection.checkConnection(Objects.requireNonNull(getActivity()).getApplicationContext()))
            {
                Observable.fromCallable(() -> {
                    Request request = new Request.Builder()
                            .url("http://coders-of-blaviken-api.herokuapp.com/api/detections")
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
                                List<Double> latList=new ArrayList<Double>();
                                List<Double> lonList=new ArrayList<Double>();

                                JSONObject jsonObject=new JSONObject(result);
                                JSONArray jsonArray=jsonObject.getJSONArray("detections");
                                double lat,lon;
                                for(int i=0;i<jsonArray.length();i++)
                                {
                                    JSONObject tDetect=jsonArray.getJSONObject(i);
                                    String[] location =tDetect.getString("location").replace("dot",".").split(",");
                                    if(location.length!=2)
                                    {
                                        lat=11;
                                        lon=77;
                                    }
                                    else {
                                        lat=Double.parseDouble(location[0].substring(0,7));
                                        lon=Double.parseDouble(location[1].substring(1,8));
                                    }
                                    lonList.add(lon);
                                    latList.add(lat);
                                    googleMap.addMarker(new MarkerOptions().position(new LatLng(lat,lon)).title(getString(R.string.criminalid)+tDetect.getString("id")));

                                }
                            }
                        });
            }
            else
            {
                Toast.makeText(getActivity().getApplicationContext(),getString(R.string.no_internet),Toast.LENGTH_SHORT).show();
            }

            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cam, 5.0f));
            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5.0f));
                }
            });

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
                    if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(mapIntent);
                    }
                    else
                    {
                        Toast.makeText(getActivity().getApplicationContext(),getString(R.string.mapnotinstalled),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    };



    public MapsFragment() {
        // Required empty public constructor
    }

    public static MapsFragment newInstance() {
        MapsFragment fragment = new MapsFragment();
        Bundle args = new Bundle();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

}