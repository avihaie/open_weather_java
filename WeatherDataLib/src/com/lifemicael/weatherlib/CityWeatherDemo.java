package com.lifemicael.weatherlib;

import java.io.IOException;

public class CityWeatherDemo {
    public static void main(String args[]) throws WeatherDataException, IOException {
//        System.out.println("Please insert 4 parameters: city name or id), units , api key, mode ");
//        System.out.println("temperature units to use, choices are: \"kelvin\", \"celsius\", \"fahrenheit\"");
//        System.out.println("api_key you get when signing in to the openweathermap site");
//        System.out.println("retrieve data mode, can be \"xml\" or \"json\" , only json is supported for now\n\n");
//
//        // args array verification checks
//        if(args.length > 4){
//            throw new IllegalArgumentException("Too many arguments inserted, please insert city name ,units, api key and mode");
//        } else if(args.length < 1) {
//            throw new IllegalArgumentException("Not sufficient arguments inserted, please insert city name ,units, api key and mode");
//        }
//
//        // initializing
//        String cityAlias= args[0];
//        String units = args[1];
//        String apiKey = args[2];
//        String mode = args[3];


        // City weather by name constructor
        CurrentCityWeather cityWeather = new CurrentCityWeather("Paris", "celsius", "0e0ab2271488dc844f64ead7184b53fc", "json");
        System.out.println("City weather by name:\n" + cityWeather.getCurrentWeather() + "\n");

        // City weather by ID constructor
        CurrentCityWeather cityWeather2 = new CurrentCityWeather(2988507, "celsius", "0e0ab2271488dc844f64ead7184b53fc", "json");
        System.out.println("City weather by id:\n" + cityWeather2.getCurrentWeather());


    }
}
