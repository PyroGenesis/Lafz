package com.example.burhanuddin.lafz;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {

    public String[][] LoginDetails(String resp)
    {
        String[][] users = new String[1000][2];
        if(resp != null) {
            try {
                JSONArray userarr = new JSONArray(resp);

                for(int i=0; i<userarr.length(); i++)
                {
                    JSONObject x = userarr.getJSONObject(i);
                    users[i][0] = x.getString("username");
                    users[i][1] = x.getString("password");
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return users;
    }

    public String[][] Trim1Info(String resp)
    {
        String[][] trim1 = new String[1000][11];
        if(resp != null) {
            try {
                JSONArray trim1arr = new JSONArray(resp);

                for(int i=0; i<trim1arr.length(); i++)
                {
                    JSONObject x = trim1arr.getJSONObject(i);
                    trim1[i][0] = x.getString("patient_ID");
                    trim1[i][1] = x.getString("vomiting");
                    trim1[i][2] = x.getString("nausea");
                    trim1[i][3] = x.getString("lowAppetite");
                    trim1[i][4] = x.getString("dizziness");
                    trim1[i][5] = x.getString("haemoglobin");
                    trim1[i][6] = x.getString("hiv");
                    trim1[i][7] = x.getString("blood_Group");
                    trim1[i][8] = x.getString("blood_Sugar");
                    trim1[i][9] = x.getString("sonography");
                    trim1[i][10] = x.getString("blood_Pressure");
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return trim1;
    }

    public String[][] Trim2Info(String resp)
    {
        String[][] trim2 = new String[1000][11];
        if(resp != null) {
            try {
                JSONArray trim2arr = new JSONArray(resp);

                for(int i=0; i<trim2arr.length(); i++)
                {
                    JSONObject x = trim2arr.getJSONObject(i);
                    trim2[i][0] = x.getString("patient_ID");
                    trim2[i][1] = x.getString("vomiting");
                    trim2[i][2] = x.getString("nausea");
                    trim2[i][3] = x.getString("lowAppetite");
                    trim2[i][4] = x.getString("dizziness");
                    trim2[i][5] = x.getString("haemoglobin");
                    trim2[i][6] = x.getString("bloodSugar");
                    trim2[i][7] = x.getString("sonography");
                    trim2[i][8] = x.getString("abnormality");
                    trim2[i][9] = x.getString("frequency");
                    trim2[i][10] = x.getString("bloodPressure");
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return trim2;
    }

    public String[][] Trim3Info(String resp)
    {
        String[][] trim3 = new String[1000][8];
        if(resp != null) {
            try {
                JSONArray trim3arr = new JSONArray(resp);

                for(int i=0; i<trim3arr.length(); i++)
                {
                    JSONObject x = trim3arr.getJSONObject(i);
                    trim3[i][0] = x.getString("patient_ID");
                    trim3[i][1] = x.getString("pain");
                    trim3[i][2] = x.getString("bleeding");
                    trim3[i][3] = x.getString("haemoglobin");
                    trim3[i][4] = x.getString("bloodSugar");
                    trim3[i][5] = x.getString("sonography");
                    trim3[i][6] = x.getString("abnormality");
                    trim3[i][7] = x.getString("bloodPressure");
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return trim3;
    }

//    public String[] Results(String resp)
//    {
//        String[] results = new String[2];
//        if(resp != null) {
//            try {
//                JSONObject x = new JSONObject(resp);
//                results[0] = x.getString("transcription");
//                results[1] = x.getString("translation");
//            }
//            catch (JSONException e)
//            {
//                e.printStackTrace();
//            }
//        }
//        return results;
//    }
}
