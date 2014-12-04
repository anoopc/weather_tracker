package com.example.WeatherTracker;

import java.util.HashMap;

/**
 * Created by anoopc on 12/3/14.
 */
public interface WeatherDataFetchCallBackHandler {
    void didReceiveWeatherData(HashMap<String, String> weatherDataMap);
}
