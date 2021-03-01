package com.example.weatherrx.data.entities

data class Day(
    val dayOfWeek: String,
    val date: String,
    val pressure: Int,
    val temp: Double,
    val icon: String,
    val windDeg: Int,
    val windSpeed: Double,
)