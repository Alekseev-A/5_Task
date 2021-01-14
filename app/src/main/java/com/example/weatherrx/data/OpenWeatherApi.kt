package com.example.weatherrx.data

import com.example.weatherrx.data.entities.City
import com.example.weatherrx.data.entities.response.ResponseDays
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {
    private companion object {
        const val UNITS = "metric"
        const val ALL_EXCLUDES = "current,minutely,hourly,daily,alerts"
    }

    @GET("weather")
    fun getCity(
        @Query("name") name: String,
        @Query("appid") key: String,
        @Query("lang") language: String,
        @Query("units") units: String = UNITS,
    ): Observable<City>

    @GET("onecall")
    fun getDetails(
        @Query("id") cityId: Int,
        @Query("appid") key: String,
        @Query("lang") language: String,
        @Query("units") units: String = UNITS,
        @Query("exclude") exclude: String = "minutely,hourly,alerts"
    ): Observable<ResponseDays>

    @GET("group")
    fun getCities(
        @Query("id") cityIds: String,
        @Query("appid") key: String,
        @Query("lang") language: String,
        @Query("units") units: String = UNITS
    ): Observable<List<City>>
}