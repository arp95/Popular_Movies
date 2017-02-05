package com.example.arpitdec5.popularmovies.utils;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.arpitdec5.popularmovies.R;

public class user_settings extends PreferenceActivity {


    CheckBoxPreference check1;
    CheckBoxPreference check2;
    CheckBoxPreference check3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        check1 = (CheckBoxPreference) findPreference("popularity");
        check2 = (CheckBoxPreference) findPreference("vote_average");
        check3 = (CheckBoxPreference) findPreference("favorite");

        check1.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                check1.setChecked(true);
                check2.setChecked(false);
                check3.setChecked(false);
                return true;
            }
        });

        check2.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                check2.setChecked(true);
                check1.setChecked(false);
                check3.setChecked(false);
                return true;
            }
        });

        check3.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                check3.setChecked(true);
                check1.setChecked(false);
                check2.setChecked(false);
                return true;
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        LinearLayout root = (LinearLayout)findViewById(android.R.id.list).getParent().getParent().getParent();
        Toolbar bar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.settings_toolbar, root, false);
        root.addView(bar, 0); // insert at top
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
