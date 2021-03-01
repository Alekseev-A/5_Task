package com.example.weatherrx.domain

import com.example.weatherrx.data.OpenWeatherApi
import com.example.weatherrx.data.entities.City
import com.example.weatherrx.data.entities.CityForecast
import com.example.weatherrx.data.entities.CityWithForecast
import com.example.weatherrx.data.store.RoomDB
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class FindRepository @Inject constructor(
    private val db: RoomDB,
    private val api: OpenWeatherApi,
) {
    fun getCityWithForecastByCityName(name: String): Single<CityWithForecast> {
        return api
            .getCity(name = name)
            .map { city ->
                CityWithForecast(
                    City(id = city.id,
                        position = db.cityDao.getMaxPosition() + 1,
                        lat = city.coord.lat,
                        lon = city.coord.lon,
                        name = city.name
                    ),
                    CityForecast(
                        dt = city.dt,
                        cityId = city.id,
                        icon = city.weather[0].icon,
                        windDeg = city.wind.deg,
                        windSpeed = city.wind.speed,
                        pressure = city.main.pressure,
                        temp = city.main.temp
                    )
                )
            }
    }

    fun addCityInDB(cityWithForecast: CityWithForecast): Completable =
        Completable.fromAction {
            db.cityDao.insertCity(cityWithForecast.city)
            db.cityForecastDao.insertCityForecast(cityWithForecast.forecast!!)
        }

}