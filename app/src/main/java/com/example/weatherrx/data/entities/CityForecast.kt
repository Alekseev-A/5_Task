package com.example.weatherrx.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "city_forecast")
data class CityForecast(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val day: Int = 0,
    val cityId: Int,
    val name: String,
    val icon: String,
    val pressure: Int,
    val temp: Double,
    val windDeg: Int,
    val windSpeed: Int
)