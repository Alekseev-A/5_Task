package com.example.weatherrx.ui.find

import androidx.lifecycle.ViewModel
import com.example.weatherrx.R
import com.example.weatherrx.data.entities.CityForecast
import com.example.weatherrx.domain.FindRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FindViewModel @Inject constructor(private val repository: FindRepository) : ViewModel() {

    val foundCityBehaviourSubject: BehaviorSubject<CityForecast> =
        BehaviorSubject.create()
    val onBackPressedPublishSubject: PublishSubject<Boolean> =
        PublishSubject.create()
    val forShowingPublishSubject: PublishSubject<Int> =
        PublishSubject.create()

    private val queryBehaviorSubject = BehaviorSubject.createDefault("")

    private val disposeBag = CompositeDisposable()

    init {
        disposeBag.add(
            queryBehaviorSubject
                .debounce(300, TimeUnit.MILLISECONDS)
                .switchMapSingle { query ->
                    repository
                        .findByCityName(query)
                        .onErrorResumeWith {}
                }
                .subscribe({
                    foundCityBehaviourSubject.onNext(it)
                }, {

                })
        )
    }

    fun setNameForSearch(name: String) {
        queryBehaviorSubject.onNext(name)
    }

    override fun onCleared() {
        super.onCleared()
        disposeBag.dispose()
    }

    fun clickOnCity(id: Int) {
        disposeBag.add(
            repository
                .addCityInDB(id)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    onBackPressedPublishSubject.onNext(true)
                }, {
                    when (it.javaClass.simpleName) {
                        "SQLiteConstraintException" -> forShowingPublishSubject.onNext(R.string.not_unique_id_error)
                    }
                })
        )
    }
}