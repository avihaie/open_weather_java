package com.lifemicael.weatherlib;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 *
 * my key id = '0e0ab2271488dc844f64ead7184b53fc'
 * REST API request by name example:
 * http://http://api.openweathermap.org/data/2.5/weather?q=London&units=metric&mode=json&appid=0e0ab2271488dc844f64ead7184b53fc
 *
 * REST API request by id example:
 * http://api.openweathermap.org/data/2.5/weather?id=2172797&units=metric&mode=json&appid=0e0ab2271488dc844f64ead7184b53fc
 *
 * REST API response example:
 * {"coord":{"lon":-0.13,"lat":51.51},"weather":[{"id":701,"main":"Mist","description":"mist","icon":"50d"},{"id":721,"main":"Haze","description":"haze","icon":"50d"}],"base":"stations","main":{"temp":5.17,"pressure":1033,"humidity":87,"temp_min":4,"temp_max":6},"visibility":8000,"wind":{"speed":1.5,"deg":360},"clouds":{"all":8},"dt":1545648600,"sys":{"type":1,"id":1414,"message":0.008,"country":"GB","sunrise":1545638714,"sunset":1545666924},"id":2643743,"name":"London","cod":200}
 *
 * */


public class CurrentCityWeather implements IWeather,IWind,IWeatherSource {
    /**
     * Retrieve a selected city weather report with relevant current weather
     * <p>
     * User can retrieve a selected city weather report with relevant current weather
     * attributes as temperature ,humidity, wind , pressure & clouds statistics
     * Weather Data Library using data from https://openweathermap.org
     * </p>
     */

    private final Map<String, String> unitConversionMap =
            Map.of("fahrenheit", "imperial", "celsius", "metric", "kelvin", "");


    private String cityName;
    private int cityId;
    private String units;
    private String apiKey;
    private String mode;
    private String dataPath;
    private String cityAlias;
    private boolean byName;
    private final String serverName = WeatherConstants.SERVER_NAME.getStringVal();
    private final String dataPathName = WeatherConstants.DATA_PATH_NAME.getStringVal();
    private final String dataPathId = WeatherConstants.DATA_PATH_ID.getStringVal();

    private Map<String, String> cityWeatherMap = new HashMap<>();


    /**
     * Constructor for CurrentCityWeather class by city name
     *
     * @param cityName chosen city name to extract the current weather details from, for example "london"
     * @param units    temperature units to use, choices are: "kelvin", "celsius", "fahrenheit"
     * @param apiKey   api_key you get when signing in to the openweathermap site
     * @param mode     retrieve data mode, can be "xml" or "json" , only xml is supported for now
     * @author Avihai Efrat
     */
    public CurrentCityWeather(String cityName, String units, String apiKey, String mode) throws WeatherDataException, IOException {
        setCityName(cityName);
        setUnits(units);
        setApiKey(apiKey);
        setMode(mode);
        byName = true;
    }

    /**
     * Constructor for CurrentCityWeather class by city ID
     *
     * @param cityId   chosen city ID to extract the current weather details from, for example for "london" use "2643743"
     * @param units    temperature units to use, choices are: "kelvin", "celsius", "fahrenheit"
     * @param apiKey   api_key you get when signing in to the openweathermap site
     * @param mode     retrieve data mode, can be "xml" or "json" , only xml is supported for now
     * @author Avihai Efrat
     */
    public CurrentCityWeather(int cityId, String units, String apiKey, String mode) throws WeatherDataException, IOException {
        setCityId(cityId);
        setUnits(units);
        setApiKey(apiKey);
        setMode(mode);
        byName = false;
    }

    // Add constructor for CurrentCityWeather by ID
    // http://api.openweathermap.org/data/2.5/weather?id=2643743&units=metric&mode=json&appid=0e0ab2271488dc844f64ead7184b53fc

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCityId() {
        return cityId;
    }

