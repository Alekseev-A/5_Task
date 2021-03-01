package com.example.weatherrx.di

import com.example.weatherrx.ui.cities.CitiesListFragment
import com.example.weatherrx.ui.details.DetailsFragment
import com.example.weatherrx.ui.find.FindFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RemoteModule::class, StoreModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(citiesListFragment: CitiesListFragment)
    fun inject(findFragment: FindFragment)
    fun inject(detailsFragment: DetailsFragment)

}