package com.gp_android_2019.bench;


import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.gp_android_2019.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SetFragment extends Fragment {

    View view;

    public SetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_set,null);

        return view;
    }

    public void makeParameter() {
        EditText path_ed = (EditText)view.findViewById(R.id.ed_path);
        BenchActivity.path = path_ed.getText().toString();

        EditText bs_ed = (EditText)view.findViewById(R.id.ed_bs);
        Spinner bs_spin = (Spinner)view.findViewById(R.id.spin_unit_bs);
        BenchActivity.bs = bs_ed.getText().toString() + bs_spin.getSelectedItem().toString();

        EditText io_size_ed = (EditText)view.findViewById(R.id.ed_io_size);
        Spinner io_size_spin = (Spinner)view.findViewById(R.id.spin_unit_io_size);
        BenchActivity.io_size = io_size_ed.getText().toString() + io_size_spin.getSelectedItem().toString();

        System.out.println(BenchActivity.io_size);

        EditText thr_ed = (EditText)view.findViewById(R.id.ed_thread);
        BenchActivity.num_jobs = thr_ed.getText().toString();

        EditText run_ed = (EditText)view.findViewById(R.id.ed_run);
        BenchActivity.runtime = run_ed.getText().toString();

        Spinner direct_spin = (Spinner)view.findViewById(R.id.spin_direct);
        BenchActivity.direct = String.valueOf(direct_spin.getSelectedItemPosition());

        EditText trans_ed = (EditText)view.findViewById(R.id.ed_trans);
        BenchActivity.numOfTrans = Integer.valueOf(trans_ed.getText().toString());
    }
}
