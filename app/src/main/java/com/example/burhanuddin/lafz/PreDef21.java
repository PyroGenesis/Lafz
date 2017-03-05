package com.example.burhanuddin.lafz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class PreDef21 extends AppCompatActivity {

    TrimesterDBHandler tdb;
    String vomit="no",nausea="no",appe="no",dizzi="no",sono="",fre="",abn="";
    int pid=0, bsgr=0, bpr=0,ans,old=0;
    double haemo=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_def21);

        tdb = new TrimesterDBHandler(getApplicationContext());
        Button b1=(Button)findViewById(R.id.nextp);
        Button b2=(Button)findViewById(R.id.ret1);

        final RadioGroup freq=(RadioGroup)findViewById(R.id.freq);
        final RadioButton t45=(RadioButton)findViewById(R.id.t45);
        final RadioButton t34=(RadioButton)findViewById(R.id.t34);
        final RadioButton t23=(RadioButton)findViewById(R.id.t23);

        final EditText hae=(EditText)findViewById(R.id.hae2);
        final EditText bdsgr=(EditText)findViewById(R.id.bsgr2);

        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            pid= extras.getInt("pid");
            old= extras.getInt("old");
        }

        String data = tdb.dataexist2(pid);


        if(data=="true")
        {

            String[] data1 = tdb.getiddata2(pid, getApplicationContext());


            pid= Integer.parseInt(data1[0]);
            vomit=String.valueOf(data1[1]);
            nausea=String.valueOf(data1[2]);
            appe=String.valueOf(data1[3]);
            dizzi=String.valueOf(data1[4]);
            haemo= Double.parseDouble(data1[5]);
            bsgr=Integer.parseInt(data1[6]);
            sono=String.valueOf(data1[7]);
            abn=String.valueOf(data1[8]);
            fre=String.valueOf(data1[9]);
            bpr= Integer.parseInt(data1[10]);


            if(fre!=null)
            {
                if(fre.equals("4-5"))
                {
                    t45.setChecked(true);
                }
                if(fre.equals("3-4"))
                {
                    t34.setChecked(true);
                }
                if(fre.equals("2-3"))
                {
                    t23.setChecked(true);
                }

            }


            hae.setText(String.valueOf(haemo));
            bdsgr.setText(String.valueOf(bsgr));

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

                ans=freq.getCheckedRadioButtonId();

                if(ans==R.id.t45)
                {
                    fre="4-5";
                }
                if(ans==R.id.t34)
                {
                    fre="3-4";
                }
                if(ans==R.id.t23)
                {
                    fre="2-3";
                }

                if(!hae.getText().toString().equals("")) {
                    haemo=Double.parseDouble(hae.getText().toString());
                }

                if(!bdsgr.getText().toString().equals("")) {
                    bsgr=Integer.parseInt(bdsgr.getText().toString());
                }

                Intent j=new Intent(getApplicationContext(),PreDef22.class);

                j.putExtra("fre",fre);
                j.putExtra("haemo",haemo);
                j.putExtra("bsgr",bsgr);
                j.putExtra("pid",pid);
                j.putExtra("son",sono);
                j.putExtra("abno",abn);
                j.putExtra("pressure",bpr);
                j.putExtra("vomit", vomit);
                j.putExtra("nausea",nausea);
                j.putExtra("appetite",appe);
                j.putExtra("dizziness",dizzi);
                startActivity(j);

            }
        });
    }
}
