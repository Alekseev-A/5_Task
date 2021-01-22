package com.example.weatherrx.domain

import com.example.weatherrx.data.OpenWeatherApi
import com.example.weatherrx.data.entities.City
import com.example.weatherrx.data.entities.CityForecast
import com.example.weatherrx.data.entities.CityWithForecast
import com.example.weatherrx.data.store.CityDao
import com.example.weatherrx.data.store.CityForecastDao
import com.example.weatherrx.data.store.CityWithForecastDao
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class CitiesRepository @Inject constructor(
    private val cityDao: CityDao,
    private val cityForecastDao: CityForecastDao,
    private val cityWithForecastDao: CityWithForecastDao,
    private val api: OpenWeatherApi
) {

    fun refreshCities(): Completable = cityDao
        .getCities()
        .firstOrError()
        .flatMap { list ->
            val idsBuilder = StringBuilder()
            for (city in list) {
                idsBuilder.append(city.id)
                idsBuilder.append(",")
            }
            return@flatMap api.getCitiesForecast(cityIds = idsBuilder.toString())
        }
        .doOnSuccess {
            cityForecastDao.updateAllForecasts(it
                .list
                .map { city ->
                    CityForecast(
                        dt = city.dt,
                        cityId = city.id ,
                        icon = city.weather[0].icon,
                        windDeg = city.wind.deg,
                        windSpeed = city.wind.speed,
                        name = city.name,
                        pressure = city.main.pressure,
                        temp = city.main.temp
                    )
                })
        }
        .ignoreElement()

    val citiesWithForecast: Observable<List<CityWithForecast>> =
        cityWithForecastDao.getCityWithForecast()

    fun swapPositionsForCities(first: City, second: City) {
        cityDao.swapPositions(first,second)
    }

    fun deleteCity(city: City) {
        cityDao.deleteCity(city)
    }
}