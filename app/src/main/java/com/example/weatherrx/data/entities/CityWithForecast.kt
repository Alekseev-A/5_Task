package com.example.weatherrx.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class CityWithForecast(
    @Embedded val city: City,
    @Relation(
        parentColumn = "id",
        entityColumn = "cityId"
    ) val forecast: CityForecast
)