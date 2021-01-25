package com.example.weatherrx.data.entities.response

class ResponseCity(
    val coord: Coord,
    val dt: Long,
    val id: Long,
    val main: Main,
    val name: String,
    val weather: List<Weather>,
    val wind: Wind
) {

    class Coord(
        val lon: Double,
        val lat: Double,
    )

    class Main(
        val pressure: Int,
        val temp: Double,
    )

    class Weather(
        val icon: String,
    )

    class Wind(
        val deg: Int,
        val speed: Double
    )
}

