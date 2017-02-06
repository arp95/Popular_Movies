package com.example.arpitdec5.popularmovies.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.arpitdec5.popularmovies.R;
import com.example.arpitdec5.popularmovies.data.MovieDescriptionHandler;
import com.example.arpitdec5.popularmovies.data.TrailorHandler;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDescription extends AppCompatActivity {

    MovieDescriptionHandler movieDescriptionHandler;
    String movie_title = null;
    TrailorHandler trailorHandler;

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ButterKnife.setDebug(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moviedescription_activity_main);
        ButterKnife.bind(this);

        //setting support for toolbar as actionBar
        setSupportActionBar(toolbar);

        /*
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        */
        trailorHandler = new TrailorHandler(this);

        Intent p = getIntent();
        Bundle m = p.getExtras();
        movie_title = m.getString("title");
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return super.onPrepareOptionsMenu(menu);
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