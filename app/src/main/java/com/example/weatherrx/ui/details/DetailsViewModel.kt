package com.example.weatherrx.ui.details

import androidx.lifecycle.ViewModel
import com.example.weatherrx.data.entities.City
import com.example.weatherrx.data.entities.CityWithDays
import com.example.weatherrx.domain.DetailsRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class DetailsViewModel @Inject constructor(private val detailsRepository: DetailsRepository) : ViewModel() {
    fun getDays(city: City) : Observable<CityWithDays> = detailsRepository.getForecastFor7Days(city).toObservable()

}