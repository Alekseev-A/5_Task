package com.example.weatherrx.data.store

import android.util.Log
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
    abstract val cityDao: CityDao
    abstract val cityForecastDao: CityForecastDao
    abstract val cityWithForecast: CityWithForecastDao

    class Callback @Inject constructor(
        private val database: Provider<CityDao>
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            Thread {
                val dao = database.get()
                dao.insertCity(City(id = 625144, position = 1, lat = 53.9, lon = 27.5667, name = "Minsk"))
                dao.insertCity(City(id = 524901, position = 2, lat = 55.7522, lon = 37.6156, name = "Moscow"))
            }.start()
        }
    }
}

