package com.tsarcevic.weatherappjava.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.tsarcevic.weatherappjava.model.local.City;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCity(City city);

    @Query("SELECT * from city_table")
    Single<List<City>> getAllCities();
}
