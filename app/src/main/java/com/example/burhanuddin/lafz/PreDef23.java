package com.example.burhanuddin.lafz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PreDef23 extends AppCompatActivity {

    TrimesterDBHandler tdb;
    CounsellorDBHandler incVisit;
    public String vomit="no",nausea="no",appe="no",dizzi="no",fre="",son="",abno="";
    public int pid=0,bsgr=0,bpr=0,a=2;
    double haemo=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_def23);

        tdb = new TrimesterDBHandler(getApplicationContext());
        incVisit = new CounsellorDBHandler(getApplicationContext());
        Button b1=(Button)findViewById(R.id.backToMain);
        Button b2=(Button)findViewById(R.id.live);
        Button b3=(Button)findViewById(R.id.store);
        final Button b4=(Button)findViewById(R.id.viewData);

        final CheckBox c1=(CheckBox)findViewById(R.id.vom2);
        final CheckBox c2=(CheckBox)findViewById(R.id.nau2);
        final CheckBox c3=(CheckBox)findViewById(R.id.lape2);
        final CheckBox c4=(CheckBox)findViewById(R.id.diz2);

        final TextView datav=(TextView)findViewById(R.id.dataview2);

        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            vomit=extras.getString("vomit");
            nausea=extras.getString("nausea");
            appe=extras.getString("appetite");
            dizzi=extras.getString("dizziness");
        }
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

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), ConfigHome.class);
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
                    fre=extras.getString("fre");
                    vomit=extras.getString("vomit");
                    nausea=extras.getString("nausea");
                    appe=extras.getString("appetite");
                    dizzi=extras.getString("dizziness");
                    son=extras.getString("son");
                    abno=extras.getString("abno");
                    bpr=extras.getInt("bpre");
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


                boolean didItWork = true;
                try {
                    String data2 = tdb.dataexist2(pid);
                    if (data2 == "false") {
                        incVisit.incVisitNo(pid);
                    }
                    tdb.deleteEntry2(pid);
                    tdb.createEntry2(pid,vomit, nausea, appe, dizzi, haemo, bsgr,son,abno,fre, bpr);
                    sendTrim2Data(String.valueOf(pid),vomit, nausea, appe, dizzi, String.valueOf(haemo), String.valueOf(bsgr),son,abno,fre, String.valueOf(bpr));
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


                Intent i = new Intent(getApplicationContext(), ViewData2.class);

                startActivity(i);
            }
        });
    }

    public void sendTrim2Data(final String pid,final String vomit,final String  nausea,final String  appe,final String dizzi, final String haemo, final String bsgr, final String son, final String abno,final String fre, final String bpr) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_SEND_TRIM2_INFO,
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
                params.put("vomiting", vomit);
                params.put("LowAppetite", appe);
                params.put("nausea", nausea);
                params.put("dizziness", dizzi);
                params.put("haemoglobin", haemo);
                params.put("bloodSugar", bsgr);
                params.put("sonography", son);
                params.put("abnormality",  abno);
                params.put("frequency", fre);
                params.put("bloodPressure", bpr);

                return params;
            }
        };


        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
