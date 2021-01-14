package com.example.weatherrx.di

import android.content.Context
import androidx.room.Room
import com.example.weatherrx.data.store.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class StoreModule {
    @Singleton
    @Provides
    fun providesRoomDB(context: Context): RoomDB =
        Room.databaseBuilder(
            context,
            RoomDB::class.java,
            "weather"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideCityDao(db: RoomDB): CityDao = db.getCityDao()

    @Provides
    fun provideDetailsDao(db: RoomDB): DayDao = db.getDayDao()
}