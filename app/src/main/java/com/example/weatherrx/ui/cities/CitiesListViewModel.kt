package com.example.weatherrx.ui.cities

import androidx.lifecycle.ViewModel
import com.example.weatherrx.data.entities.CityWithForecast
import com.example.weatherrx.domain.CitiesRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

class CitiesListViewModel @Inject constructor(
    private val citiesRepository: CitiesRepository,
) : ViewModel() {
    private val disposeBag = CompositeDisposable()

    val citiesBehaviorSubject: BehaviorSubject<List<CityViewItem>> =
        BehaviorSubject.createDefault(listOf())

    val isUpdatingBehaviorSubject: BehaviorSubject<Boolean> =
        BehaviorSubject.createDefault(false)

    val forShowingPublishSubject: PublishSubject<String> =
        PublishSubject.create()

    init {
        disposeBag.add(
            citiesRepository
                .citiesWithForecast
                .subscribe({
                    citiesBehaviorSubject.onNext(convertToViewItem(it))
                }, {
                    forShowingPublishSubject.onNext(it.localizedMessage)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposeBag.dispose()
    }

    fun update() {
        isUpdatingBehaviorSubject.onNext(true)
        disposeBag.add(
            citiesRepository
                .refreshCities()
                .subscribe({
                    isUpdatingBehaviorSubject.onNext(false)
                }, {
                    forShowingPublishSubject.onNext(it.localizedMessage)
                    isUpdatingBehaviorSubject.onNext(false)
                })
        )
    }

    private fun convertToViewItem(citiesWithForecast: List<CityWithForecast>) =
        citiesWithForecast.mapNotNull {
            if (it.forecast == null) return@mapNotNull null
            else CityViewItem(
                it.city,
                it.forecast
            )
        }


    fun onCityClick(cityViewItem: CityViewItem) {

    }

    fun onCityLongClick(cityViewItem: CityViewItem) {

    }

    fun selectCityForDelete(city: CityViewItem) {
        disposeBag.add(
            Completable.fromAction {
                citiesRepository.deleteCity(city.city)
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    fun selectCitiesForSwapPositions(first: CityViewItem, second: CityViewItem) {
        disposeBag.add(
            Completable.fromAction {
                citiesRepository.swapPositionsForCities(first.city, second.city)
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }
}