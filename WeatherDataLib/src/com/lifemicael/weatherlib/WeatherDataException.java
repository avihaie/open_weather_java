package com.lifemicael.weatherlib;

public class WeatherDataException extends Exception {
    public WeatherDataException(String message) {
        super(message);
    }
    public WeatherDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
