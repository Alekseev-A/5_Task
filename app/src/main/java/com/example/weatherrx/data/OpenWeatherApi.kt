package com.example.weatherrx.data

import com.example.weatherrx.data.entities.response.ResponseCities
import com.example.weatherrx.data.entities.response.ResponseCity
import com.example.weatherrx.data.entities.response.ResponseDays
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {
    private companion object {
        const val UNITS = "metric"
        const val ALL_EXCLUDES = "current,minutely,hourly,daily,alerts"
        const val API_KEY = "a1a22b5d6a4afba64260308e3533de9d"
    }

    @GET("weather")
    fun getCity(
        @Query("q") name: String,
//        @Query("lang") language: String,
        @Query("appid") key: String = API_KEY,
        @Query("units") units: String = UNITS,
    ): Single<ResponseCity>

    @GET("onecall")
    fun getDetails(
        @Query("id") cityId: Int,
//        @Query("lang") language: String,
        @Query("units") units: String = UNITS,
        @Query("appid") key: String = API_KEY,
        @Query("exclude") exclude: String = "minutely,hourly,alerts"
    ): Single<ResponseDays>

    @GET("group")
    fun getCitiesForecast(
        @Query("id") cityIds: String,
//        @Query("lang") language: String,
        @Query("appid") key: String = API_KEY,
        @Query("units") units: String = UNITS
    ): Single<ResponseCities>
}