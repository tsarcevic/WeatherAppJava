package com.tsarcevic.weatherappjava.interaction.remote;

import com.tsarcevic.weatherappjava.model.remote.CurrentTemperatureResponse;
import com.tsarcevic.weatherappjava.model.remote.WeatherResponse;

import io.reactivex.Single;

public interface WeatherInteraction {
    Single<WeatherResponse> getCityInfo(String city);

    Single<CurrentTemperatureResponse> getCurrentWeatherInfo(String city);
}
