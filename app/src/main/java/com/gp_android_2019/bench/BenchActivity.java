package com.gp_android_2019.bench;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.gp_android_2019.R;

import java.util.ArrayList;

public class BenchActivity extends AppCompatActivity {
    private boolean[] rwCheck;
    private boolean[] dbCheck;
    private boolean isEmpty = true;

    private String type;
    private String direct;
    private String bs;
    private String io_size;
    private String num_jobs;
    private String runtime;

    private int numOfTrans;

    private ArrayList<String> result_rw;
    private ArrayList<String> result_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bench);

        Button rw = (Button)findViewById(R.id.btn_rw);
        Button db = (Button)findViewById(R.id.btn_db);
        Button all = (Button)findViewById(R.id.btn_all);
        Button bench = (Button)findViewById(R.id.btn_bench);

        rw.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRW();
            }
        });
        db.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDB();
            }
        });
        all.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkALL();
            }
        });
        bench.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                benchmark();
            }
        });
    }

    private void benchmark() {
        CheckedBoxes();

        if (isEmpty) {
            Toast.makeText(getApplicationContext(), "Select at least one Type", Toast.LENGTH_SHORT).show();
            return ;
        }
        isEmpty = true;

        makeParameter();

        CheckTypesTask task = new CheckTypesTask(BenchActivity.this);
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
            result_rw = new ArrayList<>();
            result_db = new ArrayList<>();

            asyncDialog = new ProgressDialog(mContext);
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            asyncDialog.setMessage("Benchmarking..");

            asyncDialog.setCanceledOnTouchOutside(false);
            asyncDialog.setCancelable(true);

            asyncDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            cancel(false);
                        }
                    });

            // show dialog
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            int progress = 10;

            for (int i=0; i<rwCheck.length; i++) {
                publishProgress("Read / Write", String.valueOf(progress));

                String tmp = "";
                if (rwCheck[i]) {
                    tmp += String.valueOf(i);
                    switch (i) {
                        case 0:
                            type = "read";
                            break;
                        case 1:
                            type = "write";
                            break;
                        case 2:
                            type = "randread";
                            break;
                        case 3:
                            type = "randwrite";
                            break;
                        default:
                            break;
                    }

                    benchRW rw = new benchRW(type, direct, bs, io_size, num_jobs, runtime);
                    tmp += rw.getResult();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    parseResultRW pr = new parseResultRW(tmp);
                    String res_rw = pr.getPar();
                    result_rw.add(res_rw);
                }

                if(isCancelled()) {
                    return null;
                }

                progress += 100/rwCheck.length;
            }

            progress = 10;

            final DBHelper dbHelper = new DBHelper(getApplicationContext());
            benchDB bdb = new benchDB(dbHelper, numOfTrans);

            for (int i=0; i<dbCheck.length; i++) {
                String res_db = "";
                publishProgress("SQLite", String.valueOf(progress));
                if (dbCheck[i]) {
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

                    if(isCancelled()) {
                        return null;
                    }

                    result_db.add(res_db);
                }

                progress += 100/dbCheck.length;
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... progress) {
            asyncDialog.setMessage(progress[0]);
            asyncDialog.setProgress(Integer.parseInt(progress[1]));

            super.onProgressUpdate(progress);
        }

        @Override
        protected void onPostExecute(Void result) {
            asyncDialog.dismiss();
            super.onPostExecute(result);

            if(!isCancelled()) {
                Intent intent = new Intent(getApplicationContext(), BenchDispActivity.class);
                intent.putExtra("rw_result", result_rw);
                intent.putExtra("db_result", result_db);
                startActivity(intent);
            }
        }
    }

    private void makeParameter() {
        direct = "0";
        bs = "4k";
        io_size = "1G";
        num_jobs = "4";
        runtime = "30";

        numOfTrans = 10000;
    }

    private void CheckedBoxes() {
        ArrayList<CheckBox> boxes = new ArrayList<>();

        boxes.add((CheckBox)findViewById(R.id.chk_seq_read));
        boxes.add((CheckBox)findViewById(R.id.chk_seq_write));
        boxes.add((CheckBox)findViewById(R.id.chk_ran_read));
        boxes.add((CheckBox)findViewById(R.id.chk_ran_write));

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

        boxes.add((CheckBox)findViewById(R.id.chk_insert));
        boxes.add((CheckBox)findViewById(R.id.chk_update));
        boxes.add((CheckBox)findViewById(R.id.chk_delete));

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

    private void checkRW() {
        ArrayList<CheckBox> boxes = new ArrayList<>();
        boxes.add((CheckBox)findViewById(R.id.chk_seq_read));
        boxes.add((CheckBox)findViewById(R.id.chk_seq_write));
        boxes.add((CheckBox)findViewById(R.id.chk_ran_read));
        boxes.add((CheckBox)findViewById(R.id.chk_ran_write));

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
        boxes.add((CheckBox)findViewById(R.id.chk_insert));
        boxes.add((CheckBox)findViewById(R.id.chk_update));
        boxes.add((CheckBox)findViewById(R.id.chk_delete));

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
        boxes.add((CheckBox)findViewById(R.id.chk_seq_read));
        boxes.add((CheckBox)findViewById(R.id.chk_seq_write));
        boxes.add((CheckBox)findViewById(R.id.chk_ran_read));
        boxes.add((CheckBox)findViewById(R.id.chk_ran_write));
        boxes.add((CheckBox)findViewById(R.id.chk_insert));
        boxes.add((CheckBox)findViewById(R.id.chk_update));
        boxes.add((CheckBox)findViewById(R.id.chk_delete));

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
