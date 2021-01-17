package com.example.weatherrx.data.entities.response

import com.google.gson.annotations.SerializedName

class ResponseDays(
    @SerializedName("daily")
    val days: List<Day>
) {
    class Day(
        val clouds: Int,
        val dew_point: Double,
        val dt: Int,
        val humidity: Int,
        val pop: Double,
        val pressure: Int,
        val snow: Double,
        val sunrise: Int,
        val sunset: Int,
        val temp: Temp,
        val weather: List<Weather>,
        val wind_deg: Int,
        val wind_speed: Double
    ) {
        class Weather(val icon: String)
        class Temp(val day: Double)
    }
}

