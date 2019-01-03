package com.tsarcevic.weatherappjava.interaction;

import com.tsarcevic.weatherappjava.App;
import com.tsarcevic.weatherappjava.Constants;
import com.tsarcevic.weatherappjava.model.WeatherResponse;
import com.tsarcevic.weatherappjava.network.RetrofitUtil;

import io.reactivex.Single;

public class WeatherInteractionImpl implements WeatherInteraction {

    @Override
    public Single<WeatherResponse> getCityInfo(String city) {
        return App.getRetrofitInstance().getWeatherInfo(city, Constants.API_KEY, Constants.METRIC);
    }
}
