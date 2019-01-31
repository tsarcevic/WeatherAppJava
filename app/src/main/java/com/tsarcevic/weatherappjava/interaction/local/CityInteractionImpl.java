package com.tsarcevic.weatherappjava.interaction.local;

import android.app.Application;

import com.tsarcevic.weatherappjava.database.CityRoomDatabase;
import com.tsarcevic.weatherappjava.model.local.City;

import java.util.List;

import io.reactivex.Single;

public class CityInteractionImpl implements CityInteraction {

    private final CityRoomDatabase database;

    public CityInteractionImpl(Application application) {
        database = CityRoomDatabase.getDatabase(application.getApplicationContext());
    }

    public Single<List<City>> getAllCities() {
        return database.cityDao().getAllCities();
    }

    @Override
    public void saveCity(City city) {
        database.cityDao().insertCity(city);
    }
}
