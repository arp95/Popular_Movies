package com.example.arpitdec5.popularmovies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.OnMovieClickListener{

    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    boolean two_pane = false;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private SmoothActionBarDrawerToggle smoothActionBarToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        if(toolbar!=null) {
            smoothActionBarToggle = new SmoothActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer);
            ActionBar actionBar = getSupportActionBar();
            drawerLayout.addDrawerListener(smoothActionBarToggle);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
            smoothActionBarToggle.syncState();
        }

        if(this.findViewById(R.id.movie_review_fragment)!=null)
        {
            String movie_title = "Nothing Clicked";
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            MovieDescriptionFragment movieDescriptionFragment = new MovieDescriptionFragment(movie_title);
            //movieDescriptionFragment.setHasOptionsMenu(true);
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

    private class SmoothActionBarDrawerToggle extends ActionBarDrawerToggle {

        private Runnable runnable;

        public SmoothActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
            super(activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes);
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            invalidateOptionsMenu();
        }
        @Override
        public void onDrawerClosed(View view) {
            super.onDrawerClosed(view);
            invalidateOptionsMenu();
        }
        @Override
        public void onDrawerStateChanged(int newState) {
            super.onDrawerStateChanged(newState);
            if (runnable != null && newState == DrawerLayout.STATE_IDLE) {
                runnable.run();
                runnable = null;
            }
        }

        public void runWhenIdle(Runnable runnable) {
            this.runnable = runnable;
        }
    }

}
