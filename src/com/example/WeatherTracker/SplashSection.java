package com.example.WeatherTracker;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by anoopc on 12/1/14.
 */
public class SplashSection extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.splash_section, container, false);
        populateView(view);
        return view;
    }

    protected void populateView(View view) {
//        WeatherDataServiceManager serviceManager = new WeatherDataServiceManager();
//        serviceManager.fetchWeatherDataAsync("KSFO");
    }
}
