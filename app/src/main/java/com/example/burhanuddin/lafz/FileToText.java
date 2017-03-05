package com.example.burhanuddin.lafz;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Config;
import edu.cmu.pocketsphinx.Decoder;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.Segment;
import edu.cmu.pocketsphinx.SpeechRecognizer;

public class FileToText extends AppCompatActivity implements RecognitionListener{

    private static final String KWS_SEARCH = "wakeup";
    private static final String FORECAST_SEARCH = "forecast";
    private static final String DIGITS_SEARCH = "digits";
    private static final String PHONE_SEARCH = "phones";
    private static final String MENU_SEARCH = "menu";
    String recognizedText,PassText;
    Button b2, comm, summ;
    int pid,trim, lang=1;
    Intent in;
    String vomit="no",appe="",dizzi="no",hivv="no",pain="no",sono="no";
    String type;

    /* Keyword we are looking for to activate menu */
    private static final String KEYPHRASE = "oh mighty computer";

    /* Used to handle permission request */
    private static final int PERMISSIONS_REQUEST_RECORD_AUDIO = 1;

    private SpeechRecognizer recognizer;
    private HashMap<String, Integer> captions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_to_text);

        b2 = (Button)findViewById(R.id.ret);
        comm = (Button)findViewById(R.id.rep);
        summ = (Button)findViewById(R.id.summ);
        recognizedText = "";
        captions = new HashMap<String, Integer>();
        captions.put(KWS_SEARCH, R.string.kws_caption);
        captions.put(MENU_SEARCH, R.string.menu_caption);
        captions.put(DIGITS_SEARCH, R.string.digits_caption);
        captions.put(PHONE_SEARCH, R.string.phone_caption);
        captions.put(FORECAST_SEARCH, R.string.forecast_caption);
        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            pid= extras.getInt("pid");
            trim=extras.getInt("trim");
            type=extras.getString("type");
            lang=extras.getInt("lang");
        }
        ((TextView) findViewById(R.id.caption_text)).setText("Preparing the recognizer");


        String sampleAudio = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/Lafz/P"+pid+"T"+trim+type+".wav";
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(new File(sampleAudio));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



        //convertToSpeech(stream);
        convertToSpeechNew convert = new convertToSpeechNew();
        convert.execute(stream);
        ((TextView) findViewById(R.id.caption_text)).setText("Recognition started\nPlease wait, this may take some time...");



        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
                onBackPressed();
            }
        });


        comm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                in = new Intent(FileToText.this, CommentsToDB.class);
                in.putExtra("pid", pid);
                in.putExtra("trim", trim);
                in.putExtra("type", type);
                in.putExtra("result",PassText);
                in.putExtra("vomit", vomit);
                in.putExtra("appe", appe);
                in.putExtra("dizzi", dizzi);
                in.putExtra("hivv", hivv);
                in.putExtra("pain", pain);
                in.putExtra("sono", sono);
                startActivity(in);
            }
        });

        summ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String indata = ((TextView) findViewById(R.id.caption_text)).getText().toString();
                ((TextView) findViewById(R.id.caption_text)).setText("");
                String output = generateSummary(indata);
                ((TextView) findViewById(R.id.caption_text)).setText("SUMMARY\n" + output);
            }
        });
    }

    static {
        System.loadLibrary("pocketsphinx_jni");
    }

    //convert an inputstream to text
    /*private void convertToSpeech(final InputStream stream){
        new AsyncTask<Void, Void, Exception>() {
            @Override
            protected Exception doInBackground(Void... params) {
                try {
                    Assets assets = new Assets(FileToText.this);
                    File assetsDir = assets.syncAssets();
                    Config c = Decoder.defaultConfig();
                    c.setString("-hmm", new File(assetsDir, "en-us-ptm").getPath());
                    c.setString("-dict", new File(assetsDir, "cmudict-en-us.dict").getPath());
                    c.setBoolean("-allphone_ci", true);
                    c.setString("-lm", new File(assetsDir, "LModel.lm").getPath());
                    Decoder d = new Decoder(c);

                    d.startUtt();
                    byte[] b = new byte[4096];
                    try {
                        int nbytes;
                        while ((nbytes = stream.read(b)) >= 0) {
                            ByteBuffer bb = ByteBuffer.wrap(b, 0, nbytes);

                            // Not needed on desktop but required on android
                            bb.order(ByteOrder.LITTLE_ENDIAN);

                            short[] s = new short[nbytes/2];
                            bb.asShortBuffer().get(s);
                            d.processRaw(s, nbytes/2, false, false);
                        }
                    } catch (IOException e) {
                        //Toast.makeText(getApplicationContext(), ("Error when reading inputstream"+e.getMessage()), Toast.LENGTH_LONG).show();
                    }
                    d.endUtt();
                    System.out.println(d.hyp().getHypstr());
                    for (Segment seg : d.seg()) {
                        //do something with the result here
                        //Toast.makeText(getApplicationContext(), seg.getWord(), Toast.LENGTH_LONG).show();
                        //System.out.println(seg.getWord());
                        recognizedText = recognizedText + seg.getWord() + " ";
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), recognizedText, Toast.LENGTH_LONG).show();
                return null;
            }

        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }*/

    private class convertToSpeechNew extends AsyncTask<InputStream, Void, String>
    {
        @Override
        protected String doInBackground(InputStream... params) {
            try {


                InputStream stream = params[0];
                Assets assets = new Assets(FileToText.this);
                File assetsDir = assets.syncAssets();
                Config c = Decoder.defaultConfig();
                if(type.equals("Comments")) {
                    c.setString("-hmm", new File(assetsDir, "en-us-comm2").getPath());
                    c.setString("-dict", new File(assetsDir, "medicaldict.dic").getPath());
                    //c.setBoolean("-allphone_ci", true);
                    c.setString("-jsgf", new File(assetsDir, "gr.gram").getPath());
                    //c.setString("-lm", new File(assetsDir, "mentor.lm").getPath());
                }
                if(type.equals("Session"))
                {
                    if(lang==1)
                    {
                        c.setString("-hmm", new File(assetsDir, "en-us-eng2").getPath());
                        c.setString("-dict", new File(assetsDir, "engscript.dic").getPath());
                        c.setString("-lm", new File(assetsDir, "engscript.lm").getPath());
                    }
                    else if(lang==2)
                    {
                        c.setString("-hmm", new File(assetsDir, "en-us-hin2").getPath());
                        c.setString("-dict", new File(assetsDir, "hindiscript.dic").getPath());
                        c.setString("-lm", new File(assetsDir, "hindiscript.lm").getPath());
                    }
                }
                Decoder d = new Decoder(c);

                d.startUtt();
                byte[] b = new byte[4096];
                try {
                    int nbytes;
                    while ((nbytes = stream.read(b)) >= 0) {
                        ByteBuffer bb = ByteBuffer.wrap(b, 0, nbytes);

                        // Not needed on desktop but required on android
                        bb.order(ByteOrder.LITTLE_ENDIAN);

                        short[] s = new short[nbytes/2];
                        bb.asShortBuffer().get(s);
                        d.processRaw(s, nbytes/2, false, false);
                    }
                } catch (IOException e) {
                    //Toast.makeText(getApplicationContext(), ("Error when reading inputstream"+e.getMessage()), Toast.LENGTH_LONG).show();
                }
                d.endUtt();
                //System.out.println(d.hyp().getHypstr());
                for (Segment seg : d.seg()) {
                    //do something with the result here
                    //Toast.makeText(getApplicationContext(), seg.getWord(), Toast.LENGTH_LONG).show();
                    //System.out.println(seg.getWord());
                    recognizedText = recognizedText + seg.getWord() + " ";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return recognizedText;
        }

        @Override
        protected void onPostExecute( String result) {

            if(type.equals("Session"))
            {
                //comm.setEnabled(false);
            }

            Toast.makeText(getApplicationContext(), "Recognition Complete!", Toast.LENGTH_SHORT).show();
            result = result.replaceAll("<s> ","");
            result = result.replaceAll(" </s>","");
            result = result.replaceAll("<sil>","");
            result = result.replaceAll("\\[SPEECH\\] ","");
            result = result.replaceAll("\\[NOISE\\] ","");
            result = result.replaceAll("\\(NULL\\) ","");
            ((TextView) findViewById(R.id.caption_text)).setText("Recognition complete\nResult: " + result);

            PassText=result;
            result = result.replaceAll("\\(1\\)","");
            result = result.replaceAll("\\(2\\)","");
            result = result.replaceAll("\\(3\\)","");
            result = result.replaceAll("\\(4\\)","");

            File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Lafz/textfiles");
            if (!folder.exists()) {
                folder.mkdir();
            }
            File root= Environment.getExternalStorageDirectory();
            File logFile = new File(root.getAbsolutePath()+"/Lafz/textfiles/P"+ pid+type+".txt");



            if (!logFile.exists())
            {
                try
                {
                    logFile.createNewFile();
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            try
            {
                //BufferedWriter for performance, true to set append to file flag
                BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
                buf.append(result);
                buf.newLine();
                buf.close();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            Toast.makeText(getApplicationContext(), "Result saved in .txt file in Lafz/textfiles", Toast.LENGTH_LONG).show();





    }
    }

    public String generateSummary(String indata)
    {
        //String input = "hello i can see that you are in your third month how do you feel i feel good so starting with the reports have you been feeling dizzy now a days yes a lot actually have you been eating properly no actually recently i have been suffering from a low appetite but i am trying to remedy it you may want to look into your sugar consumption because your sugar value is low leading to the dizziness ok sir have you conducted a sonography yes only three weeks ago have you taken the tetanus injection yet no i am going to do it next week ok do that soon now lets come to the more important issue at hand you are hiv positive and that is not good for your baby though it may appear to be fine currently yes sir we have thought it through and want to move forward with the pregnancy ok so you have to look into anti retrovial treatment that would prevent the child form being HIV positive";
        //String input = "namaskar aap kaise ho namaskar sir mujhe kuchh dino se pait mein dard aur kamzori sa mehsoos ho raha hai haa aisa hona swabhavik hai kya aap khana barabar kha rahe ho haan mein koshish kar rahi hu kya aap ko chakkar bhi aate hai haan kabhi kabhi par zyada der nahihaa isliye aap apne khaan-paan pe dhyan dijiye ulti zyada aati hai haan doo din pahle ek baar hui thi aapne tetanus ka injection liya hai nahi sirtoh aap ye kaam foran kar lein aap clinic mein aakar kara sakte hoabse aap jyada chalna aur mehnat ka kaam karna kam kar dein ab mein aapse agle mahine milne aaunga dhanyawaad dhanyawaad";
        String input = indata;
        input = input.replaceAll("\\(1\\)","");
        input = input.replaceAll("\\(2\\)","");
        input = input.replaceAll("\\(3\\)","");
        input = input.replaceAll("\\(4\\)","");
        String[] words = input.split("\\s+");
        String output = "";
        if(lang==1)
        {
            for(int i=0;i<words.length;i++) {
                if (words[i].equals("month"))
                {
                    if (words[i-1].equals("first"))
                        output = output + "Month: 1st\n";
                    if (words[i-1].equals("second"))
                        output = output + "Month: 2nd\n";
                    if (words[i-1].equals("third"))
                        output = output + "Month: 3rd\n";
                    if (words[i-1].equals("fourth"))
                        output = output + "Month: 4th\n";
                    if (words[i-1].equals("fifth"))
                        output = output + "Month: 5th\n";
                    if (words[i-1].equals("sixth"))
                        output = output + "Month: 6th\n";
                    if (words[i-1].equals("seventh"))
                        output = output + "Month: 7th\n";
                    if (words[i-1].equals("eighth"))
                        output = output + "Month: 8th\n";
                    if (words[i-1].equals("ninth"))
                        output = output + "Month: 9th\n";
                }
                if (words[i].equals("feel"))
                {
                    if (words[i+1].equals("good"))
                    {
                        output = output + "Feeling: good\n";
                    }
                }
                if (words[i].equals("dizzy"))
                {
                    int j=1;
                    while((i+j)<words.length && !(words[i+j].equals("yes") || words[i+j].equals("no")))
                    {
                        j++;
                    }
                    if((i+j)>=words.length)
                        j=1;
                    if(words[i+j].equals("yes"))
                    {
                        output = output + "Dizziness: Present\n";
                        dizzi = "yes";
                    }
                    if(words[i+j].equals("no"))
                    {
                        output = output + "Dizziness: Not present\n";
                        dizzi = "no";
                    }
                }
                if (words[i].equals("appetite"))
                {
                    output = output + "Low appetite: Yes\n";
                    appe = "yes";
                }
                if (words[i].equals("sonography"))
                {
                    if (words[i+1].equals("yes"))
                    {
                        output = output + "Sonography: Done\n";
                        sono = "yes";
                    }
                    if (words[i+1].equals("no"))
                    {
                        output = output + "Sonography: Not Done\n";
                        sono="no";
                    }
                }
                if (words[i].equals("injection"))
                {
                    int j=1;
                    while((i+j)<words.length && !(words[i+j].equals("yes") || words[i+j].equals("no")))
                    {
                        j++;
                    }
                    if((i+j)>=words.length)
                        j=1;
                    if(words[i+j].equals("yes"))
                        output = output + "Injection: Taken\n";
                    if(words[i+j].equals("no"))
                        output = output + "Injection: Not taken\n";
                }
                if (words[i].equals("HIV"))
                {
                    if (words[i+1].equals("positive"))
                    {
                        output = output + "HIV Positive\n";
                        hivv = "yes";
                    }
                    if (words[i+1].equals("negative"))
                    {
                        output = output + "HIV Negative\n";
                        hivv = "no";
                    }
                }
                if (words[i].equals("treatment"))
                {
                    output = output + "Treatment: " + words[i-2] + " " + words[i-1] + "\n";
                    break;
                }
            }
        }
        else if(lang==2)
        {
            for(int i=0;i<words.length;i++)
            {
                if (words[i].equals("dard"))
                {
                    output = output + "Pain: Present\n";
                    pain = "yes";
                }
                if (words[i].equals("kamzori"))
                    output = output + "Weakness: Present\n";
                if (words[i].equals("khana"))
                {
                    int j=1;
                    while((i+j)<words.length && !(words[i+j].equals("haan") || words[i+j].equals("nahi")))
                    {
                        j++;
                    }
                    if((i+j)>=words.length)
                        j=1;
                    if(words[i+j].equals("haan"))
                    {
                        output = output + "Low appetite: No\n";
                        appe = "no";
                    }
                    if(words[i+j].equals("nahi"))
                    {
                        output = output + "Low appetite: Yes\n";
                        appe="yes";
                    }
                }
                if (words[i].equals("chakkar"))
                {
                    int j=1;
                    while((i+j)<words.length && !(words[i+j].equals("haan") || words[i+j].equals("nahi")))
                    {
                        j++;
                    }
                    if((i+j)>=words.length)
                        j=1;
                    if(words[i+j].equals("haan"))
                    {
                        output = output + "Dizziness: Present\n";
                        dizzi = "yes";
                    }
                    if(words[i+j].equals("nahi"))
                    {
                        output = output + "Dizziness: Not present\n";
                        dizzi = "no";
                    }
                }
                if (words[i].equals("ulti"))
                {
                    int j=1;
                    while((i+j)<words.length && !(words[i+j].equals("haan") || words[i+j].equals("nahi")))
                    {
                        j++;
                    }
                    if((i+j)>=words.length)
                        j=1;
                    if(words[i+j].equals("haan"))
                    {
                        output = output + "Vomiting: Present\n";
                        vomit = "yes";
                    }
                    if(words[i+j].equals("nahi"))
                    {
                        output = output + "Vomiting: Not present\n";
                        vomit = "no";
                    }
                }
                if (words[i].equals("injection"))
                {
                    int j=1;
                    while((i+j)<words.length && !(words[i+j].equals("haan") || words[i+j].equals("nahi")))
                    {
                        j++;
                    }
                    if((i+j)>=words.length)
                        j=1;
                    if(words[i+j].equals("haan"))
                        output = output + "Injection: Taken\n";
                    if(words[i+j].equals("nahi"))
                        output = output + "Injection: Not taken\n";
                }

            }
        }
        return output;
    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onPartialResult(Hypothesis hypothesis) {

    }

    @Override
    public void onResult(Hypothesis hypothesis) {

    }

    @Override
    public void onError(Exception e) {

    }

    @Override
    public void onTimeout() {

    }
}
