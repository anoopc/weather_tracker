package com.example.WeatherTracker;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by anoopc on 12/5/14.
 */
public class WeatherDataUpdateBackgroundService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public WeatherDataUpdateBackgroundService() {
        super("WeatherDataUpdateBackgroundService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("ANOOPC_ALARM", "TRIGGERED");

        String[] stationCodes = fetchStationCodesFromDB();
        Log.d("ANOOPC_STATIONS", Arrays.toString(stationCodes));

        if (stationCodes.length <= 0) {
            return;
        }
        WeatherDataServiceManager weatherDataServiceManager = new WeatherDataServiceManager(null);
        for (String stationCode : stationCodes) {
            MyAppUtility.updateWeatherData(
                    getApplicationContext(),
                    weatherDataServiceManager.fetchWeatherDataBlocking(stationCode));
        }
    }

    private String[] fetchStationCodesFromDB() {
        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        Cursor cursor = contentResolver.query(StationTableDBHelper.StationTable.CONTENT_URI,
                new String[]{StationTableDBHelper.StationTable.STATION_CODE},
                null,
                null,
                null);
        if (cursor != null && cursor.moveToFirst()) {
            String stationCodes[] = new String[cursor.getCount()];
            int index = -1;
            do {
                stationCodes[++index] = cursor.getString(cursor.getColumnIndex(StationTableDBHelper.StationTable.STATION_CODE));
            } while (cursor.moveToNext());
            cursor.close();
            return stationCodes;
        }
        return new String[]{};
    }
}
