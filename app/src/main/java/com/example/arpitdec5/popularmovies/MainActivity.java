package com.example.arpitdec5.popularmovies;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompatSideChannelService;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.OnMovieClickListener{

    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    boolean two_pane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(this.findViewById(R.id.movie_review_fragment)!=null)
        {
            String movie_title = "Nothing Clicked";
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            MovieDescriptionFragment movieDescriptionFragment = new MovieDescriptionFragment(movie_title);
            fragmentTransaction.add(R.id.movie_review_fragment , movieDescriptionFragment);
            fragmentTransaction.commit();
        }

    }

    @Override
    public void selectMovie(String movie_title) {

        if(findViewById(R.id.movie_review_fragment)!=null)
        {
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            MovieDescriptionFragment movieDescriptionFragment = new MovieDescriptionFragment(movie_title);
            fragmentTransaction.replace(R.id.movie_review_fragment , movieDescriptionFragment);
            fragmentTransaction.commit();
        }
        else
        {
            Intent intent = new Intent(this , MovieDescription.class);
            intent.putExtra("yolo" , movie_title);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            Intent i = new Intent(this , user_settings.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
