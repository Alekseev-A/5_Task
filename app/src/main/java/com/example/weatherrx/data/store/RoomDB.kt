package com.example.weatherrx.data.store

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.weatherrx.data.entities.City
import com.example.weatherrx.data.entities.CityForecast
import javax.inject.Inject
import javax.inject.Provider

@Database(
    entities = [
        City::class,
        CityForecast::class
    ],
    version = 1
)
abstract class RoomDB : RoomDatabase() {
    abstract fun getCityDao(): CityDao
    abstract fun getCityForecastDao(): CityForecastDao

    class Callback @Inject constructor(
        private val database: Provider<CityDao>
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            Thread{
                val dao = database.get()
                dao.insertCity(City(cityId = 625144))
                dao.insertCity(City(cityId = 524901))
            }.start()
        }
    }
}

