package com.example.weatherrx.data.store

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.weatherrx.data.entities.CityWithForecast
import io.reactivex.rxjava3.core.Observable

@Dao
interface CityWithForecastDao {

    @Transaction
    @Query("SELECT * FROM city ORDER BY city.position DESC")
    fun getCityWithForecast(): Observable<List<CityWithForecast>>
}