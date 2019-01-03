package com.lifemicael.weatherlib;

public enum WeatherConstants {
    SERVER_NAME("http://api.openweathermap.org"),
    DATA_PATH_NAME("/data/2.5/weather?q="),
    DATA_PATH_ID("/data/2.5/weather?id=");

    private final String stringVal;

    WeatherConstants(String stringVal) {
        this.stringVal = stringVal;
    }

    public String getStringVal() {
        return stringVal;
    }
}
