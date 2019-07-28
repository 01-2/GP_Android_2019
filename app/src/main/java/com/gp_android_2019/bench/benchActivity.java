package com.gp_android_2019.bench;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.gp_android_2019.R;

import java.util.ArrayList;

public class benchActivity extends AppCompatActivity {
    private boolean[] isCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bench);
    }

    public void selectBenchmark(View view) {
        Intent intent = new Intent(this, DispBenchActivity.class);

        selectedCheckBox();

        intent.putExtra("data", isCheck);
        startActivity(intent);
    }

    public void onClickAllButton(View view) {
        ArrayList<CheckBox> boxes = new ArrayList<>();

        boxes.add((CheckBox)findViewById(R.id.seq_read_chk));
        boxes.add((CheckBox)findViewById(R.id.seq_write_chk));
        boxes.add((CheckBox)findViewById(R.id.ran_read_chk));
        boxes.add((CheckBox)findViewById(R.id.ran_write_chk));

        int cnt_CheckBox = boxes.size();

        boolean allChk = true;
        for (int i=0; i < cnt_CheckBox; i++) {
            allChk = allChk & boxes.get(i).isChecked();
            if (!allChk)
                break;
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

    public void selectedCheckBox() {
        ArrayList<CheckBox> boxes = new ArrayList<>();

        boxes.add((CheckBox)findViewById(R.id.seq_read_chk));
        boxes.add((CheckBox)findViewById(R.id.seq_write_chk));
        boxes.add((CheckBox)findViewById(R.id.ran_read_chk));
        boxes.add((CheckBox)findViewById(R.id.ran_write_chk));

        int cnt_CheckBox = boxes.size();

        isCheck = new boolean[cnt_CheckBox];
        for (int i=0; i < cnt_CheckBox; i++) {
            if (boxes.get(i).isChecked()) {
                isCheck[i] = true;
            }
            else {
                isCheck[i] = false;
            }
        }
    }
}
