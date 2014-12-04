package com.example.WeatherTracker;

import android.util.Log;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;

/**
 * Created by anoopc on 12/2/14.
 */
public class WeatherDataXMLHandler extends DefaultHandler {

    public static final String STATION_CODE = "station_id";
    public static final String STATION_FULL_NAME = "location";
    public static final String OBSERVATION_TIME = "observation_time";
    public static final String TEMPERATURE_STRING = "temperature_string";
    public static final String WEATHER = "weather";
    public static final String RELATIVE_HUMIDITY = "relative_humidity";
    public static final String PRESSURE_STRING = "pressure_string";
    public static final String CREDIT = "credit";
    public static final String CREDIT_URL = "credit_URL";
    public static final String VISIBILITY = "visibility_mi";

    private static HashMap<String, String> weatherDataFormatXMLToDBKeyHashMap = generateXMLToDBKeyMap();

    private static HashMap<String, String> generateXMLToDBKeyMap() {
        HashMap<String, String> keyHashMap = new HashMap<String, String>();
        keyHashMap.put(STATION_FULL_NAME, StationTableDBHelper.StationTable.STATION_FULL_NAME);
        keyHashMap.put(STATION_CODE, StationTableDBHelper.StationTable.STATION_CODE);
        keyHashMap.put(WEATHER, StationTableDBHelper.StationTable.WEATHER);
        keyHashMap.put(OBSERVATION_TIME, StationTableDBHelper.StationTable.OBSERVATION_TIME);
        keyHashMap.put(TEMPERATURE_STRING, StationTableDBHelper.StationTable.TEMPERATURE_STRING);
        keyHashMap.put(RELATIVE_HUMIDITY, StationTableDBHelper.StationTable.RELATIVE_HUMIDITY);
        keyHashMap.put(PRESSURE_STRING, StationTableDBHelper.StationTable.PRESSURE_STRING);
        keyHashMap.put(CREDIT, StationTableDBHelper.StationTable.CREDIT);
        keyHashMap.put(CREDIT_URL, StationTableDBHelper.StationTable.CREDIT_URL);
        keyHashMap.put(VISIBILITY, StationTableDBHelper.StationTable.VISIBILITY);
        return keyHashMap;
    }

    private String currentElement;
    private HashMap<String, String> weatherDataMap;

    public WeatherDataXMLHandler() {
        this.weatherDataMap = new HashMap<String, String>();
    }

    public HashMap<String, String> getWeatherDataMap() {
        return weatherDataMap;
    }

    @Override
    public void startElement(String uri,
                             String localName,
                             String qName,
                             Attributes attributes) throws SAXException {
        //update current Element being scanned
        if (WeatherDataXMLHandler.weatherDataFormatXMLToDBKeyHashMap.containsKey(localName)) {
            this.currentElement = localName;
        } else {
            this.currentElement = null;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        this.currentElement = null;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (this.currentElement != null) {
            String currentKey = WeatherDataXMLHandler.weatherDataFormatXMLToDBKeyHashMap.get(this.currentElement);
            if (currentKey != null) {
                String currentValue = new String(ch, start, length).trim();
                this.weatherDataMap.put(currentKey, currentValue);
            }
        }
    }
}
