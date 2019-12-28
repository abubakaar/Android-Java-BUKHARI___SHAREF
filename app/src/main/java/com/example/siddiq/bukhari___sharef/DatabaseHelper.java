package com.example.siddiq.bukhari___sharef;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Abubkr on 9/5/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {


    int time = 0;
    String DB_PATH = "/data/data/com.example.siddiq.bukhari___sharef/databases/";
    private static String DB_NAME = "Sahih_Bukhari_Database";
    Set<String> hs = new HashSet<>();
    private SQLiteDatabase myDataBase;
    ArrayList<String> Words = new ArrayList<String>();
    private final Context mycontext;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1000);
        this.mycontext = context;


    }

    public void createDatabase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {

        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");

            }
        }

    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String mypath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLException e) {

        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {

        InputStream myInpuut = mycontext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInpuut.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInpuut.close();
    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if (myDataBase != null) {
            myDataBase.close();
            super.close();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

    public List<String> getAllUsers() {
        List<String> listUsers = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        int a=1;
//Query pass into cursor
        try {
            //Sub sy pehle jab Hum app khoelan gy to Books kay Naam k lye ya Query ha
            c = db.rawQuery("SELECT Chapter_Names,Urdu_name FROM Sahih_Hadith where Hadith_Number=1", null);
            if (c == null) return null;

            String name,uname;

            c.moveToFirst();//boht sari rows nazar a ri hogi, us me sy jo bhi pehli pehli ari hgi pick kare ga wo idr
            do {
                name = c.getString(0);//.replaceAll("\\s+", " ");
                uname = c.getString(1);//.replaceAll("\\s+", " ");
                listUsers.add(name+"\n\n"+uname);

            } while (c.moveToNext());//har dafa new row me la jae ga
            c.close();
        } catch (Exception e) {
            Log.e("tle99", e.getMessage());
        }

        db.close();


        return listUsers;
    }


    public List<String> getchapters(String book) {
        List<String> listUsers = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        int a=1;

        //Book select karny k bad, huamry pass HADITH k numbers a rhy ho gy, wo yahi sy ae gy
        try {
            c = db.rawQuery("SELECT Hadith_Number FROM Sahih_Hadith where Chapter_Names = '"+book+"'", null);
            if (c == null) return null;

            String name;
            c.moveToFirst();
            do {
                //add in list only HADITH numbers
                name = c.getString(0).replaceAll("\\s+", " ");  // ya koi khass func ni ha .replaceAll("\\s+", " ");
                listUsers.add(name);

            } while (c.moveToNext());
            c.close();
        } catch (Exception e) {
            Log.e("tle99", e.getMessage());
        }

        db.close();


        return listUsers;
    }

    public List<String>  gethadith(String book, String hadith, int language) {
        List<String> listUsers = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        int a=1;

        try {
            c = db.rawQuery("SELECT * FROM Sahih_Hadith where Chapter_Names = '"+book+"' and Hadith_Number = '"+hadith+"' ", null);
            if (c == null) return null;

            String name;
            c.moveToFirst();
            do {
                name = c.getString(language);
                listUsers.add(name);
                name=c.getString(6);
                listUsers.add(name);

            } while (c.moveToNext());
            c.close();
        } catch (Exception e) {
            Log.e("tle99", e.getMessage());
        }

        db.close();


        return listUsers;
    }



    public ArrayList<String> Get_Fav() {
        try {
            Words.clear();
            // Select All Query


            String selectQuery = "SELECT * FROM Sahih_Hadith where Bookmarks = 1" ;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Words.add(cursor.getString(1)+"\n Hadith-NO: "+cursor.getString(5));
                } while (cursor.moveToNext());
            }

            // return contact list
            cursor.close();
            db.close();
            hs.addAll(Words);
            Words.clear();
            Words.addAll(hs);
            hs.clear();
            return Words;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("all_contact", "" + e);
        }

        return Words;
    }

public int fav(String book, String hadith){


    String selectQuery=("UPDATE Sahih_Hadith SET Bookmarks = 1 where Chapter_Names = '"+book+"' and Hadith_Number = '"+hadith+"' ");
    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);
    if(cursor.moveToFirst())
    return 1;
    else
        return 0;


}

    public int del(String book, String hadith){


        String selectQuery=("UPDATE Sahih_Hadith SET Bookmarks = 0 where Chapter_Names = '"+book+"' and Hadith_Number = '"+hadith+"' ");
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst())
            return 1;
        else
            return 0;


    }




}