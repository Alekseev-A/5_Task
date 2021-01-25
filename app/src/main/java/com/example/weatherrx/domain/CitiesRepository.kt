package com.example.weatherrx.domain

import com.example.weatherrx.data.OpenWeatherApi
import com.example.weatherrx.data.entities.City
import com.example.weatherrx.data.entities.CityForecast
import com.example.weatherrx.data.entities.CityWithForecast
import com.example.weatherrx.data.store.RoomDB
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class CitiesRepository @Inject constructor(
    private val db: RoomDB,
    private val api: OpenWeatherApi,
) {
    val citiesWithForecast: Observable<List<CityWithForecast>> =
        db.cityWithForecast.getCityWithForecast()

    fun refreshCities(): Completable = db.cityDao
        .getCitiesObservable()
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
            db.cityForecastDao.updateAllForecasts(it
                .list
                .map { city ->
                    CityForecast(
                        dt = city.dt,
                        cityId = city.id,
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

    fun changeCityPosition(city: City, position: Int) = db.runInTransaction {
        val decrement = city.position < position

        val citiesForDeviationByOne =
            if (decrement) db.cityDao.getCitiesBetweenPositions(city.position + 1, position)
            else db.cityDao.getCitiesBetweenPositions(position, city.position - 1)
        if (citiesForDeviationByOne.any { it.id == city.id }) return@runInTransaction

        val changedCities = mutableListOf<City>()
        changedCities.add(City(city.id, position))
        val newPosition = { x: Int -> if (decrement) x - 1 else x + 1 }
        citiesForDeviationByOne.forEach {
            changedCities.add(
                City(
                    it.id,
                    newPosition(it.position)
                )
            )
        }
        db.cityDao.updateCities(changedCities)
    }

    fun deleteCity(city: City) = db.runInTransaction {
        val originalCities = db.cityDao.getCities()
        val position = originalCities
            .first { it.id == city.id }.position

        val newList = originalCities
            .takeWhile { city -> city.position > position }
            .map { city ->
                City(
                    city.id,
                    city.position - 1
                )
            }

        db.cityDao.deleteCity(city)
        db.cityDao.updateCities(newList)
    }
}