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
    fun citiesWithForecast(): Observable<List<CityWithForecast>> =
        db.cityWithForecast
            .getCityWithForecast()
            .onErrorResumeNext {
                refreshCities().andThen(citiesWithForecast())
            }
            .retry()

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
                        pressure = city.main.pressure,
                        temp = city.main.temp
                    )
                })
        }
        .ignoreElement()

    fun changeCityPosition(city: City, position: Int) = db.runInTransaction {
        val isRises = city.position < position

        val citiesForDeviationByOne =
            if (isRises) db.cityDao.getCitiesBetweenPositions(city.position + 1, position)
            else db.cityDao.getCitiesBetweenPositions(position, city.position - 1)

        if (citiesForDeviationByOne.any { it.id == city.id }) return@runInTransaction

        val changedCities = mutableListOf<City>()
        changedCities.add(
            city.copy(position = position)
        )
        val newPosition = { x: Int -> if (isRises) x - 1 else x + 1 }
        citiesForDeviationByOne.forEach {
            changedCities.add(
                it.copy(position = newPosition(it.position))
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
            .map {
                it.copy(position = it.position - 1)
            }

        db.cityDao.deleteCity(city)
        db.cityDao.updateCities(newList)
    }
}