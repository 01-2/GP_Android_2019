package com.gp_android_2019;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gp_android_2019.R;
import com.gp_android_2019.io.ioActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button fragBtn = (Button)findViewById(R.id.frag_btn);
        Button ioBtn = (Button)findViewById(R.id.iocheck_btn);
        Button benchBtn = (Button)findViewById(R.id.bench_btn);

        fragBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), fragActivity.class);
                startActivity(intent);
            }
        });

        ioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ioActivity.class);
                startActivity(intent);
            }
        });

        benchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), benchActivity.class);
                startActivity(intent);
            }
        });
    }
}
