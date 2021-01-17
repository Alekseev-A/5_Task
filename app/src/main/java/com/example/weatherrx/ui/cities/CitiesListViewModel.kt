package com.example.weatherrx.ui.cities

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.weatherrx.R
import com.example.weatherrx.data.OpenWeatherApi
import com.example.weatherrx.data.entities.City
import com.example.weatherrx.data.entities.CityForecast
import com.example.weatherrx.data.store.CityDao
import javax.inject.Inject

class CitiesListViewModel @Inject constructor(
    cityDao: CityDao,
    api: OpenWeatherApi,
    context: Context
) : ViewModel() {

    val citiesObservable = cityDao.getCities().flatMap { list ->
        var ids = ""
        list.forEachIndexed { index, city ->
            ids += city.cityId
            if (index != list.size - 1) ids += ","
        }
        return@flatMap api.getCities(
            cityIds = ids,
            language = context.getString(R.string.language),
            key = context.getString(R.string.api_key)
        )
    }
        .map { response ->
        val result = mutableListOf<CityViewItem>()

        response.list.forEach {
            result.add(
                CityViewItem(
                    City(cityId = it.id),
                    CityForecast(
                        name = it.name,
                        icon = it.weather[0].icon,
                        pressure = it.main.pressure,
                        temp = it.main.temp,
                        windDeg = it.wind.deg,
                        windSpeed = it.wind.speed,
                        cityId = it.id
                    )
                )
            )
        }
        return@map result
    }
}