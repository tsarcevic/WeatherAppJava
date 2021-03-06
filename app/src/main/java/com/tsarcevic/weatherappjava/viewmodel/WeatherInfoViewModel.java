package com.tsarcevic.weatherappjava.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.tsarcevic.weatherappjava.base.BaseViewModel;
import com.tsarcevic.weatherappjava.interaction.remote.WeatherInteraction;
import com.tsarcevic.weatherappjava.interaction.remote.WeatherInteractionImpl;
import com.tsarcevic.weatherappjava.model.remote.CurrentTemperatureResponse;
import com.tsarcevic.weatherappjava.model.remote.WeatherError;
import com.tsarcevic.weatherappjava.model.remote.WeatherFinal;
import com.tsarcevic.weatherappjava.model.remote.WeatherResponse;
import com.tsarcevic.weatherappjava.util.RetrofitErrorResponseUtil;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WeatherInfoViewModel extends BaseViewModel {

    private WeatherInteraction weatherInteraction = new WeatherInteractionImpl();

    private MutableLiveData<WeatherResponse> weatherResponse = new MutableLiveData<>();
    private MutableLiveData<CurrentTemperatureResponse> currentWeatherResponse = new MutableLiveData<>();
    private MutableLiveData<WeatherError> weatherResponseError = new MutableLiveData<>();

    public void getFullWeatherInformation(String city) {
        // TODO: 14.1.2019. do Ivan's solution
        loading.setValue(true);
        addSubscription(Single.zip(weatherInteraction.getCityInfo(city), weatherInteraction.getCurrentWeatherInfo(city),
                (cityInfoResponse, currentWeatherResponse) -> new WeatherFinal(currentWeatherResponse, cityInfoResponse))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleFulLWeatherInfoResponse, this::handleFullWeatherInfoResponseError));
    }

    private void handleFulLWeatherInfoResponse(WeatherFinal weatherFinal) {
        loading.setValue(false);
        weatherResponse.setValue(weatherFinal.getWeatherResponse());
        currentWeatherResponse.setValue(weatherFinal.getCurrentTemperatureResponse());
    }

    private void handleFullWeatherInfoResponseError(Throwable throwable) {
        loading.setValue(false);
        WeatherError error = RetrofitErrorResponseUtil.parseQueryThrowableAsHttpException(throwable);
        weatherResponseError.setValue(error);
    }


   /* public void getWeatherInformation(String city) {
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
    }*/

    public MutableLiveData<WeatherResponse> getWeatherResponse() {
        return weatherResponse;
    }


    public MutableLiveData<CurrentTemperatureResponse> getCurrentWeatherResponse() {
        return currentWeatherResponse;
    }

    public MutableLiveData<WeatherError> getWeatherResponseError() {
        return weatherResponseError;
    }
}
