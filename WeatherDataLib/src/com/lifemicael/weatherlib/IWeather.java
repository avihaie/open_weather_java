package com.lifemicael.weatherlib;

import javax.json.JsonObject;
import java.io.IOException;

public interface IWeather {
    /**
     * Get a current temperature of a city name
     *
     * @param cityName chosen city name to extract the current weather details from, for example "london"
     * @param mode retrieve data mode, can be "xml" or "json" , only json is supported for now
     *
     * @return city temperature
     *
     */
    Double getTemperature(String cityName, String mode) throws IOException;


    /**
     * Get a current wind interface of a city
     *
     * @return IWind object that includes wind degree and speed
     */
    JsonObject getWind(String cityName, String mode) throws IOException;

    /**
     * Get a current humidity of a city name
     *
     * @param cityName chosen city name to extract the current weather details from, for example "london"
     * @param mode retrieve data mode, can be "xml" or "json" , only json is supported for now
     *
     * @return city humidity
     *
     */
    int getHumidity(String cityName, String mode) throws IOException;


}
