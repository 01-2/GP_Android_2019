package com.gp_android_2019.io;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gp_android_2019.R;

public class ioAppResult extends AppCompatActivity {
    Handler delayHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_io_result);

        Intent prevIntent = new Intent(this.getIntent());
        final String app_name = prevIntent.getStringExtra("PACK_NAME");
        Button checkBtn = (Button) findViewById(R.id.ioAppCheckBtn);
        checkBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = getPackageManager().getLaunchIntentForPackage(app_name);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                delayHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        TextView tv1 = (TextView) findViewById(R.id.vfs_result);
                    }
                },3000);
            }
        });
    }
}
