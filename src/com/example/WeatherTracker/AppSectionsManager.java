package com.example.WeatherTracker;

import android.app.ActionBar;
import android.app.Activity;

/**
 * Created by anoopc on 12/1/14.
 */
public class AppSectionsManager {
    public void initialize(Activity mainActivity) {
        ActionBar actionBar = mainActivity.getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        TabListener splashTabListener = new TabListener<SplashSection>(
                mainActivity, "splash_action", SplashSection.class);
        actionBar.addTab(actionBar.newTab()
                        .setText(R.string.splash_section_name)
                        .setTabListener(splashTabListener));

        TabListener stationsTabListener = new TabListener<StationsSection>(
                mainActivity, "stations_action", StationsSection.class);
        actionBar.addTab(actionBar.newTab()
                .setText(R.string.stations_section_name)
                .setTabListener(stationsTabListener));
    }
}
