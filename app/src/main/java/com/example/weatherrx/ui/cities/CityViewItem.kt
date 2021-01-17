package com.example.weatherrx.ui.cities

import com.example.weatherrx.data.entities.City
import com.example.weatherrx.data.entities.CityForecast

data class CityViewItem(
    val city: City,
    val forecast: CityForecast
)