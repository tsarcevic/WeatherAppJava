package com.tsarcevic.weatherappjava.interaction.remote;

import com.tsarcevic.weatherappjava.App;
import com.tsarcevic.weatherappjava.Constants;
import com.tsarcevic.weatherappjava.model.remote.CurrentTemperatureResponse;
import com.tsarcevic.weatherappjava.model.remote.WeatherResponse;

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
