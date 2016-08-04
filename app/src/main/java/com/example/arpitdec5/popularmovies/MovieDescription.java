package com.example.arpitdec5.popularmovies;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arpitdec5.popularmovies.MovieDescriptionHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MovieDescription extends AppCompatActivity {

    MovieDescriptionHandler movieDescriptionHandler;
    String movie_title = null;
    TrailorHandler trailorHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.moviedescription_activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        trailorHandler = new com.example.arpitdec5.popularmovies.TrailorHandler(this);

        Intent p = getIntent();
        Bundle m = p.getExtras();
        movie_title = m.getString("yolo");
        if(savedInstanceState==null){
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            MovieDescriptionFragment movieDescriptionFragment = new MovieDescriptionFragment(movie_title);
            fragmentTransaction.replace(R.id.movie_review_fragment , movieDescriptionFragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.share)
        {
            ArrayList<String> arrayList = new ArrayList<String>();
            arrayList = trailorHandler.get_trailor_link(movie_title);

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "First_Trailor");
            intent.putExtra(Intent.EXTRA_TEXT , "https://www.youtube.com/watch?v=" + arrayList.get(0));
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}