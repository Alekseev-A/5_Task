package com.example.weatherrx.data.store

import androidx.room.*
import com.example.weatherrx.data.entities.CityForecast
import io.reactivex.rxjava3.core.Single

@Dao
abstract class CityForecastDao {

    @Query("DELETE FROM city_forecast")
    abstract fun deleteAllCityForecast()

    @Insert
    abstract fun insertCitiesForecast(forecasts: List<CityForecast>)

    @Insert
    abstract fun insertCityForecast(forecast: CityForecast)

    @Transaction
    open fun updateAllForecasts(forecasts: List<CityForecast>) {
        deleteAllCityForecast()
        insertCitiesForecast(forecasts)
    }
}