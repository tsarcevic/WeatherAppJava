package com.tsarcevic.weatherappjava.util;

import com.google.gson.Gson;
import com.tsarcevic.weatherappjava.model.remote.WeatherError;

import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class RetrofitErrorResponseUtil {

    public static WeatherError parseQueryThrowableAsHttpException(Throwable t) {
        Gson gson = new Gson();
        if (t instanceof HttpException) {
            ResponseBody body = ((HttpException) t).response().errorBody();
            try {
                return gson.fromJson(body.string(), WeatherError.class);
            } catch (Exception e) {
                return new WeatherError();
            }
        }
        return new WeatherError();
    }
}
