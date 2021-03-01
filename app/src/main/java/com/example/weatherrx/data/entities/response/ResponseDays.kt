package com.example.weatherrx.data.entities.response

import com.google.gson.annotations.SerializedName

class ResponseDays(
    @SerializedName("daily")
    val days: List<ResponseDay>
) {
    class ResponseDay(
        val dt: Long,
        val pressure: Int,
        val temp: ResponseTemp,
        val weather: List<ResponseWeather>,
        val wind_deg: Int,
        val wind_speed: Double
    ) {
        class ResponseWeather(val icon: String)
        class ResponseTemp(val day: Double)
    }
}

