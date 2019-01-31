package com.tsarcevic.weatherappjava.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import com.tsarcevic.weatherappjava.base.BaseViewModel;
import com.tsarcevic.weatherappjava.interaction.local.CityInteraction;
import com.tsarcevic.weatherappjava.interaction.local.CityInteractionImpl;
import com.tsarcevic.weatherappjava.model.local.City;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CityViewModel extends BaseViewModel {

    private CityInteraction cityInteraction;

    private MutableLiveData<List<City>> cityListResponse = new MutableLiveData<>();
    private MutableLiveData<Boolean> cityListError = new MutableLiveData<>();

    private MutableLiveData<Long> citySaveResponse = new MutableLiveData<>();
    private MutableLiveData<Boolean> citySaveError = new MutableLiveData<>();

    public CityViewModel(Application application) {
        this.cityInteraction = new CityInteractionImpl(application);
    }

    public void getAllCities() {
        addSubscription(cityInteraction.getAllCities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleCityResponse, this::handleCityError));
    }

    private void handleCityResponse(List<City> cities) {
        cityListResponse.setValue(cities);
    }

    private void handleCityError(Throwable throwable) {
        cityListError.setValue(true);
    }

    public void saveCity(String city) {
        cityInteraction.saveCity(new City(city));
    }

    public MutableLiveData<List<City>> getCityListResponse() {
        return cityListResponse;
    }

    public MutableLiveData<Boolean> getCityListError() {
        return cityListError;
    }
}
