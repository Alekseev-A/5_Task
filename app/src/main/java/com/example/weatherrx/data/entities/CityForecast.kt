package com.example.weatherrx.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "city_forecast",
    foreignKeys = [ForeignKey(
        entity = City::class,
        parentColumns = ["id"],
        childColumns = ["cityId"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class CityForecast(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val day: Int = 0,
    val dt: Long,
    val cityId: Int,
    val name: String,
    val icon: String,
    val pressure: Int,
    val temp: Double,
    val windDeg: Int,
    val windSpeed: Double
)