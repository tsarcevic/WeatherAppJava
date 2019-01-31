package com.tsarcevic.weatherappjava.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.tsarcevic.weatherappjava.model.local.City;

@Database(entities = {City.class}, version = 1, exportSchema = false)
public abstract class CityRoomDatabase extends RoomDatabase {

    public abstract CityDao cityDao();

    private static CityRoomDatabase cityRoomDatabase;

    public static CityRoomDatabase getDatabase(final Context context) {
        if (cityRoomDatabase == null) {
            cityRoomDatabase = Room.databaseBuilder(context.getApplicationContext(), CityRoomDatabase.class, "city_database")
                    .allowMainThreadQueries()
                    .build();
        }

        return cityRoomDatabase;
    }
}
