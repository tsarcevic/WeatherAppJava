package com.tsarcevic.weatherappjava.interaction;

import com.tsarcevic.weatherappjava.model.WeatherResponse;

import io.reactivex.Single;

public interface WeatherInteraction {
    Single<WeatherResponse> getCityInfo(String city);
}