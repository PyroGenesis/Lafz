package com.example.burhanuddin.lafz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewData1 extends AppCompatActivity {

    TrimesterDBHandler tdb;
    /*
    String vomit="no",nausea="no",appe="no",dizzi="no",hivv="no",bgr=" ",sono="";
    int pid=0, haemo=0, bsgr=0, bpr=0,old=0;
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data1);

        tdb = new TrimesterDBHandler(getApplicationContext());
        final TextView pid = (TextView)findViewById(R.id.id);
        final TextView vom = (TextView)findViewById(R.id.vomit);
        final TextView nau = (TextView)findViewById(R.id.nausea);
        final TextView appe = (TextView)findViewById(R.id.appe);
        final TextView dizzi = (TextView)findViewById(R.id.dizzi);
        final TextView haemo = (TextView)findViewById(R.id.haemo);
        final TextView bld_grp = (TextView)findViewById(R.id.bld_grp);
        final TextView hiv = (TextView)findViewById(R.id.hiv);
        final TextView sug = (TextView)findViewById(R.id.sug);
        final TextView sono = (TextView)findViewById(R.id.sono);
        final TextView bp = (TextView)findViewById(R.id.bp);

        String[][] data = tdb.getData();
        int j = Integer.parseInt(data[0][11]);
        for(int i=0; i<j; i++)
        {
            pid.setText(pid.getText() + data[i][0] +"\n");
            vom.setText(vom.getText() + data[i][1] +"\n");
            nau.setText(nau.getText() + data[i][2] +"\n");
            appe.setText(appe.getText() + data[i][3] +"\n");
            dizzi.setText(dizzi.getText() + data[i][4] +"\n");
            haemo.setText(haemo.getText() + data[i][5] +"\n");
            bld_grp.setText(bld_grp.getText() + data[i][6] +"\n");
            hiv.setText(hiv.getText() + data[i][7] +"\n");
            sug.setText(sug.getText() + data[i][8] +"\n");
            sono.setText(sono.getText() + data[i][9] +"\n");
            bp.setText(bp.getText() + data[i][10] +"\n");
        }
    }
}
