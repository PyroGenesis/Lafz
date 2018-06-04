package com.example.burhanuddin.lafz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CounsellorDBHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "counsellorDB";         //Step 1:
    public static final String TABLE_NAME = "counsellorDetails";          //Make these final variables
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PASSWORD = "pass";
    public static final String COLUMN_VISITS = "visits";
    public static final String COLUMN_LOGIN = "login";

    public CounsellorDBHandler(Context context) {                       //Step 2: reduced parameters here
        super(context, DATABASE_NAME, null, 1);                     //Step 3: Added our default params
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_VISITS + " INTEGER DEFAULT 0, " +
                COLUMN_LOGIN + " INTEGER DEFAULT 0);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void register(String name,String pass) {  //Step 9: The insert function
        SQLiteDatabase db = this.getWritableDatabase();             //Open our db as writable
        ContentValues cv = new ContentValues();                     //ContentValues used to enter values in db
        cv.put(COLUMN_NAME, name);                                  //put variables in it
        cv.put(COLUMN_PASSWORD, pass);
        db.insert(TABLE_NAME, null, cv);                            //insert command
        db.close();                                                 //close db
    }

    public void truncate()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public String getData() {                                                                       //Step 11: The read function
        SQLiteDatabase db = this.getReadableDatabase();                                             // Open db for reading
        String[] columns = new String[]{ COLUMN_ID, COLUMN_NAME, COLUMN_PASSWORD, COLUMN_VISITS, COLUMN_LOGIN};        // Column names put in String array
        Cursor c = db.query(TABLE_NAME, columns,null, null, null, null, null);                     // Initialise cursor for data

        String result = "";                                                                         //Initialize index IDs in cursor
        int iID = c.getColumnIndex(COLUMN_ID);
        int iName = c.getColumnIndex(COLUMN_NAME);
        int iPass = c.getColumnIndex(COLUMN_PASSWORD);
        int ivisits = c.getColumnIndex(COLUMN_VISITS);
        int ilogin = c.getColumnIndex(COLUMN_LOGIN);

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext())                                      // For loop to iterate through data
        {
            result = result + c.getString(iID) + " " + c.getString(iName) + " " + c.getString(iPass) + " " + c.getString(ivisits) + " " + c.getString(ilogin) + "\n";
        }
        db.close();
        return result;
    }

    public String authRetPass(String userName)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[]{ COLUMN_ID, COLUMN_NAME, COLUMN_PASSWORD};
        String cond= COLUMN_NAME+ "=?";
        String[] args = { userName };
        Cursor cursor=db.query(TABLE_NAME, columns,cond,args, null, null, null);
        if(cursor != null && cursor.getCount()>0)
        {
            cursor.moveToFirst();
            return cursor.getString(2);
        }
        return "incorrectPassword";
    }

    public boolean Exists(String searchItem) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = { COLUMN_NAME };
        String selection = COLUMN_NAME + " =?";
        String[] selectionArgs = { searchItem };
        String limit = "1";

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null, limit);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public int getCIDfromName(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[]{ COLUMN_ID, COLUMN_NAME };
        String cond= COLUMN_NAME+ "=?";
        String[] args = { username };
        Cursor cursor=db.query(TABLE_NAME, columns,cond,args, null, null, null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public int getVisitNo(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[]{ COLUMN_ID, COLUMN_NAME, COLUMN_VISITS };
        String cond= COLUMN_NAME+ "=?";
        String[] args = { username };
        Cursor cursor=db.query(TABLE_NAME, columns,cond,args, null, null, null);
        cursor.moveToFirst();
        return cursor.getInt(2);
    }

    public void incVisitNo(int pid) {
        SQLiteDatabase db = this.getWritableDatabase();
        int CID = pid/1000;
        CID = CID % 1000;
        db.execSQL("UPDATE " + TABLE_NAME + " SET "
                + COLUMN_VISITS + " = " + COLUMN_VISITS + " +1 WHERE "
                + COLUMN_ID + " =" + CID);
        db.close();
    }

    public void LogIn(String username)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME + " SET "
                + COLUMN_LOGIN + " = 1 WHERE "
                + COLUMN_NAME + " = '" + username + "'");
        db.close();
    }
    public String checkLoggedIn()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = { COLUMN_NAME, COLUMN_LOGIN };
        String selection = COLUMN_LOGIN + " =?";
        String[] selectionArgs = { "1" };
        String limit = "1";

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null, limit);
        boolean exists = (cursor.getCount() > 0);
        if(exists)
        {
            cursor.moveToFirst();
            String name = cursor.getString(0);
            cursor.close();
            db.close();
            return name;
        }
        else
        {
            cursor.close();
            db.close();
            return "";
        }
    }
    public void LogOut(String username)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME + " SET "
                + COLUMN_LOGIN + " = 0 WHERE "
                + COLUMN_NAME + " = '" + username + "'");
        db.close();
    }
}
