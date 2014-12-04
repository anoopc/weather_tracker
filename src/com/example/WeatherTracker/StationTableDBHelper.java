package com.example.WeatherTracker;

import android.content.ContentValues;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by anoopc on 12/3/14.
 */
public final class StationTableDBHelper {
    public StationTableDBHelper() {}

    public static abstract class StationTable implements BaseColumns {
        public static final String TABLE_NAME = "station_observation";

        public static final Uri CONTENT_URI = Uri.parse("content://"
                + WeatherDataContentProvider.AUTHORITY
                + "/"
                + TABLE_NAME);

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd."
                + WeatherDataContentProvider.AUTHORITY + "." + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd."
                + WeatherDataContentProvider.AUTHORITY + "." + TABLE_NAME;

        public static final String STATION_CODE = "station_code";
        public static final String STATION_FULL_NAME = "station_full_name";
        public static final String OBSERVATION_TIME = "observation_time";
        public static final String TEMPERATURE_STRING = "temperature_string";
        public static final String RELATIVE_HUMIDITY = "relative_humidity";
        public static final String PRESSURE_STRING = "pressure_string";
        public static final String CREDIT = "credit";
        public static final String CREDIT_URL = "credit_URL";
        public static final String VISIBILITY = "visibility_mi";
        public static final String WEATHER = "weather";

        private static final String TEXT_TYPE = " TEXT";
        private static final String NOT_NULL_TYPE = " NOT NULL";
        private static final String COMMA_SEP = ",";
        private static final String QUOTE = "\"";

        public static final String[] ALL_COLUMN_NAMES = generateColumnNames();

        private static String[] generateColumnNames() {
            return new String[]{
                    _ID,
                    STATION_CODE,
                    STATION_FULL_NAME,
                    OBSERVATION_TIME,
                    TEMPERATURE_STRING,
                    RELATIVE_HUMIDITY,
                    PRESSURE_STRING,
                    CREDIT,
                    CREDIT_URL,
                    WEATHER,
                    VISIBILITY
            };
        }

        public static final String CREATE_TABLE_QUERY = generateCreateQuery();

        public static String generateCreateQuery() {
            String queryString = "CREATE TABLE " + TABLE_NAME + "("
                    + ALL_COLUMN_NAMES[0] + " INTEGER PRIMARY KEY";
            for (int index = 1; index < ALL_COLUMN_NAMES.length; ++index) {
                queryString += COMMA_SEP + ALL_COLUMN_NAMES[index] + TEXT_TYPE;
            }
            queryString += ")";
            Log.d("ANOOPC_QUERY", "generated Query String:" + queryString);
            return queryString;
        }

        public static String generateInsertQuery(HashMap<String, String> weatherDataMap) {
            String queryString = "INSERT INTO " + TABLE_NAME + " VALUES ("
                    + QUOTE + weatherDataMap.get(ALL_COLUMN_NAMES[0]) + QUOTE;
            for (int index = 1; index < ALL_COLUMN_NAMES.length; ++index) {
                queryString += COMMA_SEP + QUOTE + weatherDataMap.get(ALL_COLUMN_NAMES[index]) + QUOTE;
            }
            queryString += ")";
            Log.d("ANOOPC_QUERY", "generated Query String:" + queryString);
            return queryString;
        }

        public static ContentValues getContentValuesForInsertion(HashMap<String, String> weatherDataMap) {
            if (weatherDataMap == null) return  null;
            ContentValues contentValues = new ContentValues();
            for (Map.Entry<String, String> entry : weatherDataMap.entrySet()) {
                contentValues.put(entry.getKey(), entry.getValue());
            }
            Log.d("ANOOPC", "contentValues: " + contentValues);
            return contentValues;
        }
    }
}
