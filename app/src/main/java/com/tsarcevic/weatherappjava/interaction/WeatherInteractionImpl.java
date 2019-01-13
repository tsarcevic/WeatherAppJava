package com.tsarcevic.weatherappjava.interaction;

import com.tsarcevic.weatherappjava.App;
import com.tsarcevic.weatherappjava.Constants;
import com.tsarcevic.weatherappjava.model.CurrentTemperatureResponse;
import com.tsarcevic.weatherappjava.model.WeatherResponse;

import io.reactivex.Single;

public class WeatherInteractionImpl implements WeatherInteraction {

    @Override
    public Single<WeatherResponse> getCityInfo(String city) {
        return App.getRetrofitInstance().getWeatherInfo(city, Constants.API_KEY, Constants.METRIC);
    }

    @Override
    public Single<CurrentTemperatureResponse> getCurrentWeatherInfo(String city) {
        return App.getRetrofitInstance().getCurrentWeatherInfo(city, Constants.API_KEY, Constants.METRIC);
    }
}
