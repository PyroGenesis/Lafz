package com.example.burhanuddin.lafz;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.text.Editable;
import android.text.TextWatcher;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Homepage extends AppCompatActivity {

    CounsellorDBHandler auth;
    EditText Name, password, ServerIP;
    String userName,Password;
    ProgressBar progressBar;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        auth = new CounsellorDBHandler(getApplicationContext());
        Name = (EditText) findViewById(R.id.nametext);
        password = (EditText) findViewById(R.id.passtext);
        final Button login = (Button) findViewById(R.id.login);
//        final Button register = (Button) findViewById(R.id.register);
        ServerIP = (EditText)findViewById(R.id.ServerIP);
        requestQueue = Volley.newRequestQueue(this);
//        register.setEnabled(false);

//        Name.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String s1 = Name.getText().toString();
//                String s2 = password.getText().toString();
//                if (s1.equals("") && s2.equals("")) {
//                    register.setEnabled(false);
//                } else if (!s1.equals("") && s2.equals("")) {
//                    register.setEnabled(false);
//                } else if (!s2.equals("") && s1.equals("")) {
//                    register.setEnabled(false);
//                } else {
//                    register.setEnabled(true);
//                }
//            }
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//                // TODO Auto-generated method stub
//            }
//            @Override
//            public void afterTextChanged(Editable s) {
//                // TODO Auto-generated method stub
//            }
//        });
//        password.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String s1 = Name.getText().toString();
//                String s2 = password.getText().toString();
//                if (s1.equals("") && s2.equals("")) {
//                    register.setEnabled(false);
//                } else if (!s1.equals("") && s2.equals("")) {
//                    register.setEnabled(false);
//                } else if (!s2.equals("") && s1.equals("")) {
//                    register.setEnabled(false);
//                } else {
//                    register.setEnabled(true);
//                }
//            }
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//                // TODO Auto-generated method stub
//            }
//            @Override
//            public void afterTextChanged(Editable s) {
//                // TODO Auto-generated method stub
//            }
//        });


        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                URLs.ROOT_IP = ServerIP.getText().toString();
                URLs.setURLs();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_LOGIN_DETAILS,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                JsonParser j = new JsonParser();
                                String users[][] = j.LoginDetails(response);

                                auth.truncate();
                                for(int i=0; i<users.length; i++)
                                {
                                    if(users[i][0]==null || users[i][0].isEmpty())
                                        break;
                                    auth.register(users[i][0], users[i][1]);
                                }

                                boolean didItWork = true;
                                String userName=Name.getText().toString();
                                String Password=password.getText().toString();
                                String storedPassword = auth.authRetPass(userName);


                                if (storedPassword.equals(Password) && !storedPassword.equals("incorrectPassword")) {
                                    Toast.makeText(Homepage.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    auth.LogIn(userName);
                                }
                                else
                                {
                                    Toast.makeText(Homepage.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                                    didItWork = false;
                                }
                                if(didItWork)
                                {
                                    Intent intent = new Intent(Homepage.this, ConfigHome.class);
                                    intent.putExtra("username",userName);
                                    startActivity(intent);
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
//                                Toast.makeText(Homepage.this,error.toString(),Toast.LENGTH_LONG).show();
                                String data = auth.getData();
                                if(data.isEmpty())
                                {
                                    Toast.makeText(Homepage.this,"You need to connect to the Internet for first login attempt.",Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Toast.makeText(Homepage.this,"No Internet connection. Using offline login...",Toast.LENGTH_LONG).show();
                                    boolean didItWork = true;
                                    String userName=Name.getText().toString();
                                    String Password=password.getText().toString();
                                    String storedPassword = auth.authRetPass(userName);

                                    if (storedPassword.equals(Password) && !storedPassword.equals("incorrectPassword")) {
                                        Toast.makeText(Homepage.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        auth.LogIn(userName);
                                    }
                                    else
                                    {
                                        Toast.makeText(Homepage.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                                        didItWork = false;
                                    }
                                    if(didItWork)
                                    {
                                        Intent intent = new Intent(Homepage.this, ConfigHome.class);
                                        intent.putExtra("username",userName);
                                        startActivity(intent);
                                    }
                                }
                            }
                        }){
                    @Override
                    protected Map<String,String> getParams(){

                        Map<String,String> params = new HashMap<String, String>();
//                        params.put(USERNAME_KEY,username);
//                        params.put(PASSWORD_KEY,password);
                        // params.put(SERVER_KEY,servertext);
                        return params;
                    }

                };
                requestQueue.add(stringRequest);
                //userLogin();
            }
        });

//        register.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                boolean didItWork = true;
//                boolean ok=true;
//                String name = Name.getText().toString();
//                String pass = password.getText().toString();
//                if (Name.getText().toString().contains(" ")||Name.getText().toString().contains("\\")||Name.getText().toString().contains("/")) {
//                    didItWork=false;
//                    Name.setError("No Spaces,/ and \\ Allowed");
//                    Toast.makeText(Homepage.this, "No Spaces Allowed",Toast.LENGTH_SHORT).show();
//                }
//                if (auth.Exists(name)){
//                    didItWork=false;
//                    ok=false;
//                    Toast.makeText(getApplicationContext(), "Username already exist!", Toast.LENGTH_SHORT).show();}
//                try{
//                    if(ok)
//                    auth.register(name,pass);
//                }
//                catch(Exception e){
//                    didItWork = false;
//                    Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
//                }
//                finally {
//                    if(didItWork)
//                        Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


        String checkLog = auth.checkLoggedIn();
        if(!checkLog.equals(""))
        {
            URLs.ROOT_IP = ServerIP.getText().toString();
            URLs.setURLs();
            Intent intent = new Intent(Homepage.this, ConfigHome.class);
            intent.putExtra("username",checkLog);
            Toast.makeText(Homepage.this, ("Logged in as: "+checkLog), Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
    }

    private class internetPingCheck extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {}

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                URL url = new URL("http://khalsancc.96.lt");   // Change to "http://google.com" for www  test.
                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                urlc.setConnectTimeout(1500);
                urlc.setReadTimeout(1500);									// 10 s.
                urlc.connect();
                if (urlc.getResponseCode() == 200) {        // 200 = "OK" code (http connection is fine).
                    Log.wtf("Connection", "Success !");
                    return true;
                } else {
                    return false;
                }
            } catch (MalformedURLException e1) {
                return false;
            } catch (IOException e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            boolean bResponse = result;
            if (bResponse==true)
            {
                Toast.makeText(Homepage.this, "Network is available", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(Homepage.this, "Network is not available", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Name.setText("");
        password.setText("");
        String checkLog = auth.checkLoggedIn();
        if(!checkLog.equals(""))
        {
            URLs.ROOT_IP = ServerIP.getText().toString();
            URLs.setURLs();
            Intent intent = new Intent(Homepage.this, ConfigHome.class);
            intent.putExtra("username",checkLog);
            Toast.makeText(Homepage.this, ("Logged in as: "+checkLog), Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
    }

    private void userLogin() {

        userName = Name.getText().toString();
        Password = password.getText().toString();

        if (userName.equals("a") && Password.equals("a")) {
            Toast.makeText(getApplicationContext(), "Hello Admin", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), ConfigHome.class));
        } else {
            //if everything is fine
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_LOGIN,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressBar.setVisibility(View.GONE);

                            try {
                                if (!response.equals("Welcome! Login successful")) {
                                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), ConfigHome.class));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
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
                    params.put("username", userName);
                    params.put("password", Password);
                    return params;
                }
            };

            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        }
    }
}
