package com.example.weatherrx.di

import com.example.weatherrx.ui.cities.CitiesListFragment
import com.example.weatherrx.ui.cities.CitiesListViewModel
import com.example.weatherrx.ui.details.DetailsViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RemoteModule::class, StoreModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(citiesListFragment: CitiesListFragment)
}