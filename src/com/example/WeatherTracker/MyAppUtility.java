package com.example.WeatherTracker;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import java.security.PublicKey;
import java.util.HashMap;

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
}
