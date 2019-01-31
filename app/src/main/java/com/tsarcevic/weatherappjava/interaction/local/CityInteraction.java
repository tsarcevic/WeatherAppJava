package com.tsarcevic.weatherappjava.interaction.local;

import com.tsarcevic.weatherappjava.model.local.City;

import java.util.List;

import io.reactivex.Single;

public interface CityInteraction {

    Single<List<City>> getAllCities();

    void saveCity(City city);
}
