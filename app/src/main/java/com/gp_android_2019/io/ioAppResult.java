package com.gp_android_2019.io;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gp_android_2019.R;

import java.util.List;

import com.jaredrummler.android.processes.AndroidProcesses;
import com.jaredrummler.android.processes.models.AndroidAppProcess;
import com.jaredrummler.android.processes.models.Stat;

public class ioAppResult extends AppCompatActivity {

    Intent prevIntent = new Intent(this.getIntent());
    String app_name = prevIntent.getStringExtra("APP_NAME");
    String pack_name = prevIntent.getStringExtra("PACK_NAME");
    String sdir_name = prevIntent.getStringExtra("SDIR_NAME");
    String ddir_name = prevIntent.getStringExtra("DDIR_NAME");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_io_result);



        Button appLaunchBtn = (Button) findViewById(R.id.app_launch);
        Switch traceSwitch = (Switch) findViewById(R.id.trace_switch);

        TextView app_aview = (TextView) findViewById(R.id.app_textview);
        TextView app_pview = (TextView) findViewById(R.id.pack_textview);
        TextView app_sview = (TextView) findViewById(R.id.src_textview);
        TextView app_dview = (TextView) findViewById(R.id.data_textview);

        app_aview.setText(app_name);
        app_pview.setText(pack_name);
        app_sview.setText(sdir_name);
        app_dview.setText(ddir_name);


        // FRAGMENTATION INFORMATION
        TextView frag_num = (TextView) findViewById(R.id.frag_file_num);
        TextView frag_per = (TextView) findViewById(R.id.frag_file_per);
        Button defrag_btn = (Button) findViewById(R.id.defrag_launch);

        defrag_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // APP BENCHMARK
        traceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    int target_pid = getAppPid();
                    System.out.println(target_pid);

                } else {

                }
            }
        });

        appLaunchBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getPackageManager().getLaunchIntentForPackage(pack_name);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    public int getAppPid(){
        int target_pid = 0;
        try  {
            List<AndroidAppProcess> processes = AndroidProcesses.getRunningAppProcesses();
            for(AndroidAppProcess process : processes){
                String processName = process.name;
                Stat stat = process.stat();
                int pid = stat.getPid();
                if(processName.compareTo(pack_name) == 0) target_pid = pid;
            }
        } catch (Exception e)  {
            e.fillInStackTrace();
            System.out.println("ERROR : GET PID");
        }
        return target_pid;
    }
}
