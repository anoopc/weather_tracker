package com.example.WeatherTracker;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        populateView();
    }

    private void populateView() {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String favoriteStationCode = sharedPref.getString(MyAppUtility.FAVORITE_STATION_KEY, null);
        if (favoriteStationCode != null) {
            Cursor stationDataCursor = MyAppUtility.getStationDataCursor(getActivity(), favoriteStationCode);
            if (stationDataCursor != null && stationDataCursor.moveToFirst()) {
                populateFavoriteStationWeatherDataWithCursor(stationDataCursor);
                stationDataCursor.close();
            }
        }
    }

    private void populateFavoriteStationWeatherDataWithCursor(Cursor stationDataCursor) {
        int[] textViewIDs = {
                R.id.weather_station_text_value,
                R.id.time_text_value,
                R.id.weather_text_value,
                R.id.visibility_text_value,
                R.id.pressure_text_value,
                R.id.temperature_text_value
        };

        String[] stationTableColumnNames = {
                StationTableDBHelper.StationTable.STATION_FULL_NAME,
                StationTableDBHelper.StationTable.OBSERVATION_TIME,
                StationTableDBHelper.StationTable.WEATHER,
                StationTableDBHelper.StationTable.VISIBILITY,
                StationTableDBHelper.StationTable.PRESSURE_STRING,
                StationTableDBHelper.StationTable.TEMPERATURE_STRING
        };

        for (int index = 0; index < textViewIDs.length; index++) {
            TextView textView = (TextView)getView().findViewById(textViewIDs[index]);
            String data = stationDataCursor.getString(stationDataCursor.getColumnIndex(stationTableColumnNames[index]));
            textView.setText(data);
        }
    }
}
