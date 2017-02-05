package com.example.arpitdec5.popularmovies.data;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by arpitdec5 on 08-04-2016.
 */
public class MovieDescriptionHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "USERDB";
    private static final String TABLE_NAME = "movie";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_RELEASE = "release";
    private static final String KEY_VOTE_AVERAGE = "vote";
    private static final String KEY_OVERVIEW = "overview";
    private static final String KEY_FAVORITE = "favorite";

    public MovieDescriptionHandler(Context context){
        super(context , DATABASE_NAME , null , DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + KEY_ID + " VARCHAR ," + KEY_TITLE + " VARCHAR ," + KEY_IMAGE + " VARCHAR ," + KEY_RELEASE + " VARCHAR ," + KEY_VOTE_AVERAGE + " VARCHAR ," + KEY_OVERVIEW + " VARCHAR ," + KEY_FAVORITE  + " VARCHAR);";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insert(String id ,String title , String image , String release , String vote , String overview , String fav ){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_ID , id);
        cv.put(KEY_TITLE , title);
        cv.put(KEY_IMAGE , image);
        cv.put(KEY_RELEASE , release);
        cv.put(KEY_VOTE_AVERAGE  ,vote);
        cv.put(KEY_OVERVIEW , overview);
        cv.put(KEY_FAVORITE , fav);

        db.insert(TABLE_NAME, null, cv);
        db.close();
    }

    public boolean present_title(String title , String poster)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String SELECT_QUERY = "SELECT * FROM " + TABLE_NAME + ";";
        Cursor c = db.rawQuery(SELECT_QUERY , null);
        int f=0;
        while (c.getCount()!=0 && c.moveToNext())
        {
            String movie_title = c.getString(1);
            String image_path = c.getString(2);
            if(movie_title.equals(title) && image_path.equals(poster))
            {
                f=1;
                break;
            }
        }

        if(f==0)
            return true;
        else
            return false;
    }

    public String get_title(String image)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String SELECT_QUERY = "SELECT * FROM " + TABLE_NAME + ";";
        Cursor c = db.rawQuery(SELECT_QUERY , null);

        String str = null;
        while (c.getCount()!=0 && c.moveToNext())
        {
            String movie_image = c.getString(2);
            String movie_title = c.getString(1);
            if(movie_image.equals(image))
            {
                str = movie_title;
            }
        }
        return str;
    }

    public String get_id(String title)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String SELECT_QUERY = "SELECT * FROM " + TABLE_NAME + ";";
        Cursor c = db.rawQuery(SELECT_QUERY , null);

        String str = null;
        while (c.getCount()!=0 && c.moveToNext())
        {
            String movie_id = c.getString(0);
            String movie_title = c.getString(1);
            if(movie_title.equals(title))
            {
                str = movie_id;
            }
        }
        return str;
    }

    public String get_image(String title)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String SELECT_QUERY = "SELECT * FROM " + TABLE_NAME + ";";
        Cursor c = db.rawQuery(SELECT_QUERY , null);

        String str = null;
        while (c.getCount()!=0 && c.moveToNext())
        {
            String movie_image = c.getString(2);
            String movie_title = c.getString(1);
            if(movie_title.equals(title))
            {
                str = movie_image;
            }
        }
        return str;
    }

    public String get_release(String title)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String SELECT_QUERY = "SELECT * FROM " + TABLE_NAME + ";";
        Cursor c = db.rawQuery(SELECT_QUERY , null);

        String str = null;
        while (c.getCount()!=0 && c.moveToNext())
        {
            String movie_release = c.getString(3);
            String movie_title = c.getString(1);
            if(movie_title.equals(title))
            {
                str = movie_release;
            }
        }
        return str;
    }

    public String get_vote(String title)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String SELECT_QUERY = "SELECT * FROM " + TABLE_NAME + ";";
        Cursor c = db.rawQuery(SELECT_QUERY , null);

        String str = null;
        while (c.getCount()!=0 && c.moveToNext())
        {
            String movie_vote = c.getString(4);
            String movie_title = c.getString(1);
            if(movie_title.equals(title))
            {
                str = movie_vote;
            }
        }
        return str;
    }

    public String get_overview(String title)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String SELECT_QUERY = "SELECT * FROM " + TABLE_NAME + ";";
        Cursor c = db.rawQuery(SELECT_QUERY , null);

        String str = null;
        while (c.getCount()!=0 && c.moveToNext())
        {
            String movie_overview = c.getString(5);
            String movie_title = c.getString(1);
            if(movie_title.equals(title))
            {
                str = movie_overview;
            }
        }
        return str;
    }

    public void mark_fav(String title)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String UPDATE_DB  = "UPDATE " + TABLE_NAME + " SET favorite = '1' WHERE title = '" + title + "' ;";
        db.execSQL(UPDATE_DB);
        db.close();
    }

    public ArrayList<String> get_favorites()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String SELECT_QUERY = "SELECT * FROM " + TABLE_NAME + ";";
        Cursor c = db.rawQuery(SELECT_QUERY , null);

        ArrayList<String> str = new ArrayList<String>();
        while (c.getCount()!=0 && c.moveToNext())
        {
            String fav = c.getString(6);
            if(fav.equals("1"))
                str.add(c.getString(2));
        }
        return str;
    }

}