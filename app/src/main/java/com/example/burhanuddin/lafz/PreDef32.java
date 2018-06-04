package com.example.burhanuddin.lafz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PreDef32 extends AppCompatActivity {

    TrimesterDBHandler tdb;
    CounsellorDBHandler incVisit;
    public int ans1,ans2,pid,bsgr,bpre,a=3;
    public String son="",abno="",pain="",bleed="";
    double haemo=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_def32);

        tdb = new TrimesterDBHandler(getApplicationContext());
        incVisit = new CounsellorDBHandler(getApplicationContext());
        Button b1=(Button)findViewById(R.id.backToMain);
        Button b2=(Button)findViewById(R.id.live);
        Button b3=(Button)findViewById(R.id.store);
        Button b4=(Button)findViewById(R.id.viewData);

        final RadioGroup sono=(RadioGroup)findViewById(R.id.sono3);
        final RadioGroup abn=(RadioGroup)findViewById(R.id.bp3);
        final RadioButton y=(RadioButton)findViewById(R.id.sono3y);
        final RadioButton n=(RadioButton)findViewById(R.id.sono3n);
        final RadioButton aby=(RadioButton)findViewById(R.id.abn3y);
        final RadioButton abnoo=(RadioButton)findViewById(R.id.abn3n);


        final TextView datav=(TextView)findViewById(R.id.dataview3);

        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            son=extras.getString("son");
            abno=extras.getString("abno");
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
                }

                Intent j=new Intent(getApplicationContext(),RecordWav.class);
                j.putExtra("pid",pid);
                j.putExtra("trim",a);
                startActivity(j);

            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle extras = getIntent().getExtras();
                if(extras!=null) {
                    pid= extras.getInt("pid");
                    haemo=extras.getDouble("haemo");
                    bsgr=extras.getInt("bsgr");
                    pain=extras.getString("pain");
                    bleed=extras.getString("bleed");
                    bpre=extras.getInt("bpre");
                    son=extras.getString("son");
                    abno=extras.getString("abno");
                }

                ans1=sono.getCheckedRadioButtonId();

                if(ans1==R.id.sono3y)
                {
                    son="yes";
                }
                if(ans1==R.id.sono3n)
                {
                    son="no";
                }

                ans2=abn.getCheckedRadioButtonId();

                if(ans2==R.id.abn3y)
                {
                    abno="yes";
                }
                if(ans2==R.id.abn3n)
                {
                    abno="no";
                }

                boolean didItWork = true;
                try {
                    String data3 = tdb.dataexist3(pid);
                    if (data3 == "false") {
                        incVisit.incVisitNo(pid);
                    }
                    tdb.deleteEntry3(pid);
                    tdb.createEntry3(pid,pain,bleed, haemo, bsgr,son,abno, bpre);
                    sendTrim3Data(String.valueOf(pid),pain,bleed, String.valueOf(haemo), String.valueOf(bsgr),son,abno, String.valueOf(bpre));
                } catch (Exception e) {
                    didItWork = false;
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                } finally {
                    if (didItWork)
                        Toast.makeText(getApplicationContext(), "Data entered", Toast.LENGTH_SHORT).show();
                }
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ViewData3.class);
                startActivity(i);
            }
        });
    }

    public void sendTrim3Data(final String pid,final String pain,final String  bleed,final String haemo, final String bsgr, final String son, final String abno,final String bpr) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_SEND_TRIM3_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("pID", pid );
                params.put("pain", pain);
                params.put("bleeding", bleed);
                params.put("haemoglobin", haemo);
                params.put("bloodSugar", bsgr);
                params.put("sonography", son);
                params.put("abnormality",  abno);
                params.put("bloodPressure", bpr);

                return params;
            }
        };


        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
