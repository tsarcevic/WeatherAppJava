package com.tsarcevic.weatherappjava.model.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrentWeatherInfo {

    @SerializedName("temp")
    @Expose
    private Double temperature;

    @SerializedName("temp_min")
    @Expose
    private Double minimumTemperature;

    @SerializedName("temp_max")
    @Expose
    private Double maximumTemperature;

    @SerializedName("pressure")
    @Expose
    private Double pressure;

    @SerializedName("humidity")
    @Expose
    private int humidity;

    public Double getTemperature() {
        return temperature;
    }

    public Double getMinimumTemperature() {
        return minimumTemperature;
    }

    public Double getMaximumTemperature() {
        return maximumTemperature;
    }

    public Double getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }
}
