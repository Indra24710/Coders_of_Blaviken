package com.codersofblvkn.criminaltagging.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.codersofblvkn.criminaltagging.Activities.ProfileActivity;
import com.codersofblvkn.criminaltagging.Adapters.DetectionsAdapter;
import com.codersofblvkn.criminaltagging.Adapters.OnItemClickListener;
import com.codersofblvkn.criminaltagging.R;
import com.codersofblvkn.criminaltagging.Utils.Detection;
import com.codersofblvkn.criminaltagging.Utils.InternetConnection;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManualEntryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManualEntryFragment extends Fragment implements OnItemClickListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    DetectionsAdapter detectionsAdapter,editDetectionAdapter;
    SwipeRefreshLayout srl;
    TextView notext;
    EditText cid_ed;
    public ManualEntryFragment() {
        // Required empty public constructor
    }


    public static ManualEntryFragment newInstance(String param1, String param2) {
        ManualEntryFragment fragment = new ManualEntryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("CheckResult")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manual_entry, container, false);


        cid_ed=view.findViewById(R.id.cid_ed);

        notext=view.findViewById(R.id.nodata);

        srl=view.findViewById(R.id.srl);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                    networkCall(view);
            }
        });

        networkCall(view);

        return view;
    }

    @Override
    public void onItemClick(Detection item) {
//        Toast.makeText(getContext(),"Hello",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(getActivity(), ProfileActivity.class);
        intent.putExtra("detection",item);
        startActivity(intent);
    }

    @SuppressLint("CheckResult")
    public void networkCall(View view)
    {
        if(InternetConnection.checkConnection(Objects.requireNonNull(getActivity()).getApplicationContext()))
        {
            RecyclerView rv=view.findViewById(R.id.rv);
            srl.setRefreshing(true);
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
                            rv.setVisibility(View.VISIBLE);
                            notext.setVisibility(View.GONE);
                            List<Detection> detections=new ArrayList<Detection>();
                            JSONObject jsonObject=new JSONObject(result);
                            JSONArray jsonArray=jsonObject.getJSONArray("detections");
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                double lat=0,lon=0;
                                JSONObject tDetect=jsonArray.getJSONObject(i);
                                int id=tDetect.getInt("id");
                                int cid=tDetect.getInt("cid");
                                String[] location =tDetect.getString("location").replace("dot",".").split(",");
                                if(location.length!=2)
                                {
                                    lat=0;
                                    lon=0;
                                }
                                else {
                                    lat=Double.parseDouble(location[0].substring(0,7));
                                    lon=Double.parseDouble(location[1].substring(1,8));
                                }
                                Log.d("Detections",lat+" "+lon+" "+i);
//                            double lat=Double.parseDouble(location[0]);
//                            double lon=Double.parseDouble(location[1]);
                                String img=tDetect.getString("rsrc");
                                String myDate = tDetect.getString("time_stamp");
                                Date date = sdf.parse(myDate);
                                long time=date.getTime();
                                Detection detection=new Detection(lat,lon,time,img,id,cid);
                                detections.add(detection);
                            }
                            Collections.sort(detections, new Comparator<Detection>() {
                                @Override
                                public int compare(Detection t1, Detection t2) {
                                    return t1.getId()-t2.getId();
                                }
                            });
                            detectionsAdapter =new DetectionsAdapter(detections, this);
                            rv.setAdapter(detectionsAdapter);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                            rv.setLayoutManager(layoutManager);
                            rv.setItemAnimator(new SlideInUpAnimator());
                            Log.d("Detections",jsonArray.length()+" ");

                            cid_ed.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                    if(!charSequence.toString().equals(""))
                                    {
                                        List<Detection> detections_new=new ArrayList<Detection>();
                                        for (Detection det:detections)
                                        {
                                            if(det.getCid()==Integer.parseInt(charSequence.toString()))
                                            {
                                                detections_new.add(det);
                                            }
                                        }
                                        if(detections_new.size()>0)
                                        {
                                            editDetectionAdapter=new DetectionsAdapter(detections_new, new OnItemClickListener() {
                                                @Override
                                                public void onItemClick(Detection item) {
                                                    Intent intent=new Intent(getActivity(), ProfileActivity.class);
                                                    intent.putExtra("detection",item);
                                                    startActivity(intent);
                                                }
                                            });
                                            rv.setVisibility(View.VISIBLE);
                                            notext.setVisibility(View.GONE);
                                            editDetectionAdapter.notifyDataSetChanged();
                                            rv.setAdapter(editDetectionAdapter);
                                        }
                                        else
                                        {
                                            rv.setAdapter(null);
                                            rv.setVisibility(View.GONE);
                                            notext.setVisibility(View.VISIBLE);
                                        }

                                    }
                                    else
                                    {
                                        rv.setVisibility(View.VISIBLE);
                                        notext.setVisibility(View.GONE);
                                        detectionsAdapter.notifyDataSetChanged();
                                        rv.setAdapter(detectionsAdapter);
                                    }



                                }

                                @Override
                                public void afterTextChanged(Editable editable) {

                                }
                            });
                        }
                        srl.setRefreshing(false);
//                    DetectionsAdapter detectionsAdapter=new DetectionsAdapter();

                    });
        }
        else {
            Toast.makeText(getActivity().getApplicationContext(),getString(R.string.no_internet),Toast.LENGTH_SHORT).show();
            srl.setRefreshing(false);
        }

    }

}
