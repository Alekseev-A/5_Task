package com.example.weatherrx.domain

import com.example.weatherrx.data.OpenWeatherApi
import com.example.weatherrx.data.entities.CityForecast
import com.example.weatherrx.data.store.CityDao
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class FindRepository @Inject constructor(
    private val cityDao: CityDao,
    private val api: OpenWeatherApi,
) {
    fun findByCityName(name: String): Single<CityForecast> {
        return api.getCity(name = name).map { city ->
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
        }
    }

    fun addCityInDB(id: Int): Completable =
        Completable.fromAction {
            cityDao.insertCityWithCurrentPosition(id)
        }

}