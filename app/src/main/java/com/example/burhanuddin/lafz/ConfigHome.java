package com.example.burhanuddin.lafz;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cafe.adriel.androidaudiorecorder.AndroidAudioRecorder;
import cafe.adriel.androidaudiorecorder.model.AudioChannel;
import cafe.adriel.androidaudiorecorder.model.AudioSampleRate;
import cafe.adriel.androidaudiorecorder.model.AudioSource;

public class ConfigHome extends AppCompatActivity {

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static final int REQUEST_RECORDING_ACTIVITY = 300;
    private static final int PICK_FILE_REQUEST = 1;

    TrimesterDBHandler tdb;
    CounsellorDBHandler auth;
    EditText pid;
    int pidval = 0;
    String username = "", filePath="", upload_file_path="", alternate_filename="";
    RequestQueue requestQueue;
    ProgressDialog loading_dialog;
    boolean trimsuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_home);

        getRecordAudioPermission();

        tdb = new TrimesterDBHandler(getApplicationContext());
        auth = new CounsellorDBHandler(getApplicationContext());
        final RadioGroup ptype = (RadioGroup)findViewById(R.id.ptype);
        final RadioGroup trimsel = (RadioGroup)findViewById(R.id.trimsel);
        pid = (EditText)findViewById(R.id.pid);
        Button predef = (Button)findViewById(R.id.predef);
        Button live = (Button)findViewById(R.id.liv);
        Button logout = (Button)findViewById(R.id.logout);
        Button chooser = (Button)findViewById(R.id.chooser);
        requestQueue = Volley.newRequestQueue(this);
        loading_dialog = new ProgressDialog(this);
        trimsuccess = false;
//        final EditText ServerIP = (EditText)findViewById(R.id.ServerIP2);
//        Button storeIP = (Button)findViewById(R.id.storeIP);

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

