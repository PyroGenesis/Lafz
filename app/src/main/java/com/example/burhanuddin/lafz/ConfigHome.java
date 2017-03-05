package com.example.burhanuddin.lafz;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ConfigHome extends AppCompatActivity {

    TrimesterDBHandler tdb;
    CounsellorDBHandler auth;
    int pidval = 0;
    String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_home);

        tdb = new TrimesterDBHandler(getApplicationContext());
        auth = new CounsellorDBHandler(getApplicationContext());
        final RadioGroup ptype = (RadioGroup)findViewById(R.id.ptype);
        final RadioGroup trimsel = (RadioGroup)findViewById(R.id.trimsel);
        final EditText pid = (EditText)findViewById(R.id.pid);
        Button predef = (Button)findViewById(R.id.predef);
        Button live = (Button)findViewById(R.id.liv);
        Button logout = (Button)findViewById(R.id.logout);


        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            username = extras.getString("username");
        }

        if(ptype.getCheckedRadioButtonId() == R.id.newrb)
        {
            int newPID = generatePID(username);
            //Toast.makeText(ConfigHome.this, (username + newPID), Toast.LENGTH_LONG).show();
            pid.setText(Integer.toString(newPID));
            pid.setEnabled(false);
            //pid.setFocusable(false);
        }


        ptype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.newrb)
                {
                    int newPID = generatePID(username);
                    pid.setText(Integer.toString(newPID));
                    pid.setEnabled(false);
                    //pid.setFocusable(false);
                }
                else
                {
                    pid.setEnabled(true);
                    //pid.setFocusable(true);
                    pid.setText("");
                    //pid.requestFocus();
                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.LogOut(username);
                Intent in = new Intent(getApplicationContext(), Homepage.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(in);
            }
        });


        predef.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int ans = trimsel.getCheckedRadioButtonId();
                if(ans==R.id.first)
                {
                    if(!pid.getText().toString().equals("")) {
                        pidval = Integer.parseInt(pid.getText().toString());
                    }
                    if(ptype.getCheckedRadioButtonId() == R.id.oldrb)
                    {
                        String data1 = tdb.dataexist1(pidval);
                        if (data1 == "false")
                        {
                            Toast.makeText(ConfigHome.this, "Data does not exist for trimester 1", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Intent i = new Intent(getApplicationContext(),PreDef11.class);
                            i.putExtra("pid",pidval);
                            startActivity(i);
                        }
                    }
                    else
                    {
                        Intent i = new Intent(getApplicationContext(),PreDef11.class);
                        i.putExtra("pid",pidval);
                        startActivity(i);
                    }
                }
                if(ans==R.id.sec)
                {
                    if(!pid.getText().toString().equals("")) {
                        pidval=Integer.parseInt(pid.getText().toString());
                    }
                    if(ptype.getCheckedRadioButtonId() == R.id.oldrb)
                    {
                        String data2 = tdb.dataexist2(pidval);
                        if (data2 == "false")
                        {
                            Toast.makeText(ConfigHome.this, "Data does not exist for trimester 2", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Intent j=new Intent(getApplicationContext(),PreDef21.class);
                            j.putExtra("pid",pidval);
                            //j.putExtra("old",old2);
                            startActivity(j);
                        }
                    }
                    else
                    {
                        Intent j=new Intent(getApplicationContext(),PreDef21.class);
                        j.putExtra("pid",pidval);
                        //j.putExtra("old",old2);
                        startActivity(j);
                    }

                }
                if(ans==R.id.third)
                {
                    if(!pid.getText().toString().equals("")) {
                        pidval=Integer.parseInt(pid.getText().toString());
                    }
                    if(ptype.getCheckedRadioButtonId() == R.id.oldrb)
                    {
                        String data3 = tdb.dataexist3(pidval);
                        if (data3 == "false")
                        {
                            Toast.makeText(ConfigHome.this, "Data does not exist for trimester 3", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Intent k=new Intent(getApplicationContext(),PreDef31.class);
                            k.putExtra("pid",pidval);
                            //k.putExtra("old",old3);
                            startActivity(k);
                        }
                    }
                    else
                    {
                        Intent k=new Intent(getApplicationContext(),PreDef31.class);
                        k.putExtra("pid",pidval);
                        //k.putExtra("old",old3);
                        startActivity(k);
                    }
                }
            }
        });

        live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!pid.getText().toString().equals("")) {
                    pidval=Integer.parseInt(pid.getText().toString());
                }

                int a = 0;
                int ans=trimsel.getCheckedRadioButtonId();

                if(ans==R.id.first)
                {
                    a=1;
                }
                if(ans==R.id.sec)
                {
                    a=2;
                }
                if(ans==R.id.third)
                {
                    a=3;
                }

                Intent x = new Intent(getApplicationContext(),RecordWav.class);
                x.putExtra("pid",pidval);
                x.putExtra("trim",a);
                startActivity(x);

            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        RadioGroup ptype = (RadioGroup)findViewById(R.id.ptype);
        final EditText pid = (EditText)findViewById(R.id.pid);
        if(ptype.getCheckedRadioButtonId() == R.id.newrb)
        {
            int newPID = generatePID(username);
            pid.setText(Integer.toString(newPID));
            pid.setEnabled(false);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exiting App")
                .setCancelable(false)
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Leave", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                        homeIntent.addCategory( Intent.CATEGORY_HOME );
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(homeIntent);
                    }

                })
                .setNegativeButton("Stay", null)
                .show();
    }

    public int generatePID(String username) {
        CounsellorDBHandler forPID = new CounsellorDBHandler(getApplicationContext());
        int CID = forPID.getCIDfromName(username);
        int patients = forPID.getVisitNo(username);

        int newPID = 0;
        newPID = 1000 + CID;
        newPID = newPID * 1000 + patients + 1;
        return newPID;
    }
}
