package com.example.weatherrx.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherrx.data.store.*
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
    fun provideDetailsDao(db: RoomDB): CityForecastDao = db.getCityForecastDao()
}