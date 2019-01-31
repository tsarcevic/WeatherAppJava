package com.tsarcevic.weatherappjava.model.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponse {

    @SerializedName("city")
    @Expose
    private City city;

    @SerializedName("list")
    @Expose
    private List<WeatherInfo> weatherInfoList;

    public City getCity() {
        return city;
    }

    public List<WeatherInfo> getWeatherInfoList() {
        return weatherInfoList;
    }
}
