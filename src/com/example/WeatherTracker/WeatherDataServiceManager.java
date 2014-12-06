package com.example.WeatherTracker;

import android.app.Activity;
import android.util.Log;

import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by anoopc on 12/2/14.
 */
public class WeatherDataServiceManager {
    private HTTPRequestAgent httpRequestAgent;
    private WeatherDataParser weatherDataParser;
    private Activity activity;

    public WeatherDataServiceManager(Activity activity) {
        this.httpRequestAgent = new HTTPRequestAgent();
        this.weatherDataParser = new WeatherDataParser();
        this.activity = activity;
    }

    private String getWeatherUrlForStation(String stationCode) {
        return String.format(
                "http://www.weather.gov/xml/current_obs/%s.xml",
                stationCode);
    }

    public HashMap<String, String> fetchWeatherDataBlocking(final String stationCode) {
        InputStream inputStream = this.httpRequestAgent.downloadURLBlocking(getWeatherUrlForStation(stationCode));
        return new WeatherDataParser().parseWeatherDataBlocking(inputStream);
    }

    private void fetchWeatherDataAsync(final String stationCode, final WeatherDataFetchCallBackHandler handler) {
        final HTTPAgentCallBackHandler httpHandler = new HTTPAgentCallBackHandler() {
            @Override
            public void didReceiveResponse(InputStream responseXMLInputStream) {
                HashMap<String, String> weatherDataMap = weatherDataParser.parseWeatherDataAsync(responseXMLInputStream);
                if (handler != null) {
                    handler.didReceiveWeatherData(weatherDataMap);
                }
            }
        };
        this.httpRequestAgent.downloadURLAsync(getWeatherUrlForStation(stationCode), httpHandler);
    }

    public void performAddStationAsync(String stationCode) {
        fetchWeatherDataAsync(stationCode, new WeatherDataFetchCallBackHandler() {
            @Override
            public void didReceiveWeatherData(HashMap<String, String> weatherDataMap) {
                if (activity != null) {
                    String toastMessage = activity.getApplicationContext().getString(
                            (weatherDataMap == null) ? R.string.add_station_failure : R.string.add_station_success);
                    MyAppUtility.showToast(activity, toastMessage);
                }

                if (weatherDataMap == null) {
                    return;
                }

                MyAppUtility.saveWeatherData(activity.getApplicationContext(), weatherDataMap);
            }
        });
    }
}
