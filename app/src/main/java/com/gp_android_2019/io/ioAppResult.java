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

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.jaredrummler.android.processes.AndroidProcesses;
import com.jaredrummler.android.processes.models.AndroidAppProcess;
import com.jaredrummler.android.processes.models.Stat;

import org.w3c.dom.Text;

public class ioAppResult extends AppCompatActivity {

    public ArrayList<String> suCommand(String cmd){
        Process p;
        try{
            p = Runtime.getRuntime().exec(new String[]{"su", "-c", cmd});
            DataOutputStream os = new DataOutputStream((p.getOutputStream()));
            BufferedReader bf = new BufferedReader(new InputStreamReader(p.getInputStream()));

            ArrayList<String> ret = new ArrayList<>();
            String test;

            while((test = bf.readLine()) != null) ret.add(test);
            os.flush();

            return ret;
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_io_result);
        Intent prevIntent = new Intent(this.getIntent());

        final String app_name = prevIntent.getStringExtra("APP_NAME");
        final String pack_name = prevIntent.getStringExtra("PACK_NAME");
        final String sdir_name = prevIntent.getStringExtra("SDIR_NAME");
        final String ddir_name = prevIntent.getStringExtra("DDIR_NAME");

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
                    suCommand("echo function > /sys/kernel/debug/tracing/tracing_on");
                    suCommand("echo nop > /sys/kernel/debug/tracing/current_tracer");
                    suCommand("echo 1 > /sys/kernel/debug/tracing/tracing_on");
                }
                else {
                    suCommand("echo 0 > /sys/kernel/debug/tracing/tracing_on");
                    int tpid = getAppPid(pack_name);
                    System.out.println("TARGET PID : " + tpid);

                    ArrayList<String> sh_result = suCommand("cat /sys/kernel/debug/tracing/trace");
                    System.out.println("LOG SIZE : " + sh_result.size());
                    for(int i = 0; i < sh_result.size(); i++){
                        System.out.println(sh_result.get(i));
                    }
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

    public int getAppPid(String p_name){
        int target_pid = 0;
        try  {
            List<AndroidAppProcess> processes = AndroidProcesses.getRunningAppProcesses();
            for(AndroidAppProcess process : processes){
                String processName = process.name;
                Stat stat = process.stat();
                int pid = stat.getPid();
                if(processName.compareTo(p_name) == 0) target_pid = pid;
            }
        } catch (Exception e)  {
            e.fillInStackTrace();
            System.out.println("ERROR : GET PID");
        }
        return target_pid;
    }

    public void settingGraph(){
        TextView part_vfs = (TextView) findViewById(R.id.g_vfs);
        TextView part_ext4 = (TextView) findViewById(R.id.g_ext4);
        TextView part_block = (TextView) findViewById(R.id.g_block);
        TextView part_driver = (TextView) findViewById(R.id.g_driver);
    }



}
