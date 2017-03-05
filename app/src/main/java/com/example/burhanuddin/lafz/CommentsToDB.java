package com.example.burhanuddin.lafz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class CommentsToDB extends AppCompatActivity {

    String vomit="no",nausea="no",appe="",dizzi="no",hivv="no",bgr="no",pain="no",sono="no",abno="no",freq="no",ble="no";
    int pid, i,trim;
    int  bsgr=0,ans=0,bpr=0,place=0;
    double haemo=0;
    String type,ques;
    TrimesterDBHandler tdb;
    CounsellorDBHandler incVisit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_to_db);

        // Toast.makeText(getApplicationContext(), "Clicked on button", Toast.LENGTH_LONG).show();
        tdb = new TrimesterDBHandler(getApplicationContext());
        incVisit = new CounsellorDBHandler(getApplicationContext());

        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            pid= extras.getInt("pid");
            trim=extras.getInt("trim");
            type=extras.getString("type");
            ques=extras.getString("result");
            vomit=extras.getString("vomit");
            appe=extras.getString("appe");
            dizzi=extras.getString("dizzi");
            hivv=extras.getString("hivv");
            pain=extras.getString("pain");
            sono=extras.getString("sono");
        }

        //ques="VOMITING NOT PRESENT (NULL) NAUSEA PRESENT (NULL) PAIN NOT PRESENT (NULL) BLOOD SUGAR TWO (NULL) FIVE (NULL) EIGHT (NULL) SEVEN (NULL) HAEMOGLOBIN SEVEN (NULL) TWO (NULL) ONE(2) POINT SIX (NULL)";
        if(type.equals("Comments")) {
            ques = ques.replaceAll("\\(NULL\\) ", "");
            ques = ques.replaceAll("\\(2\\)", "");
            ques = ques.replaceAll("\\(3\\)", "");
            Toast.makeText(getApplicationContext(), (ques), Toast.LENGTH_LONG).show();
            String newques = ques + " END";
            String[] words = newques.split("\\s+");

            for (i = 0; i < words.length; i++) {
                if (words[i].equals("VOMITING")) {
                    if (words[i + 1].equals("NOT")) {
                        vomit = "no";
                    } else {
                        vomit = "yes";
                    }
                } else if (words[i].equals("NAUSEA")) {
                    if (words[i + 1].equals("PRESENT")) {
                        nausea = "yes";
                    } else {
                        nausea = "no";
                    }
                } else if (words[i].equals("PAIN")) {
                    if (words[i + 1].equals("NOT")) {
                        pain = "no";
                    } else {
                        pain = "yes";
                    }
                } else if (words[i].equals("BLOOD")) {
                    if (words[i + 1].equals("SUGAR")) {
                        int j = 2;
                        String num = "";
                        while (words[i + j].equals("ONE") || words[i + j].equals("TWO") || words[i + j].equals("THREE") || words[i + j].equals("FOUR") || words[i + j].equals("FIVE") || words[i + j].equals("SIX") || words[i + j].equals("SEVEN") || words[i + j].equals("EIGHT") || words[i + j].equals("NINE") || words[i + j].equals("ZERO")) {
                            num = num + words[i + j];
                            j++;
                        }
                        num = num.replaceAll("ONE", "1");
                        num = num.replaceAll("TWO", "2");
                        num = num.replaceAll("THREE", "3");
                        num = num.replaceAll("FOUR", "4");
                        num = num.replaceAll("FIVE", "5");
                        num = num.replaceAll("SIX", "6");
                        num = num.replaceAll("SEVEN", "7");
                        num = num.replaceAll("EIGHT", "8");
                        num = num.replaceAll("NINE", "9");
                        num = num.replaceAll("ZERO", "0");
                        //num = num.replaceAll("POINT",".");
                        bsgr = Integer.parseInt(num);

                    } else if (words[i + 1].equals("PRESSURE")) {
                        int j = 2;
                        String num = "";
                        while (words[i + j].equals("ONE") || words[i + j].equals("TWO") || words[i + j].equals("THREE") || words[i + j].equals("FOUR") || words[i + j].equals("FIVE") || words[i + j].equals("SIX") || words[i + j].equals("SEVEN") || words[i + j].equals("EIGHT") || words[i + j].equals("NINE") || words[i + j].equals("ZERO")) {
                            num = num + words[i + j];
                            j++;
                        }
                        num = num.replaceAll("ONE", "1");
                        num = num.replaceAll("TWO", "2");
                        num = num.replaceAll("THREE", "3");
                        num = num.replaceAll("FOUR", "4");
                        num = num.replaceAll("FIVE", "5");
                        num = num.replaceAll("SIX", "6");
                        num = num.replaceAll("SEVEN", "7");
                        num = num.replaceAll("EIGHT", "8");
                        num = num.replaceAll("NINE", "9");
                        num = num.replaceAll("ZERO", "0");
                        // num = num.replaceAll("POINT",".");
                        bpr = Integer.parseInt(num);
                        Toast.makeText(getApplicationContext(), (bpr + ""), Toast.LENGTH_LONG).show();
                    } else if (words[i + 1].equals("GROUP")) {
                        String gr = words[i + 2];
                        gr = gr.replaceAll("p", "+");
                        gr = gr.replaceAll("m", "-");
                        bgr = gr;
                    }
                }

                if (words[i].equals("HAEMOGLOBIN")) {
                    int j = 1;
                    String num = "";
                    while (words[i + j].equals("ONE") || words[i + j].equals("TWO") || words[i + j].equals("THREE") || words[i + j].equals("FOUR") || words[i + j].equals("FIVE") || words[i + j].equals("SIX") || words[i + j].equals("SEVEN") || words[i + j].equals("EIGHT") || words[i + j].equals("NINE") || words[i + j].equals("ZERO") || words[i + j].equals("POINT")) {
                        num = num + words[i + j];
                        j++;
                    }
                    num = num.replaceAll("ONE", "1");
                    num = num.replaceAll("TWO", "2");
                    num = num.replaceAll("FIVE", "5");
                    num = num.replaceAll("THREE", "3");
                    num = num.replaceAll("FOUR", "4");
                    num = num.replaceAll("SIX", "6");
                    num = num.replaceAll("SEVEN", "7");
                    num = num.replaceAll("EIGHT", "8");
                    num = num.replaceAll("NINE", "9");
                    num = num.replaceAll("ZERO", "0");
                    num = num.replaceAll("POINT", ".");
                    haemo = Double.parseDouble(num);
                }
                if (words[i].equals("SONOGRAPHY")) {
                    if (words[i + 1].equals("NOT")) {
                        sono = "no";
                    } else {
                        sono = "yes";
                    }
                }
                if (words[i].equals("HIV")) {
                    if (words[i + 1].equals("NEGATIVE")) {
                        hivv = "no";
                    } else {
                        hivv = "yes";
                    }
                }
                if (words[i].equals("FREQUENCY")) {
                    if (words[i + 4].equals("TWOTOTHREE")) {
                        freq = "2-3";
                    } else if (words[i + 4].equals("THREETOFOUR")) {
                        freq = "3-4";
                    } else if (words[i + 4].equals("FOURTOFIVE")) {
                        freq = "4-5";
                    }
                }
                if (words[i].equals("ABNORMALITIES")) {
                    if (words[i + 1].equals("NOT")) {
                        abno = "no";
                    } else {
                        abno = "yes";
                    }
                }
                if (words[i].equals("LOW")) {
                    if (words[i + 2].equals("NOT")) {
                        appe = "no";
                    } else {
                        appe = "yes";
                    }
                }
                if (words[i].equals("DIZZINESS")) {
                    if (words[i + 1].equals("NOT")) {
                        dizzi = "no";
                    } else {
                        dizzi = "yes";
                    }
                }
                if (words[i].equals("BLEEDING")) {
                    if (words[i + 1].equals("NOT")) {
                        ble = "no";
                    } else {
                        ble = "yes";
                    }
                }
            }
        }

        if(trim==1)
        {
            boolean didItWork = true;
            try {
                String data1 = tdb.dataexist1(pid);
                if (data1 == "false") {
                    incVisit.incVisitNo(pid);
                }
                tdb.deleteEntry1(pid);
                tdb.createEntry1(pid,vomit, nausea, appe, dizzi, haemo, hivv, bsgr, bgr, sono, bpr);
            } catch (Exception e) {
                didItWork = false;
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            } finally {
                if (didItWork)
                    Toast.makeText(getApplicationContext(), "Data entered", Toast.LENGTH_SHORT).show();
            }
        }
        if(trim==2)
        {
            boolean didItWork = true;
            try {
                String data2 = tdb.dataexist2(pid);
                if (data2 == "false") {
                    incVisit.incVisitNo(pid);
                }
                tdb.deleteEntry2(pid);
                tdb.createEntry2(pid,vomit, nausea, appe, dizzi, haemo, bsgr,sono,abno,freq, bpr);
            } catch (Exception e) {
                didItWork = false;
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            } finally {
                if (didItWork)
                    Toast.makeText(getApplicationContext(), "Data entered", Toast.LENGTH_SHORT).show();
            }
        }

        if(trim==3)
        {
            boolean didItWork = true;
            try {
                String data3 = tdb.dataexist3(pid);
                if (data3 == "false") {
                    incVisit.incVisitNo(pid);
                }
                tdb.deleteEntry3(pid);
                tdb.createEntry3(pid,pain,ble, haemo, bsgr,sono,abno, bpr);
            } catch (Exception e) {
                didItWork = false;
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            } finally {
                if (didItWork)
                    Toast.makeText(getApplicationContext(), "Data entered", Toast.LENGTH_SHORT).show();
            }
        }

        Toast.makeText(getApplicationContext(),(vomit+" "+nausea+" "+pain+" "+bsgr+" "+haemo), Toast.LENGTH_LONG).show();
    }
}
