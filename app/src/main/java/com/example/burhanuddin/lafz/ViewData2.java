package com.example.burhanuddin.lafz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewData2 extends AppCompatActivity {

    TrimesterDBHandler tdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data2);

        tdb = new TrimesterDBHandler(getApplicationContext());
        final TextView pid = (TextView)findViewById(R.id.pid2);
        final TextView vom = (TextView)findViewById(R.id.vomit2);
        final TextView nau = (TextView)findViewById(R.id.nausea2);
        final TextView appe = (TextView)findViewById(R.id.appe2);
        final TextView dizzi = (TextView)findViewById(R.id.dizzi2);
        final TextView haemo = (TextView)findViewById(R.id.haemo2);
        final TextView sug = (TextView)findViewById(R.id.sgr2);
        final TextView sono = (TextView)findViewById(R.id.sono2);
        final TextView abn = (TextView)findViewById(R.id.abn2);
        final TextView fre = (TextView)findViewById(R.id.fre2);
        final TextView bp = (TextView)findViewById(R.id.bp2);

        String[][] data = tdb.getData2();
        int j = Integer.parseInt(data[0][11]);
        for(int i=0; i<j; i++)
        {
            pid.setText(pid.getText() + data[i][0] +"\n");
            vom.setText(vom.getText() + data[i][1] +"\n");
            nau.setText(nau.getText() + data[i][2] +"\n");
            appe.setText(appe.getText() + data[i][3] +"\n");
            dizzi.setText(dizzi.getText() + data[i][4] +"\n");
            haemo.setText(haemo.getText() + data[i][5] +"\n");
            sug.setText(sug.getText() + data[i][6] +"\n");
            sono.setText(sono.getText() + data[i][7] +"\n");
            abn.setText(abn.getText() + data[i][8] +"\n");
            fre.setText(fre.getText() + data[i][9] +"\n");
            bp.setText(bp.getText() + data[i][10] +"\n");
        }


    }
}
