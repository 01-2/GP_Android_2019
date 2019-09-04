package com.gp_android_2019.bench;

import android.os.Bundle;
import com.gp_android_2019.R;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class benchParamFragment extends Fragment {
    public benchParamFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.bench_param, container, false);
    }
}
