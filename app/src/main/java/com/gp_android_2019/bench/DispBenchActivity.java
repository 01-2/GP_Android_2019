package com.gp_android_2019.bench;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import com.gp_android_2019.R;

import android.widget.ListView;

import java.util.ArrayList;

public class DispBenchActivity extends AppCompatActivity {
    String[] ttl_rw = { "Sequence Read", "Sequence Write", "Random Read", "Random Write" };
    String[] ttl_db = { "INSERT", "UPDATE", "DELETE" };

    ArrayList<String> results_rw;
    ArrayList<String> results_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disp_bench);

        Intent intent = getIntent();
        ArrayList<String> results_json = intent.getStringArrayListExtra("results_rw");
        results_db = intent.getStringArrayListExtra("results_db");

        ParseResultBench parse = new ParseResultBench(results_json);
        results_rw = parse.getPar();

        DispListViewAdapter adapter = new DispListViewAdapter();
        ListView rw_list = (ListView) findViewById(R.id.rw_listView);
        rw_list.setAdapter(adapter);

        for (int i = 0; i < results_rw.size(); i++) {
            String tmp = results_rw.get(i);
            int id = Integer.valueOf(tmp.substring(0, 1));
            String res = tmp.substring(1);
            adapter.addItem(ttl_rw[id], res);
        }

        for (int i = 0; i < results_db.size(); i++) {
            String tmp = results_db.get(i);
            int id = Integer.valueOf(tmp.substring(0, 1));
            String res = tmp.substring(1);
            adapter.addItem(ttl_db[id], res);
        }

//        if (adapter.isEmpty()) {
//            TextView tv = (TextView) findViewById(R.id.rw_ttl);
//            tv.setVisibility(View.GONE);
//        }
    }
}
