package com.example.burhanuddin.lafz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PreDef12 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TrimesterDBHandler tdb;
    CounsellorDBHandler incVisit;
    String vomit="",nausea="",appe="",dizzi="",hivv="",bgr="",sono="no";
    int pid=0, bsgr=0,ans=0,bpr=0,trimVal=1,place=0;
    double haemo=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_def12);

        tdb = new TrimesterDBHandler(getApplicationContext());
        incVisit = new CounsellorDBHandler(getApplicationContext());
        final RadioGroup sonoOptions=(RadioGroup)findViewById(R.id.q1);
        final Button b1=(Button)findViewById(R.id.backToMain);
        final Button b2=(Button)findViewById(R.id.live);
        final Button b3=(Button)findViewById(R.id.store);
        final Button b4=(Button)findViewById(R.id.view1);
        final RadioButton y=(RadioButton)findViewById(R.id.yes);
        final RadioButton n=(RadioButton)findViewById(R.id.no);
        final Spinner grp_name =(Spinner)findViewById(R.id.GRPname);
        final TextView t1=(TextView)findViewById(R.id.GRPdisp);

        String[] names=new String[]{" ","A+","B+","AB+","O+","A-","B-","AB-","O-"};
        ArrayAdapter<String> adap = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,names);
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assert grp_name != null;
        grp_name.setAdapter(adap);
        grp_name.setOnItemSelectedListener( this);


        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            sono=extras.getString("sonography");
            bgr=extras.getString("group");
        }
        if(sono!=null)
        {
            if(sono.equals("yes"))
            {
                y.setChecked(true);
            }
            else
            {
                n.setChecked(true);
            }

        }
      if(bgr!=null)
      {

          t1.setText(bgr);
          /*Toast.makeText(PreDef12.this,"place loop", Toast.LENGTH_LONG).show();
          if(bgr.equals("A+"))
          {
             place=1;
          }
         if(bgr.equals("B+"))
          {
              place=2;
          }
         /if(bgr.equals("AB+"))
          {
              place=3;
          }
         /if(bgr.equals("O+"))
          {
              place=4;
          }
          if(bgr.equals("A-"))
          {
              place=5;
          }
          if(bgr.equals("B-"))
          {
              place=6;
          }
          if(bgr.equals("AB-"))
          {
              place=7;
          }
          if(bgr.equals("O-"))
          {
              place =8;
          }
          if(bgr.equals("hey"))
          {
              Toast.makeText(PreDef12.this,"hey", Toast.LENGTH_LONG).show();
          }*/
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
                j.putExtra("trim",trimVal);
                startActivity(j);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                if(extras!=null)
                {
                    vomit=extras.getString("vomit");
                    nausea=extras.getString("nausea");
                    appe=extras.getString("appetite");
                    dizzi=extras.getString("dizziness");
                    haemo=extras.getDouble("haemo");
                    hivv=extras.getString("hiv");
                    bsgr=extras.getInt("sugar");
                    bpr=extras.getInt("pressure");
                    pid=extras.getInt("pid");
                    sono=extras.getString("sonography");
                }

                ans=sonoOptions.getCheckedRadioButtonId();
                if(ans==R.id.yes)
                {
                    sono="yes";
                }
                else
                {
                    sono="no";
                }

                boolean didItWork = true;
                try {
                    String data1 = tdb.dataexist1(pid);
                    if (data1 == "false") {
                        incVisit.incVisitNo(pid);
                    }
                    tdb.deleteEntry1(pid);
                    tdb.createEntry1(pid,vomit, nausea, appe, dizzi, haemo, hivv, bsgr, bgr, sono, bpr);
                    sendTrim1Data(getApplicationContext(), String.valueOf(pid),vomit, nausea, appe, dizzi, String.valueOf(haemo), hivv, String.valueOf(bsgr), bgr, sono, String.valueOf(bpr));

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

                Intent i = new Intent(getApplicationContext(), ViewData1.class);
                //i.putExtra("DATA",data);
                startActivity(i);
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {


        switch (position) {
            case 1:{
                bgr="A+";
            }
            break;
            case 2:{
                bgr="B+";
            }
            break;
            case 3:{
                bgr="AB+";
            }
            break;
            case 4:{
                bgr="O+";
            }
            break;
            case 5:{
                bgr="A-";
            }
            break;
            case 6:{
                bgr="B-";
            }
            break;
            case 7:{
                bgr="AB-";
            }
            break;
            case 8:{
                bgr="O-";
            }
            break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void sendTrim1Data(final Context c, final String pid, final String vomit, final String  nausea, final String  appe, final String dizzi, final String haemo, final String hivv, final String bsgr, final String bgr, final String sono, final String bpr) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_SEND_TRIM1_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(c, response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(c, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                params.put("hiv", hivv);
                params.put("bloodSugar", bsgr);
                params.put("bloodPressure", bpr);
                params.put("sonography", sono);
                params.put("bloodGroup",  bgr);
                return params;
            }
        };


        VolleySingleton.getInstance(c).addToRequestQueue(stringRequest);
    }

}
