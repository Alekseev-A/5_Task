package com.example.weatherrx.domain

import com.example.weatherrx.data.OpenWeatherApi
import com.example.weatherrx.data.entities.City
import com.example.weatherrx.data.entities.CityWithDays
import com.example.weatherrx.data.entities.Day
import io.reactivex.rxjava3.core.Single
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DetailsRepository @Inject constructor(val api: OpenWeatherApi) {

    fun getForecastFor7Days(city: City): Single<CityWithDays> =
        api.getDetails(lat = city.lat, lon = city.lon)
            .map {
                CityWithDays(
                    city = city,
                    days = it.days.map { day ->
                        Day(
                            date = SimpleDateFormat(
                                "E",
                                Locale.getDefault()).format(day.dt * 1000),
                            dayOfWeek = SimpleDateFormat(
                                "d M",
                                Locale.getDefault()).format(day.dt * 1000),
                            icon = day.weather[0].icon,
                            pressure = day.pressure,
                            temp = day.temp.day,
                            windDeg = day.wind_deg,
                            windSpeed = day.wind_speed
                        )
                    }
                )
            }


}