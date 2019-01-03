package com.lifemicael.weatherlib;

import java.io.IOException;

public interface IWeatherSource {

    /**
     * Get a current temperature of a city name or id
     *
     *
     * @return current temperature, wind speed and degree and humidity of a city name or id
     *
     */
    String getCurrentWeather() throws IOException;
}
