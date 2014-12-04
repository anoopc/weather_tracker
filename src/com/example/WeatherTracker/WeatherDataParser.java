package com.example.WeatherTracker;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * Created by anoopc on 12/2/14.
 */
public class WeatherDataParser implements Runnable {
    private InputStream inputStream;
    private HashMap<String, String> weatherDataHashMap;

    @Override
    public void run() {
        try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();
            XMLReader parser = saxParser.getXMLReader();

            WeatherDataXMLHandler weatherDataXMLHandler = new WeatherDataXMLHandler();
            parser.setContentHandler(weatherDataXMLHandler);

            parser.parse(new InputSource(inputStream));

            HashMap<String, String> parsedDataMap = weatherDataXMLHandler.getWeatherDataMap();
            if (!isWeatherDataValid(parsedDataMap)) {
                parsedDataMap = null;
            }
            this.weatherDataHashMap = parsedDataMap;
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, String> parseWeatherDataAsync(InputStream inputStream) {
        this.inputStream = inputStream;
        this.weatherDataHashMap = null;

        Thread asyncTask = new Thread(this);
        asyncTask.start();
        try {
            asyncTask.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return this.weatherDataHashMap;
    }

    private boolean isWeatherDataValid(HashMap<String, String> weatherDataMap) {
        return (weatherDataMap != null &&
                weatherDataMap.containsKey(StationTableDBHelper.StationTable.STATION_CODE));
    }
}
