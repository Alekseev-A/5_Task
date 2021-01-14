package com.example.weatherrx.data.store

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherrx.data.entities.City
import com.example.weatherrx.data.entities.Day

@Database(
    entities = [
        City::class,
        Day::class,
        City.Weather::class,
        Day.Weather::class
    ],
    version = 1
)
abstract class RoomDB : RoomDatabase() {
    abstract fun getCityDao(): CityDao
    abstract fun getDayDao(): DayDao
}

