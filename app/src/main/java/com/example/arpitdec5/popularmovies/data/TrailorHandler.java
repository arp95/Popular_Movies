package com.example.arpitdec5.popularmovies.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by arpitdec5 on 10-04-2016.
 */
public class TrailorHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "USERDB1";
    private static final String TABLE_NAME = "trailor";
    private static final String KEY_TITLE = "title";
    private static final String KEY_TRAILOR_LINK = "link";

    public TrailorHandler(Context context){
        super(context , DATABASE_NAME , null , DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SELECT_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + KEY_TITLE + " VARCHAR , " + KEY_TRAILOR_LINK + " VARCHAR);";
        db.execSQL(SELECT_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insert(String trailor_title , String trailor_link)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_TITLE , trailor_title);
        cv.put(KEY_TRAILOR_LINK , trailor_link);

        db.insert(TABLE_NAME, null, cv);
        db.close();
    }

    public ArrayList<String> get_trailor_link(String movie_title)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> trailors = new ArrayList<String>();
        String SELECT_QUERY = "SELECT * FROM " + TABLE_NAME + ";";


        Cursor c = db.rawQuery(SELECT_QUERY , null);
        while(c.getCount()!=0 && c.moveToNext())
        {
            if(c.getString(0).equals(movie_title))
                trailors.add(c.getString(1));
        }
        return trailors;
    }

    public boolean is_present(String link)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String SELECT_QUERY = "SELECT * FROM " + TABLE_NAME + ";";
        int f=0;

        Cursor c = db.rawQuery(SELECT_QUERY , null);
        while (c.getCount()!=0 && c.moveToNext())
        {
            if((c.getString(1)).equals(link)){
                f=1;
                break;
            }
        }
        if(f==0)
            return true;
        else
            return false;
    }
}
