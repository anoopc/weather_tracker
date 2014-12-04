package com.example.WeatherTracker;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;

public class MyActivity extends Activity{
    private AppSectionsManager appSectionsManager;

    private WeatherDataServiceManager weatherDataServiceManager;

    private SQLiteDatabase readableDB;
    private SQLiteDatabase writableDB;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        this.appSectionsManager = new AppSectionsManager();
        this.appSectionsManager.initialize(this);

        DataBaseHelper weatherDataDBHelper = new DataBaseHelper(this);
        this.readableDB = weatherDataDBHelper.getReadableDatabase();
        this.writableDB = weatherDataDBHelper.getReadableDatabase();

        this.weatherDataServiceManager = new WeatherDataServiceManager(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                setupAboutDialog();
                return true;
            case R.id.action_add_station:
                setupAddStationDialog();
                return true;
            case R.id.action_preferences:
                setupPreferencesDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupAboutDialog() {

    }

    private void setupAddStationDialog() {
        AddStationDialogFragment addStationDialogFragment = new AddStationDialogFragment();
        addStationDialogFragment.show(getFragmentManager(), "add_station");
    }

    private void setupPreferencesDialog() {

    }

    public void performAddStation(String stationCode) {
        this.weatherDataServiceManager.performAddStationAsync(stationCode);
    }

    public void saveWeatherData(HashMap<String, String> weatherDataMap) {
        Uri insertedRowUri = getApplicationContext().getContentResolver().insert(
                StationTableDBHelper.StationTable.CONTENT_URI,
                StationTableDBHelper.StationTable.getContentValuesForInsertion(weatherDataMap));
        Log.d("ANOOPC_INSERT", "" + insertedRowUri);
        if (insertedRowUri == null) {
            Log.d("ANOOPC_ERROR", "error occurred during insertion");
        }
    }

    public void onFavoriteStation(final long stationRowID) {
        Cursor cc = MyAppUtility.getStationDataCursor(this, stationRowID);
        if (cc.moveToFirst()) {
            String stationCode = cc.getString(cc.getColumnIndex(StationTableDBHelper.StationTable.STATION_CODE));
            cc.close();

            SharedPreferences.Editor sharedPreferenceEditor = getPreferences(Context.MODE_PRIVATE).edit();
            sharedPreferenceEditor.putString(MyAppUtility.FAVORITE_STATION_KEY, stationCode);
            sharedPreferenceEditor.commit();
            Log.d("ANOOPC", "Favorite Station Saved: " + stationCode);
        } else {
            Log.d("ANOOPC_Error", "Favorite Station Save Error:" + Long.toString(stationRowID));
        }
    }

    public void onDeleteStation(final long stationRowID) {
        int deletedRowCount = getApplicationContext().getContentResolver().delete(
                StationTableDBHelper.StationTable.CONTENT_URI,
                StationTableDBHelper.StationTable._ID + " = ?",
                new String[]{Long.toString(stationRowID)});
        if (deletedRowCount == 1) {
            Log.d("ANOOPC", "Station Deleted Successfully");
        } else {
            Log.d("ANOOPC_Error", "Station Deletion Failed");
        }
    }
}