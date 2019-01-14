package com.tsarcevic.weatherappjava.model;

public class WeatherFinal {

    private CurrentTemperatureResponse currentTemperatureResponse;

    private WeatherResponse weatherResponse;

    public WeatherFinal(CurrentTemperatureResponse currentTemperatureResponse, WeatherResponse weatherResponse) {
        this.currentTemperatureResponse = currentTemperatureResponse;
        this.weatherResponse = weatherResponse;
    }

    public CurrentTemperatureResponse getCurrentTemperatureResponse() {
        return currentTemperatureResponse;
    }

    public void setCurrentTemperatureResponse(CurrentTemperatureResponse currentTemperatureResponse) {
        this.currentTemperatureResponse = currentTemperatureResponse;
    }

    public WeatherResponse getWeatherResponse() {
        return weatherResponse;
    }

    public void setWeatherResponse(WeatherResponse weatherResponse) {
        this.weatherResponse = weatherResponse;
    }
}
