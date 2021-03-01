package com.example.weatherrx.ui.cities

import android.os.Parcelable
import com.example.weatherrx.data.entities.City
import com.example.weatherrx.data.entities.CityForecast
import kotlinx.parcelize.Parcelize

@Parcelize
data class CityViewItem(
    val city: City,
    val forecast: CityForecast,
) : Parcelable