package com.example.WeatherTracker;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
        long rowID = this.writableDB.insert(
                StationTableDBHelper.StationTable.TABLE_NAME,
                null,
                StationTableDBHelper.StationTable.getContentValuesForInsertion(weatherDataMap));
        Log.d("ANOOPC_INSERT", Long.toString(rowID));
        if (rowID == -1) {
            Log.d("ANOOPC_ERROR", "error occurred during insertion");
        } else {

        }
    }
}