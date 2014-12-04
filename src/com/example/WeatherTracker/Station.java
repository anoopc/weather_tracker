package com.example.WeatherTracker;

/**
 * Created by anoopc on 12/2/14.
 */
public class Station extends Object {
    private final String codeName;
    private final String fullName;

    public Station(String codeName) {
        this.codeName = codeName;
        this.fullName = codeName;//TODO: fix this
    }

    public String getCodeName() {
        return codeName;
    }

    public String getFullName() {
        return fullName;
    }
}
