package com.tsarcevic.weatherappjava.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.tsarcevic.weatherappjava.base.BaseViewModel;
import com.tsarcevic.weatherappjava.interaction.WeatherInteraction;
import com.tsarcevic.weatherappjava.interaction.WeatherInteractionImpl;
import com.tsarcevic.weatherappjava.model.CurrentTemperatureResponse;
import com.tsarcevic.weatherappjava.model.WeatherResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WeatherInfoViewModel extends BaseViewModel {

    private WeatherInteraction weatherInteraction = new WeatherInteractionImpl();

    private MutableLiveData<WeatherResponse> weatherResponse = new MutableLiveData<>();
    private MutableLiveData<CurrentTemperatureResponse> currentWeatherResponse = new MutableLiveData<>();
    private MutableLiveData<Boolean> weatherResponseError = new MutableLiveData<>();


    public void getWeatherInformation(String city) {
        loading.setValue(true);
        addSubscription(weatherInteraction.getCityInfo(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleWeatherResponse, this::handleWeatherError));
    }

    private void handleWeatherResponse(WeatherResponse weatherResponse) {
        this.weatherResponse.setValue(weatherResponse);
        loading.setValue(false);
    }

    private void handleWeatherError(Throwable throwable) {
        weatherResponseError.setValue(true);
        loading.setValue(false);
    }

    public void getCurrentWeatherInformation(String city) {
        loading.setValue(true);
        addSubscription(weatherInteraction.getCurrentWeatherInfo(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleCurrentWeatherResponse, this::handleCurrentWeatherError));
    }

    private void handleCurrentWeatherResponse(CurrentTemperatureResponse weatherResponse) {
        currentWeatherResponse.setValue(weatherResponse);
        loading.setValue(false);
    }

    private void handleCurrentWeatherError(Throwable throwable) {
        weatherResponseError.setValue(true);
        loading.setValue(false);
    }

    public MutableLiveData<WeatherResponse> getWeatherResponse() {
        return weatherResponse;
    }


    public MutableLiveData<CurrentTemperatureResponse> getCurrentWeatherResponse() {
        return currentWeatherResponse;
    }

    public MutableLiveData<Boolean> getWeatherResponseError() {
        return weatherResponseError;
    }
}
