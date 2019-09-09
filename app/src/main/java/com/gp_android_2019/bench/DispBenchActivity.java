package com.gp_android_2019.bench;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import com.gp_android_2019.R;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

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

        DispListViewAdapter adapter_rw = new DispListViewAdapter();
        ListView rw_list = (ListView) findViewById(R.id.rw_listView);
        rw_list.setAdapter(adapter_rw);

        for (int i = 0; i < results_rw.size(); i++) {
            String tmp = results_rw.get(i);
            int id = Integer.valueOf(tmp.substring(0, 1));
            String res = tmp.substring(1);
            adapter_rw.addItem(ttl_rw[id], res);
        }
        if (adapter_rw.isEmpty()) {
            TextView tv = (TextView) findViewById(R.id.rw_ttl);
            tv.setVisibility(View.GONE);
        }

        DispListViewAdapter adapter_db = new DispListViewAdapter();
        ListView db_list = (ListView) findViewById(R.id.db_listView);
        db_list.setAdapter(adapter_db);

        for (int i = 0; i < results_db.size(); i++) {
            String tmp = results_db.get(i);
            int id = Integer.valueOf(tmp.substring(0, 1));
            String res = tmp.substring(1);
            adapter_db.addItem(ttl_db[id], res);
        }
        if (adapter_db.isEmpty()) {
            TextView tv = (TextView) findViewById(R.id.db_ttl);
            tv.setVisibility(View.GONE);
        }
    }
}
