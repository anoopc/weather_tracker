package com.example.WeatherTracker;

/**
 * Created by anoopc on 12/1/14.
 */
public enum AppSection {
    APP_SECTION_SPLASH("splash_section"),
    APP_SECTIONS_STATIONS("splash_section");

    private String sectionName;

    AppSection(String sectionName) {
        this.sectionName = sectionName;
    }

    @Override
    public String toString() {
        return this.sectionName;
    }

    public String getSectionName() {
        return sectionName;
    }
}
