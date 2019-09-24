package com.gp_android_2019.bench;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gp_android_2019.R;

import java.util.ArrayList;

public class BenchDispActivity extends AppCompatActivity {

    ArrayList<Integer> res_id_rw = new ArrayList<>();
    ArrayList<Integer> res_id_db = new ArrayList<>();

    ArrayList<Integer> result_rw = new ArrayList<>();
    ArrayList<Float> result_db = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bench_disp);

        Intent intent = getIntent();
        ArrayList<String> tmp_res_rw = intent.getStringArrayListExtra("rw_result");
        if (!tmp_res_rw.isEmpty()) {
            for (int i = 0; i < tmp_res_rw.size(); i++) {
                String tmp = tmp_res_rw.get(i);
                res_id_rw.add(Integer.valueOf(tmp.substring(0, 1)));
                result_rw.add(Integer.valueOf(tmp.substring(1)));
            }
            ArrayList<Float> trans_rw = iTransform(result_rw);
            display_rw(trans_rw);
        }
        else {
            TextView rw_tv = findViewById(R.id.tv_ttl_rw);
            rw_tv.setVisibility(View.GONE);
            LinearLayout rw_graph = (LinearLayout)findViewById(R.id.graph_rw);
            rw_graph.setVisibility(View.GONE);
        }

        ArrayList<String> tmp_res_db = intent.getStringArrayListExtra("db_result");
        if (!tmp_res_db.isEmpty()) {
            for (int i = 0; i < tmp_res_db.size(); i++) {
                String tmp = tmp_res_db.get(i);
                res_id_db.add(Integer.valueOf(tmp.substring(0, 1)));
                result_db.add(Float.valueOf(tmp.substring(1)));
            }
            ArrayList<Float> trans_db = fTransform(result_db);
            display_db(trans_db);
        }
        else {
            TextView db_tv = findViewById(R.id.tv_ttl_db);
            db_tv.setVisibility(View.GONE);
            LinearLayout db_graph = (LinearLayout)findViewById(R.id.graph_db);
            db_graph.setVisibility(View.GONE);
        }
    }

    private void display_rw(ArrayList<Float> res_rw) {
        for (int i=0; i<res_rw.size(); i++) {
            switch (res_id_rw.get(i)) {
                case 0:
                    TextView sr = findViewById(R.id.bar_seq_read);
                    sr.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, res_rw.get(i)));
                    sr = findViewById(R.id.res_seq_read);
                    sr.setText(result_rw.get(i) + " KB/s");

                    LinearLayout layout_sr = (LinearLayout)findViewById(R.id.layout_seq_read);
                    layout_sr.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    TextView sw = findViewById(R.id.bar_seq_write);
                    sw.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, res_rw.get(i)));
                    sw = findViewById(R.id.res_seq_write);
                    sw.setText(result_rw.get(i) + " KB/s");

                    LinearLayout layout_sw = (LinearLayout)findViewById(R.id.layout_seq_write);
                    layout_sw.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    TextView rr = findViewById(R.id.bar_ran_read);
                    rr.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, res_rw.get(i)));
                    rr = findViewById(R.id.res_ran_read);
                    rr.setText(result_rw.get(i) + " KB/s");

                    LinearLayout layout_rr = (LinearLayout)findViewById(R.id.layout_ran_read);
                    layout_rr.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    TextView rw = findViewById(R.id.bar_ran_write);
                    rw.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, res_rw.get(i)));
                    rw = findViewById(R.id.res_ran_write);
                    rw.setText(result_rw.get(i) + " KB/s");

                    LinearLayout layout_rw = (LinearLayout)findViewById(R.id.layout_ran_write);
                    layout_rw.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }
    }

    private void display_db(ArrayList<Float> res_db) {
        for (int i=0; i<res_db.size(); i++) {
            switch (res_id_db.get(i)) {
                case 0:
                    TextView tv_ins = findViewById(R.id.bar_insert);
                    tv_ins.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, res_db.get(i)));
                    tv_ins = findViewById(R.id.res_insert);
                    tv_ins.setText(result_db.get(i) + " Trans/s");

                    LinearLayout layout_ins = (LinearLayout)findViewById(R.id.layout_insert);
                    layout_ins.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    TextView tv_upd = findViewById(R.id.bar_update);
                    tv_upd.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, res_db.get(i)));
                    tv_upd = findViewById(R.id.res_update);
                    tv_upd.setText(result_db.get(i) + " Trans/s");

                    LinearLayout layout_upd = (LinearLayout)findViewById(R.id.layout_update);
                    layout_upd.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    TextView tv_del = findViewById(R.id.bar_delete);
                    tv_del.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, res_db.get(i)));
                    tv_del = findViewById(R.id.res_delete);
                    tv_del.setText(result_db.get(i) + " Trans/s");

                    LinearLayout layout_del = (LinearLayout)findViewById(R.id.layout_delete);
                    layout_del.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }
    }

    private ArrayList<Float> iTransform(ArrayList<Integer> origin_res) {
        ArrayList<Float> tmp = new ArrayList<Float>();
        Integer max = iMax(origin_res);
        for (int i=0; i<origin_res.size(); i++) {
            Integer cur = origin_res.get(i);
            tmp.add((float)cur/max);
        }
        return tmp;
    }
    Integer iMax(ArrayList<Integer> list) {
        Integer max = list.get(0);
        for(int i=1; i<list.size(); i++) {
            Integer candidate = list.get(i);
            if (candidate > max) {
                max = candidate;
            }
        }
        return max;
    }

    private ArrayList<Float> fTransform(ArrayList<Float> origin_res) {
        ArrayList<Float> tmp = new ArrayList<Float>();
        Float max = fMax(origin_res);
        for (int i=0; i<origin_res.size(); i++) {
            Float cur = origin_res.get(i);
            tmp.add((float)cur/max);
        }
        return tmp;
    }
    Float fMax(ArrayList<Float> list) {
        Float max = list.get(0);
        for(int i=1; i<list.size(); i++) {
            Float candidate = list.get(i);
            if (candidate > max) {
                max = candidate;
            }
        }
        return max;
    }
}
