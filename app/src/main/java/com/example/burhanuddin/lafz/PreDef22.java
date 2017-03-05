package com.example.burhanuddin.lafz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class PreDef22 extends AppCompatActivity {

    public int ans1,ans2,bpre;
    public String son,abno;
    String vomit="no",nausea="no",appe="no",dizzi="no",fre="";
    int pid=0, bsgr=0, bpr=0,ans;
    double haemo=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_def22);

        Button b1=(Button)findViewById(R.id.ret);
        Button b2=(Button)findViewById(R.id.nextp);

        final RadioGroup sono=(RadioGroup)findViewById(R.id.sono2);
        final RadioGroup abn=(RadioGroup)findViewById(R.id.abn2);
        final RadioButton y=(RadioButton)findViewById(R.id.sono2y);
        final RadioButton n=(RadioButton)findViewById(R.id.sono2n);
        final RadioButton aby=(RadioButton)findViewById(R.id.abn2y);
        final RadioButton abnoo=(RadioButton)findViewById(R.id.abn2n);

        final EditText bld_pr=(EditText)findViewById(R.id.bldp2);

        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            son=extras.getString("son");
            abno=extras.getString("abno");
            bpr=extras.getInt("pressure");
        }
        if(son!=null)
        {
            if(son.equals("yes"))
            {
                y.setChecked(true);
            }
            else
            {
                n.setChecked(true);
            }

        }
        if(abno!=null)
        {
            if(abno.equals("yes"))
            {
                aby.setChecked(true);
            }
            else
            {
                abnoo.setChecked(true);
            }

        }
        if(bpr!=0)
        {
            bld_pr.setText(String.valueOf(bpr));
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),ConfigHome.class);
                startActivity(i);

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle extras = getIntent().getExtras();
                if(extras!=null) {
                    pid= extras.getInt("pid");
                    haemo=extras.getDouble("haemo");
                    bsgr=extras.getInt("bsgr");
                    fre=extras.getString("fre");
                    vomit=extras.getString("vomit");
                    nausea=extras.getString("nausea");
                    appe=extras.getString("appetite");
                    dizzi=extras.getString("dizziness");
                    son=extras.getString("son");
                    abno=extras.getString("abno");
                }

                ans1=sono.getCheckedRadioButtonId();

                if(ans1==R.id.sono2y)
                {
                    son="yes";
                }
                if(ans1==R.id.sono2n)
                {
                    son="no";
                }

                ans2=abn.getCheckedRadioButtonId();

                if(ans2==R.id.abn2y)
                {
                    abno="yes";
                }
                if(ans2==R.id.abn2n)
                {
                    abno="no";
                }

                if(!bld_pr.getText().toString().equals("")) {
                    bpr=Integer.parseInt(bld_pr.getText().toString());
                }

                Intent j=new Intent(getApplicationContext(),PreDef23.class);

                j.putExtra("fre",fre);
                j.putExtra("haemo",haemo);
                j.putExtra("bsgr",bsgr);
                j.putExtra("pid",pid);
                j.putExtra("son",son);
                j.putExtra("abno",abno);
                j.putExtra("bpre",bpr);
                j.putExtra("vomit", vomit);
                j.putExtra("nausea",nausea);
                j.putExtra("appetite",appe);
                j.putExtra("dizziness",dizzi);
                startActivity(j);

            }
        });
    }
}
