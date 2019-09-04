package com.gp_android_2019.bench;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import com.gp_android_2019.R;
import android.content.Intent;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class benchActivity extends AppCompatActivity {
    private boolean[] rwCheck;
    private boolean[] dbCheck;
    private boolean isEmpty = true;

    private benchParameter bp;
    private BenchDB bdb;

    private ArrayList<String> results_rw;
    private ArrayList<String> results_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bench);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabLayout);
        tabs.addTab(tabs.newTab().setText("TYPE"));
        tabs.addTab(tabs.newTab().setText("SETTING"));

        final ViewPager vp = (ViewPager) findViewById(R.id.view_pager);
        benchPagerAdapter pagerAdapter = new benchPagerAdapter(getSupportFragmentManager(), tabs.getTabCount());
        vp.setAdapter(pagerAdapter);
        tabs.setupWithViewPager(vp);

        vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    public void selectBenchmark(View view) {
        selectedCheckBox();
        if (isEmpty) {
            Toast.makeText(getApplicationContext(), "Select at least one Type", Toast.LENGTH_SHORT).show();
            return ;
        }

        makeParameter();

        Spinner spinner;
        spinner = (Spinner) findViewById(R.id.spin_num_trans);

        final DBHelper dbHelper = new DBHelper(getApplicationContext());
        bdb = new BenchDB(dbHelper, Integer.parseInt(spinner.getSelectedItem().toString()));

        CheckTypesTask task = new CheckTypesTask(benchActivity.this);
        task.execute();
    }

    private class CheckTypesTask extends AsyncTask<Void, String, Void> {
        ProgressDialog asyncDialog;
        private Context mContext;

        public CheckTypesTask(Context context) {
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            asyncDialog = new ProgressDialog(mContext);
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            asyncDialog.setMessage("Benchmarking..");
            asyncDialog.setCanceledOnTouchOutside(false);

            results_rw = new ArrayList<>();
            results_db = new ArrayList<>();

            // show dialog
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            int progress = 10;

            for (int i=0; i<rwCheck.length; i++) {
                String res_rw = "";
                publishProgress("Read / Write", String.valueOf(progress));
                if (rwCheck[i] == true) {
                    res_rw += String.valueOf(i);

                    switch (i) {
                        case 0:
                            bp.setType("read");
                            break;
                        case 1:
                            bp.setType("write");
                            break;
                        case 2:
                            bp.setType("randread");
                            break;
                        case 3:
                            bp.setType("randwrite");
                            break;
                        default:
                            break;
                    }

                    BenchRW rw = new BenchRW(bp);
                    res_rw += rw.getResult();

                    results_rw.add(res_rw);
                }

                progress += (i+1)*(int)(100/rwCheck.length);
            }

            progress = 10;
            for (int i=0; i<dbCheck.length; i++) {
                String res_db = "";
                publishProgress("SQLite", String.valueOf(progress));
                if (dbCheck[i] == true) {
                    res_db += String.valueOf(i);

                    switch (i) {
                        case 0:
                            res_db += bdb.benchInsert();
                            break;
                        case 1:
                            res_db += bdb.benchUpdate();
                            break;
                        case 2:
                            res_db += bdb.benchDelete();
                            break;
                        default:
                            break;
                    }

                    results_db.add(res_db);
                }

                progress += (i+1)*(int)(100/dbCheck.length);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... progress) {
            asyncDialog.setMessage(progress[0]);
            asyncDialog.setProgress(Integer.parseInt(progress[1]));

            //super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {
            asyncDialog.dismiss();
            super.onPostExecute(result);

            Intent intent = new Intent(getApplicationContext(), DispBenchActivity.class);
            intent.putExtra("results_rw", results_rw);
            intent.putExtra("results_db", results_db);
            startActivity(intent);
        }
    }

    public void makeParameter() {
        Spinner spinner;

        bp = new benchParameter();

        spinner = (Spinner) findViewById(R.id.spin_bench_bs);
        bp.setBs(spinner.getSelectedItem().toString());

        spinner = (Spinner) findViewById(R.id.spin_bench_io_size);
        bp.setIo_size(spinner.getSelectedItem().toString());

        spinner = (Spinner) findViewById(R.id.spin_bench_numjobs);
        bp.setNum_jobs(spinner.getSelectedItem().toString());

        spinner = (Spinner) findViewById(R.id.spin_bench_runtime);
        bp.setRuntime(spinner.getSelectedItem().toString());

        spinner = (Spinner) findViewById(R.id.spin_bench_direct);
        bp.setDirect(spinner.getSelectedItemPosition());
    }

    public void selectedCheckBox() {
        ArrayList<CheckBox> boxes = new ArrayList<>();

        boxes.add((CheckBox)findViewById(R.id.seq_read_chk));
        boxes.add((CheckBox)findViewById(R.id.seq_write_chk));
        boxes.add((CheckBox)findViewById(R.id.ran_read_chk));
        boxes.add((CheckBox)findViewById(R.id.ran_write_chk));

        int cnt_CheckBox = boxes.size();

        rwCheck = new boolean[cnt_CheckBox];
        for (int i=0; i < rwCheck.length; i++) {
            if (boxes.get(i).isChecked()) {
                rwCheck[i] = true;
                isEmpty = false;
            }
            else {
                rwCheck[i] = false;
            }
        }

        boxes = new ArrayList<>();

        boxes.add((CheckBox)findViewById(R.id.db_insert_chk));
        boxes.add((CheckBox)findViewById(R.id.db_update_chk));
        boxes.add((CheckBox)findViewById(R.id.db_delete_chk));

        cnt_CheckBox = boxes.size();
        dbCheck = new boolean[cnt_CheckBox];
        for (int i=0; i < dbCheck.length; i++) {
            if (boxes.get(i).isChecked()) {
                dbCheck[i] = true;
                isEmpty = false;
            }
            else {
                dbCheck[i] = false;
            }
        }
    }

    public void onClickAllButton(View view) {
        ArrayList<CheckBox> boxes = new ArrayList<>();

        boxes.add((CheckBox)findViewById(R.id.seq_read_chk));
        boxes.add((CheckBox)findViewById(R.id.seq_write_chk));
        boxes.add((CheckBox)findViewById(R.id.ran_read_chk));
        boxes.add((CheckBox)findViewById(R.id.ran_write_chk));
        boxes.add((CheckBox)findViewById(R.id.db_insert_chk));
        boxes.add((CheckBox)findViewById(R.id.db_update_chk));
        boxes.add((CheckBox)findViewById(R.id.db_delete_chk));

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
}
