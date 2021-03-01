package com.example.weatherrx.data.entities.response

class ResponseCity(
    val coord: ResponseCoord,
    val dt: Long,
    val id: Long,
    val main: ResponseMain,
    val name: String,
    val weather: List<ResponseWeather>,
    val wind: ResponseWind
) {

    class ResponseCoord(
        val lon: Double,
        val lat: Double,
    )

    class ResponseMain(
        val pressure: Int,
        val temp: Double,
    )

    class ResponseWeather(
        val icon: String,
    )

    class ResponseWind(
        val deg: Int,
        val speed: Double
    )
}

