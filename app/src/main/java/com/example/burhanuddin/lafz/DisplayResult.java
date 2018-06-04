package com.example.burhanuddin.lafz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class DisplayResult extends AppCompatActivity {

    private final String PID_KEY = "pid";
    int pid = 0;
    RequestQueue requestQueue;
    ProgressDialog loading_dialog;
    TextView hindi_view, english_view;
    StringRequest request1, request2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_result);

        hindi_view = (TextView)findViewById(R.id.view_hindi);
        english_view = (TextView)findViewById(R.id.view_english);
        requestQueue = Volley.newRequestQueue(this);
        loading_dialog = new ProgressDialog(this);

        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            pid= extras.getInt("pid");
        }

        request1 = new StringRequest(Request.Method.POST, URLs.URL_GET_TRANSCRIPTION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (loading_dialog != null)
                            loading_dialog.dismiss();
                        Toast.makeText(DisplayResult.this,"transcription: "+response,Toast.LENGTH_LONG).show();
                        hindi_view.setText(response);

                        requestQueue.add(request2);
                        loading_dialog.setMessage("Translating the audio...");
                        loading_dialog.show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (loading_dialog != null)
                            loading_dialog.dismiss();
                        Toast.makeText(DisplayResult.this,error.toString(),Toast.LENGTH_LONG).show();
//                        finish();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(PID_KEY,String.valueOf(pid));
                return params;
            }

        };
        request1.setRetryPolicy(new DefaultRetryPolicy(
                300000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request1);
        loading_dialog.setMessage("Transcribing the audio...");
        loading_dialog.show();

        request2 = new StringRequest(Request.Method.POST, URLs.URL_GET_TRANSLATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (loading_dialog != null)
                            loading_dialog.dismiss();
                        Toast.makeText(DisplayResult.this,"translation: "+response,Toast.LENGTH_LONG).show();
                        english_view.setText(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (loading_dialog != null)
                            loading_dialog.dismiss();
                        //Toast.makeText(DisplayResult.this,"Translation Toasts next",Toast.LENGTH_LONG).show();
                        //Toast.makeText(DisplayResult.this,"Get getCause Toast="+error.getCause(),Toast.LENGTH_LONG).show();
                        //Toast.makeText(DisplayResult.this,"Get StackTrace Toast="+error.getStackTrace().toString(),Toast.LENGTH_LONG).show();
                        //Toast.makeText(DisplayResult.this,"Get toString Toast="+error.getMessage(),Toast.LENGTH_LONG).show();
//                        getCause.setText(error.getCause().toString());
//                        getMessage.setText(error.getStackTrace().toString());
//                        stackTrace.setText(error.getMessage());
                        Toast.makeText(DisplayResult.this,error.toString(),Toast.LENGTH_LONG).show();
//                        finish();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){

                Map<String,String> params = new HashMap<String, String>();
                params.put(PID_KEY,String.valueOf(pid));
                return params;
            }

        };
        request2.setRetryPolicy(new DefaultRetryPolicy(
                300000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        requestQueue.add(request2);
//        loading_dialog.setMessage("Translating the audio...");
//        loading_dialog.show();
    }
}
