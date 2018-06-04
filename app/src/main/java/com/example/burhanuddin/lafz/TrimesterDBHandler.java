package com.example.burhanuddin.lafz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TrimesterDBHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "patientDB";
    public static final String TABLE_NAME1 = "trim1table";
    public static final String COLUMN_ID1 = "pid";
    public static final String COLUMN_VOM1 = "vomiting";
    public static final String COLUMN_NAU1 = "nausea";
    public static final String COLUMN_APPE1 = "appetite";
    public static final String COLUMN_DIZZ1 = "dizziness";
    public static final String COLUMN_HAEMO1 = "haemoglobin";
    public static final String COLUMN_HIV1 = "HIV";
    public static final String COLUMN_BLD_GRP1 = "blood_group";
    public static final String COLUMN_BLD_SUG1 = "blood_sugar";
    public static final String COLUMN_SONO1 = "sonography";
    public static final String COLUMN_BLD_PR1 = "blood_pressure";

    public static final String TABLE_NAME2 = "trim2table";
    public static final String COLUMN_ID2 = "pid";
    public static final String COLUMN_VOM2 = "vomiting";
    public static final String COLUMN_NAU2 = "nausea";
    public static final String COLUMN_APPE2 = "appetite";
    public static final String COLUMN_DIZZ2 = "dizziness";
    public static final String COLUMN_HAEMO2 = "haemoglobin";
    public static final String COLUMN_BLD_SUG2 = "blood_sugar";
    public static final String COLUMN_SONO2 = "sonography";
    public static final String COLUMN_ABN2 = "abnormality";
    public static final String COLUMN_FREQ2 = "frequency";
    public static final String COLUMN_BLD_PR2 = "blood_pressure";

    public static final String TABLE_NAME3 = "trim3table";
    public static final String COLUMN_ID3 = "pid";
    public static final String COLUMN_PAIN3 = "pain";
    public static final String COLUMN_BLEED3 = "bleeding";
    public static final String COLUMN_HAEMO3 = "haemoglobin";
    public static final String COLUMN_BLD_SUG3 = "blood_sugar";
    public static final String COLUMN_SONO3 = "sonography";
    public static final String COLUMN_ABN3 = "abnormality";
    public static final String COLUMN_BLD_PR3 = "blood_pressure";



    public TrimesterDBHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME1 + "(" +
                COLUMN_ID1 + " INTEGER PRIMARY KEY," +
                COLUMN_VOM1 + " TEXT, " +
                COLUMN_NAU1 + " TEXT, " +
                COLUMN_APPE1 + " TEXT, " +
                COLUMN_DIZZ1 + " TEXT, " +
                COLUMN_HAEMO1 + " REAL, " +
                COLUMN_HIV1 + " TEXT, " +
                COLUMN_BLD_GRP1 + " TEXT, " +
                COLUMN_BLD_SUG1 + " INTEGER, " +
                COLUMN_SONO1 + " TEXT, " +
                COLUMN_BLD_PR1 + " INTEGER);");

        db.execSQL("CREATE TABLE " + TABLE_NAME2 + "(" +
                COLUMN_ID2 + " INTEGER PRIMARY KEY," +
                COLUMN_VOM2 + " TEXT, " +
                COLUMN_NAU2 + " TEXT, " +
                COLUMN_APPE2 + " TEXT, " +
                COLUMN_DIZZ2 + " TEXT, " +
                COLUMN_HAEMO2 + " REAL, " +
                COLUMN_BLD_SUG2 + " INTEGER, " +
                COLUMN_SONO2 + " TEXT, " +
                COLUMN_ABN2 + " TEXT, " +
                COLUMN_FREQ2 + " TEXT, " +
                COLUMN_BLD_PR2 + " INTEGER);");

        db.execSQL("CREATE TABLE " + TABLE_NAME3 + "(" +
                COLUMN_ID3 + " INTEGER PRIMARY KEY," +
                COLUMN_PAIN3 + " TEXT," +
                COLUMN_BLEED3 + " TEXT," +
                COLUMN_HAEMO3 + " REAL, " +
                COLUMN_BLD_SUG3 + " INTEGER, " +
                COLUMN_SONO3 + " TEXT, " +
                COLUMN_ABN3 + " TEXT, " +
                COLUMN_BLD_PR3 + " INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void truncateAllTrims()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ TABLE_NAME1);
        db.execSQL("DELETE FROM "+ TABLE_NAME2);
        db.execSQL("DELETE FROM "+ TABLE_NAME3);
    }

    public void createEntry1(int pid,String vom, String nau, String appe, String diz, double hae, String hiv, int blsg, String blgrp, String sono, int bdpre)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID1,pid);
        cv.put(COLUMN_VOM1, vom);
        cv.put(COLUMN_NAU1, nau);
        cv.put(COLUMN_APPE1,appe);
        cv.put(COLUMN_DIZZ1, diz);
        cv.put(COLUMN_HAEMO1, hae);
        cv.put(COLUMN_HIV1, hiv);
        cv.put(COLUMN_BLD_GRP1, blgrp);
        cv.put(COLUMN_BLD_SUG1, blsg);
        cv.put(COLUMN_SONO1, sono);
        cv.put(COLUMN_BLD_PR1, bdpre);
        db.insert(TABLE_NAME1, null, cv);
        db.close();
    }

    public String[][] getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[]{COLUMN_ID1, COLUMN_VOM1, COLUMN_NAU1, COLUMN_APPE1, COLUMN_DIZZ1, COLUMN_HAEMO1, COLUMN_HIV1, COLUMN_BLD_GRP1, COLUMN_BLD_SUG1, COLUMN_SONO1, COLUMN_BLD_PR1};
        Cursor c = db.query(TABLE_NAME1, columns, null, null, null, null, null);

        String result[][] = new String[1000][12];
        int i = 0;

        int iID = c.getColumnIndex(COLUMN_ID1);
        int vom = c.getColumnIndex(COLUMN_VOM1);
        int nau = c.getColumnIndex(COLUMN_NAU1);
        int appe = c.getColumnIndex(COLUMN_APPE1);
        int diz = c.getColumnIndex(COLUMN_DIZZ1);
        int hae = c.getColumnIndex(COLUMN_HAEMO1);
        int grp = c.getColumnIndex(COLUMN_BLD_GRP1);
        int hiv = c.getColumnIndex(COLUMN_HIV1);
        int sgr = c.getColumnIndex(COLUMN_BLD_SUG1);
        int sono = c.getColumnIndex(COLUMN_SONO1);
        int pre = c.getColumnIndex(COLUMN_BLD_PR1);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext(), i++) {
            result[i][0] = c.getString(iID);
            result[i][1] = c.getString(vom);
            result[i][2] = c.getString(nau);
            result[i][3] = c.getString(appe);
            result[i][4] = c.getString(diz);
            result[i][5] = c.getString(hae);
            result[i][6] = c.getString(grp);
            result[i][7] = c.getString(hiv);
            result[i][8] = c.getString(sgr);
            result[i][9] = c.getString(sono);
            result[i][10] = c.getString(pre);
        }
        result[0][11] = Integer.toString(i);
        db.close();
        return result;
    }

    public String dataexist1(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        int ID1=id;
        String[] columns = new String[]{COLUMN_ID1, COLUMN_VOM1, COLUMN_NAU1, COLUMN_APPE1, COLUMN_DIZZ1, COLUMN_HAEMO1, COLUMN_HIV1, COLUMN_BLD_GRP1, COLUMN_BLD_SUG1, COLUMN_SONO1, COLUMN_BLD_PR1};
        String where =COLUMN_ID1 + " = ?";
        String[] attr ={String.valueOf(ID1)};
        //String q="SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " =" + ID1;
        Cursor c = db.query(TABLE_NAME1,columns,where, attr,null,null,null);
        //Cursor c = db.rawQuery(q,null);

        String exist="false";
        if(c.moveToFirst()){
            exist ="true";
        }

        return exist;
    }

    public String[] getiddata1(int id, Context context){
        SQLiteDatabase db = this.getReadableDatabase();
        int ID1=id;
        String[] columns = new String[]{COLUMN_ID1, COLUMN_VOM1, COLUMN_NAU1, COLUMN_APPE1, COLUMN_DIZZ1, COLUMN_HAEMO1, COLUMN_HIV1, COLUMN_BLD_GRP1, COLUMN_BLD_SUG1, COLUMN_SONO1, COLUMN_BLD_PR1};
        String where =COLUMN_ID1 + " = ?";
        String[] attr ={ String.valueOf(ID1)};
        //String q="SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " =" + ID1;
        Cursor c = db.query(TABLE_NAME1,columns,where, attr,null,null,null);

        int iID = c.getColumnIndex(COLUMN_ID1);
        int vom = c.getColumnIndex(COLUMN_VOM1);
        int nau = c.getColumnIndex(COLUMN_NAU1);
        int appe = c.getColumnIndex(COLUMN_APPE1);
        int diz = c.getColumnIndex(COLUMN_DIZZ1);
        int hae = c.getColumnIndex(COLUMN_HAEMO1);
        int grp = c.getColumnIndex(COLUMN_BLD_GRP1);
        int hiv = c.getColumnIndex(COLUMN_HIV1);
        int sgr = c.getColumnIndex(COLUMN_BLD_SUG1);
        int sono = c.getColumnIndex(COLUMN_SONO1);
        int pre = c.getColumnIndex(COLUMN_BLD_PR1);


        c.moveToFirst();


        String[] result= new String[]{c.getString(iID), c.getString(vom), c.getString(nau), c.getString(appe), c.getString(diz), c.getString(hae), c.getString(grp), c.getString(hiv), c.getString(sgr), c.getString(sono), c.getString(pre)};

        db.close();
        return result;


    }

    public void createEntry2(int pid,String vom, String nau, String appe, String diz, double hae,int blsg, String sono,String abn,String fre, int bdpre)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ID2,pid);
        cv.put(COLUMN_VOM2, vom);
        cv.put(COLUMN_NAU2, nau);
        cv.put(COLUMN_APPE2, appe);
        cv.put(COLUMN_DIZZ2, diz);
        cv.put(COLUMN_HAEMO2, hae);
        cv.put(COLUMN_BLD_SUG2, blsg);
        cv.put(COLUMN_SONO2, sono);
        cv.put(COLUMN_ABN2,abn);
        cv.put(COLUMN_FREQ2,fre);
        cv.put(COLUMN_BLD_PR2, bdpre);
        db.insert(TABLE_NAME2, null, cv);
        db.close();
    }

    public String[][] getData2() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[]{COLUMN_ID2, COLUMN_VOM2, COLUMN_NAU2, COLUMN_APPE2, COLUMN_DIZZ2, COLUMN_HAEMO2, COLUMN_BLD_SUG2, COLUMN_SONO2,COLUMN_ABN2,COLUMN_FREQ2, COLUMN_BLD_PR2};
        Cursor c = db.query(TABLE_NAME2, columns, null, null, null, null, null);

        String result[][] = new String[1000][12];
        int i = 0;

        int iID = c.getColumnIndex(COLUMN_ID2);
        int vom = c.getColumnIndex(COLUMN_VOM2);
        int nau = c.getColumnIndex(COLUMN_NAU2);
        int appe = c.getColumnIndex(COLUMN_APPE2);
        int diz = c.getColumnIndex(COLUMN_DIZZ2);
        int hae = c.getColumnIndex(COLUMN_HAEMO2);
        int sgr = c.getColumnIndex(COLUMN_BLD_SUG2);
        int sono = c.getColumnIndex(COLUMN_SONO2);
        int abn=c.getColumnIndex(COLUMN_ABN2);
        int fre=c.getColumnIndex(COLUMN_FREQ2);
        int pre = c.getColumnIndex(COLUMN_BLD_PR2);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext(), i++) {
            result[i][0] = c.getString(iID);
            result[i][1] = c.getString(vom);
            result[i][2] = c.getString(nau);
            result[i][3] = c.getString(appe);
            result[i][4] = c.getString(diz);
            result[i][5] = c.getString(hae);
            result[i][6] = c.getString(sgr);
            result[i][7] = c.getString(sono);
            result[i][8] = c.getString(abn);
            result[i][9] = c.getString(fre);
            result[i][10] = c.getString(pre);
        }
        result[0][11] = Integer.toString(i);

        db.close();
        return result;
    }

    public String dataexist2(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        int ID1=id;
        String[] columns = new String[]{COLUMN_ID2, COLUMN_VOM2, COLUMN_NAU2, COLUMN_APPE2, COLUMN_DIZZ2, COLUMN_HAEMO2, COLUMN_BLD_SUG2, COLUMN_SONO2,COLUMN_ABN2,COLUMN_FREQ2, COLUMN_BLD_PR2};
        String where =COLUMN_ID2 + " = ?";
        String[] attr ={String.valueOf(ID1)};
        //String q="SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " =" + ID1;
        Cursor c = db.query(TABLE_NAME2,columns,where, attr,null,null,null);
        //Cursor c = db.rawQuery(q,null);

        String exist="false";
        if(c.moveToFirst()){
            exist ="true";
        }

        return exist;
    }

    public String[] getiddata2(int id, Context context){
        SQLiteDatabase db = this.getReadableDatabase();
        int ID1=id;
        String[] columns = new String[]{COLUMN_ID2, COLUMN_VOM2, COLUMN_NAU2, COLUMN_APPE2, COLUMN_DIZZ2, COLUMN_HAEMO2, COLUMN_BLD_SUG2, COLUMN_SONO2,COLUMN_ABN2,COLUMN_FREQ2, COLUMN_BLD_PR2};
        String where =COLUMN_ID2 + " = ?";
        String[] attr ={ String.valueOf(ID1)};
        //String q="SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " =" + ID1;
        Cursor c = db.query(TABLE_NAME2,columns,where, attr,null,null,null);

        int iID = c.getColumnIndex(COLUMN_ID2);
        int vom = c.getColumnIndex(COLUMN_VOM2);
        int nau = c.getColumnIndex(COLUMN_NAU2);
        int appe = c.getColumnIndex(COLUMN_APPE2);
        int diz = c.getColumnIndex(COLUMN_DIZZ2);
        int hae = c.getColumnIndex(COLUMN_HAEMO2);
        int sgr = c.getColumnIndex(COLUMN_BLD_SUG2);
        int sono = c.getColumnIndex(COLUMN_SONO2);
        int abn=c.getColumnIndex(COLUMN_ABN2);
        int fre=c.getColumnIndex(COLUMN_FREQ2);
        int pre = c.getColumnIndex(COLUMN_BLD_PR2);

        c.moveToFirst();


        String[] result= new String[]{c.getString(iID), c.getString(vom), c.getString(nau), c.getString(appe), c.getString(diz), c.getString(hae), c.getString(sgr), c.getString(sono), c.getString(abn),c.getString(fre),c.getString(pre)};

        db.close();
        return result;


    }


    public void createEntry3(int pid,String pain,String ble,double hae,int blsg, String sono,String abn, int bdpre)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID3,pid);
        cv.put(COLUMN_PAIN3, pain);
        cv.put(COLUMN_BLEED3, ble);
        cv.put(COLUMN_HAEMO3, hae);
        cv.put(COLUMN_BLD_SUG3, blsg);
        cv.put(COLUMN_SONO3, sono);
        cv.put(COLUMN_ABN3,abn);
        cv.put(COLUMN_BLD_PR3, bdpre);
        db.insert(TABLE_NAME3, null, cv);
        db.close();
    }

    public String[][] getData3() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[]{COLUMN_ID3, COLUMN_PAIN3, COLUMN_BLEED3, COLUMN_HAEMO3, COLUMN_BLD_SUG3, COLUMN_SONO3,COLUMN_ABN3, COLUMN_BLD_PR3};
        Cursor c = db.query(TABLE_NAME3, columns, null, null, null, null, null);

        String result[][] = new String[1000][9];
        int i = 0;
        int iID = c.getColumnIndex(COLUMN_ID3);
        int pain = c.getColumnIndex(COLUMN_PAIN3);
        int ble = c.getColumnIndex(COLUMN_BLEED3);
        int hae = c.getColumnIndex(COLUMN_HAEMO3);
        int sgr = c.getColumnIndex(COLUMN_BLD_SUG3);
        int sono = c.getColumnIndex(COLUMN_SONO3);
        int abn=c.getColumnIndex(COLUMN_ABN3);
        int pre = c.getColumnIndex(COLUMN_BLD_PR3);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext(), i++) {
            result[i][0] = c.getString(iID);
            result[i][1] = c.getString(pain);
            result[i][2] = c.getString(ble);
            result[i][3] = c.getString(hae);
            result[i][4] = c.getString(sgr);
            result[i][5] = c.getString(sono);
            result[i][6] = c.getString(abn);
            result[i][7] = c.getString(pre);

        }
        result[0][8] = Integer.toString(i);


        db.close();
        return result;
    }
    public String dataexist3(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        int ID1=id;
        String[] columns = new String[]{COLUMN_ID3, COLUMN_PAIN3, COLUMN_BLEED3, COLUMN_HAEMO3, COLUMN_BLD_SUG3, COLUMN_SONO3,COLUMN_ABN3, COLUMN_BLD_PR3};
        String where =COLUMN_ID3 + " = ?";
        String[] attr ={String.valueOf(ID1)};
        //String q="SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " =" + ID1;
        Cursor c = db.query(TABLE_NAME3,columns,where, attr,null,null,null);
        //Cursor c = db.rawQuery(q,null);

        String exist="false";
        if(c.moveToFirst()){
            exist ="true";
        }

        return exist;
    }

    public String[] getiddata3(int id, Context context){
        SQLiteDatabase db = this.getReadableDatabase();
        int ID1=id;
        String[] columns = new String[]{COLUMN_ID3, COLUMN_PAIN3, COLUMN_BLEED3, COLUMN_HAEMO3, COLUMN_BLD_SUG3, COLUMN_SONO3,COLUMN_ABN3, COLUMN_BLD_PR3};
        String where =COLUMN_ID3 + " = ?";
        String[] attr ={ String.valueOf(ID1)};
        //String q="SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " =" + ID1;
        Cursor c = db.query(TABLE_NAME3,columns,where, attr,null,null,null);

        int iID = c.getColumnIndex(COLUMN_ID3);
        int pain = c.getColumnIndex(COLUMN_PAIN3);
        int ble = c.getColumnIndex(COLUMN_BLEED3);
        int hae = c.getColumnIndex(COLUMN_HAEMO3);
        int sgr = c.getColumnIndex(COLUMN_BLD_SUG3);
        int sono = c.getColumnIndex(COLUMN_SONO3);
        int abn=c.getColumnIndex(COLUMN_ABN3);
        int pre = c.getColumnIndex(COLUMN_BLD_PR3);

        c.moveToFirst();


        String[] result= new String[]{c.getString(iID), c.getString(pain), c.getString(ble),c.getString(hae), c.getString(sgr), c.getString(sono), c.getString(abn),c.getString(pre)};

        db.close();
        return result;


    }

    public void deleteEntry1(int pid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where =COLUMN_ID1 + " = ?";
        String[] attr ={ String.valueOf(pid)};
        db.delete(TABLE_NAME1, where, attr);
    }
    public void deleteEntry2(int pid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where =COLUMN_ID2 + " = ?";
        String[] attr ={ String.valueOf(pid)};
        db.delete(TABLE_NAME2, where, attr);
    }
    public void deleteEntry3(int pid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where =COLUMN_ID3 + " = ?";
        String[] attr ={ String.valueOf(pid)};
        db.delete(TABLE_NAME3, where, attr);
    }
}
