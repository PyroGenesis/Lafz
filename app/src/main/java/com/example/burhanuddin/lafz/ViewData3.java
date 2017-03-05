package com.example.burhanuddin.lafz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewData3 extends AppCompatActivity {

    TrimesterDBHandler tdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data3);

        tdb = new TrimesterDBHandler(getApplicationContext());
        final TextView pid = (TextView)findViewById(R.id.pid3);
        final TextView pain = (TextView)findViewById(R.id.pain3);
        final TextView ble = (TextView)findViewById(R.id.ble3);
        final TextView haemo = (TextView)findViewById(R.id.haemo3);
        final TextView sug = (TextView)findViewById(R.id.sug3);
        final TextView sono = (TextView)findViewById(R.id.sono3);
        final TextView abn = (TextView)findViewById(R.id.abn3);
        final TextView bp = (TextView)findViewById(R.id.bp3);

        String[][] data = tdb.getData3();
        int j = Integer.parseInt(data[0][8]);
        for(int i=0; i<j; i++)
        {
            pid.setText(pid.getText() + data[i][0] +"\n");
            pain.setText(pain.getText() + data[i][1] +"\n");
            ble.setText(ble.getText() + data[i][2] +"\n");
            haemo.setText(haemo.getText() + data[i][3] +"\n");
            sug.setText(sug.getText() + data[i][4] +"\n");
            sono.setText(sono.getText() + data[i][5] +"\n");
            abn.setText(abn.getText() + data[i][6] +"\n");
            bp.setText(bp.getText() + data[i][7] +"\n");
        }
    }
}
