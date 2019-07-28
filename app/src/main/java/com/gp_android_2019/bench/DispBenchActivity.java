package com.gp_android_2019.bench;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.gp_android_2019.R;

public class DispBenchActivity extends AppCompatActivity {
    static final String[] ttl = { "Sequence Read", "Sequence Write",
            "Random Read", "Random Wirte" };
    String[] data = { "1", "22", "333", "4444" };

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("rw_bench");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disp_bench);

        Intent intent = getIntent();
        boolean[] isCheck = intent.getBooleanArrayExtra("data");

        DispListViewAdapter adapter = new DispListViewAdapter();

        ListView rw_list = (ListView) findViewById(R.id.rw_listView);
        rw_list.setAdapter(adapter);

        if (isCheck.length == 0) {

        }

        for (int i = 0; i < 4; i++) {
            if (isCheck[i]) {
                adapter.addItem(ttl[i], data[i]);
            }
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String seqRead();
    public native String seqWrite();
    public native String ranRead();
    public native String ranWrite();
    public native String dbInsert();
    public native String dbUpdate();
    public native String dbDelete();
}
