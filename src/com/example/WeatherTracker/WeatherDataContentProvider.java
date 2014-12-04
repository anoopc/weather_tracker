package com.example.WeatherTracker;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by anoopc on 12/3/14.
 */
public class WeatherDataContentProvider extends ContentProvider {
    private DataBaseHelper dbHelper;

    public static final String AUTHORITY = "com.example.weather_data_tracker.db.weather_data_provider";
    private static final int STATION_TABLE_MULTIPLE_ROWS = 10;
    private static final int STATION_TABLE_UNIQUE_ROW = 11;

    private static final UriMatcher weatherDataUriMatcher = generateURIMatcher();

    private static UriMatcher generateURIMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY, StationTableDBHelper.StationTable.TABLE_NAME, STATION_TABLE_MULTIPLE_ROWS);
        matcher.addURI(AUTHORITY, StationTableDBHelper.StationTable.TABLE_NAME + "/#", STATION_TABLE_UNIQUE_ROW);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        this.dbHelper = new DataBaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (weatherDataUriMatcher.match(uri)) {
            case STATION_TABLE_MULTIPLE_ROWS:
                break;
            case STATION_TABLE_UNIQUE_ROW:
                selection += StationTableDBHelper.StationTable._ID + " = " + uri.getLastPathSegment();
                break;
            default:
                return null;
        }
        return this.dbHelper.getReadableDatabase().query(
                StationTableDBHelper.StationTable.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
    }

    @Override
    public String getType(Uri uri) {
        switch (weatherDataUriMatcher.match(uri)) {
            case STATION_TABLE_MULTIPLE_ROWS:
                return StationTableDBHelper.StationTable.CONTENT_TYPE;
            case STATION_TABLE_UNIQUE_ROW:
                return StationTableDBHelper.StationTable.CONTENT_ITEM_TYPE;
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (weatherDataUriMatcher.match(uri)) {
            case STATION_TABLE_MULTIPLE_ROWS:
                break;
            default:
                return null;
        }
        long rowID = this.dbHelper.getWritableDatabase().insert(
                StationTableDBHelper.StationTable.TABLE_NAME,
                null,
                values);
        if (rowID != -1) {
            return Uri.withAppendedPath(uri, Long.toString(rowID));
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (weatherDataUriMatcher.match(uri)) {
            case STATION_TABLE_MULTIPLE_ROWS:
                break;
            case STATION_TABLE_UNIQUE_ROW:
                selection = StationTableDBHelper.StationTable._ID + " = " + uri.getLastPathSegment()
                        + ((selection == null || selection.trim().length() == 0) ? "" : "AND (" + selection + ")");
                break;
            default:
                return 0;
        }
        return this.dbHelper.getWritableDatabase().delete(
                StationTableDBHelper.StationTable.TABLE_NAME,
                selection,
                selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        switch (weatherDataUriMatcher.match(uri)) {
            case STATION_TABLE_MULTIPLE_ROWS:
                break;
            case STATION_TABLE_UNIQUE_ROW:
                selection = StationTableDBHelper.StationTable._ID + " = " + uri.getLastPathSegment()
                        + ((selection == null || selection.trim().length() == 0) ? "" : "AND (" + selection + ")");
                break;
            default:
                return 0;
        }
        return this.dbHelper.getWritableDatabase().update(
                StationTableDBHelper.StationTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }
}
