package com.tsarcevic.weatherappjava;

import android.app.Application;

import com.tsarcevic.weatherappjava.network.ApiService;
import com.tsarcevic.weatherappjava.network.RetrofitUtil;

import retrofit2.Retrofit;

public class App extends Application {

    Retrofit retrofit;
    static ApiService apiService;

    @Override
    public void onCreate() {
        super.onCreate();
        retrofit = RetrofitUtil.createRetrofit();
        apiService = retrofit.create(ApiService.class);
    }

    public static ApiService getRetrofitInstance() {
        return apiService;
    }
}
