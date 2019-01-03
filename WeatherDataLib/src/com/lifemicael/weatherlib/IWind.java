package com.lifemicael.weatherlib;

import java.io.IOException;

public interface IWind {
    /**
     * Get a current wind speed by providing a city name string
     *
     * @return wind speed as a double value, for example: 4.6
     */
    double getWindSpeed() throws IOException;
    int getWindDegree() throws IOException;

}
