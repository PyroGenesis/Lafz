package com.example.burhanuddin.lafz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class PreDef11 extends AppCompatActivity {

    TrimesterDBHandler tdb;
    String vomit="no",nausea="no",appe="no",dizzi="no",hivv="no",bgr=" ",sono="";
    int pid=0, bsgr=0, bpr=0,old=0;
    double haemo=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_def11);

        tdb = new TrimesterDBHandler(getApplicationContext());
        Button nextPage=(Button)findViewById(R.id.nextp);
        Button backToMain=(Button)findViewById(R.id.backToMain);
        RadioGroup hiv=(RadioGroup)findViewById(R.id.HIV);
        RadioButton pos=(RadioButton)findViewById(R.id.HIVyes);
        RadioButton neg=(RadioButton)findViewById(R.id.HIVno);

        final CheckBox c1=(CheckBox)findViewById(R.id.vomChBox);
        final CheckBox c2=(CheckBox)findViewById(R.id.nauChBox);
        final CheckBox c3=(CheckBox)findViewById(R.id.lowAppChBox);
        final CheckBox c4=(CheckBox)findViewById(R.id.dizzChBox);

        final EditText hae=(EditText)findViewById(R.id.haemoVal);
        final EditText bld_sug=(EditText)findViewById(R.id.bldSugVal);
        final EditText bld_pr=(EditText)findViewById(R.id.bldPrVal);


        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            pid= extras.getInt("pid");
            old= extras.getInt("old");
        }

        String data = tdb.dataexist1(pid);
        //Toast.makeText(predef1.this, (data + pid), Toast.LENGTH_LONG).show();

        if(data=="true")
        {
            String[] data1 = tdb.getiddata1(pid, getApplicationContext());

            pid= Integer.parseInt(data1[0]);
            vomit=String.valueOf(data1[1]);
            nausea=String.valueOf(data1[2]);
            appe=String.valueOf(data1[3]);
            dizzi=String.valueOf(data1[4]);
            haemo= Double.parseDouble(data1[5]);
            bgr=String.valueOf(data1[6]);
            hivv=String.valueOf(data1[7]);
            bsgr=Integer.parseInt(data1[8]);
            sono=String.valueOf(data1[9]);
            bpr= Integer.parseInt(data1[10]);


            if(vomit.equals("yes"))
            {
                c1.setChecked(true);
            }
            else
            {
                vomit="no";
            }
            if(nausea.equals("yes"))
            {
                c2.setChecked(true);
            }
            else
            {
                nausea="no";
            }
            if(appe.equals("yes"))
            {
                c3.setChecked(true);
            }
            else
            {
                appe="no";
            }
            if(dizzi.equals("yes"))
            {
                c4.setChecked(true);
            }
            else
            {
                dizzi="no";
            }

            if(hivv!=null)
            {
                if(sono.equals("yes"))
                {
                    pos.setChecked(true);
                }
                else
                {
                    neg.setChecked(true);
                }
            }

            hae.setText(String.valueOf(haemo));
            bld_sug.setText(String.valueOf(bsgr));
            bld_pr.setText(String.valueOf(bpr));
        }


        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),ConfigHome.class);
                startActivity(i);
            }
        });

        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle extras = getIntent().getExtras();
                if(extras!=null) {
                    pid= extras.getInt("pid");
                }

                if(c1.isChecked())
                {
                    vomit="yes";
                }
                else
                {
                    vomit="no";
                }
                if(c2.isChecked())
                {
                    nausea="yes";
                }
                else
                {
                    nausea="no";
                }
                if(c3.isChecked())
                {
                    appe="yes";
                }
                else
                {
                    appe="no";
                }
                if(c4.isChecked())
                {
                    dizzi="yes";
                }
                else
                {
                    dizzi="no";
                }

                if(!hae.getText().toString().equals("")) {
                    haemo=Double.parseDouble(hae.getText().toString());
                }
                if(!bld_sug.getText().toString().equals("")) {
                    bsgr=Integer.parseInt(bld_sug.getText().toString());
                }
                if(!bld_pr.getText().toString().equals("")) {
                    bpr=Integer.parseInt(bld_pr.getText().toString());
                }

                Intent j=new Intent(getApplicationContext(),PreDef12.class);
                j.putExtra("vomit", vomit);
                j.putExtra("nausea",nausea);
                j.putExtra("appetite",appe);
                j.putExtra("dizziness",dizzi);
                j.putExtra("haemo",haemo);
                j.putExtra("hiv",hivv);
                j.putExtra("group",bgr);
                j.putExtra("sugar",bsgr);
                j.putExtra("pressure",bpr);
                j.putExtra("pid",pid);
                j.putExtra("sonography",sono);
                startActivity(j);
            }
        });
    }
}
