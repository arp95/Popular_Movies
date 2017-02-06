package com.example.arpitdec5.popularmovies.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.arpitdec5.popularmovies.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Trailors extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ButterKnife.setDebug(true);
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "Rendering Trailors ..", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_trailors);
        ButterKnife.bind(this);

        //set support for toolbar
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
