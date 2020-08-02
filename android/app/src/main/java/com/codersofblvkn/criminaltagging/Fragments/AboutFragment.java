package com.codersofblvkn.criminaltagging.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.codersofblvkn.criminaltagging.R;


public class AboutFragment extends Fragment {

    public AboutFragment() {
        // Required empty public constructor
    }

// --Commented out by Inspection START (01-07-2020 09:41 PM):
//    public static AboutFragment newInstance() {
//        AboutFragment fragment = new AboutFragment();
//        Bundle args = new Bundle();
//        return fragment;
//    }
// --Commented out by Inspection STOP (01-07-2020 09:41 PM)

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);
    }
}
