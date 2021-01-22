package com.example.weatherrx.data.store

import androidx.room.*
import com.example.weatherrx.data.entities.CityForecast
import io.reactivex.rxjava3.core.Single

@Dao
abstract class CityForecastDao {
    @Query("SELECT * FROM city_forecast where cityId=:cityId")
    abstract fun getCityForecast(cityId: Int): Single<CityForecast>

    @Query("DELETE FROM city_forecast")
    abstract fun deleteAllCityForecast()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertCitiesForecast(forecasts: List<CityForecast>)

    @Transaction
    open fun updateAllForecasts(forecasts: List<CityForecast>) {
        deleteAllCityForecast()
        insertCitiesForecast(forecasts)
    }
}