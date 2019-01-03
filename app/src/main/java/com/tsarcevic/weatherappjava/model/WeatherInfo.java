package com.tsarcevic.weatherappjava.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherInfo {

    @SerializedName("dt_txt")
    @Expose
    private String currentDate;

    @SerializedName("main")
    @Expose
    private CurrentWeatherInfo currentWeatherInfo;

    @SerializedName("weather")
    @Expose
    private List<Weather> weather;

    public String getCurrentDate() {
        return currentDate;
    }

    public CurrentWeatherInfo getCurrentWeatherInfo() {
        return currentWeatherInfo;
    }

    public List<Weather> getWeather() {
        return weather;
    }
}
