package com.gp_android_2019.bench;


import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gp_android_2019.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BenchFragment extends Fragment {

    public BenchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bench,null);

        return view;
    }

}