    public String getDataPath() {
        if(byName) {
            this.dataPath = dataPathName;
        } else {
            this.dataPath = dataPathId;
        }
        return this.dataPath;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getUnits() {
        Set<String> keys = unitConversionMap.keySet();
        for (String unit : keys) {
            String unitConversionMapValue= unitConversionMap.get(unit);
            if (units.equals(unitConversionMapValue)) {
                this.units = unit;
            }
        }
        return units;
    }

    public void setUnits(String units) throws WeatherDataException {
        Set<String> unitValidInputs = unitConversionMap.keySet();
        boolean isEqual = false;

        for (String unit : unitValidInputs) {
            if (units.equals(unit)) {
                this.units = unitConversionMap.get(unit);
                isEqual = true;
            }
        }
        if(!isEqual){
            throw new WeatherDataException("Wrong value is set for temperature units, enter only: " + unitValidInputs);
        }

    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) throws WeatherDataException {
        if (apiKey.isEmpty()) {
            throw new WeatherDataException("No value was entered to apiKey variable, please enter a valid key");
        }
        this.apiKey = apiKey;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) throws WeatherDataException {
        if (!mode.equals("json")) {
            throw new WeatherDataException("Only json mode is supported");
        }
        this.mode = mode;
    }


    public String getCityAlias() {
        if(byName) {
            this.cityAlias = cityName;
        } else {
            this.cityAlias = String.valueOf(cityId);
        }
        return this.cityAlias;
    }

    public JsonObject getJsonReadData(String cityAlias, String mode) throws IOException {
        // http://api.openweathermap.org/data/2.5/find?q=London&units=kelvin&mode=json&appid=0e0ab2271488dc844f64ead7184b53fc
        URL url = new URL(serverName + getDataPath() + cityAlias + "&units=" + units + "&mode=" + mode + "&appid=" + apiKey);
        InputStream is = url.openStream();
        JsonReader rdr = Json.createReader(is);
        JsonObject jsonReadData = rdr.readObject();
        return jsonReadData;
    }

    @Override
    public Double getTemperature(String cityAlias, String mode) throws IOException {
        return getJsonReadData(cityAlias, mode).getJsonObject("main").getJsonNumber("temp").doubleValue();
    }

    @Override
    public JsonObject getWind(String cityAlias, String mode) throws IOException {
        return getJsonReadData(cityAlias, mode).getJsonObject("wind");
    }

    @Override
    public double getWindSpeed() throws IOException {
        return getJsonReadData(getCityAlias(), mode).getJsonObject("wind").getJsonNumber("speed").doubleValue();
    }

    @Override
    public int getWindDegree() throws IOException {
        return getJsonReadData(getCityAlias(), mode).getJsonObject("wind").getJsonNumber("deg").intValue();
    }

    @Override
    public int getHumidity(String cityAlias, String mode) throws IOException {
        return getJsonReadData(cityAlias, mode).getJsonObject("main").getJsonNumber("humidity").intValue();
    }

    @Override
    public String getCurrentWeather() throws IOException {
        return "Current weather of city alias(name or id) " + getCityAlias() + " is:\n" +
                "Temperature: " + getTemperature(getCityAlias(),mode).toString() + " " + getUnits() + "\n" +
                "Wind speed: " + getWindSpeed()  + "\n" +
                "Wind degree: " + getWindDegree()  + "\n" +
                "Humidity: " + getHumidity(getCityAlias(), mode) + "%";
    }

    @Override
    public String toString() {
        return "CurrentCityWeather{" +
                "unitConversionMap=" + unitConversionMap +
                ", cityAlias='" + getCityAlias() + '\'' +
                ", units='" + units + '\'' +
                ", apiKey='" + apiKey + '\'' +
                ", mode='" + mode + '\'' +
                ", serverName='" + serverName + '\'' +
                ", dataPath='" + getDataPath() + '\'' +
                ", cityFileName='" + getCityAlias() + '\'' +
                ", cityWeatherMap=" + cityWeatherMap +
                '}';
    }
}



