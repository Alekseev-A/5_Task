package com.example.weatherrx.di

import android.content.Context
import androidx.room.Room
import com.example.weatherrx.data.store.CityDao
import com.example.weatherrx.data.store.CityForecastDao
import com.example.weatherrx.data.store.CityWithForecastDao
import com.example.weatherrx.data.store.RoomDB
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class StoreModule {
    @Singleton
    @Provides
    fun providesRoomDB(
        context: Context,
        callback: RoomDB.Callback
    ): RoomDB =
        Room.databaseBuilder(
            context,
            RoomDB::class.java,
            "weather"
        )
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()

    @Provides
    fun provideCityDao(db: RoomDB): CityDao = db.getCityDao()

    @Provides
    fun provideCityForecastDao(db: RoomDB): CityForecastDao = db.getCityForecastDao()

    @Provides
    fun provideCityWithForecastDao(db: RoomDB): CityWithForecastDao = db.getCityWithForecast()
}