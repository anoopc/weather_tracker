package com.example.WeatherTracker;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by anoopc on 12/3/14.
 */
public class MyAppUtility {
    public static final String FAVORITE_STATION_KEY = "favorite_station_key";

    public static void showToast(Activity activity, String messageString) {
        if (activity == null) {
            return;
        }
        Toast.makeText(
                activity.getApplicationContext(),
                messageString,
                Toast.LENGTH_SHORT).show();
    }

    public static Cursor getStationDataCursor(Activity activity, final long rowID) {
        return activity.getApplicationContext().getContentResolver().query(
                StationTableDBHelper.StationTable.CONTENT_URI,
                null,
                StationTableDBHelper.StationTable._ID + " = ?",
                new String[]{Long.toString(rowID)},
                null);
    }

    public static Cursor getStationDataCursor(Activity activity, final String stationCode) {
        return activity.getApplicationContext().getContentResolver().query(
                StationTableDBHelper.StationTable.CONTENT_URI,
                null,
                StationTableDBHelper.StationTable.STATION_CODE + " = ?",
                new String[]{stationCode},
                null);
    }

    public static ContentValues getContentValuesFromWeatherDataMap(HashMap<String, String> weatherDataMap) {
        if (weatherDataMap == null) return  null;
        ContentValues contentValues = new ContentValues();
        for (Map.Entry<String, String> entry : weatherDataMap.entrySet()) {
            contentValues.put(entry.getKey(), entry.getValue());
        }
        return contentValues;
    }

    public static void saveWeatherData(Context applicationContext, HashMap<String, String> weatherDataMap) {
        Uri insertedRowUri = applicationContext.getContentResolver().insert(
                StationTableDBHelper.StationTable.CONTENT_URI,
                getContentValuesFromWeatherDataMap(weatherDataMap));

        String stationCode = weatherDataMap.get(StationTableDBHelper.StationTable.STATION_CODE);
        if (insertedRowUri == null) {
            Log.d("ANOOPC_ERROR", "DB INSERT ERROR StationCode:%s" + stationCode);
        } else {
            Log.d("ANOOPC_OPERATION", "DB INSERT StationCode:%s" + stationCode);
        }
    }

    public static void updateWeatherData(Context applicationContext, HashMap<String, String> weatherDataHashMap) {
        String stationCode = weatherDataHashMap.get(StationTableDBHelper.StationTable.STATION_CODE);
        int numRowsUpdated = applicationContext.getContentResolver().update(
                StationTableDBHelper.StationTable.CONTENT_URI,
                getContentValuesFromWeatherDataMap(weatherDataHashMap),
                StationTableDBHelper.StationTable.STATION_CODE + " = ?",
                new String[]{stationCode});
        if (numRowsUpdated != 1) {
            Log.d("ANOOPC_ERROR",
                    String.format("DB Update Error, StationCode:%s, numRowsUpdated:%d", stationCode, numRowsUpdated));
        } else {
            Log.d("ANOOPC_OPERATION", String.format("DB Updated, StationCode:%s", stationCode));
        }
    }
}
