package com.example.WeatherTracker;

import java.util.HashMap;

/**
 * Created by anoopc on 12/2/14.
 */
public class StationListItemHashMap extends HashMap {
    public static String KEY_CODE_NAME = "keyCodeName";
    public static String KEY_FULL_NAME = "keyFullName";
    private Station myStation;

    public StationListItemHashMap(Station station) {
        this.myStation = station;
    }

    @Override
    public Object get(Object k) {
        String key = (String)k;
        if (KEY_CODE_NAME.equals(key)) {
            return myStation.getCodeName();
        } else if (KEY_FULL_NAME.equals(key)) {
            return myStation.getFullName();
        }
        return super.get(k);
    }
}
