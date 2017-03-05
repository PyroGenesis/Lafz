package com.example.burhanuddin.lafz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class PreDef31 extends AppCompatActivity {

    TrimesterDBHandler tdb;
    public int bsgr=0,bpre=0,pid=0,old=0;
    double haemo=0;
    public String pain="no",bleed="no",sono="",abn="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_def31);

        tdb = new TrimesterDBHandler(getApplicationContext());
        Button b1=(Button)findViewById(R.id.nextp);
        Button b2=(Button)findViewById(R.id.ret1);

        final CheckBox c1=(CheckBox)findViewById(R.id.pain);
        final CheckBox c2=(CheckBox)findViewById(R.id.ble);

        final EditText hae=(EditText)findViewById(R.id.hae3);
        final EditText bdsgr=(EditText)findViewById(R.id.bsgr3);
        final EditText bpr=(EditText)findViewById(R.id.bldp3);

        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            pid= extras.getInt("pid");
            old= extras.getInt("old");
        }


        String data = tdb.dataexist3(pid);


        if(data=="true")
        {

            String[] data1 = tdb.getiddata3(pid, getApplicationContext());


            pid= Integer.parseInt(data1[0]);
            pain=String.valueOf(data1[1]);
            bleed=String.valueOf(data1[2]);
            haemo= Double.parseDouble(data1[3]);
            bsgr=Integer.parseInt(data1[4]);
            sono=String.valueOf(data1[5]);
            abn=String.valueOf(data1[6]);
            bpre= Integer.parseInt(data1[7]);

            if(pain.equals("yes"))
            {
                c1.setChecked(true);
            }
            else
            {
                pain="no";
            }
            if(bleed.equals("yes"))
            {
                c2.setChecked(true);
            }
            else
            {
                bleed="no";
            }

            hae.setText(String.valueOf(haemo));
            bdsgr.setText(String.valueOf(bsgr));
            bpr.setText(String.valueOf(bpre));

        }


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),ConfigHome.class);
                startActivity(i);

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle extras = getIntent().getExtras();
                if(extras!=null) {
                    pid= extras.getInt("pid");
                }

                if(c1.isChecked())
                {
                    pain="yes";
                }
                else
                {
                    pain="no";
                }
                if(c2.isChecked())
                {
                    bleed="yes";
                }
                else
                {
                    bleed="no";
                }

                if(!hae.getText().toString().equals("")) {
                    haemo=Double.parseDouble(hae.getText().toString());
                }

                if(!bdsgr.getText().toString().equals("")) {
                    bsgr=Integer.parseInt(bdsgr.getText().toString());
                }

                if(!bpr.getText().toString().equals("")) {
                    bpre=Integer.parseInt(bpr.getText().toString());
                }


                Intent j=new Intent(getApplicationContext(),PreDef32.class);

                j.putExtra("pain",pain);
                j.putExtra("bleed",bleed);
                j.putExtra("haemo",haemo);
                j.putExtra("bsgr",bsgr);
                j.putExtra("pid",pid);
                j.putExtra("bpre",bpre);
                j.putExtra("son",sono);
                j.putExtra("abno",abn);
                startActivity(j);

            }
        });
    }
}
