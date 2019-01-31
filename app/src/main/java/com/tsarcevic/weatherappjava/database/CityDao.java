package com.tsarcevic.weatherappjava.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.tsarcevic.weatherappjava.model.local.City;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface CityDao {

    @Insert()
    void insertCity(City city);

    @Query("SELECT * from city_table ORDER BY id ASC")
    Single<List<City>> getAllCities();
}
