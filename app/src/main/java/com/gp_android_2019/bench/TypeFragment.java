package com.gp_android_2019.bench;


import android.content.Context;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.gp_android_2019.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TypeFragment extends Fragment {
    private View view;

    public TypeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_type,null);

        Button rw_btn = (Button)view.findViewById(R.id.btn_rw);
        Button db_btn = (Button)view.findViewById(R.id.btn_db);
        Button all_btn = (Button)view.findViewById(R.id.btn_all);

        rw_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkRW();
            }
        });
        db_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDB();
            }
        });
        all_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkALL();
            }
        });

        return view;
    }

    private void checkRW() {
        ArrayList<CheckBox> boxes = new ArrayList<>();
        boxes.add((CheckBox)view.findViewById(R.id.chk_seq_read));
        boxes.add((CheckBox)view.findViewById(R.id.chk_seq_write));
        boxes.add((CheckBox)view.findViewById(R.id.chk_ran_read));
        boxes.add((CheckBox)view.findViewById(R.id.chk_ran_write));

        int cnt_CheckBox = boxes.size();
        boolean rwChk = true;
        for (int i=0; i < cnt_CheckBox; i++) {
            if (!boxes.get(i).isChecked()) {
                rwChk = false;
                break;
            }
        }

        if (rwChk) {
            for (int i=0; i< cnt_CheckBox; i++) {
                boxes.get(i).setChecked(false);
            }
        }
        else {
            for (int i=0; i< cnt_CheckBox; i++) {
                boxes.get(i).setChecked(true);
            }
        }
    }

    private void checkDB() {
        ArrayList<CheckBox> boxes = new ArrayList<>();
        boxes.add((CheckBox)view.findViewById(R.id.chk_insert));
        boxes.add((CheckBox)view.findViewById(R.id.chk_update));
        boxes.add((CheckBox)view.findViewById(R.id.chk_delete));

        int cnt_CheckBox = boxes.size();
        boolean dbChk = true;
        for (int i=0; i < cnt_CheckBox; i++) {
            if (!boxes.get(i).isChecked()) {
                dbChk = false;
                break;
            }
        }

        if (dbChk) {
            for (int i=0; i< cnt_CheckBox; i++) {
                boxes.get(i).setChecked(false);
            }
        }
        else {
            for (int i=0; i< cnt_CheckBox; i++) {
                boxes.get(i).setChecked(true);
            }
        }
    }

    private void checkALL() {
        ArrayList<CheckBox> boxes = new ArrayList<>();
        boxes.add((CheckBox)view.findViewById(R.id.chk_seq_read));
        boxes.add((CheckBox)view.findViewById(R.id.chk_seq_write));
        boxes.add((CheckBox)view.findViewById(R.id.chk_ran_read));
        boxes.add((CheckBox)view.findViewById(R.id.chk_ran_write));
        boxes.add((CheckBox)view.findViewById(R.id.chk_insert));
        boxes.add((CheckBox)view.findViewById(R.id.chk_update));
        boxes.add((CheckBox)view.findViewById(R.id.chk_delete));

        int cnt_CheckBox = boxes.size();
        boolean allChk = true;
        for (int i=0; i < cnt_CheckBox; i++) {
            if (!boxes.get(i).isChecked()) {
                allChk = false;
                break;
            }
        }

        if (allChk) {
            for (int i=0; i< cnt_CheckBox; i++) {
                boxes.get(i).setChecked(false);
            }
        }
        else {
            for (int i=0; i< cnt_CheckBox; i++) {
                boxes.get(i).setChecked(true);
            }
        }
    }

    public void CheckedBoxes() {
        ArrayList<CheckBox> boxes = new ArrayList<>();

        boxes.add((CheckBox)view.findViewById(R.id.chk_seq_read));
        boxes.add((CheckBox)view.findViewById(R.id.chk_seq_write));
        boxes.add((CheckBox)view.findViewById(R.id.chk_ran_read));
        boxes.add((CheckBox)view.findViewById(R.id.chk_ran_write));

        int cnt_CheckBox = boxes.size();
        BenchActivity.rwCheck = new boolean[cnt_CheckBox];
        for (int i=0; i < BenchActivity.rwCheck.length; i++) {
            if (boxes.get(i).isChecked()) {
                BenchActivity.rwCheck[i] = true;
                BenchActivity.isEmpty = false;
            }
            else {
                BenchActivity.rwCheck[i] = false;
            }
        }

        boxes = new ArrayList<>();

        boxes.add((CheckBox)view.findViewById(R.id.chk_insert));
        boxes.add((CheckBox)view.findViewById(R.id.chk_update));
        boxes.add((CheckBox)view.findViewById(R.id.chk_delete));

        cnt_CheckBox = boxes.size();
        BenchActivity.dbCheck = new boolean[cnt_CheckBox];
        for (int i=0; i < BenchActivity.dbCheck.length; i++) {
            if (boxes.get(i).isChecked()) {
                BenchActivity.dbCheck[i] = true;
                BenchActivity.isEmpty = false;
            }
            else {
                BenchActivity.dbCheck[i] = false;
            }
        }
    }
}
