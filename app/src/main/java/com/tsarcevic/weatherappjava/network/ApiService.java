package com.tsarcevic.weatherappjava.network;

import com.tsarcevic.weatherappjava.model.CurrentTemperatureResponse;
import com.tsarcevic.weatherappjava.model.WeatherResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("forecast")
    Single<WeatherResponse> getWeatherInfo(@Query("q") String city, @Query("appid") String apiKey, @Query("units") String units);

    @GET("weather")
    Single<CurrentTemperatureResponse> getCurrentWeatherInfo(@Query("q") String city, @Query("appid") String apiKey, @Query("units") String units);
}
