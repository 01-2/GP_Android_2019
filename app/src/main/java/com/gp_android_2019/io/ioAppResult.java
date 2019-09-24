package com.gp_android_2019.io;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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
    layer l = new layer(), s1 = new layer(), s2 = new layer();

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
        Button save1Btn = (Button) findViewById(R.id.save1);
        Button save2Btn =  (Button) findViewById(R.id.save2);
        Button compareBtn = (Button) findViewById(R.id.save2);
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
        final TextView frag_num = (TextView) findViewById(R.id.frag_file_num);
        final TextView frag_per = (TextView) findViewById(R.id.frag_file_per);
        Button defrag_btn = (Button) findViewById(R.id.defrag_launch);

        final fragManage frag = new fragManage(ddir_name);

        frag_num.setText(frag.appFragmentedFileNum +  " / " + frag.appFileNum.toString());
        frag_per.setText(String.format("%.2f", frag.appFragmentedPercentage*100) + "%");

        defrag_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                frag.defragAllFiles();

                Toast t = Toast.makeText(getApplicationContext(), frag.defragMentedNum.toString() + " files defragmented", Toast.LENGTH_LONG);
                t.show();

                frag_num.setText(frag.appFragmentedFileNum +  " / " + frag.appFileNum.toString());
                frag_per.setText(String.format("%.2f", frag.appFragmentedPercentage*100) + "%");
            }
        });

        // APP BENCHMARK
        traceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    suCommand("echo function > /sys/kernel/debug/tracing/current_tracer");
                    suCommand("echo nop > /sys/kernel/debug/tracing/current_tracer");
                    suCommand("echo 1 > /sys/kernel/debug/tracing/tracing_on");
                }
                else {
                    suCommand("echo 0 > /sys/kernel/debug/tracing/tracing_on");
                    int tpid = getAppPid(pack_name);
                    System.out.println("TARGET PID : " + tpid);


                    parseLog p = new parseLog();
                    l = p.parseAll(tpid);

                    drawGraph();
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
        save1Btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast t;
                if(l.pid == 0) {
                    t = Toast.makeText(getApplicationContext(), "Run application first", Toast.LENGTH_LONG);
                    t.show();
                }else {
                    s1 = l;
                    l.clear();

                    t = Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG);
                    t.show();
                }
            }
        });
        save2Btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast t;
                if(l.pid == 0) {
                    t = Toast.makeText(getApplicationContext(), "Run application first", Toast.LENGTH_LONG);
                    t.show();
                }else {
                    s2 = l;
                    l.clear();

                    t = Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG);
                    t.show();
                }
            }
        });
        compareBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast t = null;
                if (s1.pid == 0) {
                    t = Toast.makeText(getApplicationContext(), "Save slot 1 empty. You should save result first", Toast.LENGTH_LONG);
                    t.show();
                } else if (s2.pid == 0) {
                    t = Toast.makeText(getApplicationContext(), "Save slot 2 empty. You should save result first", Toast.LENGTH_LONG);
                    t.show();
                } else {
                    t = Toast.makeText(getApplicationContext(), "Hida", Toast.LENGTH_LONG);
                    t.show();
                }
            }

        });

    }

    public void drawGraph(){
        TextView vfs_r = (TextView)findViewById(R.id.vfs_r);
        TextView vfs_w = (TextView)findViewById(R.id.vfs_w);
        TextView ext4_r = (TextView)findViewById(R.id.ext4_r);
        TextView ext4_w = (TextView)findViewById(R.id.ext4_w);
        TextView block_r = (TextView)findViewById(R.id.block_r);
        TextView block_w = (TextView)findViewById(R.id.block_w);
        TextView driver_r = (TextView)findViewById(R.id.driver_r);
        TextView driver_w = (TextView)findViewById(R.id.driver_w);

        TextView g_vfs_r = (TextView)findViewById(R.id.g_vfs);
        TextView g_vfs_w = (TextView)findViewById(R.id.g_w_vfs);
        TextView g_ext4_r = (TextView)findViewById(R.id.g_ext4);
        TextView g_ext4_w = (TextView)findViewById(R.id.g_w_ext4);
        TextView g_block_r = (TextView)findViewById(R.id.g_block);
        TextView g_block_w = (TextView)findViewById(R.id.g_w_block);
        TextView g_driver_r = (TextView)findViewById(R.id.g_driver);
        TextView g_driver_w = (TextView)findViewById(R.id.g_w_driver);

        TableRow tr = (TableRow) findViewById(R.id.row_r);
        TableRow tw = (TableRow) findViewById(R.id.row_w);

        // set text
        vfs_r.setText(Integer.toString(l.vfs_r) + "us");
        vfs_w.setText(Integer.toString(l.vfs_w) + "us");
        ext4_r.setText(Integer.toString(l.ext_r) + "us");
        ext4_w.setText(Integer.toString(l.ext_w) + "us");
        block_r.setText(Integer.toString(l.block_r) + "us");
        block_w.setText(Integer.toString(l.block_w) + "us");
        driver_r.setText(Integer.toString(l.driver_r) + "us");
        driver_w.setText(Integer.toString(l.driver_w) + "us");


        int max_r = getMaxR();
        int max_w = getMaxW();
        //calc mean
        float div_r = l.vfs_r + l.driver_r + l.block_r + l.ext_r;
        float div_w = l.vfs_w + l.driver_w + l.block_w + l.ext_w;

        float ext_rr = 0.0001f, vfs_rr = 0.0001f, block_rr = 0.0001f, driver_rr = 0.0001f;
        float ext_ww = 0.0001f, vfs_ww = 0.0001f, block_ww = 0.0001f, driver_ww = 0.0001f;

        if(l.vfs_r != 0.0f)
            vfs_rr = (float)l.vfs_r;
        if(l.ext_r != 0.0f)
            ext_rr = (float)l.ext_r;
        if(l.block_r != 0.0f)
            block_rr = (float)l.block_r;
        if(l.driver_r != 0.0f)
            driver_rr = (float)l.driver_r;

        if(l.vfs_w != 0.0f)
            vfs_ww = (float)l.vfs_w;
        if(l.ext_w != 0.0f)
            ext_ww = (float)l.ext_w;
        if(l.block_w != 0.0f)
            block_ww = (float)l.block_w;
        if(l.driver_w != 0.0f)
            driver_ww = (float)l.driver_w;


        //draw r graph
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));
        tw.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));

        g_vfs_r.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, vfs_rr/div_r));
        g_ext4_r.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, ext_rr/div_r));
        g_block_r.setLayoutParams(new TableRow.LayoutParams(0,  TableRow.LayoutParams.MATCH_PARENT, block_rr/div_r));
        g_driver_r.setLayoutParams(new TableRow.LayoutParams(0,  TableRow.LayoutParams.MATCH_PARENT, driver_rr/div_r));

        //draw w graph
        g_vfs_w.setLayoutParams(new TableRow.LayoutParams(0,  TableRow.LayoutParams.MATCH_PARENT, vfs_ww));
        g_ext4_w.setLayoutParams(new TableRow.LayoutParams(0,  TableRow.LayoutParams.MATCH_PARENT, ext_ww));
        g_block_w.setLayoutParams(new TableRow.LayoutParams(0,  TableRow.LayoutParams.MATCH_PARENT, block_ww));
        g_driver_w.setLayoutParams(new TableRow.LayoutParams(0,  TableRow.LayoutParams.MATCH_PARENT, driver_ww));

    }
    public int getMaxW(){
        int max = l.vfs_w;

        if(l.ext_r > max)
            max = l.ext_w;
        if(l.block_r > max)
            max = l.block_w;
        if(l.driver_r > max)
            max = l.driver_w;

        return max;
    }

    public int getMaxR(){
        int max = l.vfs_r;

        if(l.ext_r > max)
            max = l.ext_r;
        if(l.block_r > max)
            max = l.block_r;
        if(l.driver_r > max)
            max = l.driver_r;

        return max;
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
