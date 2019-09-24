package com.gp_android_2019.io;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gp_android_2019.R;

import org.w3c.dom.Text;

import static java.lang.Math.abs;

public class ioCompareActivity extends AppCompatActivity {
    public layer l1, l2;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent prevIntent = new Intent(this.getIntent());

        // get saved data
        ioCompare data = (ioCompare) prevIntent.getSerializableExtra("save");
        l1 = data.l1;
        l2 = data.l2;

        drawGraph(l1);
        drawGraph2(l2);
        drawSummary();

    }

    private void drawSummary() {
        TextView vfs = (TextView) findViewById(R.id.summary_vfs);
        TextView ext = (TextView) findViewById(R.id.summary_ext4);
        TextView block = (TextView) findViewById(R.id.summary_block);
        TextView driver = (TextView) findViewById(R.id.summary_driver);

        TextView vfs_p = (TextView) findViewById(R.id.summary_vfs_p);
        TextView ext_p = (TextView) findViewById(R.id.summary_ext4_p);
        TextView block_p = (TextView) findViewById(R.id.summary_block_p);
        TextView driver_p = (TextView) findViewById(R.id.summary_driver_p);

        float vfss = -999 , extt = -999 , blockk = -999, driverr=-999;

        if (l1.vfs_r != 0 && l2.vfs_r != 0) vfss = (l1.vfs_r > l2.vfs_r) ? (float)(l1.vfs_r / l2.vfs_r) : -(float)(l1.vfs_r / l2.vfs_r);
        if (l1.ext_r != 0 && l2.ext_r != 0) extt = (l1.ext_r > l2.ext_r) ? (float)(l1.ext_r / l2.ext_r) : -(float)(l2.ext_r / l2.ext_r);
        if (l1.block_r != 0 && l2.block_r != 0) blockk = (l1.block_r > l2.block_r) ?  (float)(l1.block_r / l2.block_r) : -(float)(l1.block_r /l2.block_r);
        if (l1.driver_r != 0 && l2.driver_r != 0) driverr = (l1.driver_r > l2.driver_r) ? (float)(l1.driver_r / l2.driver_r) : -(float)(l1.driver_r / l2.driver_r);

        vfs.setText( Integer.toString(abs(l1.vfs_r - l2.vfs_r)) );
        ext.setText( Integer.toString(abs(l1.ext_r - l2.ext_r)) );
        block.setText( Integer.toString((abs(l1.block_r - l2.block_r ))));
        driver.setText( Integer.toString((abs(l1.driver_r - l2.driver_r)))) ;

        if (vfss != -999)
            vfs_p.setText( String.format("%.2f", vfss) + "%");
        else
            vfs_p.setText("NaN");
        if (extt != -999)
            ext_p.setText( String.format("%.2f", extt) + "%");
        else
            ext_p.setText("NaN");
        if(blockk != -999)
            block_p.setText( String.format("%.2f", blockk) + "%");
        else
            block_p.setText("NaN");
        if(driverr != -999)
            driver_p.setText( String.format("%.2f", driverr) + "%");
        else
            driver_p.setText("NaN");

        vfs = (TextView) findViewById(R.id.summary_vfs_w);
        ext = (TextView) findViewById(R.id.summary_ext4_w);
        block = (TextView) findViewById(R.id.summary_block_w);
        driver = (TextView) findViewById(R.id.summary_driver_w);

        vfs_p = (TextView) findViewById(R.id.summary_vfs_p_w);
        ext_p = (TextView) findViewById(R.id.summary_ext4_p_w);
        block_p = (TextView) findViewById(R.id.summary_block_p_w);
        driver_p = (TextView) findViewById(R.id.summary_driver_p_w);

        vfss = -999; extt = -999; blockk = -999; driverr=-999;

        if (l1.vfs_w != 0 && l2.vfs_w != 0) vfss = (l1.vfs_w > l2.vfs_w) ? (float)(l1.vfs_w / l2.vfs_w) : -(float)(l1.vfs_w / l2.vfs_w);
        if (l1.ext_w != 0 && l2.ext_w != 0) extt = (l1.ext_w > l2.ext_w) ? (float)(l1.ext_w / l2.ext_w) : -(float)(l2.ext_w / l2.ext_w);
        if (l1.block_w != 0 && l2.block_w != 0) blockk = (l1.block_w > l2.block_w) ?  (float)(l1.block_w / l2.block_w) : -(float)(l1.block_w /l2.block_w);
        if (l1.driver_w != 0 && l2.driver_w != 0) driverr = (l1.driver_w > l2.driver_w) ? (float)(l1.driver_w / l2.driver_w) : -(float)(l1.driver_w / l2.driver_w);

        vfs.setText( Integer.toString(abs(l1.vfs_w - l2.vfs_w)) );
        ext.setText( Integer.toString(abs(l1.ext_w - l2.ext_w)) );
        block.setText( Integer.toString((abs(l1.block_w - l2.block_w ))));
        driver.setText( Integer.toString((abs(l1.driver_w - l2.driver_w)))) ;

        if (vfss != -999)
            vfs_p.setText( String.format("%.2f", vfss) + "%");
        else
            vfs_p.setText("NaN");
        if (extt != -999)
            ext_p.setText( String.format("%.2f", extt) + "%");
        else
            ext_p.setText("NaN");
        if(blockk != -999)
            block_p.setText( String.format("%.2f", blockk) + "%");
        else
            block_p.setText("NaN");
        if(driverr != -999)
            driver_p.setText( String.format("%.2f", driverr) + "%");
        else
            driver_p.setText("NaN");
    }


    void drawGraph(layer l){
        TextView vfs_r = (TextView)findViewById(R.id.vfs_before_r);
        TextView vfs_w = (TextView)findViewById(R.id.vfs_before_w);
        TextView ext4_r = (TextView)findViewById(R.id.ext4_before_r);
        TextView ext4_w = (TextView)findViewById(R.id.ext4_before_w);
        TextView block_r = (TextView)findViewById(R.id.block_before_r);
        TextView block_w = (TextView)findViewById(R.id.block_before_w);
        TextView driver_r = (TextView)findViewById(R.id.driver_before_r);
        TextView driver_w = (TextView)findViewById(R.id.driver_before_w);

        TextView g_vfs_r = (TextView)findViewById(R.id.g_vfs);
        TextView g_vfs_w = (TextView)findViewById(R.id.g_w_vfs);
        TextView g_ext4_r = (TextView)findViewById(R.id.g_ext4);
        TextView g_ext4_w = (TextView)findViewById(R.id.g_w_ext4);
        TextView g_block_r = (TextView)findViewById(R.id.g_block);
        TextView g_block_w = (TextView)findViewById(R.id.g_w_block);
        TextView g_driver_r = (TextView)findViewById(R.id.g_driver);
        TextView g_driver_w = (TextView)findViewById(R.id.g_w_driver);

        TableRow tr = (TableRow) findViewById(R.id.row_rb);
        TableRow tw = (TableRow) findViewById(R.id.row_wb);

        // set text
        vfs_r.setText(Integer.toString(l.vfs_r) + "us");
        vfs_w.setText(Integer.toString(l.vfs_w) + "us");
        ext4_r.setText(Integer.toString(l.ext_r) + "us");
        ext4_w.setText(Integer.toString(l.ext_w) + "us");
        block_r.setText(Integer.toString(l.block_r) + "us");
        block_w.setText(Integer.toString(l.block_w) + "us");
        driver_r.setText(Integer.toString(l.driver_r) + "us");
        driver_w.setText(Integer.toString(l.driver_w) + "us");

        float div_r = l1.vfs_r + l1.driver_r + l1.block_r + l1.ext_r;
        float div_w = l1.vfs_w + l1.driver_w + l1.block_w + l1.ext_w;

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
        g_vfs_w.setLayoutParams(new TableRow.LayoutParams(0,  TableRow.LayoutParams.MATCH_PARENT, vfs_ww/div_w));
        g_ext4_w.setLayoutParams(new TableRow.LayoutParams(0,  TableRow.LayoutParams.MATCH_PARENT, ext_ww/div_w));
        g_block_w.setLayoutParams(new TableRow.LayoutParams(0,  TableRow.LayoutParams.MATCH_PARENT, block_ww/div_w));
        g_driver_w.setLayoutParams(new TableRow.LayoutParams(0,  TableRow.LayoutParams.MATCH_PARENT, driver_ww/div_w));
    }

    void drawGraph2(layer l){
        TextView vfs_r = (TextView)findViewById(R.id.vfs_after_r);
        TextView vfs_w = (TextView)findViewById(R.id.vfs_after_w);
        TextView ext4_r = (TextView)findViewById(R.id.ext4_after_r);
        TextView ext4_w = (TextView)findViewById(R.id.ext4_after_w);
        TextView block_r = (TextView)findViewById(R.id.block_after_r);
        TextView block_w = (TextView)findViewById(R.id.block_after_w);
        TextView driver_r = (TextView)findViewById(R.id.driver_after_r);
        TextView driver_w = (TextView)findViewById(R.id.driver_after_w);

        TextView g_vfs_r = (TextView)findViewById(R.id.g_vfs_after);
        TextView g_vfs_w = (TextView)findViewById(R.id.g_w_vfs_after);
        TextView g_ext4_r = (TextView)findViewById(R.id.g_ext4_after);
        TextView g_ext4_w = (TextView)findViewById(R.id.g_w_ext4_after);
        TextView g_block_r = (TextView)findViewById(R.id.g_block_after);
        TextView g_block_w = (TextView)findViewById(R.id.g_w_block_after);
        TextView g_driver_r = (TextView)findViewById(R.id.g_driver_after);
        TextView g_driver_w = (TextView)findViewById(R.id.g_w_driver_after);

        TableRow tr = (TableRow) findViewById(R.id.row_ra);
        TableRow tw = (TableRow) findViewById(R.id.row_wa);

        // set text
        vfs_r.setText(Integer.toString(l.vfs_r) + "us");
        vfs_w.setText(Integer.toString(l.vfs_w) + "us");
        ext4_r.setText(Integer.toString(l.ext_r) + "us");
        ext4_w.setText(Integer.toString(l.ext_w) + "us");
        block_r.setText(Integer.toString(l.block_r) + "us");
        block_w.setText(Integer.toString(l.block_w) + "us");
        driver_r.setText(Integer.toString(l.driver_r) + "us");
        driver_w.setText(Integer.toString(l.driver_w) + "us");

        float div_r = l1.vfs_r + l1.driver_r + l1.block_r + l1.ext_r;
        float div_w = l1.vfs_w + l1.driver_w + l1.block_w + l1.ext_w;

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
        g_vfs_w.setLayoutParams(new TableRow.LayoutParams(0,  TableRow.LayoutParams.MATCH_PARENT, vfs_ww/div_w));
        g_ext4_w.setLayoutParams(new TableRow.LayoutParams(0,  TableRow.LayoutParams.MATCH_PARENT, ext_ww/div_w));
        g_block_w.setLayoutParams(new TableRow.LayoutParams(0,  TableRow.LayoutParams.MATCH_PARENT, block_ww/div_w));
        g_driver_w.setLayoutParams(new TableRow.LayoutParams(0,  TableRow.LayoutParams.MATCH_PARENT, driver_ww/div_w));
    }

}
