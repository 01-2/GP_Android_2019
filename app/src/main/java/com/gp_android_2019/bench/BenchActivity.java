package com.gp_android_2019.bench;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.gp_android_2019.R;

import java.util.ArrayList;

public class BenchActivity extends AppCompatActivity {
    static public boolean[] rwCheck;
    static public boolean[] dbCheck;
    static public boolean isEmpty = true;

    FragmentManager fm;
    FragmentTransaction ft;

    BenchFragment fBench;
    TypeFragment fType;
    SetFragment fSetting;

    static public String path;
    static public String type;
    static public String direct;
    static public String bs;
    static public String io_size;
    static public String num_jobs;
    static public String runtime;

    static public int numOfTrans;

    private ArrayList<String> result_rw;
    private ArrayList<String> result_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bench);

        final ImageButton bench_btn = (ImageButton) findViewById(R.id.btn_bench);
        final ImageButton type_btn = (ImageButton) findViewById(R.id.btn_type);
        final ImageButton param_btn = (ImageButton) findViewById(R.id.btn_param);

        bench_btn.setBackgroundResource(R.color.colorSelect);

        fBench = new BenchFragment();
        fType = new TypeFragment();
        fSetting = new SetFragment();

        fm = getFragmentManager();

        ft = fm.beginTransaction();
        ft.replace(R.id.frame_layout, fType);
        ft.commit();

        ft = fm.beginTransaction();
        ft.replace(R.id.frame_layout, fSetting);
        ft.commit();

        ft = fm.beginTransaction();
        ft.replace(R.id.frame_layout, fBench);
        ft.commit();

        bench_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                bench_btn.setBackgroundResource(R.color.colorSelect);
                type_btn.setBackgroundResource(R.color.colorDeSelect);
                param_btn.setBackgroundResource(R.color.colorDeSelect);

                ft = fm.beginTransaction();
                ft.replace(R.id.frame_layout, fBench);
                ft.commit();
            }
        });
        type_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                bench_btn.setBackgroundResource(R.color.colorDeSelect);
                type_btn.setBackgroundResource(R.color.colorSelect);
                param_btn.setBackgroundResource(R.color.colorDeSelect);

                ft = fm.beginTransaction();
                ft.replace(R.id.frame_layout, fType);
                ft.commit();
            }
        });
        param_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                bench_btn.setBackgroundResource(R.color.colorDeSelect);
                type_btn.setBackgroundResource(R.color.colorDeSelect);
                param_btn.setBackgroundResource(R.color.colorSelect);

                ft = fm.beginTransaction();
                ft.replace(R.id.frame_layout, fSetting);
                ft.commit();
            }
        });
    }

    public void benchmark(View v) {
        fType.CheckedBoxes();

        if (isEmpty) {
            Toast.makeText(getApplicationContext(), "Select at least one Type", Toast.LENGTH_SHORT).show();
            return ;
        }
        isEmpty = true;

        fSetting.makeParameter();

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

                    benchRW rw = new benchRW(path, type, direct, bs, io_size, num_jobs, runtime);
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
}