//        storeIP.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                URLs.ROOT_IP = ServerIP.getText().toString();
//                URLs.setURLs();
//            }
//        });

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

                updatePreDef();
                int ans = trimsel.getCheckedRadioButtonId();
                if(ans==R.id.first)
                {
                    if(!pid.getText().toString().equals("")) {
                        pidval = Integer.parseInt(pid.getText().toString());
                    }
                    if(ptype.getCheckedRadioButtonId() == R.id.oldrb)
                    {
                        Intent i = new Intent(getApplicationContext(),PreDef11.class);
                        i.putExtra("pid",pidval);
                        startActivity(i);
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
                        Intent j=new Intent(getApplicationContext(),PreDef21.class);
                        j.putExtra("pid",pidval);
                        startActivity(j);
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
                        Intent k=new Intent(getApplicationContext(),PreDef31.class);
                        k.putExtra("pid",pidval);
                        startActivity(k);
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

                String folderPath = Environment.getExternalStorageDirectory().getPath();
                File folder = new File(folderPath, "Lafz");
                if (!folder.exists()) {
                    folder.mkdirs();
                }
                filePath = folder.getAbsolutePath() +"/"+pidval + ".wav";
                int color = getResources().getColor(R.color.colorPrimaryDark);
                AndroidAudioRecorder.with(ConfigHome.this)
                        // Required
                        .setFilePath(filePath)
                        .setColor(color)
                        .setRequestCode(REQUEST_RECORDING_ACTIVITY)

                        // Optional
                        .setSource(AudioSource.MIC)
                        .setChannel(AudioChannel.MONO)
                        .setSampleRate(AudioSampleRate.HZ_16000)
                        .setAutoStart(false)
                        .setKeepDisplayOn(true)

                        // Start recording
                        .record();
            }
        });

        chooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload_file_path = "";
                alternate_filename = "";
                showFileChooser();
            }
        });
    }

    public void getRecordAudioPermission()
    {
        if (ContextCompat.checkSelfPermission(ConfigHome.this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(ConfigHome.this,
                    Manifest.permission.RECORD_AUDIO)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(ConfigHome.this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        REQUEST_RECORD_AUDIO_PERMISSION);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_RECORD_AUDIO_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    getRecordAudioPermission();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_RECORDING_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), filePath, Toast.LENGTH_LONG).show();
                PreDef12 pre1 = new PreDef12();
                pre1.sendTrim1Data(getApplicationContext(), pid.getText().toString(), "no", "no", "no", "no", "0", "no", "0", "O+", "no", "0");
                // Great! User has recorded and saved the audio file
//                try {
//                    final String uploadId =
//                            new MultipartUploadRequest(getApplicationContext(), URLs.URL_SEND_AUDIO_FILES)
//                                    // starting from 3.1+, you can also use content:// URI string instead of absolute file
//                                    .addFileToUpload(filePath, "audio name")
//                                    .setNotificationConfig(new UploadNotificationConfig())
//                                    .setMaxRetries(2)
//                                    .setDelegate(new UploadStatusDelegate() {
//                                        @Override
//                                        public void onProgress(Context context, UploadInfo uploadInfo) {
//
//                                        }
//
//                                        @Override
//                                        public void onError(Context context, UploadInfo uploadInfo, Exception exception) {
//                                            Toast.makeText(getApplicationContext(),"Upload error fn Toasts next",Toast.LENGTH_LONG).show();
//                                            Toast.makeText(getApplicationContext(), uploadInfo.toString(), Toast.LENGTH_LONG).show();
//                                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
//                                        }
//
//                                        @Override
//                                        public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
//                                            Toast.makeText(getApplicationContext(), serverResponse.getBodyAsString(), Toast.LENGTH_LONG).show();
//                                            if(!pid.getText().toString().equals("")) {
//                                                pidval=Integer.parseInt(pid.getText().toString());
//                                            }
//                                            Intent x = new Intent(getApplicationContext(),DisplayResult.class);
//                                            x.putExtra("pid",pidval);
//                                            startActivity(x);
//                                        }
//
//                                        @Override
//                                        public void onCancelled(Context context, UploadInfo uploadInfo) {
//
//                                        }
//                                    })
//                                    .startUpload();
//                    // output.setText(uploadId);
//                } catch (Exception exc) {
//                    Toast.makeText(getApplicationContext(), "AndroidUploadService" + exc.getMessage(), Toast.LENGTH_LONG).show();
//                }
            } else if (resultCode == RESULT_CANCELED) {
                // Oops! User has canceled the recording
            }
        }
        if(requestCode == PICK_FILE_REQUEST){
            if(resultCode == Activity.RESULT_OK){
                if(data == null){
                    //no data present
                    Toast.makeText(getApplicationContext(), "No data present!", Toast.LENGTH_LONG).show();
                    return;
                }
                Uri selectedFileUri = data.getData();
                Toast.makeText(getApplicationContext(),"Last path segment = "+ selectedFileUri.getLastPathSegment(),Toast.LENGTH_LONG).show();
                upload_file_path = FilePath.getPath(this,selectedFileUri);
                alternate_filename = upload_file_path.substring(upload_file_path.lastIndexOf('/')+1, upload_file_path.length()-4);

                if(upload_file_path != null && !upload_file_path.equals("")){
                    Toast.makeText(this,"Selected File Path = "+upload_file_path,Toast.LENGTH_SHORT).show();
                    loading_dialog.setMessage("Uploading file...");
                    loading_dialog.show();
                    uploadFile();
                }else{
                    Toast.makeText(this,"Cannot upload file to server",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        updatePreDef();
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

        int tempPID = (1000+CID)*1000 + 1;
        int trim1PID = tempPID, trim2PID = tempPID, trim3PID = tempPID;

        int i,j,k;
        for(i=0; i<998; i++)
        {
            String exists = tdb.dataexist1(trim1PID+i);
            if(exists.equals("false"))
                break;
        }
        for(j=0; j<998; j++)
        {
            String exists = tdb.dataexist2(trim2PID+j);
            if(exists.equals("false"))
                break;
        }
        for(k=0; k<998; k++)
        {
            String exists = tdb.dataexist3(trim3PID+k);
            if(exists.equals("false"))
                break;
        }

        int max = 0;
        if(i > max)
            max = i;
        if(j > max)
            max = j;
        if(k > max)
            max = k;

        int newPID = 0;
        newPID = 1000 + CID;
        newPID = newPID * 1000 + max + 1;
        return newPID;
    }

    public void updatePreDef()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_GET_TRIM1_DETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JsonParser j = new JsonParser();
                        String trim1[][] = j.Trim1Info(response);
                        tdb.truncateAllTrims();
                        for(int i=0; i<trim1.length; i++)
                        {
                            if(trim1[i][0]==null || trim1[i][0].isEmpty())
                                break;
                            tdb.createEntry1(Integer.parseInt(trim1[i][0]), trim1[i][1], trim1[i][2], trim1[i][3], trim1[i][4], Double.parseDouble(trim1[i][5]), trim1[i][6], Integer.parseInt(trim1[i][7]), trim1[i][8], trim1[i][9], Integer.parseInt(trim1[i][10]));
                        }
                        trimsuccess = true;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                                Toast.makeText(Homepage.this,error.toString(),Toast.LENGTH_LONG).show();
                        Toast.makeText(ConfigHome.this,"Offline mode. Modificaions will NOT be saved.",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                return params;
            }
        };
        requestQueue.add(stringRequest);

        if(trimsuccess)
        {
            StringRequest stringRequest2 = new StringRequest(Request.Method.POST, URLs.URL_GET_TRIM2_DETAILS,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JsonParser j = new JsonParser();
                            String trim2[][] = j.Trim2Info(response);
                            for(int i=0; i<trim2.length; i++)
                            {
                                if(trim2[i][0]==null || trim2[i][0].isEmpty())
                                    break;
                                tdb.createEntry2(Integer.parseInt(trim2[i][0]), trim2[i][1], trim2[i][2], trim2[i][3], trim2[i][4], Double.parseDouble(trim2[i][5]), Integer.parseInt(trim2[i][6]), trim2[i][7], trim2[i][8], trim2[i][9], Integer.parseInt(trim2[i][10]));
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                                Toast.makeText(Homepage.this,error.toString(),Toast.LENGTH_LONG).show();
                        }
                    }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    return params;
                }
            };
            requestQueue.add(stringRequest2);

            StringRequest stringRequest3 = new StringRequest(Request.Method.POST, URLs.URL_GET_TRIM3_DETAILS,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JsonParser j = new JsonParser();
                            String trim3[][] = j.Trim3Info(response);
                            for(int i=0; i<trim3.length; i++)
                            {
                                if(trim3[i][0]==null || trim3[i][0].isEmpty())
                                    break;
                                tdb.createEntry3(Integer.parseInt(trim3[i][0]), trim3[i][1], trim3[i][2], Double.parseDouble(trim3[i][3]), Integer.parseInt(trim3[i][4]), trim3[i][5], trim3[i][6], Integer.parseInt(trim3[i][7]));
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                                Toast.makeText(Homepage.this,error.toString(),Toast.LENGTH_LONG).show();
                        }
                    }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    return params;
                }
            };
            requestQueue.add(stringRequest3);
            trimsuccess = false;
        }
    }

    public void uploadFile(){
        try {
            Toast.makeText(getApplicationContext(), upload_file_path + " from upload file fn", Toast.LENGTH_LONG).show();
            final String uploadId =
                    new MultipartUploadRequest(getApplicationContext(), URLs.URL_SEND_AUDIO_FILES)
                            // starting from 3.1+, you can also use content:// URI string instead of absolute file
                            .addFileToUpload(upload_file_path, "audio name")
                            .setNotificationConfig(new UploadNotificationConfig())
                            .setMaxRetries(2)
                            .setDelegate(new UploadStatusDelegate() {
                                @Override
                                public void onProgress(Context context, UploadInfo uploadInfo) {

                                }

                                @Override
                                public void onError(Context context, UploadInfo uploadInfo, Exception exception) {
                                    if (loading_dialog != null)
                                        loading_dialog.dismiss();
                                    Toast.makeText(getApplicationContext(),"Upload error fn Toasts next",Toast.LENGTH_LONG).show();
                                    Toast.makeText(getApplicationContext(), uploadInfo.toString(), Toast.LENGTH_LONG).show();
                                    Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                                    if (loading_dialog != null)
                                        loading_dialog.dismiss();
                                    Toast.makeText(getApplicationContext(),"on completed",Toast.LENGTH_LONG).show();
                                    String response = serverResponse.getBodyAsString();
                                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                                    if(!alternate_filename.isEmpty())
                                        pidval = Integer.parseInt(alternate_filename);
                                    else if(!pid.getText().toString().equals("")) {
                                        pidval=Integer.parseInt(pid.getText().toString());
                                    }
                                    if(response.equals("success"))
                                    {
                                        Intent x = new Intent(getApplicationContext(),DisplayResult.class);
                                        x.putExtra("pid",pidval);
                                        startActivity(x);
                                    }
                                    else if(response.equals("failed"))
                                    {
                                        Toast.makeText(getApplicationContext(), "Upload failed on server", Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Invalid response", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onCancelled(Context context, UploadInfo uploadInfo) {
                                    if (loading_dialog != null)
                                        loading_dialog.dismiss();
                                    Toast.makeText(getApplicationContext(),"on cancelled",Toast.LENGTH_LONG).show();
                                }
                            })
                            .startUpload();
            // output.setText(uploadId);
        } catch (Exception exc) {
            Toast.makeText(getApplicationContext(), "AndroidUploadService" + exc.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        //sets the select file to all types of files
        intent.setType("*/*");
        //allows to select data and return it
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //starts new activity to select file and return data
        startActivityForResult(Intent.createChooser(intent,"Choose File to Upload.."),PICK_FILE_REQUEST);
    }
}
